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
package org.netbeans.modules.gwt4nb;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.JMenuItem;
import org.netbeans.api.project.Project;
import org.openide.awt.DynamicMenuContent;
import org.openide.awt.Mnemonics;
import org.openide.util.ContextAwareAction;
import org.openide.util.Lookup;
import org.openide.util.Utilities;
import org.openide.util.actions.Presenter;

/**
 * Action that operates on a project.
 */
public abstract class ProjectAction extends AbstractAction implements
        ContextAwareAction {
    private static final long serialVersionUID = 1;
    private final boolean hideIfDisabled;

    /**
     * @param hideIfDisabled if true and the action is disabled, it will not
     *     show in popup menus
     */
    public ProjectAction(boolean hideIfDisabled) {
        this.hideIfDisabled = hideIfDisabled;
    }

    public void actionPerformed(ActionEvent e) {
        Lookup context = Utilities.actionsGlobalContext();
        Project p = context.lookup(Project.class);
        if (p != null && isEnabledFor(p)) {
            perform(p);
        }
    }

    public Action createContextAwareInstance(Lookup context) {
        return new ContextAction(context);
    }

    /**
     * Enables/disables this action.
     *
     * @param p a Project
     * @return true = the action is enabled for this project
     */
    protected abstract boolean isEnabledFor(Project p);

    /**
     * Computes a label for this action
     *
     * @param p selected project
     * @return computed label
     */
    protected abstract String labelFor(Project p);

    protected String iconFor(Project p) {
        return null;
    }

    /**
     * Performs the action
     *
     * @param project selected project
     */
    protected abstract void perform(Project project);

    private final class ContextAction extends AbstractAction implements
            Presenter.Popup {
        private static final long serialVersionUID = 1;
        private final Project p;

        public ContextAction(Lookup context) {
            Project _p = context.lookup(Project.class);
            p = (_p != null && isEnabledFor(_p)) ? _p : null;
        }

        public void actionPerformed(ActionEvent e) {
            perform(p);
        }

        public JMenuItem getPopupPresenter() {
            class Presenter extends JMenuItem implements DynamicMenuContent {
                private static final long serialVersionUID = 1;

                public Presenter() {
                    super(ContextAction.this);
                }

                public JComponent[] getMenuPresenters() {
                    JComponent[] r = new JComponent[0];
                    if (p != null) {
                        if (!hideIfDisabled || isEnabledFor(p)) {
                            Mnemonics.setLocalizedText(this, labelFor(p));
                            r = new JComponent[] {this};
                        }
                    }
                    return r;
                }

                public JComponent[] synchMenuPresenters(JComponent[] items) {
                    return getMenuPresenters();
                }
            }
            return new Presenter();
        }
    }
}