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
 * Controller for the "interface extends Constants"
 * 
 * @author Tomasz.Slota@Sun.COM
 * @author see https://github.com/gwt4nb/gwt4nb/
 */
public class NewConstantsPanel implements WizardDescriptor.Panel<WizardDescriptor>,
        WizardDescriptor.FinishablePanel<WizardDescriptor> {
    
    private Project project;
    private NewConstantsPanelVisual component;
    private WizardDescriptor wizardDescriptor;
    private final FileObject dir;
    
    /** 
     * Creates a new instance of NewServicePanel
     *
     * @param project web project
     * @param dir directory for the new files or null
     */
    public NewConstantsPanel(Project project, FileObject dir) {
        if (project == null)
            throw new NullPointerException("project == null"); // NOI18N
        this.project = project;
        this.dir = dir;
    }
    
    public Component getComponent() {
        if (component == null){
            component = new NewConstantsPanelVisual(this);
        }
        
        return component;
    }
    
    public Project getProject(){
        return project;
    }
    
    public HelpCtx getHelp() {
        return new HelpCtx(NewConstantsPanel.class);
    }
    
    public void readSettings(WizardDescriptor settings) {
        wizardDescriptor = settings;
        wizardDescriptor.putProperty("NewProjectWizard_Title", // NOI18N
                NbBundle.getMessage(NewConstantsPanel.class,
                "NewConst")); // NOI18N
    }
    
    public void storeSettings(WizardDescriptor settings) {
        wizardDescriptor = settings;

        wizardDescriptor.putProperty(
                NewConstantsWizardIterator.INTERFACE_NAME_PROPERTY,
                component.getInterfaceName());
    }
    
    private boolean IsFileFoundInChildren(FileObject parent, String fileName){
        for (FileObject tmp : parent.getChildren()){
            if(tmp.isFolder()){
                if(IsFileFoundInChildren(tmp,fileName)){
                    return true;
                }
            }else if(tmp.getName().equals(component.getInterfaceName())&&
                    tmp.getExt().equals("java")){ // NOI18N
                return true;
            }
            
        }
        return false;
    }
    
    public boolean isValid() {
        String ERR_MSG = "WizardPanel_errorMessage"; // NOI18N
        
        if (!GWTProjectInfo.isGWTProject(project)){
            wizardDescriptor.putProperty(ERR_MSG,
                    NbBundle.getMessage(NewConstantsPanel.class, 
                    "CannotCreateSvc")); // NOI18N
            
            return false;
        }
        
        if (component != null){
            if (!GWT4NBUtil.isValidJavaIdentifier(component.getInterfaceName())){
                wizardDescriptor.putProperty(ERR_MSG,
                        NbBundle.getMessage(NewConstantsPanel.class, 
                        "InvId")); // NOI18N
                
                return false;
            }
            
            if (dir.getFileObject(component.getInterfaceName(), 
                    "java") != null) { // NOI18N
                wizardDescriptor.putProperty(ERR_MSG,
                        NbBundle.getMessage(NewConstantsPanel.class, 
                        "FileExists"));  // NOI18N
                return false;
            }

            if (dir.getFileObject(component.getInterfaceName(),
                    "properties") != null) { // NOI18N
                wizardDescriptor.putProperty(ERR_MSG,
                        NbBundle.getMessage(NewConstantsPanel.class, 
                        "PropExists")); // NOI18N
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
