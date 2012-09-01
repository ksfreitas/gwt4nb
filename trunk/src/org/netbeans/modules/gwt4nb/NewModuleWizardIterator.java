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

import java.io.IOException;
import java.util.Set;
import java.util.LinkedHashSet;
import javax.swing.event.ChangeListener;
import org.netbeans.api.project.Project;
import org.openide.WizardDescriptor;
import org.openide.WizardDescriptor.Panel;
import org.netbeans.spi.project.ui.templates.support.Templates;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;

/**
 * New module.
 *
 * @author Tomasz.Slota@Sun.COM
 * @author lemnik@dev.java.net
 * @author see https://gwt4nb.dev.java.net/
 */
public class NewModuleWizardIterator implements
        WizardDescriptor.InstantiatingIterator<WizardDescriptor> {
    private NewModulePanel panel;
    private WizardDescriptor wizard;
    
    static final String MODULE_NAME_PROPERTY = "moduleName"; // NOI18N
    static final String CREATE_CLIENT_PACKAGE_PROPERTY = "createClientPackage"; // NOI18N
    static final String CREATE_SERVER_PACKAGE_PROPERTY = "createServerPackage"; // NOI18N
    static final String CREATE_PUBLIC_PACKAGE_PROPERTY = "createPublicPackage"; // NOI18N
    
    public Set<FileObject> instantiate() throws IOException {
        final Set<FileObject> toOpen = new LinkedHashSet<FileObject>(2);
        
        String module = (String) wizard.getProperty(MODULE_NAME_PROPERTY);
        boolean createClientPackage =
                (Boolean) wizard.getProperty(CREATE_CLIENT_PACKAGE_PROPERTY);
        boolean createServerPackage =
                (Boolean) wizard.getProperty(CREATE_SERVER_PACKAGE_PROPERTY);
        boolean createPublicPackage =
                (Boolean) wizard.getProperty(CREATE_PUBLIC_PACKAGE_PROPERTY);

        Project p = Templates.getProject(wizard);
        if (p != null) {
            GWTProjectInfo info = GWTProjectInfo.get(p);
            if (info != null) {
                FileObject sourcesDir = info.getSourcesDir();
                String modulePackage =
                        GWTProjectInfo.getModulePackage(module);
                int pos = module.lastIndexOf('.');
                FileObject moduleDir = FileUtil.createFolder(sourcesDir,
                        modulePackage.replace('.', '/'));
                Version v = info.getGWTVersion();
                String res;
                if (v.compareTo(new Version(2, 0)) >= 0)
                    res = "/org/netbeans/modules/gwt4nb/Module_2.0.gwt.xml"; // NOI18N
                else
                    res = "/org/netbeans/modules/gwt4nb/Module_1.7.gwt.xml"; // NOI18N
                GWT4NBUtil.copyResource(
                        res,
                        moduleDir.createData(module.substring(pos + 1) +
                        ".gwt.xml"), new String[0], new String[0]); // NOI18N
                if (createClientPackage)
                    FileUtil.createFolder(moduleDir, "client"); // NOI18N
                if (createServerPackage)
                    FileUtil.createFolder(moduleDir, "server"); // NOI18N
                if (createPublicPackage)
                    FileUtil.createFolder(moduleDir, "public"); // NOI18N
                info.registerModule(module);
            }
        }

        return toOpen;
    }
    
    public void initialize(WizardDescriptor wizard) {
        this.wizard = wizard;
        panel = new NewModulePanel(Templates.getProject(wizard),
                Templates.getTargetFolder(wizard));
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

    public static NewModuleWizardIterator createIterator(){
        return new NewModuleWizardIterator();
    }
}
