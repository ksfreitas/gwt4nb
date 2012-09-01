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

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;

import com.sun.source.tree.ClassTree;
import com.sun.source.tree.ExpressionTree;
import com.sun.source.tree.MemberSelectTree;
import com.sun.source.tree.MethodInvocationTree;
import com.sun.source.tree.MethodTree;
import com.sun.source.tree.Tree;
import com.sun.source.tree.Tree.Kind;
import com.sun.source.tree.VariableTree;
import com.sun.source.util.TreePath;
import org.netbeans.api.java.classpath.ClassPath;
import org.netbeans.api.java.queries.UnitTestForSourceQuery;
import org.netbeans.api.java.source.ClassIndex.NameKind;
import org.netbeans.api.java.source.ClassIndex.SearchScope;
import org.netbeans.api.java.source.ClasspathInfo;
import org.netbeans.api.java.source.ClasspathInfo.PathKind;
import org.netbeans.api.java.source.CompilationInfo;
import org.netbeans.api.java.source.ElementHandle;
import org.netbeans.api.project.FileOwnerQuery;
import org.netbeans.api.project.Project;
import org.netbeans.modules.gwt4nb.GWT4NBUtil;
import org.netbeans.modules.gwt4nb.GWTProjectInfo;
import org.netbeans.spi.editor.hints.ErrorDescription;
import org.netbeans.spi.editor.hints.Fix;
import org.netbeans.spi.editor.hints.Severity;
import org.netbeans.spi.java.classpath.support.ClassPathSupport;
import org.netbeans.spi.java.hints.Hint;
import org.netbeans.spi.java.hints.HintContext;
import org.netbeans.spi.java.hints.HintSeverity;
import org.netbeans.spi.java.hints.TriggerTreeKind;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;
import org.openide.filesystems.URLMapper;
import org.openide.util.Exceptions;
import org.openide.util.NbBundle.Messages;

import static org.netbeans.modules.gwt4nb.hints.Bundle.*;

/**
 * Hints classes unsupported by GWT.
 *
 * This file is modelled after
 * http://kenai.com/projects/nbappengine/sources/main/content/hints/src/org/netbeans/modules/j2ee/appengine/hints/UnsupportedClass.java?rev=51
 * author Jan Lahoda
 */
@Hint(description="#DESC_UnsupportedClass",
    displayName="#DN_UnsupportedClass",
    severity= Severity.WARNING,
    hintKind= Hint.Kind.ACTION,
    id="org.netbeans.modules.gwt4nb.hints.UnsupportedClass",
    category="#org-netbeans-modules-java-hints/rules/hints/gwt")
@Messages({"DN_UnsupportedClass=Unsupported Class",
    "DESC_UnsupportedClass=Unsupported Class",
    "org-netbeans-modules-java-hints/rules/hints/gwt=GWT"})
public class UnsupportedClass {
    private static final Set<String> jreWhitelist;

    static {
        Set<String> whitelist = new HashSet<String>();
        URL url = UnsupportedClass.class.getResource("class-white-list"); // NOI18N
        FileObject file = URLMapper.findFileObject(url);

        try {
            for (String line : file.asLines("UTF-8")) { // NOI18N
                whitelist.add(line);
            }
        } catch (IOException ex) {
            Exceptions.printStackTrace(ex);
        }

        jreWhitelist = whitelist;
    }

    public Set<Kind> getTreeKinds() {
        return EnumSet.of(Kind.IDENTIFIER, Kind.MEMBER_SELECT);
    }

    private static final Map<CompilationInfo, Boolean> hasGWT_ =
            new WeakHashMap<CompilationInfo, Boolean>();

    private static boolean hasGWT(CompilationInfo info) {
        Boolean val = hasGWT_.get(info);

        if (val != null) {
            return val;
        }

        Project p = FileOwnerQuery.getOwner(info.getFileObject());
        if (p == null)
            return false;

        final GWTProjectInfo pi = GWTProjectInfo.get(p);
        boolean b = pi != null;
        if (b) {
            ExpressionTree pn = info.getCompilationUnit().getPackageName();
            if (pn == null)
                b = false;
            else {
                String pn_ = pn.toString();
                List<String> modules = pi.getModules();
                for (String module: modules) {
                    String clientPackage =
                            GWTProjectInfo.getClientPackage(module);
                    b = clientPackage.equals(pn_) ||
                            pn_.startsWith(clientPackage + "."); // NOI18N
                }
            }
        }
        hasGWT_.put(info, b);
        return b;
    }

    private static final ClassPath EMPTY = ClassPathSupport.createClassPath(
            new FileObject[0]);

    private static final Map<CompilationInfo, Set<String>> info2ProjectWhitelist =
            new WeakHashMap<CompilationInfo, Set<String>>();

    private static Set<String> getProjectBasedWhitelist(CompilationInfo info) {
        Set<String> cached = info2ProjectWhitelist.get(info);

        if (cached != null) {
            return cached;
        }

        long start = System.currentTimeMillis();

        ClasspathInfo cpInfo = ClasspathInfo.create(EMPTY, info.getClasspathInfo().getClassPath(PathKind.COMPILE), info.getClasspathInfo().getClassPath(PathKind.SOURCE));
        Set<String> result = new HashSet<String>();
        Set<ElementHandle<TypeElement>> declaredTypes =
                cpInfo.getClassIndex().getDeclaredTypes("", // NOI18N
                NameKind.PREFIX, EnumSet.of(SearchScope.DEPENDENCIES,
                SearchScope.SOURCE));

        if (declaredTypes == null) {
            return null;
        }

        for (ElementHandle<TypeElement> h : declaredTypes) {
            result.add(h.getBinaryName());
        }

        long end = System.currentTimeMillis();

        Logger.getLogger("TIMER").log(Level.FINE,  // NOI18N
                "Project Based Whitelist", new Object[] { // NOI18N
                info.getFileObject(), (end - start)});

        info2ProjectWhitelist.put(info, result);

        return result;
    }

    @TriggerTreeKind(Kind.IDENTIFIER)
    public static List<ErrorDescription> runIdentifier(final HintContext context){
      return run(context.getInfo(), context.getPath());
    }

    @TriggerTreeKind(Kind.MEMBER_SELECT)
    public static List<ErrorDescription> runMemberSelect(final HintContext context){
      return run(context.getInfo(), context.getPath());
    }

    @Messages("ERR_UnsupportedClass=Class {0} not supported by the GWT")
    private static List<ErrorDescription> run(final CompilationInfo info, final TreePath treePath){
        if (!hasGWT(info) || isUnitTest(info)) {
            return null;
        }

        Element el = info.getTrees().getElement(treePath);

        if (el == null || (!el.getKind().isClass() && !el.getKind().isInterface())) {
            return null;
        }

        TypeMirror tm = info.getTrees().getTypeMirror(treePath);

        if (tm == null || tm.getKind() == TypeKind.ERROR) {
            return null;
        }

        TypeElement te = (TypeElement) el;
        String fqn = ElementHandle.create(te).getBinaryName();

        if (jreWhitelist.contains(fqn)) {
            return null;
        }

        Set<String> projectBasedWhitelist = getProjectBasedWhitelist(info);

        if (projectBasedWhitelist == null) {
            return null;
        }

        if (projectBasedWhitelist.contains(fqn)) {
            return null;
        }

        return Collections.singletonList(forName(info, treePath.getLeaf(),
                ERR_UnsupportedClass(fqn))); // NOI18N
    }
    private static ErrorDescription forName(CompilationInfo info, Tree tree, String text, Fix... fixes) {
        int[] span = computeNameSpan(tree, info);

        if (span != null && span[0] != (-1) && span[1] != (-1)) {
            return org.netbeans.spi.editor.hints.ErrorDescriptionFactory.createErrorDescription(HintSeverity.WARNING.toEditorSeverity(), text, Arrays.asList(fixes), info.getFileObject(), span[0], span[1]);
        }

        return null;
    }

    private static int[] computeNameSpan(Tree tree, CompilationInfo info) {
        switch (tree.getKind()) {
            case METHOD:
                return info.getTreeUtilities().findNameSpan((MethodTree) tree);
            case CLASS:
                return info.getTreeUtilities().findNameSpan((ClassTree) tree);
            case VARIABLE:
                return info.getTreeUtilities().findNameSpan((VariableTree) tree);
            case MEMBER_SELECT:
                return info.getTreeUtilities().findNameSpan((MemberSelectTree) tree);
            case METHOD_INVOCATION:
                return computeNameSpan(((MethodInvocationTree) tree).getMethodSelect(), info);
            default:
                return new int[] {
                    (int) info.getTrees().getSourcePositions().getStartPosition(info.getCompilationUnit(), tree),
                    (int) info.getTrees().getSourcePositions().getEndPosition(info.getCompilationUnit(), tree),
                };
        }
    }

    private static boolean isUnitTest(CompilationInfo info) {
        ClassPath sourcePath = info.getClasspathInfo().getClassPath(PathKind.SOURCE);

        if (sourcePath == null) {
            GWT4NBUtil.LOGGER.log(Level.FINE, "No source path for {0}", // NOI18N
                    FileUtil.getFileDisplayName(info.getFileObject()));

            return true;
        }

        FileObject ownerRoot = sourcePath.findOwnerRoot(info.getFileObject());

        if (ownerRoot == null) {
            GWT4NBUtil.LOGGER.log(Level.FINE,
                    "No owning root for {0} on its own source path", // NOI18N
                    FileUtil.getFileDisplayName(info.getFileObject()));

            return true;
        }

        return UnitTestForSourceQuery.findSources(ownerRoot).length > 0;
    }

}