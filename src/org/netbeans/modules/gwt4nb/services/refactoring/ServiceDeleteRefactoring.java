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
 * Software is Sun Microsystems, Inc. Portions Copyright 1997-2006 Sun
 * Microsystems, Inc. All Rights Reserved.
 */
package org.netbeans.modules.gwt4nb.services.refactoring;

import org.netbeans.modules.gwt4nb.services.ServiceClassSet;
import org.netbeans.modules.refactoring.api.AbstractRefactoring;
import org.openide.util.Lookup;

/**
 * Service Delete Refactoring implementation class.
 *
 * @author Prem
 */
public final class ServiceDeleteRefactoring extends AbstractRefactoring {
    private static final ServiceClassSet EMPTY_SERVICE_SET = new ServiceClassSet();

    // parameters of the refactoring
    private ServiceClassSet serviceClassSet;
    
    
    public ServiceDeleteRefactoring(Lookup sourceType) {
        super(sourceType);
    }
    
    public ServiceClassSet getMembers() {
        // never return null
        return serviceClassSet == null ? EMPTY_SERVICE_SET : serviceClassSet;
    }

    
    public void setMembers(ServiceClassSet serviceClassSet) {
        this.serviceClassSet = serviceClassSet;
    }
}
