package org.netbeans.modules.gwt4nb;

import java.io.IOException;
import org.apache.tools.ant.module.api.support.ActionUtils;
import org.netbeans.api.project.FileOwnerQuery;
import org.netbeans.api.project.Project;
import org.openide.filesystems.FileObject;
import org.openide.loaders.DataObject;
import org.openide.nodes.Node;
import org.openide.util.HelpCtx;
import org.openide.util.NbBundle;
import org.openide.util.actions.CookieAction;

public final class HostedModeAction extends CookieAction {
    
    protected void performAction(Node[] activatedNodes) {
        DataObject d = (DataObject) activatedNodes[0].getCookie(DataObject.class);
        Project c = FileOwnerQuery.getOwner(d.getPrimaryFile());
        if(c!=null){
            FileObject buildFo = c.getProjectDirectory().getFileObject("build.xml");
            
            try {
                ActionUtils.runTarget(buildFo,new String[] {"debug-connect-gwt-shell-hosted"},null);
            } catch (IllegalArgumentException ex) {
                ex.printStackTrace();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
    
    protected int mode() {
        return CookieAction.MODE_EXACTLY_ONE;
    }
    
    public String getName() {
        return NbBundle.getMessage(HostedModeAction.class, "CTL_HostedModeAction");
    }
    
    protected Class[] cookieClasses() {
        return new Class[] {
            DataObject.class
        };
    }
    
    protected boolean enable(Node[] node) {
        if(node==null || node.length<1){
            return false;
        }
        DataObject dataObject = (DataObject) node[0].getCookie(DataObject.class);
        
        if (dataObject != null){
            Project p = FileOwnerQuery.getOwner(dataObject.getPrimaryFile());
            if(p!=null && GWTProjectInfo.isGWTProject(p)){
                return true;
            }
        }
        
        return false;
    }
    
    protected String iconResource() {
        return "org/netbeans/modules/gwt4nb/resources/debug_in_hosted_mode.png";
    }
    
    public HelpCtx getHelpCtx() {
        return HelpCtx.DEFAULT_HELP;
    }
    
    protected boolean asynchronous() {
        return false;
    }
    
}

