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
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;
import javax.swing.JProgressBar;
import org.netbeans.api.java.classpath.ClassPath;
import org.netbeans.api.java.project.classpath.ProjectClassPathModifier;
import org.netbeans.api.project.FileOwnerQuery;
import org.netbeans.api.project.Project;
import org.netbeans.api.project.ProjectInformation;
import org.netbeans.api.project.ProjectUtils;
import org.netbeans.api.project.libraries.Library;
import org.netbeans.api.project.libraries.LibraryManager;
import org.netbeans.modules.gwt4nb.settings.GWTSettings;
import org.netbeans.modules.j2ee.dd.api.web.DDProvider;
import org.netbeans.modules.j2ee.dd.api.web.WebApp;
import org.netbeans.modules.j2ee.dd.api.web.WelcomeFileList;
import org.netbeans.modules.web.api.webmodule.WebModule;
import org.netbeans.modules.web.spi.webmodule.FrameworkConfigurationPanel;
import org.netbeans.modules.web.spi.webmodule.WebFrameworkProvider;
import org.netbeans.spi.java.project.classpath.ProjectClassPathExtender;
import org.netbeans.spi.project.support.ant.AntProjectHelper;
import org.netbeans.spi.project.support.ant.EditableProperties;
import org.openide.ErrorManager;
import org.openide.filesystems.FileLock;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileSystem;
import org.openide.filesystems.FileSystem.AtomicAction;
import org.openide.filesystems.FileUtil;
import org.openide.filesystems.Repository;
import org.openide.loaders.DataFolder;
import org.openide.loaders.DataObject;
import org.openide.util.NbBundle;
import org.openide.xml.XMLUtil;

/**
 * @author Tomas.Zezula@Sun.COM
 * @author Tomasz.Slota@Sun.COM
 */
public class GWTFrameworkProvider extends WebFrameworkProvider {

    private static final String LIBS_FOLDER = "org-netbeans-api-project-libraries/Libraries"; //NOI18N
    private static final String PATTERN_PRJ_NAME = "__PROJECT_NAME__"; //NOI18N
    public static final String GWT_DEV = "gwt-dev-\\w*.jar"; //NOI18N
    private static final String CLIENT_FOLDER = "client"; //NOI18N
    private static final String SERVER_FOLDER = "server"; //NOI18N
    private static final String TEMPLATE_JAVA_CLASS = "Templates/Classes/EntryPoint.java"; //NOI18N
    private static final String GWT_USER = "gwt-user.jar"; //NOI18N
    static final String LIB_GWT_NAME = "GWT"; //NOI18N
    private static final String LIB_GWT = "gwt";
    private GWTConfigPanel pnlConfig = null;
    private static String gwtVersion = "";
    private static String prevGwtVersion = "";

    /** Creates a new instance of GWTFrameworkProvider */
    public GWTFrameworkProvider() {
        super("Google Web Toolkit", "Desc");
    }

    @Override
    public Set extend(WebModule webModule) {
        EnableGWTAction enableGWTAction = new EnableGWTAction(webModule);
        if(pnlConfig!=null){
            pnlConfig.setProject(FileOwnerQuery.getOwner(webModule.getDocumentBase()));
        }

        try {
            FileSystem fileSystem = webModule.getDocumentBase().getFileSystem();
            fileSystem.runAtomicAction(new AtomicAction() {

                public void run() throws IOException {
                    // fetch current GWT version
                    try {
                        FileObject devjar = getGWTDevArchive(pnlConfig.getGwtFolder());
                        URL url = FileUtil.toFile(devjar).toURL();
                        URL[] urls = new URL[]{url};
                        ClassLoader cl = new URLClassLoader(urls);

                        Class cls = cl.loadClass("com.google.gwt.dev.About");
                        Field gwtVers = cls.getField("GWT_VERSION_NUM");
                        gwtVersion = gwtVers.get(null).toString();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    createGWTUserLibrary(pnlConfig.getGwtFolder(), LIB_GWT_NAME + gwtVersion, LIB_GWT + gwtVersion + ".xml");
                }
            });

            fileSystem.runAtomicAction(enableGWTAction);
        } catch (Exception ex) {
            ErrorManager.getDefault().notify(ex);
        }

        return enableGWTAction.getObjectsToOpen();
    }

    public static void upgradeGWTVersion(WebModule webModule, File gwtFolder, final JProgressBar jProgressBar) {
        UpgradeGWTAction upgradeGWTAction = new UpgradeGWTAction(webModule, gwtFolder, jProgressBar);
        final File gwtfolder = gwtFolder;
        try {
            FileSystem fileSystem = webModule.getDocumentBase().getFileSystem();
            fileSystem.runAtomicAction(new AtomicAction() {

                public void run() throws IOException {
                    // fetch upgraded GWT version
                    try {
                        FileObject devjar = getGWTDevArchive(gwtfolder);
                        URL url = FileUtil.toFile(devjar).toURL();
                        URL[] urls = new URL[]{url};
                        ClassLoader cl = new URLClassLoader(urls);

                        Class cls = cl.loadClass("com.google.gwt.dev.About");
                        Field gwtVers = cls.getField("GWT_VERSION_NUM");
                        gwtVersion = gwtVers.get(null).toString();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    // create a global upgraded GWT user library
                    createGWTUserLibrary(gwtfolder, LIB_GWT_NAME + gwtVersion, LIB_GWT + gwtVersion + ".xml");
                    jProgressBar.setValue(35);
                }
            });

            fileSystem.runAtomicAction(upgradeGWTAction);
            jProgressBar.setValue(99);
        } catch (Exception ex) {
            ErrorManager.getDefault().notify(ex);
        }

        return;
    }

    private class EnableGWTAction implements AtomicAction {

        private Set<FileObject> toOpen = new LinkedHashSet<FileObject>();
        private WebModule webModule;

        EnableGWTAction(WebModule webModule) {
            this.webModule = webModule;
        }

        public void run() throws IOException {
            Project project = FileOwnerQuery.getOwner(webModule.getDocumentBase());
            assert project != null;

            ProjectInformation projectInfo = ProjectUtils.getInformation(project);
            String projectName = projectInfo.getName();

            FileObject nbprj = project.getProjectDirectory().getFileObject(GWTProjectInfo.PRJ_DIR);
            // COPY build-gwt.xml
            FileObject buildGwt = nbprj.createData(GWTProjectInfo.BUILD_GWT);
            Util.copyResource(GWTProjectInfo.RESOURCE_BASE + GWTProjectInfo.BUILD_GWT, buildGwt, new String[]{PATTERN_PRJ_NAME}, new String[]{projectName});
            FileObject foBuildXML = nbprj.getParent().getFileObject("build.xml");
            patchBuildXML(foBuildXML);

            // ADD LIBRARY
            ProjectClassPathExtender cpe = (ProjectClassPathExtender) project.getLookup().lookup(ProjectClassPathExtender.class);
            File gwtFolder = pnlConfig.getGwtFolder();
            Library libUser = LibraryManager.getDefault().getLibrary(LIB_GWT_NAME + gwtVersion);

            if (libUser != null) {
                //set global GWTLocation
                GWTSettings gwtSettings = GWTSettings.getDefault();
                gwtSettings.setGWTLocation(gwtFolder);
                cpe.addLibrary(libUser);
            } else {
                ErrorManager.getDefault().log("Could not add GWT library");
            }

            FileObject gwtRtLib = getGWTDevArchive(gwtFolder);
            assert gwtRtLib != null;
            cpe.addArchiveFile(gwtRtLib);

            FileObject gwtServletLib = FileUtil.toFileObject(FileUtil.normalizeFile(new File(gwtFolder, "gwt-servlet.jar")));

            cpe.addArchiveFile(gwtServletLib);

            EditableProperties projectProps = ProjectPropertiesUtil.getEditableProperties(project, AntProjectHelper.PROJECT_PROPERTIES_PATH);
            projectProps.setProperty("j2ee.deploy.on.save", "false"); //NOI18N
            ProjectPropertiesUtil.storeEditableProperties(project, AntProjectHelper.PROJECT_PROPERTIES_PATH, projectProps);

            // CREATE ENTRY POINT CLASS
            FileObject src = GWTProjectInfo.getSourcesDir(project);

            String[] epNames = getEntryPointModuleName(pnlConfig.getGWTModule());
            if (epNames != null) {
                assert epNames.length == 2 && epNames[0] != null && epNames[1] != null;
                createEntryPoint(src, epNames[0], epNames[1], toOpen);
            }

            // CREATE gwt.properties
            String gwtLocation;
            if (System.getProperty("os.name").contains("Windows")) {
                gwtLocation = gwtFolder.toString().replace("\\", "/");
            } else {
                gwtLocation = gwtFolder.toString();
            }

            toOpen.add(createGWTProperties(nbprj, pnlConfig.getGWTModule(), gwtLocation));


            toOpen.add(createHtml(webModule.getDocumentBase(), epNames[0], epNames[1]));

            FileObject indexJSP = webModule.getDocumentBase().getFileObject("index.jsp");

            if (indexJSP != null && indexJSP.isData() && indexJSP.canRead() && indexJSP.canWrite()) {
                patchIndexJSP(indexJSP, pnlConfig.getGWTModule());
            } else {
                ErrorManager.getDefault().log("Couldn't patch index.jsp");
            }

            FileObject dd = webModule.getDeploymentDescriptor();
            WebApp ddRoot = DDProvider.getDefault().getDDRoot(dd);

            if (ddRoot != null) {
                try {
                    WelcomeFileList welcomeFiles = ddRoot.getSingleWelcomeFileList();
                    if (welcomeFiles == null) {
                        welcomeFiles = (WelcomeFileList) ddRoot.createBean("WelcomeFileList"); //NOI18N
                        ddRoot.setWelcomeFileList(welcomeFiles);
                    }

                    if (welcomeFiles.sizeWelcomeFile() == 0) {
                        String welcomePage = GWTProjectInfo.WELCOME_FILE;
                        welcomeFiles.addWelcomeFile(welcomePage); //NOI18N
                    }

                    ddRoot.write(dd);
                } catch (ClassNotFoundException e) {
                    ErrorManager.getDefault().notify(e);
                }
            }
        }

        public Set<FileObject> getObjectsToOpen() {
            return toOpen;
        }
    }

    public static class UpgradeGWTAction implements AtomicAction {

        private Set<FileObject> toOpen = new LinkedHashSet<FileObject>();
        private WebModule webModule;
        File gwtFolder;
        File prevGwtFolder;
        JProgressBar jbar;


        UpgradeGWTAction(WebModule webModule, File gwtFolder, JProgressBar jProgressBar) {
            this.webModule = webModule;
            this.gwtFolder = gwtFolder;
            this.jbar = jProgressBar;
        }

        public void run() throws IOException {
            Project project = FileOwnerQuery.getOwner(webModule.getDocumentBase());
            assert project != null;
            String gwtLocation = GWTProjectInfo.readGWTProperty(project, "gwt.install.dir");
            if (gwtLocation != null && !gwtLocation.equals("")) {
                prevGwtFolder = new File(gwtLocation);
            } else {
                GWTSettings gwtSettings = GWTSettings.getDefault();
                prevGwtFolder = gwtSettings.getGWTLocation();
            }
            // fetch previous GWT version
            try {
                FileObject devjar = getGWTDevArchive(prevGwtFolder);
                URL url = FileUtil.toFile(devjar).toURL();
                URL[] urls = new URL[]{url};
                ClassLoader cl = new URLClassLoader(urls);

                Class cls = cl.loadClass("com.google.gwt.dev.About");
                Field gwtVers = cls.getField("GWT_VERSION_NUM");
                prevGwtVersion = gwtVers.get(null).toString();
            } catch (Exception e) {
                e.printStackTrace();
            }


            // Modify LIBRARY
            Library libUser = LibraryManager.getDefault().getLibrary(LIB_GWT_NAME);
            Library prevLibUser = LibraryManager.getDefault().getLibrary(LIB_GWT_NAME + prevGwtVersion);
            Library upLibUser = LibraryManager.getDefault().getLibrary(LIB_GWT_NAME + gwtVersion);
            FileObject projectArtifact = webModule.getJavaSources()[0];
            jbar.setValue(45);
            if (libUser != null || prevLibUser != null) {
                //update global gwtLocation
                GWTSettings gwtSettings = GWTSettings.getDefault();
                gwtSettings.setGWTLocation(gwtFolder);
                //update gwt properties for the project.
                gwtLocation = gwtFolder.toString();
                if (System.getProperty("os.name").contains("Windows")) {
                    gwtLocation = gwtLocation.replace("\\", "/");
                }
                GWTProjectInfo.writeGWTProperty(project, "gwt.install.dir", gwtLocation);
                // Remove reference to the obsolete GWT lib if present.
                // Also remove the reference to the previousGWT${version} lib.
                if (libUser != null) {
                    ProjectClassPathModifier.removeLibraries(new Library[]{libUser}, projectArtifact, ClassPath.COMPILE);
                }
                if (prevLibUser != null) {
                    ProjectClassPathModifier.removeLibraries(new Library[]{prevLibUser}, projectArtifact, ClassPath.COMPILE);
                }
                // Add the newly created GWT${version} lib reference.
                ProjectClassPathModifier.addLibraries(new Library[]{upLibUser}, projectArtifact, ClassPath.COMPILE);
            } else {
                ErrorManager.getDefault().log("Could not add the upgraded GWT library");
            }
            jbar.setValue(55);

            // Modify Jar references
            FileObject gwtRtLib = getGWTDevArchive(prevGwtFolder);
            assert gwtRtLib != null;
            assert FileUtil.isArchiveFile(gwtRtLib.getURL()) == true;
            ProjectClassPathModifier.removeRoots(new URL[]{FileUtil.getArchiveRoot(gwtRtLib.getURL())}, projectArtifact, ClassPath.COMPILE);
            gwtRtLib = getGWTDevArchive(gwtFolder);
            jbar.setValue(65);
            assert gwtRtLib != null;
            assert FileUtil.isArchiveFile(gwtRtLib.getURL()) == true;
            ProjectClassPathModifier.addRoots(new URL[]{FileUtil.getArchiveRoot(gwtRtLib.getURL())}, projectArtifact, ClassPath.COMPILE);
            jbar.setValue(75);

            FileObject gwtServletLib = FileUtil.toFileObject(FileUtil.normalizeFile(new File(prevGwtFolder, "gwt-servlet.jar")));
            ProjectClassPathModifier.removeRoots(new URL[]{FileUtil.getArchiveRoot(gwtServletLib.getURL())}, projectArtifact, ClassPath.COMPILE);
            gwtServletLib = FileUtil.toFileObject(FileUtil.normalizeFile(new File(gwtFolder, "gwt-servlet.jar")));
            ProjectClassPathModifier.addRoots(new URL[]{FileUtil.getArchiveRoot(gwtServletLib.getURL())}, projectArtifact, ClassPath.COMPILE);
            jbar.setValue(85);
        }

        public Set<FileObject> getObjectsToOpen() {
            return toOpen;
        }
    }

    @Override
    public boolean isInWebModule(WebModule webModule) {
        Project project = FileOwnerQuery.getOwner(webModule.getDocumentBase());
        return GWTProjectInfo.isGWTProject(project);
    }

    public File[] getConfigurationFiles(WebModule webModule) {
        Project project = FileOwnerQuery.getOwner(webModule.getDocumentBase());
        File projectDir = FileUtil.toFile(project.getProjectDirectory());
        return new File[]{new File(projectDir, "nbproject/gwt.properties")}; //NOI18N
    }

    public FrameworkConfigurationPanel getConfigurationPanel(WebModule webModule) {
        boolean defaultValue = webModule == null || !isInWebModule(webModule);
        Project p = null;
        if(webModule!=null){
            p = FileOwnerQuery.getOwner(webModule.getDocumentBase());
        }
        pnlConfig = new GWTConfigPanel(p);
        pnlConfig.enableComponents(defaultValue);
        return pnlConfig;
    }



    private static FileObject createGWTProperties(FileObject projectDir, String gwtModule, String gwtLocation) throws IOException {
        FileObject propFile = projectDir.createData("gwt.properties");
        FileLock lock = propFile.lock();
        PrintWriter writer = new PrintWriter(propFile.getOutputStream(lock));

        writer.print(NbBundle.getMessage(GWTFrameworkProvider.class, "gwt.properties_content", gwtModule, gwtLocation));

        writer.close();
        lock.releaseLock();
        return propFile;
    }

    private static FileObject getGWTDevArchive(final File gwtRoot) {
        assert gwtRoot != null;
        assert gwtRoot.exists() && gwtRoot.isDirectory();
        File[] files = gwtRoot.listFiles();
        Pattern pattern = Pattern.compile(GWT_DEV, Pattern.CASE_INSENSITIVE);
        for (File file : files) {
            if (pattern.matcher(file.getName()).matches()) {
                return FileUtil.toFileObject(FileUtil.normalizeFile(file));
            }
        }
        return null;
    }

    private static String[] getEntryPointModuleName(String entryPointClass) {
        if (entryPointClass == null) {
            return null;
        }
        int endIndex = entryPointClass.lastIndexOf('.');
        assert endIndex > 0;
        return new String[]{entryPointClass.substring(0, endIndex), entryPointClass.substring(endIndex + 1)};
    }

    private static void createEntryPoint(final FileObject srcRoot, final String pkg, final String cls, final Set<FileObject> toOpen) throws IOException {
        assert srcRoot != null;
        assert pkg != null;
        assert cls != null;
        assert toOpen != null;
        FileObject folder;
        if (pkg.length() > 0) {
            folder = FileUtil.createFolder(srcRoot, pkg.replace('.', '/')); //NOI18N
        } else {
            folder = srcRoot;
        }
        assert folder != null;
        FileObject cf = folder.createFolder(CLIENT_FOLDER);
        FileObject sf = folder.createFolder(SERVER_FOLDER);
        toOpen.add(createModule(folder, pkg, cls));
        toOpen.add(createJava(cf, cls));
    }

    private static String getEntryPointClassName(String moduleName) {
        return moduleName + "EntryPoint";
    }


    private static void patchBuildXML(FileObject foBuildXML) {
        //TODO: provide a better implementation
        Util.replaceInFile(foBuildXML, "<import file=\"nbproject/build-impl.xml\"/>", "<import file=\"nbproject/build-gwt.xml\"/>" + System.getProperty("line.separator") + "\t<import file=\"nbproject/build-impl.xml\"/>");
    }

    /** Changes the index.jsp file. Only when there is <h1>JSP Page</h1> string.
     */
    private void patchIndexJSP(FileObject indexjsp, String gwtModule) throws IOException {
        String nl = System.getProperty("line.separator");
        Util.replaceInFile(indexjsp, "<h1>JSP Page</h1>", "<h1>JSP Page</h1>" + nl + "\t<h3><a href=\"" + GWTProjectInfo.WELCOME_FILE + "\">GWT page</a></h3>" + nl);
    }

    private static FileObject createHtml(final FileObject folder, final String pkg, final String cls) throws IOException {
        assert folder != null;
        assert folder.isFolder();
        assert pkg != null;
        assert cls != null;
        FileObject fo = folder.createData("welcomeGWT.html"); //NOI18N
        FileLock lock = fo.lock();
        try {
            PrintWriter out = new PrintWriter(new OutputStreamWriter(fo.getOutputStream(lock)));
            try {
                String gwtOutputSubDir = pkg + '.' + cls;
                out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
                out.println("<html>");
                out.println("    <head>");
                out.println("        <meta name='gwt:module' content='" + gwtOutputSubDir + "=" + gwtOutputSubDir + "'>");
                out.println("        <title>" + cls + "</title>");
                out.println("    </head>");
                out.println("    <body>");
                out.println("        <script language=\"javascript\" src=\"" + gwtOutputSubDir + "/" + gwtOutputSubDir + ".nocache.js\"></script>");
                out.println("    </body>");
                out.println("</html>");
            } finally {
                out.close();
            }
        } finally {
            lock.releaseLock();
        }
        return fo;
    }

    private static FileObject createModule(final FileObject folder, final String pkg, final String cls) throws IOException {
        assert folder != null;
        assert folder.isFolder();
        assert cls != null;
        FileObject fo = folder.createData(cls + ".gwt.xml"); //NOI18N
        FileLock lock = fo.lock();
        try {
            PrintWriter out = new PrintWriter(new OutputStreamWriter(fo.getOutputStream(lock)));
            try {
                String className = getEntryPointClassName(cls);
                out.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>"); //NOI18N
                out.println("<module>"); //NOI18N
                out.println("\t<inherits name=\"com.google.gwt.user.User\"/>"); //NOI18N
                out.println("\t<entry-point class=\"" + (pkg != null ? (pkg + '.') : "") + CLIENT_FOLDER + '.' + className + "\"/>"); //NOI18N
                out.println("\t<!-- Do not define servlets here, use web.xml -->"); //NOI18N
                out.println("</module>"); //NOI18N
            } finally {
                out.close();
            }
        } finally {
            lock.releaseLock();
        }
        return fo;
    }

    @SuppressWarnings(value = "unchecked")
    private static FileObject createJava(final FileObject folder, final String cls) throws IOException {
        assert folder != null;
        assert folder.isFolder();
        assert cls != null;
        final FileObject jc = Repository.getDefault().getDefaultFileSystem().findResource(TEMPLATE_JAVA_CLASS);
        assert jc != null;
        final DataObject template = DataObject.find(jc);
        assert template != null;
        String className = getEntryPointClassName(cls);
        DataObject jdo = template.createFromTemplate(DataFolder.findFolder(folder), className);
        return jdo.getPrimaryFile();
    }

    //XXX: Replace this when API for managing libraries is available
    public static void createGWTUserLibrary(File gwtFolder, String libname, String libxml) throws IOException {
        assert gwtFolder != null;
        final File userJar = new File(gwtFolder, GWT_USER);
        if (!userJar.exists()) {
            return;
        }

        Library lib = LibraryManager.getDefault().getLibrary(libname);
        final List<URL> src = lib == null ? Collections.<URL>emptyList() : (List<URL>) lib.getContent("src");
        List<URL> _javadoc = lib == null ? Collections.<URL>emptyList() : (List<URL>) lib.getContent("javadoc");
        final File gwtJavadoc = FileUtil.normalizeFile(new File(new File(gwtFolder, "doc"), "javadoc"));
        if (_javadoc.isEmpty() && gwtJavadoc.exists() && gwtJavadoc.isDirectory() && gwtJavadoc.canRead()) {
            _javadoc = Collections.singletonList(gwtJavadoc.toURI().toURL());
        }
        final List<URL> javadoc = _javadoc;
        final FileSystem sysFs = Repository.getDefault().getDefaultFileSystem();
        assert sysFs != null;
        final FileObject libsFolder = sysFs.findResource(LIBS_FOLDER);
        assert libsFolder != null && libsFolder.isFolder();
        FileObject gwt = libsFolder.getFileObject(libxml);
        if (gwt == null) {
            gwt = libsFolder.createData(libxml);
        }
        FileLock lock = gwt.lock();
        try {
            PrintWriter out = new PrintWriter(new OutputStreamWriter(gwt.getOutputStream(lock)));
            try {
                out.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>"); //NOI18N
                out.println("<!DOCTYPE library PUBLIC \"-//NetBeans//DTD Library Declaration 1.0//EN\" \"http://www.netbeans.org/dtds/library-declaration-1_0.dtd\">"); //NOI18N
                out.println("<library version=\"1.0\">"); //NOI18N
                out.println("\t<name>" + XMLUtil.toElementContent(libname) + "</name>"); //NOI18N
                out.println("<localizing-bundle>org.netbeans.modules.gwt4nb.Bundle</localizing-bundle>");
                out.println("\t<type>j2se</type>"); //NOI18N
                out.println("\t<volume>"); //NOI18N
                out.println("\t\t<type>classpath</type>"); //NOI18N
                out.println("\t\t<resource>" + XMLUtil.toElementContent(FileUtil.getArchiveRoot(userJar.toURI().toURL()).toString()) + "</resource>"); //NOI18N
                out.println("\t</volume>"); //NOI18N
                out.println("\t<volume>"); //NOI18N
                out.println("\t\t<type>src</type>"); //NOI18N
                out.println("\t</volume>"); //NOI18N
                for (URL root : src) {
                    out.println("\t\t<resource>" + XMLUtil.toElementContent(root.toExternalForm()) + "</resource>"); //NOI18N
                }
                out.println("\t<volume>"); //NOI18N
                out.println("\t\t<type>javadoc</type>"); //NOI18N
                for (URL root : javadoc) {
                    out.println("\t\t<resource>" + XMLUtil.toElementContent(root.toExternalForm()) + "</resource>"); //NOI18N
                }
                out.println("\t</volume>"); //NOI18N
                out.println("</library>"); //NOI18N
            } finally {
                out.close();
            }
        } finally {
            lock.releaseLock();
        }
    }
}
