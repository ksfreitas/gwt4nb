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
package org.netbeans.modules.gwt4nb.services.refactoring.plugins;

import org.netbeans.modules.gwt4nb.services.ServiceClassSet;
import org.netbeans.modules.refactoring.api.Problem;
import org.netbeans.modules.gwt4nb.services.refactoring.ServiceDeleteRefactoring;
import org.netbeans.modules.refactoring.spi.ProgressProviderAdapter;
import org.netbeans.modules.refactoring.spi.RefactoringPlugin;
import org.netbeans.modules.refactoring.spi.RefactoringElementsBag;
import org.openide.filesystems.FileObject;

/** Plugin that implements the core functionality of Service Delete refactoring.
 *
 * @author Prem
 */
public final class ServiceDeleteRefactoringPlugin extends ProgressProviderAdapter implements RefactoringPlugin {
    /** Reference to the parent refactoring instance */
    private final ServiceDeleteRefactoring refactoring;
    
    /** Creates a new instance of ServiceDeleteRefactoringPlugin
     * @param refactoring Parent refactoring instance.
     */
    ServiceDeleteRefactoringPlugin(ServiceDeleteRefactoring refactoring) {
        this.refactoring = refactoring;
    }
    
    public Problem fastCheckParameters() {
        ServiceClassSet set = refactoring.getMembers();
        
        
        return null;
    }
    
    public Problem prepare(RefactoringElementsBag refactoringElements) {
        refactoringElements.addFileChange(refactoring, new ServiceDeleteElementImpl(refactoring, refactoringElements));
        return null;
    }
    
    protected FileObject getFileObject() {
        return null;
    }
    
    public void cancelRequest() {
        
    }
    
    public Problem preCheck() {
        return null;
    }
    
    public Problem checkParameters() {
        return null;
    }
    
}




