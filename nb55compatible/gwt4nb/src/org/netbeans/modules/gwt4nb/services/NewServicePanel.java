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
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.StringTokenizer;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import org.netbeans.api.project.Project;
import org.netbeans.modules.gwt4nb.GWTProjectInfo;
import org.netbeans.modules.gwt4nb.Util;
import org.openide.WizardDescriptor;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;
import org.openide.util.HelpCtx;

/**
 *
 * @author Tomasz.Slota@Sun.COM
 */
public class NewServicePanel implements WizardDescriptor.Panel,
        WizardDescriptor.FinishablePanel {
    
    private Project project;
    private NewServicePanelVisual component;
    private WizardDescriptor wizardDescriptor;
    private FileObject foClientPckg;
    
    /** Creates a new instance of NewServicePanel */
    public NewServicePanel(Project project) {
        this.project = project;
        FileObject dirSrc = GWTProjectInfo.getSourcesDir(project);
        foClientPckg =  dirSrc.getFileObject(GWTProjectInfo.getClientPackage(project).replace('.', '/'));
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
    
    public void readSettings(Object settings) {
        wizardDescriptor = (WizardDescriptor) settings;
        wizardDescriptor.putProperty("NewProjectWizard_Title", "New GWT Service");
    }
    
    public void storeSettings(Object settings) {
        wizardDescriptor = (WizardDescriptor) settings;
        
        wizardDescriptor.putProperty(NewServiceWizardIterator.SERVICE_NAME_PROPERTY,
                component.getServiceName());
        
        wizardDescriptor.putProperty(NewServiceWizardIterator.SERVICE_SERVLET_MAPPING,
                component.getServletMapping());
        
        wizardDescriptor.putProperty(NewServiceWizardIterator.CREATE_USAGE_EXAMPLE, 
                component.createUsageExample());
        
        wizardDescriptor.putProperty(NewServiceWizardIterator.SERVICE_SUBPACKAGE,
                component.getServiceSubpackage());
    }
    
    private boolean IsFileFoundInChildren(FileObject parent, String fileName){
        for(FileObject tmp : parent.getChildren()){
            
            if(tmp.isFolder()){
                if(IsFileFoundInChildren(tmp,fileName)){
                    return true;
                }
            }else if(tmp.getName().equals(component.getServiceName())&&
                    tmp.getExt().equals("java")){
                return true;
            }
            
        }
        return false;
    }
    
    public boolean isValid() {
        String ERR_MSG = "WizardPanel_errorMessage";
        
        if (!GWTProjectInfo.isGWTProject(project)){
            wizardDescriptor.putProperty(ERR_MSG,
                    "Cannot Create GWT Service in a non-GWT Project");
            
            return false;
        }
        
        if (component != null){
            if (!Util.isValidJavaIdentifier(component.getServiceName())){
                wizardDescriptor.putProperty(ERR_MSG,
                        "Invalid identifier");
                
                return false;
            }
            
            StringTokenizer strTok = new StringTokenizer(component.getServiceSubpackage(),".");
            while(strTok.hasMoreElements()){
                String tmp = strTok.nextToken();
                if(!tmp.equals("") && !Util.isValidJavaIdentifier(tmp)){
                    wizardDescriptor.putProperty(ERR_MSG,
                            "Invalid identifier");
                    
                    return false;
                }
            }
            
            if(IsFileFoundInChildren(foClientPckg,component.getServiceName())){
                wizardDescriptor.putProperty(ERR_MSG,
                        "File already exists, choose a different name"); 
                return false;
            }
        }
        
        wizardDescriptor.putProperty(ERR_MSG, null);
        
        return true;
    }
    
    private final Set<ChangeListener> listeners = new HashSet(1);
    
    public final void addChangeListener(ChangeListener l) {
        synchronized (listeners) {
            listeners.add(l);
        }
    }
    
    public final void removeChangeListener(ChangeListener l) {
        synchronized (listeners) {
            listeners.remove(l);
        }
    }
    
    protected final void fireChangeEvent() {
        Iterator it;
        synchronized (listeners) {
            it = new HashSet(listeners).iterator();
        }
        ChangeEvent ev = new ChangeEvent(this);
        while (it.hasNext()) {
            ((ChangeListener) it.next()).stateChanged(ev);
        }
    }
    
    public boolean isFinishPanel() {
        return true;
    }
    
}
