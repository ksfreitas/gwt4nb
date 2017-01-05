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

import java.awt.Component;

import java.io.File;

import java.util.Set;

import java.util.concurrent.CopyOnWriteArraySet;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.netbeans.api.project.Project;

import org.netbeans.modules.gwt4nb.settings.GWTSettings;


import org.openide.WizardDescriptor;
import org.openide.WizardValidationException;

import org.openide.util.HelpCtx;
import org.openide.util.NbBundle;

/**
 *
 * @author tomslot
 * @author see https://github.com/gwt4nb/gwt4nb/
 */
public class GWTConfigPanel implements 
        WizardDescriptor.FinishablePanel<Object>,
        WizardDescriptor.ValidatingPanel<Object> {
    private GWTConfigPanelVisual pnlVisual;

    private final Set<ChangeListener> listeners = new CopyOnWriteArraySet<ChangeListener>();

    private WizardDescriptor wizardDescriptor;

    /** a project or null */
    private final Project project;

    /**
     * Creates a new instance of GWTConfigPanel
     * 
     * @param project a project or null
     */
    public GWTConfigPanel(Project project) {
        this.project = project;

        pnlVisual = new GWTConfigPanelVisual(this, project);
        File defaultGWTFolder = null;
        if (project != null) {
            File gwtLocation = GWT4NBUtil.getProjectGWTDir(project);
            if (gwtLocation != null) {
                defaultGWTFolder = gwtLocation;
            }
        } else {
            defaultGWTFolder = GWTSettings.getGWTLocation();
        }


        if (defaultGWTFolder != null) {
            pnlVisual.setGWTFolder(defaultGWTFolder);
        }
    }

    public void enableComponents(boolean enabled) {
        pnlVisual.enableComponents(enabled);
    }

    public Component getComponent() {
        return pnlVisual;
    }

    public HelpCtx getHelp() {
        return null;
    }

    public void readSettings(Object settings) {
        wizardDescriptor = (WizardDescriptor)settings;
    }

    public void storeSettings(Object settings) {
    }

    private void setErrorMessage(String message) {
        if(wizardDescriptor != null) {
            wizardDescriptor.putProperty(
                    "WizardPanel_errorMessage", message); // NOI18N
        }
    }

    public boolean isValid() {
        // Maven projects do not have settings
        if (project != null && GWT4NBUtil.isMavenProject(project))
            return true;

        File gwtFolder = getGwtFolder();

        if(!(gwtFolder.exists() && gwtFolder.isDirectory() &&
                new File(gwtFolder, "gwt-user.jar").exists())) { // NOI18N
            setErrorMessage(NbBundle.getMessage(GWTConfigPanel.class, 
                    "ERROR_Invalid_GWT_Folder")); // NOI18N
            return false;
        }

        boolean validModule = GWT4NBUtil.isValidGWTModuleName(getGWTModule());

        if (!validModule) {
            setErrorMessage(NbBundle.getMessage(GWTConfigPanel.class, 
                    "ERROR_Invalid_GWT_Module_Name")); // NOI18N
            return false;
        }

        setErrorMessage(""); // NOI18N
        return true;
    }

    public boolean isFinishPanel() {
        return true;
    }

    public void validate() throws WizardValidationException {
    }

    public File getGwtFolder() {
        return pnlVisual.getGWTFolder();
    }

    public Project getProject() {
        return pnlVisual.getProject();
    }

    /**
     * @param p a project or null for a new project
     */
    public void setProject(Project p) {
        pnlVisual.setProject(p);
    }

    public String getGWTModule() {
        return pnlVisual.getGWTModule();
    }

    public final void addChangeListener(ChangeListener l) {
        listeners.add(l);
    }

    public final void removeChangeListener(ChangeListener l) {
        listeners.remove(l);
    }

    protected final void fireChangeEvent() {
        final ChangeEvent ev = new ChangeEvent(this);

        for(final ChangeListener listener : listeners) {
            listener.stateChanged(ev);
        }
    }
}
