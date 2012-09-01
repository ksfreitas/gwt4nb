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
package org.netbeans.modules.gwt4nb.hints;


import org.netbeans.modules.gwt4nb.*;
import com.sun.source.tree.Tree;
import com.sun.source.util.TreePath;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import org.netbeans.api.java.source.CancellableTask;
import org.netbeans.api.java.source.CompilationInfo;
import javax.lang.model.element.TypeElement;
import org.netbeans.modules.gwt4nb.services.ServiceClassSet;
import org.netbeans.modules.gwt4nb.services.ServiceClassSetUtils;
import org.openide.filesystems.FileObject;
import org.netbeans.spi.editor.hints.ErrorDescription;
import org.netbeans.spi.editor.hints.HintsController;

/**
 *
 * @author Tomasz.Slota@Sun.COM
 */
public class GWTHintsProvider implements CancellableTask<CompilationInfo> {
    private static final Collection<? extends GWTProblemFinder> CHECKS = Arrays.asList(
            new ServiceAsyncConsistentWithService()
            );
    
    private FileObject file;
    
    public GWTHintsProvider(FileObject file) {
        this.file = file;
    }
    
    public void cancel() {
    }
    
    public void run(CompilationInfo info) throws Exception {
        List<ErrorDescription> problemsFound = new ArrayList<ErrorDescription>();
        // iterate over all the types (classes, interfaces) defined in this file
        for (Tree tree : info.getCompilationUnit().getTypeDecls()){
            
            if (tree.getKind() == Tree.Kind.CLASS){
                TreePath path = info.getTrees().getPath(info.getCompilationUnit(), tree);
                TypeElement javaClass = (TypeElement) info.getTrees().getElement(path);
                ServiceClassSet serviceClassSet = ServiceClassSetUtils.resolveServiceClassSet(info, javaClass);
                
                if (serviceClassSet != null){
                    for (GWTProblemFinder problemFinder : CHECKS){
                        Collection<ErrorDescription> problems = problemFinder.check(info, javaClass, serviceClassSet);
                        
                        if (problems != null){
                            problemsFound.addAll(problems);
                        }
                    }
                }
            }
        }
        
        HintsController.setErrors(info.getFileObject(), "GWT4NB", problemsFound); //NOI18N
    }
    
    public static interface GWTProblemFinder{
        Collection<ErrorDescription> check(CompilationInfo info, TypeElement javaClass, ServiceClassSet serviceClassSet);
    }
}
