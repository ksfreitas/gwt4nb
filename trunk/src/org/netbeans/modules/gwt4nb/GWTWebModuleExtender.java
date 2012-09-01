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
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.logging.Level;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import org.netbeans.api.java.classpath.ClassPath;
import org.netbeans.api.java.project.classpath.ProjectClassPathModifier;
import org.netbeans.api.progress.ProgressHandle;
import org.netbeans.api.progress.ProgressHandleFactory;
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
import org.netbeans.modules.web.api.webmodule.ExtenderController;
import org.netbeans.modules.web.api.webmodule.WebModule;
import org.netbeans.modules.web.project.api.WebProjectLibrariesModifier;
import org.netbeans.modules.web.spi.webmodule.WebModuleExtender;
import org.netbeans.spi.project.support.ant.AntProjectHelper;
import org.netbeans.spi.project.support.ant.EditableProperties;
import org.openide.filesystems.FileLock;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileStateInvalidException;
import org.openide.filesystems.FileUtil;
import org.openide.loaders.DataFolder;
import org.openide.loaders.DataObject;
import org.openide.util.Exceptions;
import org.openide.util.HelpCtx;
import org.openide.util.NbBundle;
import org.openide.util.Utilities;
import org.openide.xml.XMLUtil;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

/**
 * Extends a project with GWT support.
 */
public class GWTWebModuleExtender extends WebModuleExtender {
    // see http://maven.apache.org/pom.html

    private static final List<String> POM_ELEMENTS = Arrays.asList(
            "modelVersion", // NOI18N
            "groupId", // NOI18N
            "artifactId", // NOI18N
            "version", // NOI18N
            "packaging", // NOI18N
            "dependencies", // NOI18N
            "parent", // NOI18N
            "dependencyManagement", // NOI18N
            "modules", // NOI18N
            "properties", // NOI18N
            "build", // NOI18N
            "reporting", // NOI18N
            "name", // NOI18N
            "description", // NOI18N
            "url", // NOI18N
            "inceptionYear", // NOI18N
            "licenses", // NOI18N
            "organization", // NOI18N
            "developers", // NOI18N
            "contributors", // NOI18N
            "issueManagement", // NOI18N
            "ciManagement", // NOI18N
            "mailingLists", // NOI18N
            "scm", // NOI18N
            "prerequisites", // NOI18N
            "repositories", // NOI18N
            "pluginRepositories", // NOI18N
            "distributionManagement", // NOI18N
            "profiles" // NOI18N
            );
    private static final List<String> BUILD_ELEMENTS = Arrays.asList(
            "directory", // NOI18N
            "outputDirectory", // NOI18N
            "finalName", // NOI18N
            "testOutputDirectory", // NOI18N
            "sourceDirectory", // NOI18N
            "scriptSourceDirectory", // NOI18N
            "testSourceDirectory", // NOI18N
            "resources", // NOI18N
            "testResources", // NOI18N
            "pluginManagement", // NOI18N
            "plugins" // NOI18N
            );
    private static final String GWT_USER = "gwt-user.jar"; // NOI18N
    private static final String GWT_SERVLET_DEPS = "gwt-servlet-deps.jar"; // NOI18N
    private static final String PATTERN_PRJ_NAME = "__PROJECT_NAME__"; // NOI18N
    private static final String CLIENT_FOLDER = "client"; // NOI18N
    private static final String TEMPLATE_JAVA_CLASS =
            "Templates/Classes/EntryPoint.java"; // NOI18N
    private static final String LIB_GWT_NAME = "GWT"; // NOI18N
    private static final String DEFAULT_GWT_VERSION_MAVEN = "2.0.3"; // NOI18N
    /**
     * this module will be extended with GWT or null if a new project is being
     * created
     */
    private WebModule webModule;
    private GWTConfigPanel pnlConfig = null;
    private final GWTFrameworkProvider fp;
    private final ExtenderController ec;
    private final Set<ChangeListener> listeners =
            new CopyOnWriteArraySet<ChangeListener>();

    /**
     * @param webModule this module will be extended with GWT or null if a new
     * project is being created
     * @param ec ?
     * @param fp GWT framework provider
     */
    public GWTWebModuleExtender(WebModule webModule,
            ExtenderController ec, GWTFrameworkProvider fp) {
        this.webModule = webModule;
        this.ec = ec;
        this.fp = fp;

        boolean defaultValue = webModule == null || !fp.isInWebModule(webModule);
        Project p = null;
        if (webModule != null) {
            p = FileOwnerQuery.getOwner(webModule.getDocumentBase());
        }
        pnlConfig = new GWTConfigPanel(p);
        pnlConfig.enableComponents(defaultValue);
        pnlConfig.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                fireChangeEvent();
            }
        });
    }

    @Override
    public void addChangeListener(ChangeListener l) {
        listeners.add(l);
    }

    @Override
    public void removeChangeListener(ChangeListener l) {
        listeners.remove(l);
    }

    /**
     * Fires a ChangeEvent.
     */
    private void fireChangeEvent() {
        final ChangeEvent ev = new ChangeEvent(this);

        for (final ChangeListener listener : listeners) {
            listener.stateChanged(ev);
        }
    }

    @Override
    public JComponent getComponent() {
        Project project = null;
        FileObject pom = null;
        if (webModule != null) {
            project = FileOwnerQuery.getOwner(webModule.getDocumentBase());
            if (project != null) {
                pom = project.getProjectDirectory().getFileObject(
                        "pom.xml"); // NOI18N
            }
        }
        if (pom == null) {
            return (JComponent) pnlConfig.getComponent();
        } else {
            return new JPanel();
        }
    }

    @Override
    public HelpCtx getHelp() {
        return null;
    }

    @Override
    public void update() {
        // TODO: pnlConfig.
    }

    @Override
    public boolean isValid() {
        return pnlConfig.isValid();
    }

    @Override
    public Set<FileObject> extend(final WebModule webModule) {
        // webModule is never null
        this.webModule = webModule;

        if (pnlConfig != null) {
            pnlConfig.setProject(FileOwnerQuery.getOwner(
                    webModule.getDocumentBase()));
        }

        final Set<FileObject> toOpen = new HashSet<FileObject>();
        try {
            enableGWT(toOpen);
        } catch (Exception ex) {
            GWT4NBUtil.LOGGER.log(Level.SEVERE, "", ex); // NOI18N
        }

        return toOpen;
    }

    /**
     * Creates gwt.properties for Ant projects.
     *
     * @param projectDir TODO
     * @param gwtModule TODO
     * @param gwtLocation TODO
     * @return gwt.properties
     */
    private static FileObject createGWTProperties(FileObject projectDir,
            String gwtModule, String gwtLocation) throws IOException {
        FileObject propFile = projectDir.createData(GWTProjectInfo.GWT_PROPERTIES);
        String gwtVersion_ = null;

        // update the default version with the selected for the project
        if (gwtLocation != null) {
            String gwtVersion = GWT4NBUtil.findGWTVersion(
                    new File(gwtLocation));
            if (gwtVersion != null) {
                Version ver = new Version(gwtVersion);
                gwtVersion_ = ver.getVersionPart(0) + "." + // NOI18N
                        ver.getVersionPart(1);
            }
        }
        if (gwtVersion_ == null) {
            gwtVersion_ = "2.4.0"; // NOI18N
        }
        EditableProperties ep = Gwt4nbModule.createGWTProperties();
        ep.setProperty("gwt.module", gwtModule); // NOI18N
        ep.setProperty("gwt.version", gwtVersion_); // NOI18N
        OutputStream os = propFile.getOutputStream();
        try {
            ep.store(os);
        } finally {
            os.close();
        }
        return propFile;
    }

    private static String[] getEntryPointModuleName(String entryPointClass) {
        if (entryPointClass == null) {
            return null;
        }
        int endIndex = entryPointClass.lastIndexOf('.');
        assert endIndex > 0;
        return new String[]{entryPointClass.substring(0, endIndex),
                    entryPointClass.substring(endIndex + 1)};
    }

    private static void createEntryPoint(Project project,
            final FileObject srcRoot,
            final String pkg, final String cls,
            final Set<FileObject> toOpen, Version gwtversion) throws IOException {
        FileObject folder;
        if (pkg.length() > 0) {
            folder = FileUtil.createFolder(
                    srcRoot, pkg.replace('.', '/')); // NOI18N
        } else {
            folder = srcRoot;
        }
        assert folder != null;
        FileObject cf = folder.createFolder(CLIENT_FOLDER);
        toOpen.add(createModule(folder, pkg, cls));
        toOpen.add(createJava(project, cf, cls, gwtversion));
    }

    private static void patchBuildXML(FileObject foBuildXML) {
        //TODO: provide a better implementation
        GWT4NBUtil.replaceInFile(foBuildXML,
                "<import file=\"nbproject/build-impl.xml\"/>", // NOI18N
                "<import file=\"nbproject/build-gwt.xml\"/>" + // NOI18N
                System.getProperty("line.separator") + // NOI18N
                "\t<import file=\"nbproject/build-impl.xml\"/>"); // NOI18N
    }

    /**
     * Changes the index.jsp file. Only when there is <h1>JSP Page</h1> string.
     */
    private void patchIndexJSP(FileObject indexjsp, String gwtModule) throws IOException {
        String nl = System.getProperty("line.separator"); // NOI18N
        GWT4NBUtil.replaceInFile(indexjsp, "<h1>JSP Page</h1>", // NOI18N
                "<h1>JSP Page</h1>" + nl + "\t<h3><a href=\"" + // NOI18N
                GWTProjectInfo.WELCOME_FILE + "\">GWT page</a></h3>" + nl); // NOI18N
    }

    private static FileObject createHtml(final FileObject folder,
            final String pkg, final String cls) throws IOException {
        assert folder != null;
        assert folder.isFolder();
        assert pkg != null;
        assert cls != null;
        FileObject fo = folder.createData("welcomeGWT.html"); // NOI18N
        FileLock lock = fo.lock();
        try {
            PrintWriter out = new PrintWriter(new OutputStreamWriter(fo.getOutputStream(lock)));
            try {
                String gwtOutputSubDir = pkg + '.' + cls;
                out.println("<!doctype html>"); // NOI18N
                out.println("<!--"); // NOI18N
                out.println("The DOCTYPE declaration above will set the browser's rendering engine into"); // NOI18N
                out.println("\"Standards Mode\". Replacing this declaration with a \"Quirks Mode\" doctype may"); // NOI18N
                out.println("lead to some differences in layout."); // NOI18N
                out.println("-->"); // NOI18N

                out.println("<html>"); // NOI18N
                out.println("    <head>"); // NOI18N
                out.println("        <meta name='gwt:module' content='" + gwtOutputSubDir + "=" + gwtOutputSubDir + "'>"); // NOI18N
                out.println("        <title>" + cls + "</title>"); // NOI18N
                out.println("        <script type=\"text/javascript\"  src=\"" + gwtOutputSubDir + "/" + gwtOutputSubDir + ".nocache.js\"></script>"); // NOI18N
                out.println("    </head>"); // NOI18N
                out.println("    <body>"); // NOI18N
                out.println("        <iframe src=\"javascript:''\" id=\"__gwt_historyFrame\" style=\"width:0;height:0;border:0\"></iframe>");
                out.println("    </body>"); // NOI18N
                out.println("</html>"); // NOI18N
            } finally {
                out.close();
            }
        } finally {
            lock.releaseLock();
        }
        return fo;
    }

    /**
     * Creates GWT module definition.
     *
     * @param folder output directory
     * @param pkg package or null if default client package should be used
     * @param cls name of the module without package
     * @return .gwt.xml file
     * @throws IOException if the file cannot be created/written
     */
    private static FileObject createModule(final FileObject folder,
            final String pkg, final String cls) throws IOException {
        assert folder != null;
        assert folder.isFolder();
        assert cls != null;
        FileObject fo = folder.createData(cls + ".gwt.xml"); // NOI18N
        String className = getEntryPointClassName(cls);
        String entryPoint = (pkg != null ? (pkg + '.') : "") + // NOI18N
                CLIENT_FOLDER + '.' + className;
        GWT4NBUtil.copyResource("/org/netbeans/modules/gwt4nb/Module.gwt.xml", // NOI18N
                fo, new String[]{"ENTRY_POINT"}, new String[]{entryPoint}); // NOI18N
        return fo;
    }

    private static FileObject createJava(Project project,
            final FileObject folder,
            final String cls, Version gwtversion) throws IOException {
        assert folder != null;
        assert folder.isFolder();
        assert cls != null;
        final FileObject jc = FileUtil.getConfigFile(TEMPLATE_JAVA_CLASS);
        assert jc != null;
        final DataObject template = DataObject.find(jc);
        assert template != null;
        String className = getEntryPointClassName(cls);
        final Map<String, Object> templateParameters = new HashMap<String, Object>(3);
        templateParameters.put("gwtversion", gwtversion); // NOI18N
        DataObject jdo = template.createFromTemplate(
                DataFolder.findFolder(folder), className, templateParameters);
        return jdo.getPrimaryFile();
    }

    private static String getEntryPointClassName(String moduleName) {
        return moduleName + "EntryPoint"; // NOI18N
    }

    private void addGWTLibraries(final Project project, final File gwtFolder)
            throws FileStateInvalidException, UnsupportedOperationException,
            IOException {
        String gwtVersion = GWT4NBUtil.findGWTVersion(gwtFolder);
        if (gwtVersion == null) {
            GWT4NBUtil.LOGGER.log(Level.SEVERE, "Cannot determine GWT version for {0}", gwtFolder);
            return;
        }

        // ADD LIBRARY
        Library libUser = LibraryManager.getDefault().getLibrary(
                LIB_GWT_NAME
                + gwtVersion);
        if (libUser == null) {
            libUser = createGWTUserLibrary(gwtFolder, LIB_GWT_NAME
                    + gwtVersion);
        }
        final FileObject sourcesDir = GWTProjectInfo.getSourcesDir(project);

        if (libUser != null) {
            // set global GWTLocation
            GWTSettings.setGWTLocation(gwtFolder);
            ProjectClassPathModifier.addLibraries(new Library[]{
                        libUser}, sourcesDir, ClassPath.COMPILE);
        } else {
            GWT4NBUtil.LOGGER.warning("Could not add GWT library"); // NOI18N
        }

//        final FileObject gwtRtLib = GWT4NBUtil.getGWTDevArchive(gwtFolder);
//        assert gwtRtLib != null;

//        final FileObject gwtServletLib = FileUtil.toFileObject(
//                FileUtil.normalizeFile(new File(gwtFolder,
//                "gwt-servlet.jar"))); // NOI18N
//
//        ProjectClassPathModifier.addRoots(new URL[]{
//                    FileUtil.getArchiveRoot(gwtRtLib.toURL())}, sourcesDir, ClassPath.COMPILE);
//
//        ProjectClassPathModifier.addRoots(new URL[]{
//                    FileUtil.getArchiveRoot(gwtServletLib.toURL())},
//                sourcesDir, ClassPath.COMPILE);

        final EditableProperties projectProps =
                GWT4NBUtil.getEditableProperties(
                project, AntProjectHelper.PROJECT_PROPERTIES_PATH);
        projectProps.setProperty("j2ee.deploy.on.save", "false"); // NOI18N

        GWT4NBUtil.storeEditableProperties(
                project,
                AntProjectHelper.PROJECT_PROPERTIES_PATH,
                projectProps);
    }

    /**
     * Enables GWT in a web project.
     *
     * @param toOpen files that will be opened in the editor are stored here
     */
    private void enableGWT(Set<FileObject> toOpen) throws IOException {
        Project project = FileOwnerQuery.getOwner(webModule.getDocumentBase());
        assert project != null;

        FileObject pom = project.getProjectDirectory().getFileObject(
                "pom.xml"); // NOI18N
        if (pom != null) {
            extendMavenProject();
        } else {
            extendAntProject(toOpen);
        }
    }

    /**
     * Extends an Ant based project with GWT
     *
     * @param toOpen files that will be opened in the editor are stored here
     */
    private void extendAntProject(Set<FileObject> toOpen) throws IOException {
        Project project = FileOwnerQuery.getOwner(webModule.getDocumentBase());
        assert project != null;

        ProjectInformation projectInfo = ProjectUtils.getInformation(project);
        String projectName = projectInfo.getName();

        FileObject nbprj = project.getProjectDirectory().getFileObject(
                GWTProjectInfo.PRJ_DIR);

        // COPY build-gwt.xml
        FileObject buildGwt = nbprj.createData(GWTProjectInfo.BUILD_GWT);
        GWT4NBUtil.copyResource(GWTProjectInfo.RESOURCE_BASE
                + GWTProjectInfo.BUILD_GWT, buildGwt,
                new String[]{PATTERN_PRJ_NAME}, new String[]{projectName});
        FileObject foBuildXML = nbprj.getParent().getFileObject(
                "build.xml"); // NOI18N
        patchBuildXML(foBuildXML);

        File gwtFolder = pnlConfig.getGwtFolder();

        addGWTLibraries(project, gwtFolder);

        // create entry point class
        FileObject src = GWTProjectInfo.getSourcesDir(project);
        String[] epNames = getEntryPointModuleName(pnlConfig.getGWTModule());
        if (epNames != null) {
            assert epNames.length == 2 && epNames[0] != null
                    && epNames[1] != null;
            final String version = GWT4NBUtil.findGWTVersion(gwtFolder);
            if (version != null) {
                createEntryPoint(project, src, epNames[0], epNames[1],
                        toOpen,
                        new Version(version));
            }
        }

        String gwtLocation;
        if (System.getProperty("os.name").contains("Windows")) { // NOI18N
            gwtLocation = gwtFolder.toString().replace("\\", "/"); // NOI18N
        } else {
            gwtLocation = gwtFolder.toString();
        }

        GWT4NBUtil.setProjectGWTDir(project, new File(gwtLocation));

        createGWTProperties(nbprj, pnlConfig.getGWTModule(),
                gwtLocation);

        // this is necessary, because gwt.properties template
        // does not contain all properties, some of them are only
        // created in Gwt4nbModule
        Gwt4nbModule.updateGWTProject(project);

        if (epNames != null) {
            toOpen.add(createHtml(webModule.getDocumentBase(), epNames[0],
                    epNames[1]));
        }

        FileObject indexJSP = webModule.getDocumentBase().getFileObject(
                "index.jsp"); // NOI18N

        if (indexJSP != null && indexJSP.isData() && indexJSP.canRead()
                && indexJSP.canWrite()) {
            patchIndexJSP(indexJSP, pnlConfig.getGWTModule());
        } else {
            GWT4NBUtil.LOGGER.warning("Couldn't patch index.jsp"); // NOI18N
        }

        updateWelcomePage(webModule);
    }

    /**
     * Extends a Maven project with GWT.
     */
    private void extendMavenProject() {
        try {
            Project project =
                    FileOwnerQuery.getOwner(webModule.getDocumentBase());
            FileObject pom =
                    project.getProjectDirectory().getFileObject(
                    "pom.xml"); // NOI18N
            Document pomDoc = GWT4NBUtil.parseXMLFile(pom);
            extendPOM(pomDoc);
            FileLock lock = pom.lock();
            try {
                OutputStream os = pom.getOutputStream(lock);
                try {
                    XMLUtil.write(pomDoc, os, "UTF-8"); // NOI18N
                } finally {
                    os.close();
                }
            } finally {
                lock.releaseLock();
            }

            // create entry point class
            FileObject src = GWTProjectInfo.getSourcesDir(project);
            String[] epNames = new String[]{
                "org.yournamehere", "Main" // NOI18N
            };
            if (src != null) {
                Set<FileObject> toOpen = new HashSet<FileObject>();
                final String version = DEFAULT_GWT_VERSION_MAVEN;
                createEntryPoint(project, src, epNames[0], epNames[1],
                        toOpen, new Version(version));
            }

            // create welcomeGWT.html
            createHtml(webModule.getDocumentBase(), epNames[0],
                    epNames[1]);

            updateWelcomePage(webModule);
        } catch (Exception ex) {
            Exceptions.printStackTrace(ex);
        }
    }

    /**
     * Extends a pom.xml with elements for GWT.
     *
     * @param pomDoc pom.xml
     */
    private void extendPOM(Document pomDoc) {
        Element doce = pomDoc.getDocumentElement();

        Element properties = findOrCreate(doce,
                "properties", POM_ELEMENTS); // NOI18N
        appendTextElement(properties,
                "gwt.version", DEFAULT_GWT_VERSION_MAVEN); // NOI18N
        appendTextElement(properties,
                "runTarget", "welcomeGWT.html"); // NOI18N

        Element dependencies = findOrCreate(doce, "dependencies", // NOI18N
                POM_ELEMENTS);

        appendDependencyElement(dependencies, "com.google.gwt", // NOI18N
                "gwt-servlet", "${gwt.version}", "runtime"); // NOI18N
        appendDependencyElement(dependencies, "com.google.gwt", // NOI18N
                "gwt-user", "${gwt.version}", "provided"); // NOI18N

        Element build = findOrCreate(doce,
                "build", POM_ELEMENTS); // NOI18N
        Element plugins = findOrCreate(build,
                "plugins", BUILD_ELEMENTS); // NOI18N
        Element plugin = pomDoc.createElement("plugin"); // NOI18N
        plugins.appendChild(plugin);
        appendTextElement(plugin, "groupId", "org.codehaus.mojo"); // NOI18N
        appendTextElement(plugin, "artifactId", "gwt-maven-plugin"); // NOI18N
        appendTextElement(plugin, "version", "1.2"); // NOI18N
        Element executions = pomDoc.createElement("executions"); // NOI18N
        plugin.appendChild(executions);
        Element execution = pomDoc.createElement("execution"); // NOI18N
        executions.appendChild(execution);
        Element goals = pomDoc.createElement("goals"); // NOI18N
        execution.appendChild(goals);
        appendTextElement(goals, "goal", "compile"); // NOI18N
        //appendTextElement(goals, "goal", "generateAsync");
        appendTextElement(goals, "goal", "test"); // NOI18N
        Element configuration = pomDoc.createElement(
                "configuration"); // NOI18N
        plugin.appendChild(configuration);
        appendTextElement(configuration, "hostedWebapp", // NOI18N
                "${project.build.directory}/${project.build.finalName}"); // NOI18N

        /*
         This is added to remove javax.servlet classes from WEB-INF/classes
         http://jira.codehaus.org/browse/MGWT-166
         <plugin>
         <groupId>org.apache.maven.plugins</groupId>
         <artifactId>maven-antrun-plugin</artifactId>
         <executions>
         <execution>
         <id>remove-javax</id>
         <phase>compile</phase>
         <configuration>
         <tasks>
         <delete dir="${project.build.directory}/classes/javax" />
         </tasks>
         </configuration>
         <goals>
         <goal>run</goal>
         </goals>
         </execution>
         </executions>
         </plugin>
         */
        plugin = pomDoc.createElement("plugin"); // NOI18N
        plugins.appendChild(plugin);
        appendTextElement(plugin,
                "groupId", "org.apache.maven.plugins"); // NOI18N
        appendTextElement(plugin,
                "artifactId", "maven-antrun-plugin"); // NOI18N
        executions = pomDoc.createElement("executions"); // NOI18N
        plugin.appendChild(executions);
        execution = pomDoc.createElement("execution"); // NOI18N
        executions.appendChild(execution);
        appendTextElement(execution, "id", "remove-javax"); // NOI18N
        appendTextElement(execution, "phase", "compile"); // NOI18N
        configuration = pomDoc.createElement("configuration"); // NOI18N
        plugin.appendChild(configuration);
        Element tasks = pomDoc.createElement("tasks"); // NOI18N
        configuration.appendChild(tasks);
        Element delete = pomDoc.createElement("delete"); // NOI18N
        delete.setAttribute("dir", // NOI18N
                "${project.build.directory}/classes/javax"); // NOI18N
        tasks.appendChild(delete);
        goals = pomDoc.createElement("goals"); // NOI18N
        execution.appendChild(goals);
        appendTextElement(goals, "goal", "run"); // NOI18N
    }

    /**
     * Adds a <dependency> element.
     *
     * @param dependencies <dependencies>
     * @param groupId groupId
     * @param artifactId artifactId
     * @param version version
     * @param scope scope
     * @return created element
     */
    private Element appendDependencyElement(Element dependencies,
            String groupId, String artifactId, String version,
            String scope) {
        Element dependency = dependencies.getOwnerDocument().
                createElement("dependency"); // NOI18N
        dependencies.appendChild(dependency);
        appendTextElement(dependency, "groupId", groupId); // NOI18N
        appendTextElement(dependency, "artifactId", artifactId); // NOI18N
        appendTextElement(dependency, "version", version); // NOI18N
        appendTextElement(dependency, "scope", scope); // NOI18N
        return dependency;
    }

    /**
     * Creates a child element with the given text.
     *
     * @param parent parent element
     * @param name name of the child element
     * @param value text for the child element
     * @return child element
     */
    private Element appendTextElement(Element parent,
            String name, String value) {
        Element e = parent.getOwnerDocument().createElement(name);
        parent.appendChild(e);
        Text t = parent.getOwnerDocument().
                createTextNode(value);
        e.appendChild(t);
        return e;
    }

    /**
     * Searches child elements for one with a partucular name. Creates it if it
     * does not yet exist.
     *
     * @param parent parent element
     * @param name name of the new element
     * @param def names of the elements according to DTD (in the right order
     * @return the found or inserted element
     */
    private Element findOrCreate(Element parent, String name,
            List<String> def) {
        NodeList nl = parent.getElementsByTagName(name);
        if (nl.getLength() > 0) {
            return (Element) nl.item(0);
        } else {
            int index = def.indexOf(name);
            nl = parent.getElementsByTagName("*"); // NOI18N
            Element insertBefore = null;
            for (int i = 0; i < nl.getLength(); i++) {
                Element el = (Element) nl.item(i);
                int eindex = def.indexOf(el.getNodeName());
                if (eindex > index) {
                    insertBefore = el;
                    break;
                }
            }
            Element e = parent.getOwnerDocument().createElement(name);
            if (insertBefore != null) {
                parent.insertBefore(e, insertBefore);
            } else {
                parent.appendChild(e);
            }
            return e;
        }
    }

    /**
     * Changes the GWT directory for a project
     *
     * @param webModule a web module
     * @param newGWTDir new GWT directory
     * @param progressBar here the progress of the operation will be shown
     */
    public static void upgradeGWTVersion(
            final WebModule webModule,
            final File newGWTDir) {
        try {
            upgradeGWT(webModule, newGWTDir);
        } catch (Throwable ex) {
            GWT4NBUtil.unexpectedException(ex);
        }
    }

    /**
     * Change GWT directory for a project.
     *
     * @param webModule web module
     * @param newGWTDir new GWT directory
     */
    private static void upgradeGWT(final WebModule webModule, File newGWTDir)
            throws IOException {
        ProgressHandle ph =
                ProgressHandleFactory.createHandle(
                NbBundle.getMessage(GWTWebModuleExtender.class,
                "ChangingLoc")); // NOI18N
        ph.start(100);
        try {
            Project project = FileOwnerQuery.getOwner(
                    webModule.getDocumentBase());
            assert project != null;
            WebProjectLibrariesModifier wplm =
                    project.getLookup().lookup(
                    WebProjectLibrariesModifier.class);

            // find out the old version
            File oldGWTDir = GWT4NBUtil.getProjectGWTDir(project);
            String prevGWTVersion = null;
            if (oldGWTDir != null) {
                prevGWTVersion = GWT4NBUtil.findGWTVersion(oldGWTDir);
            }

            // fetch upgraded GWT version
            String newGWTVersion = GWT4NBUtil.findGWTVersion(newGWTDir);
            if (newGWTVersion == null) {
                GWT4NBUtil.LOGGER.log(
                        Level.SEVERE, "Cannot determine GWT version for {0}", newGWTDir);
                return;
            }

            // update global gwtLocation
            GWTSettings.setGWTLocation(newGWTDir);

            // update gwt properties for the project.
            String newGWTLocation = newGWTDir.toString();
            if (System.getProperty("os.name").contains("Windows")) { // NOI18N
                newGWTLocation = newGWTLocation.replace("\\", "/"); // NOI18N
            }
            GWT4NBUtil.setProjectGWTDir(project, new File(newGWTLocation));

            final FileObject sourcesDir = GWTProjectInfo.getSourcesDir(project);
            // Remove reference to the obsolete GWT lib if present.
            Library libUserWithoutVersion = LibraryManager.getDefault().
                    getLibrary(LIB_GWT_NAME);
            if (libUserWithoutVersion != null) {
                ProjectClassPathModifier.removeLibraries(new Library[]{
                            libUserWithoutVersion}, sourcesDir, ClassPath.COMPILE);
            }

            // Also remove the reference to the previousGWT${version} lib.
            // prevGwtVersion may be null, but this only results in the library
            // not being found
            Library oldLibUser = LibraryManager.getDefault().getLibrary(
                    LIB_GWT_NAME + prevGWTVersion);
            if (oldLibUser != null) {
                try {
                    // this sometimes throws the following:
                    // org.netbeans.modules.masterfs.filebasedfs.utils.
                    // FSException: Cannot get exclusive access to
                    // \nbproject\project.properties
                    // (probably opened for reading).
                    ProjectClassPathModifier.removeLibraries(new Library[]{
                                oldLibUser}, sourcesDir, ClassPath.COMPILE);
                } catch (Throwable t) {
                    GWT4NBUtil.unexpectedException(t);
                }
            }

            // Add the newly created GWT${version} lib reference.
            Library newLibUser = LibraryManager.getDefault().getLibrary(
                    LIB_GWT_NAME + newGWTVersion);
            if (newLibUser == null) {
                newLibUser = createGWTUserLibrary(
                        newGWTDir, LIB_GWT_NAME + newGWTVersion);
            }

            ProjectClassPathModifier.addLibraries(new Library[]{
                        newLibUser}, sourcesDir, ClassPath.COMPILE);
            ph.progress(55);

//            // remove old gwt-dev...jar
//            FileObject oldGWTDevJar = GWT4NBUtil.getGWTDevArchive(oldGWTDir);
//            if (oldGWTDevJar != null) {
//                ProjectClassPathModifier.removeRoots(new URL[]{
//                            FileUtil.getArchiveRoot(oldGWTDevJar.toURL())},
//                        sourcesDir, ClassPath.COMPILE);
//            }

//            // add new gwt-dev...jar
//            FileObject newGWTDevJar = GWT4NBUtil.getGWTDevArchive(newGWTDir);
//            ProjectClassPathModifier.removeRoots(new URL[]{
//                        FileUtil.getArchiveRoot(newGWTDevJar.toURL())},
//                    sourcesDir, ClassPath.COMPILE);
//            ph.progress(75);

//            // remove old gwt-servlet...jar
//            FileObject oldGWTServletJar = FileUtil.toFileObject(
//                    FileUtil.normalizeFile(new File(oldGWTDir,
//                    "gwt-servlet.jar"))); // NOI18N
//            if (oldGWTServletJar != null) {
//                ProjectClassPathModifier.removeRoots(new URL[]{
//                            FileUtil.getArchiveRoot(oldGWTServletJar.toURL())},
//                        sourcesDir, ClassPath.COMPILE);
//            }

//            // add new gwt-servlet...jar
//            FileObject newGWTServletJar = FileUtil.toFileObject(
//                    FileUtil.normalizeFile(new File(newGWTDir,
//                    "gwt-servlet.jar"))); // NOI18N
//            ProjectClassPathModifier.addRoots(new URL[]{
//                        FileUtil.getArchiveRoot(newGWTServletJar.toURL())},
//                    sourcesDir, ClassPath.COMPILE);

            // write new version to nbproject/gwt.properties
            GWTProjectInfo info =
                    GWTProjectInfo.get(project);
            info.setAntProperty("gwt.version", // NOI18N
                    new Version(newGWTVersion).
                    toString(2, 2));
        } finally {
            ph.finish();
        }
    }

    /**
     * Creates a GWTXXX library.
     *
     * @param gwtFolder GWT directory
     * @param libname name of the library
     * @return created library
     */
    private static Library createGWTUserLibrary(File gwtFolder,
            String libname) throws IOException {
        assert gwtFolder != null;

        final File userJar = new File(gwtFolder, GWT_USER);
        final File servletDepsJar = new File(gwtFolder, GWT_SERVLET_DEPS);
        ArrayList<URL> lib = new ArrayList<URL>();
        lib.add(FileUtil.getArchiveRoot(Utilities.toURI(userJar).toURL()));
        lib.add(FileUtil.getArchiveRoot(Utilities.toURI(servletDepsJar).toURL()));

        Map<String, List<URL>> contents = new HashMap<String, List<URL>>();
        contents.put("classpath", lib);
        contents.put("src", lib);
        final File gwtJavadoc = FileUtil.normalizeFile(
                new File(new File(gwtFolder, "doc"), "javadoc")); // NOI18N
        if (gwtJavadoc.exists()
                && gwtJavadoc.isDirectory() && gwtJavadoc.canRead()) {
            contents.put("javadoc", Collections.singletonList( // NOI18N
                    Utilities.toURI(gwtJavadoc).toURL()));
        }
        return LibraryManager.getDefault().createLibrary("j2se", // NOI18N
                libname,
                contents);
    }

    /**
     * Updates the welcome page in the web.xml (adds welcomeGWT.html).
     *
     * @param webModule web module
     */
    private void updateWelcomePage(WebModule webModule) throws IOException {
        FileObject dd = webModule.getDeploymentDescriptor();
        if (dd != null) {
            WebApp ddRoot = DDProvider.getDefault().getDDRoot(dd);

            if (ddRoot != null) {
                try {
                    WelcomeFileList welcomeFiles = ddRoot.
                            getSingleWelcomeFileList();
                    if (welcomeFiles == null) {
                        welcomeFiles = (WelcomeFileList) ddRoot.createBean(
                                "WelcomeFileList"); // NOI18N
                        ddRoot.setWelcomeFileList(welcomeFiles);
                    }

                    // this "if" (but not it's content)
                    // is only a workaround for
                    // https://netbeans.org/bugzilla/show_bug.cgi?id=178900
                    if (welcomeFiles != null) {
                        if (welcomeFiles.sizeWelcomeFile() == 0) {
                            String welcomePage = GWTProjectInfo.WELCOME_FILE;
                            welcomeFiles.addWelcomeFile(welcomePage); // NOI18N
                        }
                    }

                    ddRoot.write(dd);
                } catch (ClassNotFoundException e) {
                    GWT4NBUtil.LOGGER.log(Level.SEVERE, "", e); // NOI18N
                }
            }
        }
    }
}
