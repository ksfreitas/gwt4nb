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

import java.io.IOException;

import java.util.Map;
import java.util.Set;
import java.util.HashMap;
import java.util.LinkedHashSet;

import java.util.StringTokenizer;
import java.util.logging.Level;
import javax.swing.event.ChangeListener;

import org.netbeans.api.project.Project;

import org.netbeans.modules.gwt4nb.GWTProjectInfo;

import org.netbeans.modules.gwt4nb.GWT4NBUtil;
import org.netbeans.modules.j2ee.dd.api.web.WebApp;
import org.netbeans.modules.j2ee.dd.api.web.Servlet;
import org.netbeans.modules.j2ee.dd.api.web.DDProvider;
import org.netbeans.modules.j2ee.dd.api.web.ServletMapping;

import org.netbeans.modules.web.api.webmodule.WebModule;

import org.openide.WizardDescriptor;
import org.openide.WizardDescriptor.Panel;

import org.netbeans.spi.project.ui.templates.support.Templates;


import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;

import org.openide.loaders.DataFolder;
import org.openide.loaders.DataObject;
import org.openide.loaders.DataObjectNotFoundException;

/**
 * @author Tomasz.Slota@Sun.COM
 * @author lemnik@dev.java.net
 * @author see https://github.com/gwt4nb/gwt4nb/
 */
public class NewServiceWizardIterator implements
        WizardDescriptor.InstantiatingIterator<WizardDescriptor> {
    /**
     * Creates a Java package if necessary.
     *
     * @param project a project
     * @param packageName package name
     * @return directory for the package
     */
    public static FileObject ensurePackageExists(
            final Project project,
            final String packageName) throws IOException {
        final FileObject dirSrc = GWTProjectInfo.getSourcesDir(project);

        final String packagePath = packageName.replace('.', '/');

        FileObject packageDir = dirSrc.getFileObject(packagePath);

        if (packageDir == null) {
            final StringTokenizer tokenizer = new StringTokenizer(
                    packagePath, "/"); // NOI18N

            FileObject dir = dirSrc;

            while (tokenizer.hasMoreTokens()) {
                final String name = tokenizer.nextToken();
                final FileObject subdir = dir.getFileObject(name);

                if (subdir == null) {
                    dir = dir.createFolder(name);
                } else {
                    dir = subdir;
                }
            }

            packageDir = dir;
        }

        return packageDir;
    }

    private NewServicePanel panel;
    private WizardDescriptor wizard;
    
    static final String SERVICE_NAME_PROPERTY = "GWTServiceName"; // NOI18N
    static final String SERVICE_SERVLET_MAPPING = "GWTServiceServletMapping"; // NOI18N
    static final String CREATE_USAGE_EXAMPLE = "GWTCreateUsageExample"; // NOI18N
    static final String SERVICE_SUBPACKAGE = "GWTServiceSubpackage"; // NOI18N
    static final String MODULE_NAME_PROPERTY = "GWTModuleName"; // NOI18N
    
    /** Creates a new instance of NewServiceWizardIterator */
    public NewServiceWizardIterator() {
    }

    /**
     * @return selected module name like "org.yournamehere.Main"
     */
    private String getModuleName() {
        return (String) wizard.getProperty(MODULE_NAME_PROPERTY);
    }

    private String getPackageName(final String basePackage) {
        final String subpackage = (String) wizard.getProperty(SERVICE_SUBPACKAGE);

        if (subpackage != null && subpackage.length() > 0) {
            return basePackage + "." + subpackage; // NOI18N
        } else {
            return basePackage;
        }
    }

    private String getServiceName() {
        return (String)wizard.getProperty(SERVICE_NAME_PROPERTY);
    }

    private String getServletPackage() {
        return getPackageName(GWTProjectInfo.getServerPackage(
                getModuleName()));
    }

    private String getClientSyncInterface() {
        return getServiceName();
    }

    private String getClientAsyncInterface() {
        return getServiceName() + "Async"; // NOI18N
    }

    private String getServletClassName() {
        return getServiceName() + "Impl"; // NOI18N
    }

    private FileObject processTemplate(
            final String templateName,
            final DataFolder outputDir,
            final String className,
            final Map<String, ? extends Object> templateParameters)
            throws DataObjectNotFoundException, IOException {

        final DataObject template = DataObject.find(
                FileUtil.getConfigFile(templateName));

        return template.createFromTemplate(
                outputDir,
                className,
                templateParameters).getPrimaryFile();
    }

    private void createWebAppBinding(
            final Project project,
            final String servletMapping) throws IOException {
        String gwtOutputDir = "/" + getModuleName() + "/"; // NOI18N

        // Add servlet entry to web.xml
        WebModule webModule = WebModule.getWebModule(project.getProjectDirectory());
        WebApp webApp = DDProvider.getDefault().
                getDDRoot(webModule.getDeploymentDescriptor());
        if(webApp != null) {
            try {
                Servlet servlet = (Servlet) webApp.createBean("Servlet"); // NOI18N
                servlet.setServletName(getServiceName());
                servlet.setServletClass(getServletPackage() + "." + // NOI18N
                        getServletClassName()); // NOI18N
                webApp.addServlet(servlet);
                ServletMapping mapping = (ServletMapping)
                        webApp.createBean("ServletMapping"); // NOI18N
                mapping.setServletName(getServiceName());
                mapping.setUrlPattern(gwtOutputDir + servletMapping);
                webApp.addServletMapping(mapping);
                webApp.write(webModule.getDeploymentDescriptor());
            } catch(ClassNotFoundException e) {
                GWT4NBUtil.LOGGER.log(Level.SEVERE, "", e); // NOI18N
            }
        }
    }

    public Set<FileObject> instantiate() throws IOException {
        final Project project = Templates.getProject(wizard);

        String clientPackage = getPackageName(
                GWTProjectInfo.getClientPackage(getModuleName()));
        final String servletPackage = getServletPackage();

        final String serviceName = getServiceName();
        final String servletMapping = (String)wizard.getProperty(
                SERVICE_SERVLET_MAPPING);

        final Map<String, Object> templateParameters = new HashMap<String, Object>(3);
        templateParameters.put("url", servletMapping); // NOI18N
        templateParameters.put("gwtversion",  // NOI18N
                GWTProjectInfo.get(project).
                getGWTVersion());
        templateParameters.put("servicename", getServiceName()); // NOI18N
        templateParameters.put("clientpackage", clientPackage); // NOI18N
        templateParameters.put("servletpackage", servletPackage); // NOI18N

        final DataFolder clientPackageDir = DataFolder.findFolder(
                ensurePackageExists(project,
                clientPackage));

        final DataFolder servletPackageDir = DataFolder.findFolder(
                ensurePackageExists(project, servletPackage));

        final Set<FileObject> toOpen = new LinkedHashSet<FileObject>(3);
        
        toOpen.add(processTemplate(
                "Templates/Classes/GWTService.java", // NOI18N
                clientPackageDir,
                getClientSyncInterface(),
                templateParameters));

        toOpen.add(processTemplate(
                "Templates/Classes/GWTServiceAsync.java", // NOI18N
                clientPackageDir,
                getClientAsyncInterface(),
                templateParameters));

        toOpen.add(processTemplate(
                "Templates/Classes/GWTServiceImpl.java", // NOI18N
                servletPackageDir,
                getServletClassName(),
                templateParameters));
        
        createWebAppBinding(project, servletMapping);
        
        // create usage example
        if ((Boolean)wizard.getProperty(CREATE_USAGE_EXAMPLE)){
            toOpen.add(processTemplate(
                    "Templates/Classes/GWTServiceUsageExample.java", // NOI18N
                    clientPackageDir,
                    serviceName + "UsageExample", // NOI18N
                    templateParameters));
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
    
    public Panel<WizardDescriptor> current() {
        return panel;
    }
    
    public String name() {
        return "1"; // NOI18N
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

    public static NewServiceWizardIterator createIterator(){
        return new NewServiceWizardIterator();
    }
    
}
