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

import java.io.File;
import org.netbeans.api.project.Project;
import org.netbeans.api.project.FileOwnerQuery;
import org.netbeans.modules.web.api.webmodule.ExtenderController;
import org.netbeans.modules.web.api.webmodule.WebModule;
import org.netbeans.modules.web.spi.webmodule.WebFrameworkProvider;
import org.netbeans.modules.web.spi.webmodule.WebModuleExtender;
import org.openide.filesystems.FileUtil;
import org.openide.util.NbBundle;

/**
 * Provider for the GWT framework in web applications.
 *
 * @author Tomas.Zezula@Sun.COM
 * @author Tomasz.Slota@Sun.COM
 * @author see https://github.com/gwt4nb/gwt4nb/
 */
public class GWTFrameworkProvider extends WebFrameworkProvider {
    /** 
     * Creates a new instance of GWTFrameworkProvider
     */
    public GWTFrameworkProvider() {
        super(NbBundle.getMessage(GWTFrameworkProvider.class, "GWT"), // NOI18N
                NbBundle.getMessage(GWTFrameworkProvider.class,
                "AddSupport")); // NOI18N
    }

    @Override
    public WebModuleExtender createWebModuleExtender(WebModule wm,
            ExtenderController ec) {
        return new GWTWebModuleExtender(wm, ec, this);
    }
    
    @Override
    public boolean isInWebModule(WebModule webModule) {
        Project project = FileOwnerQuery.getOwner(webModule.getDocumentBase());
        return project != null && GWTProjectInfo.isGWTProject(project);
    }

    public File[] getConfigurationFiles(WebModule webModule) {
        Project project = FileOwnerQuery.getOwner(webModule.getDocumentBase());
        File projectDir = FileUtil.toFile(project.getProjectDirectory());
        return new File[]{new File(projectDir,
                "nbproject/" + GWTProjectInfo.GWT_PROPERTIES)}; // NOI18N
    }
}
