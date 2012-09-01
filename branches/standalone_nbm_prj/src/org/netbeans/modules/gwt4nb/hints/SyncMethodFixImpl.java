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

import org.netbeans.modules.gwt4nb.hints.GWTHintsProvider;
import com.sun.source.tree.ClassTree;
import com.sun.source.tree.CompilationUnitTree;
import com.sun.source.tree.MethodTree;
import com.sun.source.tree.Tree;
import com.sun.source.tree.VariableTree;
import java.io.IOException;
import java.util.Collections;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
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
 *
 * @author prem
 */
final class SyncMethodFixImpl implements Fix {
    
    private FileObject asyncFo;
    private FileObject syncFo;
    private ExecutableElement serviceMethod;
    private ElementHandle serviceMethodHandle;
    private String serviceClassName;
    private String serviceMethodName;
    
    public SyncMethodFixImpl(FileObject asyncFo,FileObject syncFo, ElementHandle
            serviceMethodHandle, String sClass, String sMethod) {
        this.asyncFo = asyncFo;
        this.syncFo = syncFo;
        this.serviceMethodHandle = serviceMethodHandle;
        this.serviceClassName = sClass;
        this.serviceMethodName = sMethod;
    }
    
    public String getText() {
        return NbBundle.getMessage(SyncMethodFixImpl.class, "HINT_SYNC_METHOD",serviceMethodName,serviceClassName,serviceClassName+ServiceClassSetUtils.ASYNC_SUFFIX);
    }
    
    public ChangeInfo implement() {
        JavaSource jsSync = JavaSource.forFileObject(syncFo) ;
        JavaSource jsAsync = JavaSource.forFileObject(asyncFo) ;
                
        try{
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
                            if(clazz.getSimpleName().toString().endsWith(ServiceClassSetUtils.ASYNC_SUFFIX)){
                                serviceMethod = (ExecutableElement) serviceMethodHandle.resolve(workingCopy) ;
                                assert serviceMethod !=null;
                                MethodTree newMethod = make.Method(serviceMethod, null);
                                VariableTree asynVar = make.Variable(make.Modifiers(Collections.<Modifier>emptySet()),
                                        "asyncCallback", make.Identifier("AsyncCallback"), null) ;
                                newMethod = make.addMethodParameter(newMethod, asynVar);
                                ClassTree modifiedClazz = make.addClassMember(clazz, newMethod);
                                workingCopy.rewrite(clazz, modifiedClazz);
                                
                            }
                        }
                    }
                }
            }).commit();
            
        } catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }
      
}

