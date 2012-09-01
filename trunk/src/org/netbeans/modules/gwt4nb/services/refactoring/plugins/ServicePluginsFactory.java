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
package org.netbeans.modules.gwt4nb.services.refactoring.plugins;

import org.netbeans.modules.gwt4nb.services.refactoring.ServiceDeleteRefactoring;
import org.netbeans.modules.gwt4nb.services.refactoring.ServiceRenameRefactoring;
import org.netbeans.modules.refactoring.api.AbstractRefactoring;
import org.netbeans.modules.refactoring.spi.RefactoringPlugin;
import org.netbeans.modules.refactoring.spi.RefactoringPluginFactory;

/**
 * Factory responsible for creating refactoring plugins implemented in this module.
 *
 * @author Martin Matula
 */
public class ServicePluginsFactory implements RefactoringPluginFactory {
    /** Factory method called by a refactoring. Creates and returns a new plugin
     * instance for a given refactoring. If no plugin for a given refactoring
     * is present, this method returns null.
     * @param refactoring Parent refactoring for which a plugin should be created.
     * @return New instance of a refactoring plugin for the provided refactoring
     * or <code>null</code>.
     */
    public RefactoringPlugin createInstance(AbstractRefactoring refactoring) {
        if (refactoring instanceof ServiceDeleteRefactoring) {
            return new ServiceDeleteRefactoringPlugin((ServiceDeleteRefactoring) refactoring);
        } else if (refactoring instanceof ServiceRenameRefactoring) {
            return new ServiceRenameRefactoringPlugin((ServiceRenameRefactoring) refactoring);
        }
       
        return null;
    }
}
