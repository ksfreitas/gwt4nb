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
package org.netbeans.modules.gwt4nb;

import java.io.File;
import javax.swing.JFileChooser;
import org.netbeans.api.project.Project;
import org.netbeans.modules.web.api.webmodule.WebModule;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;
import org.openide.awt.StatusDisplayer;
import org.openide.util.NbBundle;
import org.openide.windows.WindowManager;

/**
 * Changes GWT SDK location for a project.
 */
public class ChangeGWTSDKAction extends ProjectAction {
    private static final long serialVersionUID = 1;

    /**
     * -
     */
    public ChangeGWTSDKAction() {
        super(true);
    }

    protected boolean isEnabledFor(Project p) {
        GWTProjectInfo pi = GWTProjectInfo.get(p);
        return pi != null && !pi.isMaven();
    }

    protected String labelFor(Project p) {
        return NbBundle.getMessage(ChangeGWTSDKAction.class, 
                "ChangeSDK"); // NOI18N
    }

    protected void perform(Project project) {
        GWTProjectInfo pi = GWTProjectInfo.get(project);
        if (pi == null || pi.isMaven())
            return;

        File oldGWTDir = GWT4NBUtil.getProjectGWTDir(project);
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle(NbBundle.getMessage(
                getClass(), "LBL_SelectGWTLocation")); // NOI18N
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        chooser.setSelectedFile(oldGWTDir);
        if (JFileChooser.APPROVE_OPTION != chooser.showOpenDialog(
                WindowManager.getDefault().getMainWindow())) {
            return;
        }

        File gwtRoot = chooser.getSelectedFile();
        if (gwtRoot.equals(oldGWTDir))
            return;

        String gwtVersion = GWT4NBUtil.findGWTVersion(gwtRoot);
        if (gwtVersion == null) {
            DialogDisplayer.getDefault().notify(
                    new NotifyDescriptor.Message(
                    NbBundle.getMessage(ChangeGWTSDKAction.class, 
                    "InvLoc"), // NOI18N
                    NotifyDescriptor.ERROR_MESSAGE));
            return;
        }

        MyWorker myWorker = new MyWorker(project, gwtRoot);
        myWorker.start();
    }

    private class MyWorker extends GWT4NBSwingWorker {
        private final File gwtRoot;
        private final Project project;

        MyWorker(Project project, File gwtRoot) {
            this.gwtRoot = gwtRoot;
            this.project = project;
        }

        public Object construct() {
            WebModule webModule = WebModule.getWebModule(
                    project.getProjectDirectory());
            GWTWebModuleExtender.upgradeGWTVersion(webModule,
                    gwtRoot);
            return null;
        }

        public void finished() {
            StatusDisplayer.getDefault().setStatusText(
                    NbBundle.getMessage(ChangeGWTSDKAction.class, 
                    "LocSucc")); // NOI18N
        }
    }
}