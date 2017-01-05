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
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.List;

import org.netbeans.api.project.Project;
import org.netbeans.api.project.FileOwnerQuery;

import org.openide.filesystems.FileObject;

import org.openide.loaders.DataObject;

import org.openide.nodes.Node;
import org.openide.util.Exceptions;

import org.openide.util.HelpCtx;
import org.openide.util.NbBundle;

import org.openide.util.actions.CookieAction;

import org.apache.tools.ant.module.api.support.ActionUtils;
import org.openide.filesystems.FileUtil;
import org.openide.util.Lookup;

/**
 * Run a GWT project in hosted mode.
 */
public final class HostedModeAction extends CookieAction {
    private static final long serialVersionUID = 1;
    
    @SuppressWarnings("rawtypes")
    protected void performAction(Node[] activatedNodes) {
        Project p = activatedNodes[0].getLookup().lookup(Project.class);
        if (p == null) {
            DataObject d = activatedNodes[0].getCookie(DataObject.class);
            p = FileOwnerQuery.getOwner(d.getPrimaryFile());
        }
        if (p != null) {
            GWTProjectInfo pi = GWTProjectInfo.get(p);
            if (pi.isMaven()) {
                /*
                 the corresponding Java code:
                 RunConfig rc = RunUtils.createRunConfig(
                        FileUtil.toFile(p.getProjectDirectory()), p,
                        "Hosted Mode", Collections.singletonList("gwt:debug"));
                 RunUtils.executeMaven(rc);
                 */
                try {
                    ClassLoader syscl =
                            Lookup.getDefault().
                            lookup(ClassLoader.class);
                    Class runUtils =
                            syscl.loadClass("org.netbeans.modules.maven.api.execute.RunUtils"); // NOI18N
                    Class runConfig =
                            syscl.loadClass("org.netbeans.modules.maven.api.execute.RunConfig"); // NOI18N
                    Method createRunConfig = runUtils.getMethod("createRunConfig", // NOI18N
                            new Class[] {File.class, Project.class, String.class,
                            List.class});
                    Object rc = createRunConfig.invoke(null,
                            FileUtil.toFile(p.getProjectDirectory()),
                            p, NbBundle.getMessage(HostedModeAction.class, 
                            "HostedMode"), // NOI18N
                            Collections.singletonList("gwt:debug")); // NOI18N
                    Method executeMaven = runUtils.getMethod(
                            "executeMaven", // NOI18N
                            new Class[] {runConfig});
                    executeMaven.invoke(null, rc);
                } catch (Exception ex) {
                    Exceptions.printStackTrace(ex);
                }
            } else {
                FileObject buildFo = p.getProjectDirectory().
                        getFileObject("build.xml"); // NOI18N

                try {
                    ActionUtils.runTarget(buildFo, new String[] {
                        "debug-connect-gwt-shell-hosted"}, null); // NOI18N
                } catch(IllegalArgumentException ex) {
                    GWT4NBUtil.unexpectedException(ex);
                } catch(IOException ex) {
                    GWT4NBUtil.unexpectedException(ex);
                }
            }
        }
    }

    protected int mode() {
        return CookieAction.MODE_EXACTLY_ONE;
    }

    public String getName() {
        return NbBundle.getMessage(HostedModeAction.class,
                "CTL_HostedModeAction"); // NOI18N
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    protected Class<Node.Cookie>[] cookieClasses() {
        return new Class[] {
            DataObject.class
        };
    }

    @Override
    protected boolean enable(Node[] node) {
        if (node == null || node.length < 1) {
            return false;
        }
        
        Project p = node[0].getLookup().lookup(Project.class);
        if (p == null) {
            DataObject dataObject = node[0].getCookie(DataObject.class);
            if (dataObject != null) {
                p = FileOwnerQuery.getOwner(dataObject.getPrimaryFile());
            }
        }

        return p != null && GWTProjectInfo.isGWTProject(p);
    }

    @Override
    protected String iconResource() {
        return "org/netbeans/modules/gwt4nb/resources/debug_in_hosted_mode.png"; // NOI18N
    }

    public HelpCtx getHelpCtx() {
        return HelpCtx.DEFAULT_HELP;
    }

    @Override
    protected boolean asynchronous() {
        return false;
    }
}

