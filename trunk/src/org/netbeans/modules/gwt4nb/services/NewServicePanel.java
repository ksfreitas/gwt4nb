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
package org.netbeans.modules.gwt4nb.services;

import java.awt.Component;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.concurrent.CopyOnWriteArraySet;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import org.netbeans.api.project.Project;
import org.netbeans.modules.gwt4nb.GWTProjectInfo;
import org.netbeans.modules.gwt4nb.GWT4NBUtil;
import org.openide.WizardDescriptor;
import org.openide.filesystems.FileObject;
import org.openide.util.HelpCtx;
import org.openide.util.NbBundle;

/**
 * Controller for the "GWT RPC Service"
 * 
 * @author Tomasz.Slota@Sun.COM
 */
public class NewServicePanel implements 
        WizardDescriptor.Panel<WizardDescriptor>,
        WizardDescriptor.FinishablePanel<WizardDescriptor> {
    
    private Project project;
    private NewServicePanelVisual component;
    private WizardDescriptor wizardDescriptor;

    /** directory for the client package or null if it does not exist */
    private FileObject foClientPckg;
    
    /** 
     * Creates a new instance of NewServicePanel
     */
    public NewServicePanel(Project project) {
        if (project == null)
            throw new NullPointerException("project == null"); // NOI18N
        this.project = project;
        FileObject dirSrc = GWTProjectInfo.getSourcesDir(project);
        final GWTProjectInfo pi = GWTProjectInfo.get(project);
        List<String> modules = pi.getModules();
        final String module = modules.get(0);
        String clientPackage = GWTProjectInfo.getClientPackage(module);
        foClientPckg = dirSrc.getFileObject(
                clientPackage.replace('.', '/'));
    }
    
    public Component getComponent() {
        if (component == null){
            component = new NewServicePanelVisual(this);
        }
        
        return component;
    }
    
    public Project getProject(){
        return project;
    }
    
    public HelpCtx getHelp() {
        return new HelpCtx(NewServicePanel.class);
    }
    
    public void readSettings(WizardDescriptor settings) {
        wizardDescriptor = settings;
        wizardDescriptor.putProperty("NewProjectWizard_Title",  // NOI18N
                NbBundle.getMessage(NewServicePanel.class, "NewSvc")); // NOI18N
    }
    
    public void storeSettings(WizardDescriptor settings) {
        wizardDescriptor = settings;
        wizardDescriptor.putProperty(NewServiceWizardIterator.SERVICE_NAME_PROPERTY,
                component.getServiceName());
        wizardDescriptor.putProperty(NewServiceWizardIterator.SERVICE_SERVLET_MAPPING,
                component.getServletMapping());
        wizardDescriptor.putProperty(NewServiceWizardIterator.CREATE_USAGE_EXAMPLE, 
                component.createUsageExample());
        wizardDescriptor.putProperty(NewServiceWizardIterator.SERVICE_SUBPACKAGE,
                component.getServiceSubpackage());
        wizardDescriptor.putProperty(NewServiceWizardIterator.MODULE_NAME_PROPERTY,
                component.getModuleName());
    }
    
    private boolean isFileFoundInChildren(FileObject parent, String fileName){
        for (FileObject tmp: parent.getChildren()){
            if (tmp.isFolder()) {
                if (isFileFoundInChildren(tmp, fileName)){
                    return true;
                }
            } else if(tmp.getName().equals(component.getServiceName()) &&
                    tmp.getExt().equals("java")) { // NOI18N
                return true;
            }
        }
        return false;
    }
    
    public boolean isValid() {
        String ERR_MSG = "WizardPanel_errorMessage"; // NOI18N
        
        if (!GWTProjectInfo.isGWTProject(project)){
            wizardDescriptor.putProperty(ERR_MSG,
                    NbBundle.getMessage(NewServicePanel.class, 
                    "CannotCreateSvc")); // NOI18N
            
            return false;
        }
        
        if (component != null){
            if (!GWT4NBUtil.isValidJavaIdentifier(component.getServiceName())){
                wizardDescriptor.putProperty(ERR_MSG,
                        NbBundle.getMessage(NewServicePanel.class, 
                        "InvId")); // NOI18N
                
                return false;
            }
            
            StringTokenizer strTok = new StringTokenizer(
                    component.getServiceSubpackage(), "."); // NOI18N
            while(strTok.hasMoreElements()){
                String tmp = strTok.nextToken();
                if(!tmp.equals("") && !GWT4NBUtil.isValidJavaIdentifier(tmp)){ // NOI18N
                    wizardDescriptor.putProperty(ERR_MSG,
                            NbBundle.getMessage(NewServicePanel.class, 
                            "InvId")); // NOI18N
                    
                    return false;
                }
            }
            
            if (foClientPckg != null &&
                    isFileFoundInChildren(foClientPckg,
                    component.getServiceName())){
                wizardDescriptor.putProperty(ERR_MSG,
                        NbBundle.getMessage(NewServicePanel.class, 
                        "FileExists"));  // NOI18N
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
