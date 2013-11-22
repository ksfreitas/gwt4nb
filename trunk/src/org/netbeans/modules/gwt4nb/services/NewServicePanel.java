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
import static org.netbeans.modules.gwt4nb.services.Bundle.*;
import org.openide.util.NbBundle.Messages;

/**
 * Controller for the "GWT RPC Service"
 * 
 * @author Tomasz.Slota@Sun.COM
 * @author benno.markiewicz@googlemail.com (contributor)
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
        if (!modules.isEmpty()) {
            final String module = modules.get(0);
            String clientPackage = GWTProjectInfo.getClientPackage(module);
            foClientPckg = dirSrc.getFileObject(
                    clientPackage.replace('.', '/'));
        } else {
            foClientPckg = null;
        }
    }
    
    @Override
    public Component getComponent() {
        if (component == null){
            component = new NewServicePanelVisual(this);
        }
        
        return component;
    }
    
    public Project getProject(){
        return project;
    }
    
    @Override
    public HelpCtx getHelp() {
        return new HelpCtx(NewServicePanel.class);
    }
    
    @Override
    public void readSettings(WizardDescriptor settings) {
        wizardDescriptor = settings;
        wizardDescriptor.putProperty("NewProjectWizard_Title",  // NOI18N
                NbBundle.getMessage(NewServicePanel.class, "NewSvc")); // NOI18N
    }
    
    @Override
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
    
    @Messages(
            {
                "error.nomodule=No module found",
                "error.InvIdServiceName=Invalid identifier for service name",
                "error.InvIdServiceSubPackage=Invalid identifier for subpackage",
                "error.CannotCreateSvc=Cannot create GWT Service in a non-GWT Project",
                "error.FileExists=File already exists, choose a different name"
            }
    )
    @Override
    public boolean isValid() {
        String ERR_MSG = "WizardPanel_errorMessage"; // NOI18N

        if (!GWTProjectInfo.isGWTProject(project)) {
            wizardDescriptor.putProperty(ERR_MSG, error_CannotCreateSvc());
            return false;
        }

        if (component != null) {
            if (null == component.getModuleName()) {
                wizardDescriptor.putProperty(ERR_MSG, error_nomodule());
                return false;
            }
            if (!GWT4NBUtil.isValidJavaIdentifier(component.getServiceName())) {
                wizardDescriptor.putProperty(ERR_MSG, error_InvIdServiceName());
                return false;
            }

            StringTokenizer strTok = new StringTokenizer(
                    component.getServiceSubpackage(), "."); // NOI18N
            while (strTok.hasMoreElements()) {
                String tmp = strTok.nextToken();
                if (!tmp.isEmpty() && !GWT4NBUtil.isValidJavaIdentifier(tmp)) {
                    wizardDescriptor.putProperty(ERR_MSG, error_InvIdServiceSubPackage());
                    return false;
                }
            }

            if (foClientPckg != null
                    && isFileFoundInChildren(foClientPckg,
                            component.getServiceName())) {
                wizardDescriptor.putProperty(ERR_MSG, error_FileExists());
                return false;
            }
        }

        wizardDescriptor.putProperty(ERR_MSG, null);

        return true;
    }
    
    private final Set<ChangeListener> listeners = new CopyOnWriteArraySet<ChangeListener>();
    
    @Override
    public final void addChangeListener(final ChangeListener l) {
        if(l != null) {
            listeners.add(l);
        }
    }
    
    @Override
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
    
    @Override
    public boolean isFinishPanel() {
        return true;
    }
}
