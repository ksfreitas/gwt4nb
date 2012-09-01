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

package org.netbeans.modules.gwt4nb.services;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.Enumeration;
import java.util.LinkedHashSet;
import java.util.Set;
import javax.swing.event.ChangeListener;
import org.netbeans.api.project.Project;
import org.netbeans.modules.gwt4nb.GWTProjectInfo;
import org.netbeans.modules.gwt4nb.Util;
import org.netbeans.modules.j2ee.dd.api.web.DDProvider;
import org.netbeans.modules.j2ee.dd.api.web.Servlet;
import org.netbeans.modules.j2ee.dd.api.web.ServletMapping;
import org.netbeans.modules.j2ee.dd.api.web.WebApp;
import org.netbeans.modules.web.api.webmodule.WebModule;
import org.openide.WizardDescriptor;
import org.openide.WizardDescriptor.Panel;
import org.netbeans.spi.project.ui.templates.support.Templates;
import org.openide.ErrorManager;
import org.openide.filesystems.FileChangeListener;
import org.openide.filesystems.FileLock;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileStateInvalidException;
import org.openide.filesystems.FileSystem;
import org.openide.filesystems.FileUtil;
import org.openide.filesystems.Repository;
import org.openide.loaders.DataFolder;
import org.openide.loaders.DataObject;

/**
 * @author Tomasz.Slota@Sun.COM
 */
public class NewServiceWizardIterator implements WizardDescriptor.InstantiatingIterator {
    private NewServicePanel panel;
    private WizardDescriptor wizard;
    
    static final String SERVICE_NAME_PROPERTY = "GWTServiceName"; //NOI18N
    static final String SERVICE_SERVLET_MAPPING = "GWTServiceServletMapping"; //NOI18N
    static final String CREATE_USAGE_EXAMPLE = "GWTCreateUsageExample"; //NOI18N
    static final String SERVICE_SUBPACKAGE = "GWTServiceSubpackage"; //NOI18N
    
    /** Creates a new instance of NewServiceWizardIterator */
    public NewServiceWizardIterator() {
    }
    
    public static NewServiceWizardIterator createIterator(){
        return new NewServiceWizardIterator();
    }
    
    public Set instantiate() throws IOException {
        
        Project project = Templates.getProject(wizard);
        String serviceName = (String) wizard.getProperty(SERVICE_NAME_PROPERTY);
        String subpackage = (String) wizard.getProperty(SERVICE_SUBPACKAGE);
                
        FileObject dirSrc = GWTProjectInfo.getSourcesDir(project);
        
        FileObject foDirClient = dirSrc.getFileObject(GWTProjectInfo.getClientPackage(
                project).replace('.', '/'));
        DataFolder dirClient = DataFolder.findFolder(foDirClient);
        if(subpackage.length()>0){
            dirClient = DataFolder.findFolder(FileUtil.createFolder(foDirClient,
                    subpackage.replace(".","/")));
        }
        
                        
        FileObject foDirServer = dirSrc.getFileObject(GWTProjectInfo.getServerPackage(
                project).replace('.', '/'));
        DataFolder dirServer = DataFolder.findFolder(foDirServer);
        if(subpackage.length()>0){
            dirServer = DataFolder.findFolder(FileUtil.createFolder(foDirServer,
                    subpackage.replace(".","/")));
        }
        
        
        DataObject tmpltService = DataObject.find(Repository.getDefault().getDefaultFileSystem().findResource(
                "Templates/Classes/GWTService.java")); //NOI18N
        
        DataObject tmpltServiceImpl = DataObject.find(Repository.getDefault().getDefaultFileSystem().findResource(
                "Templates/Classes/GWTServiceImpl.java")); //NOI18N
        
        DataObject tmpltServiceAsync = DataObject.find(Repository.getDefault().getDefaultFileSystem().findResource(
                "Templates/Classes/GWTServiceAsync.java")); //NOI18N
        
        DataObject doService = tmpltService.createFromTemplate(dirClient, serviceName);
        
        DataObject doServiceImpl = tmpltServiceImpl.createFromTemplate(dirServer,
                serviceName + "Impl"); //NOI18N
        
        Util.replaceInFile(doServiceImpl.getPrimaryFile(),
                new String[]{"__CLIENT_SOURCES_ROOT__", "__SERVICE_NAME__"},
                new String[]{GWTProjectInfo.getClientPackage(project), serviceName});
        
        DataObject doServiceAsync = tmpltServiceAsync.createFromTemplate(dirClient, serviceName + "Async"); //NOI18N
        
        Set toOpen = new LinkedHashSet(3);
        toOpen.add(doService.getPrimaryFile());
        toOpen.add(doServiceImpl.getPrimaryFile());
        toOpen.add(doServiceAsync.getPrimaryFile());
        
        // Add servlet entry to web.xml
        
        String servletName = serviceName; //TODO: allow customizing the name
        String servletMapping = (String) wizard.getProperty(SERVICE_SERVLET_MAPPING);
        WebModule webModule = WebModule.getWebModule(project.getProjectDirectory());
        WebApp webApp = DDProvider.getDefault().getDDRoot(webModule.getDeploymentDescriptor());
        
        if (webApp != null){
            try{
                Servlet servlet = (Servlet)webApp.createBean("Servlet"); //NOI18N
                servlet.setServletName(servletName);
                if(subpackage.length()>0){
                    subpackage = "."+subpackage;
                }
                servlet.setServletClass(GWTProjectInfo.getServerPackage(project)
                        + subpackage + "." + serviceName + "Impl"); //NOI18N
                
                webApp.addServlet(servlet);
                
                ServletMapping mapping = (ServletMapping)webApp.createBean("ServletMapping"); //NOI18N
                mapping.setServletName(servletName);
                mapping.setUrlPattern(servletMapping);
                
                webApp.addServletMapping(mapping);
                
                webApp.write(webModule.getDeploymentDescriptor());
                
            } catch (ClassNotFoundException e){
                ErrorManager.getDefault().notify(e);
            }
        }
        
        // create usage example
        
        if ((Boolean)wizard.getProperty(CREATE_USAGE_EXAMPLE)){
            DataObject tmpltServiceUsageExample = DataObject.find(Repository.getDefault().getDefaultFileSystem().findResource(
                "Templates/Classes/GWTServiceUsageExample.java")); //NOI18N
            
            DataObject doExample = tmpltServiceUsageExample.createFromTemplate(dirClient, serviceName + "UsageExample");
            
            Util.replaceInFile(doExample.getPrimaryFile(),
                new String[]{"__URL_PATTERN__", "__SERVICE_NAME__"},
                new String[]{serviceName.toLowerCase(), serviceName});
            
            toOpen.add(doExample.getPrimaryFile());
        }
        
        return toOpen;
    }
    
    public void initialize(WizardDescriptor wizard) {
        this.wizard = wizard;
        panel = new NewServicePanel(Templates.getProject(wizard));
    }
    
    public void uninitialize(WizardDescriptor wizard) {
        panel = null;
    }
    
    public Panel current() {
        return panel;
    }
    
    public String name() {
        return "1"; //NOI18N
    }
    
    public boolean hasNext() {
        return false;
    }
    
    public boolean hasPrevious() {
        return false;
    }
    
    public void nextPanel() {
        // should never be called
    }
    
    public void previousPanel() {
        // should never be called
    }
    
    public void addChangeListener(ChangeListener l) {
    }
    
    public void removeChangeListener(ChangeListener l) {
    }
    
}
