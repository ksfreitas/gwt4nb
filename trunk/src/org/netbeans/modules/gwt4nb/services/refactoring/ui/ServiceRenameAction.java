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
package org.netbeans.modules.gwt4nb.services.refactoring.ui;

import java.util.Hashtable;
import org.netbeans.api.project.FileOwnerQuery;
import org.netbeans.api.project.Project;
import org.netbeans.modules.gwt4nb.GWTProjectInfo;
import org.openide.cookies.EditorCookie;
import org.openide.loaders.DataObject;
import org.openide.nodes.Node;
import org.openide.text.CloneableEditorSupport.Pane;
import org.openide.util.HelpCtx;
import org.openide.util.Lookup;
import org.openide.util.NbBundle;
import org.openide.util.actions.CookieAction;
import org.openide.util.lookup.AbstractLookup;
import org.openide.util.lookup.InstanceContent;
import org.openide.windows.TopComponent;

public final class ServiceRenameAction extends CookieAction {
    private static final long serialVersionUID = 1;
    
    protected void performAction(Node[] activatedNodes) {
        ServiceRefactoringActionsProvider.doRename(getLookup(activatedNodes));
    }
    
    protected boolean enable(Node[] node)  {
        if (node == null || node.length<1){
            return false;
        }
        DataObject dataObject = node[0].getCookie(DataObject.class);
        
        if (dataObject != null){
            Project p = FileOwnerQuery.getOwner(dataObject.getPrimaryFile());
            if (p != null && GWTProjectInfo.isGWTProject(p)) {
                return ServiceRefactoringActionsProvider.canRename(
                        getLookup(node));
            }
        }
        
        return false;
    }

    protected int mode() {
        return CookieAction.MODE_EXACTLY_ONE;
    }
    
    public String getName() {
        return NbBundle.getMessage(ServiceRenameAction.class, 
                "CTL_ServiceRenameAction"); // NOI18N
    }
    
    protected Class<?>[] cookieClasses() {
        return new Class<?>[] {
            Project.class
        };
    }
    
    protected void initialize() {
        super.initialize();
        // see org.openide.util.actions.SystemAction.iconResource() javadoc
        // for more details
        putValue("noIconInMenu", Boolean.TRUE); // NOI18N
    }
    
    public HelpCtx getHelpCtx() {
        return HelpCtx.DEFAULT_HELP;
    }
    
    protected boolean asynchronous() {
        return false;
    }
    
    protected Lookup getLookup(Node[] n) {
        InstanceContent ic = new InstanceContent();
        for (Node node: n)
            ic.add(node);
        
        if (n.length>0) {
            EditorCookie tc = getTextComponent(n[0]);
            if (tc != null) {
                ic.add(tc);
            }
        }
        ic.add(new Hashtable<Object, Object>(0));
        return new AbstractLookup(ic);
    }
    
    protected static EditorCookie getTextComponent(Node n) {
        DataObject dobj = n.getCookie(DataObject.class);
        if (dobj != null) {
            EditorCookie ec = dobj.getCookie(EditorCookie.class);
            if (ec != null) {
                TopComponent activetc = TopComponent.getRegistry().
                        getActivated();
                if (activetc instanceof Pane) {
                    return ec;
                }
            }
        }
        return null;
    }    
}

