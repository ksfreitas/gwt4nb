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

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import java.util.WeakHashMap;
import java.util.logging.Level;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import org.netbeans.api.project.Project;
import org.netbeans.api.project.Sources;
import org.netbeans.api.project.SourceGroup;
import org.netbeans.api.project.ProjectUtils;

import org.netbeans.api.java.project.JavaProjectConstants;


import org.netbeans.spi.project.AuxiliaryConfiguration;

import org.netbeans.spi.project.support.ant.EditableProperties;
import org.openide.filesystems.FileObject;
import org.openide.util.Exceptions;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Information about a GWT project (Maven or Ant).
 * 
 * @author Tomasz Slota
 * @author see https://github.com/gwt4nb/gwt4nb/
 */
public class GWTProjectInfo {
    public static final String BUILD_GWT   = "build-gwt.xml"; // NOI18N
    public static final String PRJ_DIR =  "nbproject"; // NOI18N
    public static final String GWT_PROPERTIES = "gwt.properties"; // NOI18N
    public static final String RESOURCE_BASE = "org/netbeans/modules/gwt4nb/resources/"; // NOI18N
    public static final String WELCOME_FILE = "welcomeGWT.html"; // NOI18N

    private static Map<Project, WeakReference<GWTProjectInfo>> infos =
            new WeakHashMap<Project, WeakReference<GWTProjectInfo>>();

    /**
     * @param module a GWT module like "org.yournamehere.Main"
     * @return package for server classes (default module)
     */
    public static String getServerPackage(String module) {
        return getModulePackage(module) + ".server"; // NOI18N
    }

    /**
     * Finds the first name of a registered "source-root" in a web project.
     * This method uses the {@code AuxiliaryConfiguration} and reads in the
     * web-apps data section to find the &lt;source-roots&gt; element and
     * the &lt;root&gt; elements it contains.
     *
     * @param project the {@code Project} to find the source-root of
     * @return the reference to the source-root as described in the
     *      project.xml file
     */
    private static String getSourcesDirReference(final Project project) {
        final AuxiliaryConfiguration configuration =
                ProjectUtils.getAuxiliaryConfiguration(project);

        final Element webappElement = configuration.getConfigurationFragment(
                "data", "http://www.netbeans.org/ns/web-project/3", true); // NOI18N

        if(webappElement != null) {
            final Element sourceRootsElement = (Element)webappElement.
                    getElementsByTagName("source-roots").item(0); // NOI18N

            if(sourceRootsElement != null) {
                final NodeList sourceRoots =
                        sourceRootsElement.getElementsByTagName("root"); // NOI18N

                for(int i = 0; i < sourceRoots.getLength(); i++) {
                    final Element root = (Element)sourceRoots.item(i);
                    final String id = root.getAttribute("id"); // NOI18N

                    if(id.length() > 0) {
                        return id;
                    }
                }
            }
        }

        return "src.dir"; // NOI18N
    }

    /**
     * @param module a GWT module like org.yournamehere.Main
     * @return package for client classes
     */
    public static String getClientPackage(String module) {
        return getModulePackage(module) + ".client"; // NOI18N
    }

    /**
     * Test whether a project is a GWT project.
     *
     * @param project a project
     * @return true = Ant or Maven based GWT project
     */
    public static boolean isGWTProject(Project project) {
        return get(project) != null;
    }

    /**
     * Test whether a project is a GWT project.
     *
     * @param project a project
     * @return true = Ant or Maven based GWT project
     */
    public static boolean isAntGWTProject(Project project) {
        GWTProjectInfo info = get(project);
        return info != null && !info.isMaven();
    }

    /**
     * Test whether a project is a Maven project and GWT project.
     *
     * @param project a project or null
     * @return true = Maven based GWT project
     */
    public static boolean isMavenGWTProject(Project project) {
        GWTProjectInfo info = get(project);
        return info != null && info.isMaven();
    }

    /**
     * Reads a property from gwt.properties
     *
     * @param project a project
     * @param propName name of the property
     * @return value of the property or null if absent
     */
    public static String readGWTPropertyAnt(Project project, String propName){
        GWTProjectInfo info = get(project);
        String r = null;
        if (info != null)
            r = info.getAntProperty(propName);
        return r;
    }
    
    public static void writeGWTPropertyAnt(Project project, String propName,
            String propValue) {
        GWTProjectInfo info = get(project);
        if (info != null) {
            info.setAntProperty(propName, propValue);
        }
    }

    /**
     * Determines package name from a module name
     *
     * @param module module name like org.yournamehere.Main
     * @return package name "org.yournamehere"
     */
    public static String getModulePackage(String module){
        int end = module.lastIndexOf("."); // NOI18N
        if (end <= 0) {
            //fallback to full modulename (which may contain no dot)
            return module;
        }
        return module.substring(0, end);
    }

    /**
     * @param project a GWT project
     * @return directory for sources (/src)
     */
    public static FileObject getSourcesDir(final Project project) {
        return get(project).getSourcesDir();
    }

    /**
     * Returns (maybe cached) GWT project information.
     * 
     * @param p a project (Maven or Ant)
     * @return GWT project information or null if p is not a GWT project
     */
    public static GWTProjectInfo get(Project p) {
        WeakReference<GWTProjectInfo> infor = infos.get(p);
        GWTProjectInfo info;
        if (infor != null)
            info = infor.get();
        else
            info = null;

        if (info == null) {
            final FileObject projectDirectory = p.getProjectDirectory();
            FileObject pom = projectDirectory.getFileObject("pom.xml"); // NOI18N
            if (pom != null) {
                try {
                    Document pomDoc = GWT4NBUtil.parseXMLFile(pom);
                    XPathFactory factory = XPathFactory.newInstance();
                    XPath xp = factory.newXPath();
                    NodeList nl = (NodeList) xp.evaluate(
                            "//project/build/plugins/plugin[artifactId='gwt-maven-plugin']", // NOI18N
                            pomDoc,
                            XPathConstants.NODESET);
                    if (nl.getLength() > 0)
                        info = new GWTProjectInfo(p, true);
                } catch (Exception ex) {
                    GWT4NBUtil.LOGGER.log(Level.FINE, null, ex);
                }
            } else {
                FileObject buildGWT = projectDirectory.
                        getFileObject(PRJ_DIR + "/" + BUILD_GWT); // NOI18N

                if (buildGWT != null)
                    info = new GWTProjectInfo(p, false);
            }

            if (info != null)
                infos.put(p, new WeakReference<GWTProjectInfo>(info));
        }
        return info;
    }

    /**
     * Removes cache entry for a project.
     * 
     * @param p GWT project (Maven or Ant)
     */
    public static void removeCacheEntry(Project p) {
        infos.remove(p);
    }

    private final Project project;
    private boolean maven;

    /**
     * @param p a GWT project (Maven or Ant)
     * @param maven true = Maven, false = Ant
     */
    private GWTProjectInfo(Project p, boolean maven) {
        this.project = p;
        this.maven = maven;
    }

    /**
     * @return true = Maven, false = Ant
     */
    public boolean isMaven() {
        return maven;
    }

    /**
     * Changes the value of a property in nbproject/gwt.properties. Does
     * nothing for Maven projects.
     *
     * @param propName name of the property
     * @param propValue value of the property
     */
    public void setAntProperty(String propName, String propValue) {
        EditableProperties p = getProperties();
        if (p != null) {
            p.setProperty(propName, propValue);
            setProperties(p);
        }
    }

    /**
     * gwt.properties
     * 
     * @return gwt.properties or null for Maven projects
     */
    public EditableProperties getProperties() {
        if (!maven) {
            EditableProperties props = new EditableProperties(false);
            FileObject propsFile = project.getProjectDirectory().
                    getFileObject(PRJ_DIR + "/" + GWT_PROPERTIES); // NOI18N
            if (propsFile != null) {
                try {
                    InputStream is = propsFile.getInputStream();
                    props.load(is);
                    is.close();
                } catch (IOException e) {
                    GWT4NBUtil.LOGGER.log(Level.SEVERE, "", e); // NOI18N
                }
            }
            return props;
        } else {
            return null;
        }
    }

    /**
     * Reads a property from nbproject/gwt.properties.
     *
     * @param propName name of the property
     * @return property value or null if absent or it is a Maven project
     */
    public String getAntProperty(String propName) {
        EditableProperties p = getProperties();
        if (p != null)
            return p.getProperty(propName);
        else
            return null;
    }

    /**
     * @return GWT version.
     */
    public Version getGWTVersion() {
        Version r = null;
        if (maven)
            r = new Version(new int[] {1, 7, 0});
        else {
            String versionString = readGWTPropertyAnt(project, 
                    "gwt.version"); // NOI18N

            if (versionString == null) {
                File d = GWT4NBUtil.getProjectGWTDir(project);
                if (d != null)
                    versionString = GWT4NBUtil.findGWTVersion(d);

                if (versionString == null)
                    versionString = "1.6"; // NOI18N

                writeGWTPropertyAnt(project, 
                        "gwt.version", versionString); // NOI18N
            }

            if (versionString != null)
                r = new Version(versionString);
        }

        return r;
    }

    /**
     * @return GWT modules like "org.yournamehere.Main"
     */
    public List<String> getModules() {
        List<String> r = new ArrayList<String>();
        if (maven){
            final FileObject projectDirectory = project.getProjectDirectory();
            FileObject pom = projectDirectory.getFileObject("pom.xml"); // NOI18N
            if (pom != null)
            {
                try
                {
                    Document pomDoc = GWT4NBUtil.parseXMLFile(pom);
                    XPathFactory factory = XPathFactory.newInstance();
                    XPath xp = factory.newXPath();
                    NodeList nl = (NodeList) xp.evaluate(
                            "//project/build/plugins/plugin[artifactId='gwt-maven-plugin']/configuration/modules/module", // NOI18N
                            pomDoc,
                            XPathConstants.NODESET);
                    if (nl.getLength() > 0)
                    {
                        for (int i = 0; i < nl.getLength(); i++)
                        {
                            Node item = nl.item(i);
                            r.add(item.getTextContent());
                        }
                    }
                }
                catch (Exception ex)
                {
                    Exceptions.printStackTrace(ex);
                }
            }
        }
        else {
            String gm = readGWTPropertyAnt(project,
                    "gwt.module"); // NOI18N
            if (gm == null)
                gm = "";
            String[] s = gm.split(" "); // NOI18N
            if (s.length > 0)
            {
                r.addAll(Arrays.asList(s));
            }
        }
        return r;
    }

    /**
     * @return first directory for source files
     */
    public FileObject getSourcesDir() {
        final Sources sources = ProjectUtils.getSources(project);
        final SourceGroup[] javaSources = sources.getSourceGroups(
                JavaProjectConstants.SOURCES_TYPE_JAVA);

        final String referenceName = getSourcesDirReference(project);

        for(final SourceGroup sg : javaSources) {
            final String name = sg.getName();

            if(name.equals(referenceName) ||
                    // ${referenceName}
                    name.regionMatches(2, referenceName, 0,
                    referenceName.length())) {
                return sg.getRootFolder();
            }
        }

        // SourceRoot is used by Maven projects
        for(final SourceGroup sg : javaSources) {
            final String name = sg.getName();

            if(name.equals("SourceRoot")) { // NOI18N
                return sg.getRootFolder();
            }
        }

        if (javaSources.length > 0)
            return javaSources[0].getRootFolder();
        else
            return project.getProjectDirectory().
                    getFileObject("src/java"); // NOI18N
    }

    /**
     * Registers a new GWT module in gwt.properties
     * 
     * @param module name of the module
     */
    public void registerModule(String module) {
        if (!maven) {
            String gm = readGWTPropertyAnt(project, "gwt.module"); // NOI18N
            if (gm == null)
                gm = ""; // NOI18N
            gm = gm.trim();
            if (gm.length() != 0)
                gm += " "; // NOI18N
            gm += module;
            writeGWTPropertyAnt(project, "gwt.module", gm); // NOI18N
        }
    }

    /**
     * Saves gwt.properties. No-op for Maven projects.
     *
     * @param props gwt.properties
     */
    public void setProperties(EditableProperties props) {
        if (!maven) {
            FileObject propsFile = project.getProjectDirectory().
                    getFileObject(PRJ_DIR + "/" + GWT_PROPERTIES); // NOI18N
            try {
                OutputStream os = propsFile.getOutputStream();
                props.store(os);
                os.close();
            } catch (IOException e) {
                GWT4NBUtil.LOGGER.log(Level.SEVERE, "", e); // NOI18N
            }
        }
    }
}
