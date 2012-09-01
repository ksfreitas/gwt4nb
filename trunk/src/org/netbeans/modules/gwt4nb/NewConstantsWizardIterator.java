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
import java.util.Map;
import java.util.Set;
import java.util.HashMap;
import java.util.LinkedHashSet;
import javax.swing.event.ChangeListener;
import org.openide.WizardDescriptor;
import org.openide.WizardDescriptor.Panel;
import org.netbeans.spi.project.ui.templates.support.Templates;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;
import org.openide.loaders.DataFolder;
import org.openide.loaders.DataObject;

/**
 * New com.google.gwt.i18n.client.Constants.
 *
 * @author Tomasz.Slota@Sun.COM
 * @author lemnik@dev.java.net
 * @author see https://github.com/gwt4nb/gwt4nb/
 */
public class NewConstantsWizardIterator implements
        WizardDescriptor.InstantiatingIterator<WizardDescriptor> {
    private NewConstantsPanel panel;
    private WizardDescriptor wizard;
    
    static final String INTERFACE_NAME_PROPERTY = "interfaceName"; // NOI18N
    
    public Set<FileObject> instantiate() throws IOException {
        FileObject targetFolder = Templates.getTargetFolder(wizard);

        final Map<String, Object> templateParameters = new HashMap<String, Object>(3);
        final DataFolder dir = DataFolder.findFolder(targetFolder);

        final Set<FileObject> toOpen = new LinkedHashSet<FileObject>(2);
        
        String intf = (String)wizard.getProperty(INTERFACE_NAME_PROPERTY);

        DataObject template = DataObject.find(
                FileUtil.getConfigFile(
                "Templates/GWT/Constants.java")); // NOI18N
        toOpen.add(template.createFromTemplate(
                dir,
                intf,
                templateParameters).getPrimaryFile());

        template = DataObject.find(
                FileUtil.getConfigFile(
                "Templates/GWT/Constants.properties")); // NOI18N
        toOpen.add(template.createFromTemplate(
                dir,
                intf,
                templateParameters).getPrimaryFile());
        
        return toOpen;
    }
    
    public void initialize(WizardDescriptor wizard) {
        this.wizard = wizard;
        panel = new NewConstantsPanel(Templates.getProject(wizard),
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

    public static NewConstantsWizardIterator createIterator(){
        return new NewConstantsWizardIterator();
    }
}
