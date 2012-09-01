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
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import org.netbeans.api.project.Project;
import org.openide.WizardDescriptor;
import org.openide.filesystems.FileObject;
import org.openide.util.HelpCtx;
import org.openide.util.NbBundle;

/**
 * New module.
 * 
 * @author Tomasz.Slota@Sun.COM
 * @author see https://gwt4nb.dev.java.net/
 */
public class NewModulePanel implements WizardDescriptor.Panel<WizardDescriptor>,
        WizardDescriptor.FinishablePanel<WizardDescriptor> {
    private Project project;
    private NewModulePanelVisual component;
    private WizardDescriptor wizardDescriptor;
    private final FileObject dir;
    
    /** 
     * @param project web project
     * @param dir directory for the new files or null
     */
    public NewModulePanel(Project project, FileObject dir) {
        if (project == null)
            throw new NullPointerException("project == null"); // NOI18N
        this.project = project;
        this.dir = dir;
    }
    
    public Component getComponent() {
        if (component == null){
            component = new NewModulePanelVisual(this);
        }
        
        return component;
    }
    
    public Project getProject(){
        return project;
    }
    
    public HelpCtx getHelp() {
        return new HelpCtx(NewModulePanel.class);
    }
    
    public void readSettings(WizardDescriptor settings) {
        wizardDescriptor = settings;
        wizardDescriptor.putProperty("NewProjectWizard_Title",  // NOI18N
                NbBundle.getMessage(NewModulePanel.class, "NewMod")); // NOI18N
    }
    
    public void storeSettings(WizardDescriptor settings) {
        wizardDescriptor = settings;

        wizardDescriptor.putProperty(
                NewModuleWizardIterator.MODULE_NAME_PROPERTY,
                component.getModuleName());
        wizardDescriptor.putProperty(
                NewModuleWizardIterator.CREATE_CLIENT_PACKAGE_PROPERTY,
                component.getCreateClientPackage());
        wizardDescriptor.putProperty(
                NewModuleWizardIterator.CREATE_SERVER_PACKAGE_PROPERTY,
                component.getCreateServerPackage());
        wizardDescriptor.putProperty(
                NewModuleWizardIterator.CREATE_PUBLIC_PACKAGE_PROPERTY,
                component.getCreatePublicPackage());
    }
    
    public boolean isValid() {
        String ERR_MSG = "WizardPanel_errorMessage"; // NOI18N
        
        GWTProjectInfo pi =
                GWTProjectInfo.get(project);
        if (pi == null) {
            wizardDescriptor.putProperty(ERR_MSG,
                    NbBundle.getMessage(NewModulePanel.class, 
                    "CannotCreateSvc")); // NOI18N
            return false;
        }
        
        if (component != null) {
            if (!GWT4NBUtil.isValidGWTModuleName(component.getModuleName())) {
                wizardDescriptor.putProperty(ERR_MSG,
                        NbBundle.getMessage(NewModulePanel.class, 
                        "InvModName")); // NOI18N
                return false;
            }
            FileObject sourcesDir = pi.getSourcesDir();
            if (sourcesDir.getFileObject(component.getModuleName().replace(
                    '.', '/') +
                    ".gwt.xml") != null) { // NOI18N
                wizardDescriptor.putProperty(ERR_MSG,
                        NbBundle.getMessage(NewModulePanel.class, 
                        "ModuleExists")); // NOI18N
                return false;
            }
        }
        
        wizardDescriptor.putProperty(ERR_MSG, null);
        
        return true;
    }
    
    private final Set<ChangeListener> listeners = new CopyOnWriteArraySet<ChangeListener>();
    
    public final void addChangeListener(final ChangeListener l) {
        if(l != null) {
            listeners.add(l);
        }
    }
    
    public final void removeChangeListener(final ChangeListener l) {
        if(l != null) {
            listeners.remove(l);
        }
    }
    
    protected final void fireChangeEvent() {
        final ChangeEvent ev = new ChangeEvent(this);

        for(final ChangeListener l : listeners) {
            l.stateChanged(ev);
        }
    }
    
    public boolean isFinishPanel() {
        return true;
    }
}
