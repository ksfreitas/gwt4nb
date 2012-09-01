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

import com.sun.source.tree.ClassTree;
import com.sun.source.tree.CompilationUnitTree;
import com.sun.source.tree.MethodTree;
import com.sun.source.tree.Tree;
import java.io.IOException;
import java.util.List;
import javax.lang.model.element.ExecutableElement;
import org.netbeans.api.java.source.CancellableTask;
import org.netbeans.api.java.source.ElementHandle;
import org.netbeans.api.java.source.JavaSource;
import org.netbeans.api.java.source.JavaSource.Phase;
import org.netbeans.api.java.source.TreeMaker;
import org.netbeans.api.java.source.WorkingCopy;
import org.netbeans.modules.gwt4nb.services.ServiceClassSetUtils;
import org.netbeans.spi.editor.hints.ChangeInfo;
import org.netbeans.spi.editor.hints.Fix;
import org.openide.filesystems.FileObject;
import org.openide.util.NbBundle;

/**
 * Fix implementation to remove unmatched methods
 * @author prem
 */

final class RemoveMethodFixImpl implements Fix {
    
    private FileObject asyncFo;
    private FileObject syncFo;
    private List<ElementHandle> unmatchedMethods;
    private String serviceClassName = "";
    
    
    public RemoveMethodFixImpl(FileObject asyncFo,FileObject syncFo, List<ElementHandle>
            unmatchedMethods,String serviceClassName) {
        this.asyncFo = asyncFo;
        this.syncFo = syncFo;
        this.unmatchedMethods = unmatchedMethods;
        this.serviceClassName = serviceClassName;
        
    }
    
    public String getText() {
        return NbBundle.getMessage(RemoveMethodFixImpl.class, "HINT_REMOVE_UMSYNC_METHODS",serviceClassName+ServiceClassSetUtils.ASYNC_SUFFIX,serviceClassName);
    }
    
    // removes unmatched methods in classA and runs a dummy task on classB
    public ChangeInfo implement() {
        JavaSource jsSync = JavaSource.forFileObject(syncFo) ;
        JavaSource jsAsync = JavaSource.forFileObject(asyncFo) ;
        
        try {
            jsSync.runModificationTask(new CancellableTask<WorkingCopy>() {
                public void cancel() {}
                public void run(WorkingCopy workingCopy){
                    // dummy task to change the state of classB
                }
            }).commit();
            jsAsync.runModificationTask(new CancellableTask<WorkingCopy>() {
                public void cancel() {}
                public void run(WorkingCopy workingCopy) throws IOException {
                    workingCopy.toPhase(Phase.RESOLVED);
                    CompilationUnitTree cut = workingCopy.getCompilationUnit();
                    TreeMaker make = workingCopy.getTreeMaker();
                    
                    for (Tree typeDecl : cut.getTypeDecls()) {
                        if (Tree.Kind.CLASS == typeDecl.getKind()) {
                            ClassTree clazz = (ClassTree) typeDecl;
                            ClassTree modifiedClazz=clazz;
                            if(clazz.getSimpleName().toString().endsWith(ServiceClassSetUtils.ASYNC_SUFFIX)){
                                for(ElementHandle classAMethodHandle : unmatchedMethods ){
                                    ExecutableElement classAMethod = (ExecutableElement)
                                            classAMethodHandle.resolve(workingCopy) ;
                                    String name = classAMethod.getSimpleName().toString();
                                    MethodTree aMethod = workingCopy.getTrees().getTree(classAMethod);
                                    modifiedClazz = make.removeClassMember(modifiedClazz,aMethod);
                                }
                                if(modifiedClazz != null){
                                    workingCopy.rewrite(clazz, modifiedClazz);
                                }
                                
                            }
                        }
                    }
                }
            }).commit();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return null;
    }
}
