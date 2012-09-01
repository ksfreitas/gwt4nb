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
 */
package org.netbeans.modules.gwt4nb;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.Properties;
import org.apache.tools.ant.module.api.support.ActionUtils;
import org.netbeans.api.project.Project;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;
import org.openide.filesystems.FileLock;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;
import org.openide.util.Exceptions;
import org.openide.util.NbBundle;

/**
 * Runs GWT dev mode and Google App Engine in the same JVM.
 */
public final class RunDevGAEAction extends ProjectAction {
    private static final long serialVersionUID = 1;

    /**
     * -
     */
    public RunDevGAEAction() {
        super(true);
    }

    public void actionPerformed(ActionEvent e) {
        assert false;
    }

    protected boolean isEnabledFor(Project p) {
        assert p != null;

        return GWTProjectInfo.isGWTProject(p) && isAppEnginePluginUsed(p) &&
                (GWTProjectInfo.get(p).getGWTVersion().getPartsCount() == 2);
    }

    /**
     * this method dose not work properly and is not used currently
     * it tries to write to private.properties .. but almost fails every time
     *
     * A work around is to manually define "appengine.agent" to  path + "/lib/agent/appengine-agent.jar"
     * @param p
     */
    private void ensureAppEngineAgentDefined(Project p) {
        FileObject propertiesFileRead = p.getProjectDirectory().getFileObject(
                "nbproject/private/private.properties"); // NOI18N
        FileObject propertiesFileWrite = p.getProjectDirectory().getFileObject(
                "nbproject/private/private.properties"); // NOI18N
        Properties properties = new Properties();

        try {

            String agent = ""; // NOI18N
            try {
                properties.load(propertiesFileRead.getInputStream());
                String classpath = properties.getProperty(
                        "j2ee.platform.classpath"); // NOI18N
                if (classpath != null) {
                    String[] paths = classpath.split(":"); // NOI18N
                    String path = paths[0];
                    path = path.substring(0, path.lastIndexOf("/lib/")); // NOI18N
                    agent = path + "/lib/agent/appengine-agent.jar"; // NOI18N

                }
            } finally {
                propertiesFileRead.getInputStream().close();
            }

            FileLock lock = null;
            try {
                lock = propertiesFileWrite.lock();
            } catch (IOException ex) {
                Exceptions.printStackTrace(ex);
            }
            try {
                properties.setProperty("appengine.agent", agent); // NOI18N
                properties.store(propertiesFileWrite.getOutputStream(lock), 
                        ""); // NOI18N

            } finally {
                propertiesFileWrite.getOutputStream(lock).close();
                lock.releaseLock();
            }

        } catch (IOException ex) {
            Exceptions.printStackTrace(ex);
        }
    }

    /*
    private void resetClientUrlPart(Project p) throws IOException {
        //build.web.dir=${build.dir}/web
        FileObject propertiesFile = p.getProjectDirectory().getFileObject("nbproject/project.properties");

        Properties properties = new Properties();
        properties.load(propertiesFile.getInputStream());

        String buildDir = properties.getProperty("build.dir");
        String buildWebDir = properties.getProperty("build.web.dir");

        String warDir = buildWebDir.replace("${build.dir}", buildDir);

        FileObject warFolderObject = p.getProjectDirectory().getFileObject(warDir);

        ArrayList<String> htmlPages = new ArrayList<String>();
        for (FileObject fileObject : warFolderObject.getChildren()) {
        }
    }
    */
    
    private boolean isAppEnginePluginUsed(Project p) {
        //Make sure we are using the Appengine plugin
        FileObject propertiesFile = p.getProjectDirectory().
                getFileObject("nbproject/project.properties"); // NOI18N

        if (propertiesFile == null)
            return false;

        Boolean isAppEngine = false;
        Properties properties = new Properties();
        try {
            properties.load(propertiesFile.getInputStream());
            String j2eeServer = properties.getProperty(
                    "j2ee.server.type"); // NOI18N
            if (j2eeServer != null) {
                if (j2eeServer.equals("AppEngine")) { // NOI18N
                    isAppEngine = true;
                }
            }
        } catch (IOException ex) {
            Exceptions.printStackTrace(ex);
        }

        return isAppEngine;
    }

    protected String labelFor(Project p) {
        assert p != null;
        
        // menu item label
        return NbBundle.getMessage(RunDevGAEAction.class, 
                "RunGWTDevGAE"); // NOI18N
    }

    protected void perform(Project p) {
        assert p != null;

        if (p != null) {

            //TODO: Find a better way to write to private.properties file
            //ensureAppEngineAgentDefined(p);

            GWTProjectInfo pi = GWTProjectInfo.get(p);
            if (pi.isMaven()) {
                //Not yet supported
                String msg = NbBundle.getMessage(
                        RunDevGAEAction.class, "MavenUnsupp", // NOI18N
                        FileUtil.getFileDisplayName(p.getProjectDirectory()));
                DialogDisplayer.getDefault().notify(new NotifyDescriptor.Message(msg));

            } else {
                FileObject buildFo = p.getProjectDirectory().
                        getFileObject("build.xml"); // NOI18N

                try {
                    ActionUtils.runTarget(buildFo, new String[]{
                                "gwt-devmode-on-appengine"}, null); // NOI18N
                } catch (IllegalArgumentException ex) {
                    ex.printStackTrace();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
}
