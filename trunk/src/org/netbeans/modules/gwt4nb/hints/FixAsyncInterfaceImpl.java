/*
 * The contents of this file are subject to the terms of the Common Development
 * and Distribution License (the License). You may not use this file except in
 * compliance with the License.
 *
 * You can obtain a copy of the License at http://www.netbeans.org/cddl.html
 * or http://www.netbeans.org/cddl.txt.
 *
 * When distributing Covered Code, include this CDDL Header Notice in each file
 * and include the License file at http://www.netbeans.org/cddl.txt.
 * If applicable, add the following below the CDDL Header, with the fields
 * enclosed by brackets [] replaced by your own identifying information:
 * "Portions Copyrighted [year] [name of copyright owner]"
 *
 * Portions Copyrighted 2007 Sun Microsystems, Inc.
 */
package org.netbeans.modules.gwt4nb.hints;

import com.sun.source.tree.BlockTree;
import com.sun.source.tree.ClassTree;
import com.sun.source.tree.CompilationUnitTree;
import com.sun.source.tree.ExpressionTree;
import com.sun.source.tree.MethodTree;
import com.sun.source.tree.ModifiersTree;
import com.sun.source.tree.Tree;
import com.sun.source.tree.TypeParameterTree;
import com.sun.source.tree.VariableTree;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.Name;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import org.netbeans.api.java.source.CancellableTask;
import org.netbeans.api.java.source.ElementHandle;
import org.netbeans.api.java.source.JavaSource;
import org.netbeans.api.java.source.JavaSource.Phase;
import org.netbeans.api.java.source.TreeMaker;
import org.netbeans.api.java.source.WorkingCopy;
import org.netbeans.api.project.FileOwnerQuery;
import org.netbeans.api.project.Project;
import org.netbeans.modules.gwt4nb.GWTProjectInfo;
import org.netbeans.modules.gwt4nb.Version;
import org.netbeans.modules.gwt4nb.services.ServiceClassSetUtils;
import org.netbeans.spi.editor.hints.ChangeInfo;
import org.netbeans.spi.editor.hints.Fix;
import org.openide.filesystems.FileObject;
import org.openide.util.NbBundle;

/**
 *
 * @author prem
 */
final class FixAsyncInterfaceImpl implements Fix {

    private static final Version V15 = new Version("1.5"); // NOI18N
    private FileObject asyncFo;
    private FileObject syncFo;
    private Version gwtVersion;
    private ExecutableElement serviceMethod;
    @SuppressWarnings("rawtypes")
    private List<ElementHandle> methods;
    private ElementHandle serviceMethodHandle;
    private List<ElementHandle> unmatchedMethods;

    @SuppressWarnings("rawtypes")
    public FixAsyncInterfaceImpl(
            final FileObject asyncFo,
            final FileObject syncFo,
            final List<ElementHandle> methods,
            final List<ElementHandle> unmatchedMethods) {

        this.asyncFo = asyncFo;
        this.syncFo = syncFo;
        this.methods = methods;
        this.unmatchedMethods = unmatchedMethods;

        final Project owner = FileOwnerQuery.getOwner(asyncFo);
        this.gwtVersion = GWTProjectInfo.get(
                owner).getGWTVersion();
    }

    protected Set<Modifier> getMethodModifiers(
            final ExecutableElement serviceMethod) {
        final Set<Modifier> modifiers = EnumSet.copyOf(serviceMethod.
                getModifiers());
        modifiers.remove(Modifier.ABSTRACT);

        return modifiers;
    }

    private Tree getReturnTypeTree(final TreeMaker make, final TypeMirror type) {
        final TypeKind kind = type.getKind();

        if (kind.isPrimitive()) {
            return make.Identifier(JavaModelUtils.getNonPrimitiveTypeName(
                    type, false));
        } else if (kind == TypeKind.VOID) {
            return make.Identifier("Void"); // NOI18N
        } else {
            return make.Type(type);
        }
    }

    protected Tree getAsyncCallback(final TreeMaker make,
            final ExecutableElement serviceMethod) {
        final TypeMirror type = serviceMethod.getReturnType();

        if (gwtVersion.compareTo(V15) < 0) {
            return make.Identifier("AsyncCallback"); // NOI18N
        } else {
            return make.ParameterizedType(
                    make.Identifier("AsyncCallback"), // NOI18N
                    Collections.singletonList(getReturnTypeTree(make, type)));
        }
    }

    protected MethodTree createMethodTree(final TreeMaker make) {
        final ModifiersTree emptyModifiers = make.Modifiers(
                Collections.<Modifier>emptySet());
        final List<? extends VariableElement> serviceParameters = serviceMethod.
                getParameters();
        final List<VariableTree> parameters =
                new ArrayList<VariableTree>(serviceParameters.size() + 1);

        for (final VariableElement var : serviceParameters) {
            final TypeMirror mirror = var.asType();

            final CharSequence typeName =
                    mirror.getKind() == TypeKind.DECLARED
                    ? ((DeclaredType) mirror).asElement().getSimpleName()
                    : var.asType().toString();

            Tree paramType;
            if (mirror.getKind() == TypeKind.DECLARED) {
                List<Tree> argTrees = new ArrayList<Tree>();
                List<? extends TypeMirror> typeArguments =
                        ((DeclaredType) mirror).getTypeArguments();
                for (final TypeMirror m : typeArguments) {
                    argTrees.add(make.Type(m));
                }
                if (argTrees.isEmpty()) {
                    paramType = make.Identifier(typeName);
                } else {
                    paramType = make.ParameterizedType(
                            make.Identifier(typeName),
                            argTrees);
                }
            } else {
                paramType = make.Identifier(typeName);
            }

            parameters.add(make.Variable(
                    emptyModifiers,
                    var.getSimpleName(), paramType,
                    null));
        }

        parameters.add(make.Variable(
                emptyModifiers,
                "asyncCallback", // NOI18N
                getAsyncCallback(make, serviceMethod),
                null));

        final MethodTree newMethod = make.Method(
                make.Modifiers(getMethodModifiers(serviceMethod)),
                serviceMethod.getSimpleName(),
                make.Identifier("void"), // NOI18N
                Collections.<TypeParameterTree>emptyList(),
                parameters,
                Collections.<ExpressionTree>emptyList(),
                (BlockTree) null,
                null);

        return newMethod;
    }

    @Override
    public String getText() {
        return NbBundle.getMessage(FixAsyncInterfaceImpl.class,
                "HINT_SYNC"); // NOI18N
    }

    @Override
    public ChangeInfo implement() {
        JavaSource jsSync = JavaSource.forFileObject(syncFo);
        JavaSource jsAsync = JavaSource.forFileObject(asyncFo);

        try {
            jsSync.runModificationTask(new CancellableTask<WorkingCopy>() {
                @Override
                public void cancel() {
                }

                @Override
                public void run(WorkingCopy workingCopy) {
                    // dummy task to change the state of classB
                }
            }).commit();

            jsAsync.runModificationTask(new RemoveUnmatchedMethods()).commit();
            for (ElementHandle eh : methods) {
                serviceMethodHandle = eh;
                jsAsync.runModificationTask(new CreateAsyncMethod()).commit();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private class CreateAsyncMethod implements CancellableTask<WorkingCopy> {

        private boolean isAsyncClassName(final Name name) {
            return name.toString().endsWith(ServiceClassSetUtils.ASYNC_SUFFIX);
        }

        private void resolveServiceMethod(final WorkingCopy workingCopy) {
            serviceMethod = (ExecutableElement) serviceMethodHandle.resolve(
                    workingCopy);
        }

        private void createAsyncMethodImpl(
                final TreeMaker make,
                final ClassTree clazz,
                final WorkingCopy workingCopy) {

            final MethodTree newMethod = createMethodTree(make);
            final ClassTree modifiedClazz = make.addClassMember(clazz, newMethod);

            workingCopy.rewrite(clazz, modifiedClazz);
        }

        @Override
        public void cancel() {
        }

        @Override
        public void run(final WorkingCopy workingCopy) throws IOException {
            workingCopy.toPhase(Phase.RESOLVED);

            CompilationUnitTree cut = workingCopy.getCompilationUnit();
            TreeMaker make = workingCopy.getTreeMaker();

            for (final Tree typeDecl : cut.getTypeDecls()) {
                if (Tree.Kind.INTERFACE == typeDecl.getKind()) {
                    final ClassTree clazz = (ClassTree) typeDecl;

                    if (isAsyncClassName(clazz.getSimpleName())) {
                        resolveServiceMethod(workingCopy);

                        // what happens if the service-method has been deleted
                        // or edited? it won't resolve if it has, so we do
                        // this check to make sure it's all still the same
                        if (serviceMethod != null) {
                            createAsyncMethodImpl(make, clazz, workingCopy);
                        }
                    }
                }
            }
            
            workingCopy.toPhase(Phase.UP_TO_DATE);
        }
    }

    private class RemoveUnmatchedMethods implements CancellableTask<WorkingCopy> {

        @Override
        public void cancel() {
        }

        @Override
        public void run(final WorkingCopy workingCopy) throws IOException {
            workingCopy.toPhase(Phase.RESOLVED);

            CompilationUnitTree cut = workingCopy.getCompilationUnit();
            TreeMaker make = workingCopy.getTreeMaker();

            for (Tree typeDecl : cut.getTypeDecls()) {
                if (Tree.Kind.INTERFACE == typeDecl.getKind()) {
                    ClassTree clazz = (ClassTree) typeDecl;
                    ClassTree modifiedClazz = clazz;
                    if (clazz.getSimpleName().toString().endsWith(ServiceClassSetUtils.ASYNC_SUFFIX)) {
                        for (ElementHandle classAMethodHandle : unmatchedMethods) {
                            ExecutableElement classAMethod = (ExecutableElement) classAMethodHandle.resolve(workingCopy);
                            MethodTree aMethod = workingCopy.getTrees().getTree(classAMethod);
                            modifiedClazz = make.removeClassMember(modifiedClazz, aMethod);
                        }
                        if (modifiedClazz != null) {
                            workingCopy.rewrite(clazz, modifiedClazz);
                        }
                    }
                }
            }
            
            workingCopy.toPhase(Phase.UP_TO_DATE);
            
        }
    }
}
