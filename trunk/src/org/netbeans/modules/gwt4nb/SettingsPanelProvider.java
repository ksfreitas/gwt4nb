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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;
import java.util.Properties;
import javax.swing.JComponent;
import org.netbeans.api.project.Project;
import org.netbeans.spi.project.support.ant.EditableProperties;
import org.netbeans.spi.project.ui.support.ProjectCustomizer;
import org.netbeans.spi.project.ui.support.ProjectCustomizer.Category;
import org.openide.util.Lookup;
import org.openide.util.NbBundle;

/**
 * Provider for GWT project settings panel.
 */
public class SettingsPanelProvider implements ProjectCustomizer.CompositeCategoryProvider {
    /**
     * Creates a provider for the panel
     *
     * @return created provider
     */
    public static SettingsPanelProvider create() {
        return new SettingsPanelProvider();
    }

    @Override
    public Category createCategory(Lookup lookup) {
        Project prj = lookup.lookup(Project.class);
        ProjectCustomizer.Category r;
        if (GWTProjectInfo.isAntGWTProject(prj)) {
            r = ProjectCustomizer.Category.create(NbBundle.getMessage(
                    SettingsPanelProvider.class, "GWT"), // NOI18N
                    NbBundle.getMessage(
                            SettingsPanelProvider.class, "GWT"), // NOI18N
                    null, (Category[]) null);
        } else {
            r = null;
        }
        return r;
    }

    @Override
    public JComponent createComponent(Category c, Lookup lookup) {
        Project prj = lookup.lookup(Project.class);
        final SettingsPanel sp = new SettingsPanel();
        if (prj != null) {
            final GWTProjectInfo pi = GWTProjectInfo.get(prj);
            if (pi != null && !pi.isMaven()) {
                final EditableProperties ep = pi.getProperties();
                Properties p = new Properties();
                for (Map.Entry<String, String> e : ep.entrySet()) {
                    p.setProperty(e.getKey(), e.getValue());
                }
                sp.fillPanel(p);

                c.setStoreListener(new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        Properties props = new Properties();
                        sp.fillData(props);
                        for (Map.Entry<Object, Object> me : props.entrySet()) {
                            ep.setProperty((String) me.getKey(),
                                    (String) me.getValue());
                        }
                        pi.setProperties(ep);
                    }
                });
            }
        }
        return sp;
    }
}
