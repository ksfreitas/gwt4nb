package org.netbeans.modules.gwt4nb.services.refactoring.ui;

import java.awt.event.ActionEvent;
import javax.swing.Action;
import javax.swing.JMenuItem;
import org.openide.util.HelpCtx;

import org.openide.util.actions.SystemAction;
import org.openide.util.actions.Presenter.Menu;
import org.openide.util.actions.Presenter.Popup;

/** Action that displays refactoring submenu action delegates to it.
 *
 * @author Prem
 */
public class ServiceRefactorAction extends SystemAction implements Menu, Popup {
    private final SubMenuAction action = new SubMenuAction(false);
    
    public void actionPerformed(ActionEvent ev) {
        // do nothing -- should never be called
    }
    
    public String getName() {
        return "Service Refactor";
    }
    
    public HelpCtx getHelpCtx() {
        return HelpCtx.DEFAULT_HELP;
        
    }
    
    public JMenuItem getMenuPresenter() {
        return action.getMenuPresenter();
    }
    
    public JMenuItem getPopupPresenter() {
        return action.getPopupPresenter();
    }
}