package org.netbeans.modules.gwt4nb.services.refactoring.ui;

import java.util.Hashtable;
import org.netbeans.api.project.FileOwnerQuery;
import org.netbeans.api.project.Project;
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
    
    protected void performAction(Node[] activatedNodes) {
        ServiceRefactoringActionsProvider.doRename(getLookup(activatedNodes));
    }
    
    protected boolean enable(Node[] node)  {
        if(node==null || node.length<1){
            return false;
        }
        DataObject dataObject = (DataObject) node[0].getCookie(DataObject.class);
        
        if (dataObject != null){
            
            Project p = FileOwnerQuery.getOwner(dataObject.getPrimaryFile());
            if (p != null && org.netbeans.modules.gwt4nb.GWTProjectInfo.isGWTProject(p)) {
                
                return ServiceRefactoringActionsProvider.canRename(getLookup(node));
            }
        }
        
    return false;
}

    protected int mode() {
        return CookieAction.MODE_EXACTLY_ONE;
    }
    
    public String getName() {
        return NbBundle.getMessage(ServiceRenameAction.class, "CTL_ServiceRenameAction");
    }
    
    protected Class[] cookieClasses() {
        return new Class[] {
            Project.class
        };
    }
    
    protected void initialize() {
        super.initialize();
        // see org.openide.util.actions.SystemAction.iconResource() javadoc for more details
        putValue("noIconInMenu", Boolean.TRUE);
    }
    
    public HelpCtx getHelpCtx() {
        return HelpCtx.DEFAULT_HELP;
    }
    
    protected boolean asynchronous() {
        return false;
    }
    
    protected Lookup getLookup(Node[] n) {
        InstanceContent ic = new InstanceContent();
        for (Node node:n)
            ic.add(node);
        if (n.length>0) {
            EditorCookie tc = getTextComponent(n[0]);
            if (tc != null) {
                ic.add(tc);
            }
        }
        ic.add(new Hashtable(0));
        return new AbstractLookup(ic);
    }
    
    protected static EditorCookie getTextComponent(Node n) {
        DataObject dobj = (DataObject) n.getCookie(DataObject.class);
        if (dobj != null) {
            EditorCookie ec = (EditorCookie) dobj.getCookie(EditorCookie.class);
            if (ec != null) {
                TopComponent activetc = TopComponent.getRegistry().getActivated();
                if (activetc instanceof Pane) {
                    return ec;
                }
            }
        }
        return null;
    }
    
}

