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
package org.netbeans.modules.gwt4nb.services.refactoring.ui;

import java.io.IOException;
import javax.swing.*;
import javax.swing.text.TextAction;
import org.openide.awt.Actions;
import org.openide.cookies.InstanceCookie;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;
import org.openide.loaders.DataFolder;
import org.openide.loaders.DataObject;
import org.openide.util.LookupListener;
import org.openide.util.NbBundle;
import org.openide.util.actions.Presenter;

/**
 *
 * @author Prem
 */
public final class SubMenuAction extends TextAction 
        implements Presenter.Menu, Presenter.Popup {
    private static final long serialVersionUID = 1;

    private final boolean showIcons;
    
    public static SubMenuAction create(FileObject o) {
        return new SubMenuAction(true);
    }
    
    public static JMenu createMenu() {
        SubMenuAction action = new SubMenuAction(true);
        return (JMenu) action.getMenuPresenter();
    }
    
    SubMenuAction(boolean showIcons) {
        super(NbBundle.getMessage(SubMenuAction.class, 
                "LBL_ServiceRefactorAction")); // NOI18N
        this.showIcons = showIcons;
    }
    
    public void actionPerformed(java.awt.event.ActionEvent e) {
    }
    
    public javax.swing.JMenuItem getMenuPresenter() {
        return new SubMenu();
    }
    
    public javax.swing.JMenuItem getPopupPresenter() {
        return getMenuPresenter();
    }
    
    public boolean equals(Object o) {
        return (o instanceof SubMenuAction);
    }
    
    public int hashCode() {
        return 1;
    }
    
    private class SubMenu extends JMenu implements LookupListener {
        private static final long serialVersionUID = 1;
        private boolean actions = false;
        
        public SubMenu() {
            super((String) SubMenuAction.this.getValue(Action.NAME));
            if (showIcons)
                setMnemonic(NbBundle.getMessage(SubMenuAction.class, 
                        "LBL_ActionMnemonic").charAt(0)); // NOI18N
        }
        
        /**
         * Gets popup menu. Overrides superclass.
         * Adds lazy menu items creation.
         */
        public JPopupMenu getPopupMenu() {
            if (!actions) {
                createMenuItems();
            }
            return super.getPopupMenu();
        }
        
        /** Creates items when actually needed. */
        private void createMenuItems() {
            actions = true;
            removeAll();
            FileObject fo = FileUtil.getConfigFile("Actions/Tools"); // NOI18N
            DataFolder df = DataFolder.findFolder(fo);
                
            if (df != null) {
                DataObject actionObjects[] = df.getChildren();
                for (int i = 0; i < actionObjects.length; i++) {
                    InstanceCookie ic = actionObjects[i].getCookie(
                            InstanceCookie.class);
                    if (ic == null) continue;
                    Object instance;
                    try {
                        instance = ic.instanceCreate();
                    } catch (IOException e) {
                        // ignore
                        e.printStackTrace();
                        continue;
                    } catch (ClassNotFoundException e) {
                        // ignore
                        e.printStackTrace();
                        continue;
                    }
                    if (instance instanceof ServiceRenameAction) {
                        // if the action is the service refactoring action,
                        // pass it information
                        // whether it is in editor, popup or main menu
                        JMenuItem mi = new JMenuItem();
                        Actions.connect(mi, (Action) instance, true);
                        if (!showIcons)
                            mi.setIcon(null);
                        add(mi);
                    } else if (instance instanceof JSeparator) {
                        add((JSeparator) instance);
                    } else if (instance instanceof ServiceDeleteAction) {
                        JMenuItem mi = new JMenuItem();
                        Actions.connect(mi, (Action) instance, true);
                        if (!showIcons)
                            mi.setIcon(null);
                        add(mi);
                    }
                }
            }
        }
         
        public void resultChanged(org.openide.util.LookupEvent ev) {
        }
    }
}
