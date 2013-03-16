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

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.netbeans.api.java.project.JavaProjectConstants;
import org.netbeans.api.project.Project;
import org.netbeans.api.project.ProjectInformation;
import org.netbeans.api.project.ProjectUtils;
import org.netbeans.api.project.SourceGroup;
import org.netbeans.api.project.ui.OpenProjects;
import org.netbeans.spi.project.support.ant.EditableProperties;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileSystem;
import org.openide.filesystems.FileSystem.AtomicAction;
import org.openide.modules.ModuleInstall;
import org.openide.util.Exceptions;

/**
 * Module installation class for gwt4nb
 *
 * @author prem
 * @author see https://github.com/gwt4nb/gwt4nb/
 */
public class Gwt4nbModule extends ModuleInstall {
    private final static long serialVersionUID = 1;

    private static final List<PropertyInfo> PROPERTY_INFOS =
            new ArrayList<PropertyInfo>();

    static {
        PROPERTY_INFOS.add(new PropertyInfo("gwt.module", // NOI18N
                "org.yournamehere.Main", new String [] { // NOI18N
                "# The names of the modules to compile (separated by a space character)" // NOI18N
                }));
        PROPERTY_INFOS.add(new PropertyInfo("gwt.output.dir", // NOI18N
                "/org.yournamehere.Main", new String [] { // NOI18N
                "# Folder within the web app context path where the output", // NOI18N
                "# of the GWT module compilation will be stored.", // NOI18N
                "# This setting is only used for GWT 1.5. For newer versions please use", // NOI18N
                "# the rename-to attribute in the GWT module file (.gwt.xml).", // NOI18N
                }));

        PROPERTY_INFOS.add(new PropertyInfo("gwt.compiler.output.style", // NOI18N
                "OBF", new String [] { // NOI18N
                "# Script output style: OBF[USCATED], PRETTY, or DETAILED", // NOI18N
                }));

        PROPERTY_INFOS.add(new PropertyInfo("gwt.compiler.jvmargs", // NOI18N
                "-Xmx256M", new String [] { // NOI18N
                "# Additional JVM arguments for the GWT compiler", // NOI18N
                }));

        PROPERTY_INFOS.add(new PropertyInfo("gwt.compiler.local.workers", // NOI18N
                "1", new String [] { // NOI18N
                "# Specifies the number of local workers to use whe compiling permutations and module(s)", // NOI18N
                }));

        PROPERTY_INFOS.add(new PropertyInfo("gwt.compiler.logLevel", // NOI18N
                "INFO", new String [] { // NOI18N
                "# The level of logging detail: ERROR, WARN, INFO, TRACE, DEBUG,", // NOI18N
                }));

        PROPERTY_INFOS.add(new PropertyInfo("gwt.shell.output.style", // NOI18N
                "OBF", new String [] { // NOI18N
                "# Script output style: OBF[USCATED], PRETTY, or DETAILED", // NOI18N
                }));

        PROPERTY_INFOS.add(new PropertyInfo("gwt.shell.logLevel", // NOI18N
                "WARN", new String [] { // NOI18N
                "# The level of logging detail: ERROR, WARN, INFO, TRACE, DEBUG,", // NOI18N
                }));

        PROPERTY_INFOS.add(new PropertyInfo("gwt.shell.jvmargs", // NOI18N
                "-Xmx256M", new String [] { // NOI18N
                "# Additional JVM arguments for the GWT shell/GWT hosted mode (GWT 1.6)", // NOI18N
                "# Add -d32 here and use at least GWT 1.7.1 to debug on a Mac", // NOI18N
                "# (32-bit JVM is required by GWT for debugging before GWT 2.0)", // NOI18N
                }));

        PROPERTY_INFOS.add(new PropertyInfo("gwt.shell.java", // NOI18N
                "", new String [] { // NOI18N
                "# Java executable that should be used for the GWT Dev mode", // NOI18N
                "# This can be used to choose a 32-bit JVM on a 64-bit OS", // NOI18N
                "# (32-bit JVM is required by GWT for debugging before GWT 2.0)", // NOI18N
                "# Leave the value empty if the default JVM should be used.", // NOI18N
                }));

        PROPERTY_INFOS.add(new PropertyInfo("gwt.version", // NOI18N
                "1.7", new String [] { // NOI18N
                "# GWT version: 1.5, 1.6, 1.7, 2.0 or 2.1", // NOI18N
                }));
        PROPERTY_INFOS.add(new PropertyInfo("gwt.shell.code.server.port", // NOI18N
                "9997", new String [] { // NOI18N
                "# since GWT 2.0", // NOI18N
                "# Specifies the TCP port for the code server" // NOI18N
                }));
        PROPERTY_INFOS.add(new PropertyInfo("gwt.shell.port", // NOI18N
                "8888", new String [] { // NOI18N
                "# since GWT 2.0", // NOI18N
                "# Specifies the TCP port for the embedded web server" // NOI18N
                }));
        PROPERTY_INFOS.add(new PropertyInfo("gwt.compiler.args", // NOI18N
                "", new String [] { // NOI18N
                "# Additional GWT compiler arguments", // NOI18N
                "# GWT 2.0/2.1 compiler supports these:", // NOI18N
                "#  -workDir                The compiler's working directory for internal use (must be writeable; defaults to a system temp dir)", // NOI18N
                "#  -gen                    Debugging: causes normally-transient generated types to be saved in the specified directory", // NOI18N
                "#  -ea                     Debugging: causes the compiled output to check assert statements", // NOI18N
                "#  -XdisableClassMetadata  EXPERIMENTAL: Disables some java.lang.Class methods (e.g. getName())", // NOI18N
                "#  -XdisableCastChecking   EXPERIMENTAL: Disables run-time checking of cast operations", // NOI18N
                "#  -validateOnly           Validate all source code, but do not compile", // NOI18N
                "#  -draftCompile           Enable faster, but less-optimized, compilations", // NOI18N
                "#  -compileReport          Create a compile report that tells the Story of Your Compile", // NOI18N
                "#  -localWorkers           The number of local workers to use when compiling permutations", // NOI18N
                "#  -extra                  The directory into which extra files, not intended for deployment, will be written", // NOI18N
                }));
        PROPERTY_INFOS.add(new PropertyInfo("gwt.test.jvmargs", // NOI18N
                "-Xmx256M", new String [] { // NOI18N
                "# Additional JVM arguments for JUnit tests" // NOI18N
                }));
        PROPERTY_INFOS.add(new PropertyInfo("gwt.shell.args", // NOI18N
                "", new String [] { // NOI18N
                "# Additional arguments for the GWT shell", // NOI18N
                "# e.g. -bindAddress 0.0.0.0 since GWT 2.0.1" // NOI18N
                }));
    }

    /**
     * Checks the comments for a property and adds the specified line if it is
     * not yet there.
     *
     * @param props properties
     * @param propertyName name of the property
     * @param commentLine a comment line (should start with a #)
     */
    private static void ensureCommentLine(EditableProperties props,
            String propertyName, String commentLine) {
        String[] c = props.getComment(propertyName);
        boolean found = false;
        for (String s: c) {
            if (s.contains(commentLine)) {
                found = true;
                break;
            }
        }
        if (!found) {
            String[] newc = new String[c.length + 1];
            System.arraycopy(c, 0, newc, 0, c.length);
            newc[newc.length - 1] = commentLine;
            props.setComment(propertyName, newc, true);
        }
    }

    /**
     * Information about a property in gwt.properties
     */
    private final static class PropertyInfo {
        /** name of the property */
        public String name;

        /** default property value */
        public String defaultValue;

        /** comments */
        public String[] comments;

        public PropertyInfo(String name, String defaultValue, String[] comments) {
            this.name = name;
            this.defaultValue = defaultValue;
            this.comments = comments;
        }
    }

    private PropertyChangeListener openProjectsListener;
    private List<Project> lastOpenProjects;

    @Override
    public void restored()  {
        // update build-gwt.xml when a project is opened
        openProjectsListener = new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent evt) {
                List<Project> openProjects = new ArrayList<Project>(
                        Arrays.asList(OpenProjects.getDefault().
                        getOpenProjects()));

                // lastOpenProjects will contain the open projects before
                // the event was triggered
                openProjects.removeAll(lastOpenProjects);
                updateProjects(openProjects);
                lastOpenProjects = new ArrayList<Project>(openProjects);
            }
        };
        OpenProjects op = OpenProjects.getDefault();
        lastOpenProjects = new ArrayList<Project>(
                Arrays.asList(op.getOpenProjects()));
        op.addPropertyChangeListener(openProjectsListener);

        //update build-gwt.xml of the open projects when the ide starts up
        updateProjects(lastOpenProjects);
    }

    /**
     * Iterate through projects and updates them.
     *
     * @param projects projects
     */
    private void updateProjects(List<Project> projects) {
        for (final Project p: projects) {
            if (GWTProjectInfo.isGWTProject(p)) {
                try {
                    FileSystem fileSystem = p.getProjectDirectory().
                            getFileSystem();
                    fileSystem.runAtomicAction(new AtomicAction() {
                        public void run() throws IOException {
                            updateGWTProject(p);
                        }
                    });
                } catch (IOException ex) {
                    Exceptions.printStackTrace(ex);
                }
            }
        }
    }

    /**
     * Creates a new gwt.properties file
     *
     * @return gwt.properties
     */
    public static EditableProperties createGWTProperties() {
        EditableProperties ep = new EditableProperties(false);
        for (PropertyInfo propInfo: PROPERTY_INFOS) {
            ep.setProperty(propInfo.name, propInfo.defaultValue);
            ep.setComment(propInfo.name, propInfo.comments, true);
        }
        return ep;
    }

    /**
     * Updates a GWT project
     *  - build-gwt.xml
     *  - gwt.properties
     *
     * @param p a GWT project
     * @throws IOException
     */
    public static void updateGWTProject(final Project p) throws IOException {
        ProjectInformation projectInfo =
                ProjectUtils.getInformation(p);
        String projectName = projectInfo.getName();
        FileObject nbprj = p.getProjectDirectory().
                getFileObject(GWTProjectInfo.PRJ_DIR);

        // Maven projects do not have nbproject dir
        if (nbprj != null) {
            //support for multiple Java sources for both compile and debug
            //strategy: find all relevant occurances of ${src.dir} and append additional source
            SourceGroup[] sg = ProjectUtils.getSources(p).getSourceGroups(JavaProjectConstants.SOURCES_TYPE_JAVA);
            List<String> patterns = new ArrayList<String>();
            List<String> replace = new ArrayList<String>();
            //escaped ${src.dir} pattern
            String srcDir = "\\$\\{src\\.dir\\}";
            for (int i = 0; i < sg.length; i++) {
                String name = sg[i].getName();
                if ("${src.dir}".equals(name)) continue; //ignore the primary source
                if (name.startsWith("${test")) continue; //ignore test sources
                //escape secondary src var (always starts with $)
                String append = "\\" + name;
                //find any occurance of ${src.dir} followed by ':' path separator
                patterns.add("(" + srcDir + ")\\:"); 
                //append secondary source path right after the primary one
                replace.add("$1:" + append + ":"); 
                //find any ${src.dir}" preceeded by ':' path separator
                patterns.add("\\:(" + srcDir + ")\""); 
                //append secondary source path right after the primary one
                replace.add(":$1:" + append + "\""); 
                //find any <pathelement path="${src.dir}"/> element 
                patterns.add("(?s)(\\s*<pathelement path=\")(" + srcDir + ")(\"\\/>)");
                //append the <pathelement> for the additional source on the next line
                replace.add("$1$2$3$1" + append + "$3");
                //find any <srcfiles path="${src.dir}">...</srcfiles>
                patterns.add("(?s)(\\s*<srcfiles dir=\")(" + srcDir + ")(\">.*<\\/srcfiles>)"); 
                //append the <srcfile> for the additional source on the next line(s)
                replace.add("$1$2$3$1" + append + "$3");
            }
            patterns.add("__PROJECT_NAME__");
            replace.add(projectName);
            
            // COPY build-gwt.xml
            FileObject buildGwt = nbprj.getFileObject(
                    GWTProjectInfo.BUILD_GWT);
            GWT4NBUtil.copyResource(GWTProjectInfo.RESOURCE_BASE +
                    GWTProjectInfo.BUILD_GWT, buildGwt,
                    patterns.toArray(new String[patterns.size()]), // NOI18N
                    replace.toArray(new String[patterns.size()]), true); //multiline mode

        }

        GWTProjectInfo pi = GWTProjectInfo.get(p);
        if (pi != null && !pi.isMaven()) {
            EditableProperties props = pi.getProperties();
            for (PropertyInfo propInfo: PROPERTY_INFOS) {
                String v = props.getProperty(propInfo.name);
                if (v == null) {
                    props.setProperty(propInfo.name, propInfo.defaultValue);
                    props.setComment(propInfo.name, propInfo.comments, true);
                }
            }
            ensureCommentLine(props, "gwt.module", // NOI18N
                    "# The names of the modules to compile " + // NOI18N
                    "(separated by a space character)"); // NOI18N

            pi.setProperties(props);
        }
    }
}
