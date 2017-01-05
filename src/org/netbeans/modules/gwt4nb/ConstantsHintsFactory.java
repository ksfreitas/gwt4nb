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
 * The Original Software is NetBeans. The Initial Developer of the Original
 * Software is Sun Microsystems, Inc. Portions Copyright 1997-2007 Sun
 * Microsystems, Inc. All Rights Reserved.
 */
package org.netbeans.modules.gwt4nb;

import com.sun.source.tree.Tree;
import com.sun.source.util.TreePath;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Name;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.NoType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.ElementFilter;
import org.netbeans.api.java.source.CancellableTask;
import org.netbeans.api.java.source.CompilationInfo;
import org.netbeans.api.java.source.ElementHandle;
import org.netbeans.api.java.source.JavaSource.Phase;
import org.netbeans.api.java.source.JavaSource.Priority;
import org.netbeans.api.java.source.support.EditorAwareJavaSourceTaskFactory;
import org.netbeans.modules.gwt4nb.hints.HintsUtils;
import org.netbeans.spi.editor.hints.ErrorDescription;
import org.netbeans.spi.editor.hints.HintsController;
import org.netbeans.spi.project.support.ant.EditableProperties;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;
import org.openide.util.NbBundle;

/**
 * Searches for missing keys in the corresponding .properties file for a
 * class that extends com.google.gwt.i18n.client.Constants
 */
public class ConstantsHintsFactory extends EditorAwareJavaSourceTaskFactory {
    public ConstantsHintsFactory(){
        super(Phase.RESOLVED, Priority.LOW);
    }
    
    public CancellableTask<CompilationInfo> createTask(final FileObject file) {
        return new CancellableTask<CompilationInfo>() {
            public void cancel() {
            }
            public void run(CompilationInfo p) throws Exception {
                run0(file, p);
            }
        };
    }

    private void run0(FileObject file, CompilationInfo info) throws IOException {
        List<ErrorDescription> problemsFound = new ArrayList<ErrorDescription>();
        
        NoType noType =
                info.getTypes().getNoType(TypeKind.NONE);

        // iterate over all the types (classes, interfaces) defined in this file
        for (Tree tree: info.getCompilationUnit().getTypeDecls()){
            if (tree.getKind() == Tree.Kind.CLASS){
                TreePath path = info.getTrees().getPath(
                        info.getCompilationUnit(), tree);
                TypeElement c = (TypeElement) info.getTrees().
                        getElement(path);
                TypeMirror superclass = c.getSuperclass();
                
                // only interfaces and java.lang.Object have 
                // superclass.kind=None
                if (superclass.equals(noType)) {
                    List<? extends TypeMirror> interfaces = c.getInterfaces();
                    boolean extendsConstants = false;
                    for (TypeMirror tm: interfaces) {
                        Name qn =((TypeElement) ((DeclaredType) tm).
                                asElement()).getQualifiedName();
                        if (qn.contentEquals(
                                "com.google.gwt.i18n.client.Constants")) { // NOI18N
                            extendsConstants = true;
                            break;
                        }
                    }
                    if (extendsConstants) {
                        runOnInterface(problemsFound, file, info, c);
                    }
                }
            }
        }

        HintsController.setErrors(info.getFileObject(),
                getClass().getName(), problemsFound); 
    }

    /**
     * Creates hints for an interface that extends
     * com.google.gwt.i18n.client.Constants
     *
     * @param problemsFound problems will be stored here
     * @param file .java file
     * @param info compilation unit
     * @param c interface a extends com.google.gwt.i18n.client.Constants
     */
    private void runOnInterface(List<ErrorDescription> problemsFound,
            FileObject file, CompilationInfo info, TypeElement c)
            throws IOException {
        FileObject p =
                FileUtil.findBrother(file, "properties"); // NOI18N
        if (p != null) {
            EditableProperties ep = new EditableProperties(false);
            InputStream is = p.getInputStream();
            try {
                ep.load(is);
            } finally {
                is.close();
            }
            List<ExecutableElement> ms =
                    new ArrayList<ExecutableElement>();

            for (ExecutableElement m: ElementFilter.methodsIn(
                    c.getEnclosedElements())) {
                String methodName = m.getSimpleName().toString();
                if (!ep.containsKey(methodName)) {
                    ms.add(m);
                }
            }
            if (ms.size() > 0) {
                String msg = NbBundle.getMessage(
                        ConstantsHintsFactory.class,
                        "ConstantsHintsFactory.MissingKeys"); // NOI18N
                List<ElementHandle<ExecutableElement>> ehs =
                        new ArrayList<ElementHandle<ExecutableElement>>();
                for (ExecutableElement m: ms)
                    ehs.add(ElementHandle.create(m));
                ErrorDescription error = HintsUtils.createProblem(
                        c, info, msg,
                        new ConstantsCreateKeyFix(file, ehs, ep));

                problemsFound.add(error);
            }
        }
    }
}
