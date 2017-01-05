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

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;
import java.util.regex.Pattern;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.netbeans.api.project.Project;
import org.netbeans.api.project.ProjectManager;
import org.netbeans.api.project.ProjectUtils;
import org.netbeans.modules.gwt4nb.settings.GWTSettings;
import org.netbeans.spi.project.support.ant.EditableProperties;
import org.openide.filesystems.FileLock;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileSystem.AtomicAction;
import org.openide.filesystems.FileUtil;
import org.openide.filesystems.URLMapper;
import org.openide.util.Exceptions;
import org.openide.util.Mutex;
import org.openide.util.MutexException;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

/**
 * Utilities
 *
 * @author tomslot
 * @author see https://github.com/gwt4nb/gwt4nb/
 */
public class GWT4NBUtil {

    /**
     * GWT installation dir property name for
     * ProjectUtils.getPreferences(project, getClass(), false).get(...)
     * build-gwt.xml uses the same value.
     */
    public static final String GWT_DIR_PROPERTY = "GWTDir"; // NOI18N

    /**
     * Logger for the whole module.
     */
    public static final Logger LOGGER = Logger.getLogger(GWT4NBUtil.class.getName());

    static {
        System.setProperty(LOGGER.getName() + ".level", "100"); // NOI18N
        try {
            LogManager.getLogManager().readConfiguration();
        } catch (IOException ex) {
            Exceptions.printStackTrace(ex);
        } catch (SecurityException ex) {
            Exceptions.printStackTrace(ex);
        }
    }

    public static void replaceInFile(FileObject file, String searchedString,
            String replacement) {
        replaceInFile(file, new String[]{searchedString},
                new String[]{replacement});
    }

    public static void replaceInFile(FileObject file,
            String searchedStrings[], String replacements[]) {
        //TODO: provide a better implementation
        if (searchedStrings.length != replacements.length) {
            throw new IllegalArgumentException();
        }

        String orgFileContent = null;

        InputStream is = null;
        try {
            is = file.getInputStream();
            byte rawContent[] = new byte[is.available()];
            is.read(rawContent);
            orgFileContent = new String(rawContent);

            is.close();

            String alteredContent = orgFileContent;

            for (int i = 0; i < searchedStrings.length; i++) {
                alteredContent = alteredContent.replace(
                        searchedStrings[i], replacements[i]);
            }

            FileLock lock = file.lock();
            PrintWriter writer = new PrintWriter(file.getOutputStream(lock));
            writer.print(alteredContent);
            writer.close();
            lock.releaseLock();
        } catch (IOException ex) {
            GWT4NBUtil.LOGGER.log(Level.SEVERE, "", ex); // NOI18N
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
            } catch (IOException ex) {
                GWT4NBUtil.LOGGER.log(Level.SEVERE, "", ex); // NOI18N
            }
        }
    }

    /**
     * Copies a resource to a file and replaces variables.
     *
     * @param res {@link Class#getResourceAsStream(java.lang.String)}
     * @param to target file
     * @param mapFrom regular expressions to search in every line
     * @param mapTo replacement strings
     */
    public static void copyResource(final String res, final FileObject to,
            final String[] mapFrom, final String[] mapTo) throws IOException {
        copyResource(res, to, mapFrom, mapTo, false);
    }

    /**
     * Copies a resource to a file and replaces variables.
     *
     * @param res {@link Class#getResourceAsStream(java.lang.String)}
     * @param to target file
     * @param mapFrom regular expressions to search
     * @param mapTo replacement strings
     * @param multiLine whether to search/replace within multiple lines
     */
    public static void copyResource(final String res, final FileObject to,
            final String[] mapFrom, final String[] mapTo, boolean multiLine) throws IOException {
        assert res != null;
        assert to != null;
        assert mapFrom != null;
        assert mapTo != null;
        assert mapFrom.length == mapTo.length;
        InputStream _in = GWTFrameworkProvider.class.getClassLoader().
                getResourceAsStream(res);
        assert _in != null;
        BufferedReader in = new BufferedReader(new InputStreamReader(_in));
        try {
            FileLock lock = to.lock();
            try {
                if (multiLine) {
                    //first copy input to String without regex mapping
                    StringWriter sw = new StringWriter();
                    PrintWriter out = new PrintWriter(sw);
                    try {
                        copyStream(in, out, new String[0], new String[0]);
                    } finally {
                        out.close();
                    }
                    //regex/replace an entire content string
                    String content = sw.toString();
                    for (int i = 0; i < mapFrom.length; i++) {
                        content = content.replaceAll(mapFrom[i], mapTo[i]);
                    }
                    //write to the target
                    OutputStreamWriter writer = new OutputStreamWriter(to.getOutputStream(lock));
                    try {
                        writer.write(content);
                    } finally {
                        writer.close();
                    }
                } else {
                    PrintWriter out = new PrintWriter(
                            new OutputStreamWriter(to.getOutputStream(lock)));
                    try {
                        copyStream(in, out, mapFrom, mapTo);
                    } finally {
                        out.close();
                    }
                }
            } finally {
                lock.releaseLock();
            }
        } finally {
            in.close();
        }
    }

    public static void copyStream(final BufferedReader in,
            final PrintWriter out, final String[] mapFrom,
            String[] mapTo) throws IOException {
        String line;
        while ((line = in.readLine()) != null) {
            for (int i = 0; i < mapFrom.length; i++) {
                line = line.replaceAll(mapFrom[i], mapTo[i]);
            }
            out.println(line);
        }
    }

    public static boolean isValidJavaIdentifier(String txt) {
        if (txt.length() == 0 || !Character.isJavaIdentifierStart(txt.charAt(0))) {
            return false;
        }

        for (int i = 1; i < txt.length(); i++) {
            if (!Character.isJavaIdentifierPart(txt.charAt(i))) {
                return false;
            }
        }

        return true;
    }

    /**
     * Changes the associated GWT directory for a project.
     *
     * @param project a GWT project
     * @param d new GWT directory
     */
    public static void setProjectGWTDir(Project project, File d) {
        Preferences pp
                = ProjectUtils.getPreferences(project, GWT4NBUtil.class, false);
        pp.put(GWT4NBUtil.GWT_DIR_PROPERTY, d.getAbsolutePath());
        try {
            pp.flush();
        } catch (BackingStoreException ex) {
            Exceptions.printStackTrace(ex);
        }
    }

    /**
     * Returns GWT directory for a project
     *
     * @param project a project
     * @return GWT directory or null
     */
    public static File getProjectGWTDir(Project project) {
        assert project != null;
        Preferences pp
                = ProjectUtils.getPreferences(project, GWT4NBUtil.class, false);
        String gwtLocation = pp.get(GWT4NBUtil.GWT_DIR_PROPERTY, null);

        // for older projects read the property from
        // nbproject/gwt.properties
        if (gwtLocation == null) {
            gwtLocation = GWTProjectInfo.readGWTPropertyAnt(project,
                    "gwt.install.dir"); // NOI18N
            if (gwtLocation != null) {
                pp.put(GWT4NBUtil.GWT_DIR_PROPERTY, gwtLocation);
                try {
                    pp.flush();
                } catch (BackingStoreException ex) {
                    Exceptions.printStackTrace(ex);
                }
            }
        }

        if (gwtLocation == null) {
            File f = GWTSettings.getGWTLocation();
            if (f != null) {
                gwtLocation = f.getAbsolutePath();
                pp.put(GWT4NBUtil.GWT_DIR_PROPERTY, gwtLocation);
                try {
                    pp.flush();
                } catch (BackingStoreException ex) {
                    Exceptions.printStackTrace(ex);
                }
            }
        }

        if (gwtLocation != null) {
            return new File(gwtLocation);
        } else {
            return null;
        }
    }

    /**
     * Parses an XML file
     *
     * @param fo XML file
     * @return parsed file
     */
    public static Document parseXMLFile(FileObject fo)
            throws ParserConfigurationException, SAXException, IOException {
        DocumentBuilderFactory dbf
                = javax.xml.parsers.DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        InputStream is = fo.getInputStream();
        Document doc;
        try {
            doc = db.parse(is);
        } finally {
            is.close();
        }
        return doc;
    }

    /**
     * Checks for presence of pom.xml
     *
     * @param project a project
     * @return true = Maven project
     */
    public static boolean isMavenProject(Project project) {
        final FileObject projectDirectory = project.getProjectDirectory();
        FileObject pom = projectDirectory.getFileObject("pom.xml"); // NOI18N
        return pom != null;
    }

    /**
     * Finds a FileObject for a resource in this module.
     *
     * @param path /org/netbeans/modules/gwt4nb/A.txt
     * @return the corresponding FileObject
     */
    public static FileObject getModuleResource(String path) {
        return URLMapper.findFileObject(GWT4NBUtil.class.getResource(path));
    }

    /**
     * This method should be called if an unexpected exception occures.
     * Something like BadLocationException if you know the text insert position
     * is right.
     *
     * @param ex the "unexpected" exception
     */
    public static void unexpectedException(Throwable ex) {
        Exceptions.printStackTrace(ex);
    }

    public static EditableProperties getEditableProperties(final Project prj,
            final String propertiesPath)
            throws IOException {
        try {
            return ProjectManager.mutex().readAccess(
                    new Mutex.ExceptionAction<EditableProperties>() {
                        public EditableProperties run() throws IOException {
                            FileObject propertiesFo = prj.getProjectDirectory().
                            getFileObject(propertiesPath);
                            EditableProperties ep = null;
                            if (propertiesFo != null) {
                                InputStream is = null;
                                ep = new EditableProperties();
                                try {
                                    is = propertiesFo.getInputStream();
                                    ep.load(is);
                                } finally {
                                    if (is != null) {
                                        is.close();
                                    }
                                }
                            }
                            return ep;
                        }
                    });
        } catch (MutexException ex) {
            return null;
        }
    }

    public static void storeEditableProperties(final Project prj,
            final String propertiesPath, final EditableProperties ep)
            throws IOException {
        try {
            ProjectManager.mutex().writeAccess(
                    new Mutex.ExceptionAction<Void>() {
                        public Void run() throws IOException {
                            FileObject propertiesFo = prj.getProjectDirectory().
                            getFileObject(propertiesPath);
                            if (propertiesFo != null) {
                                OutputStream os = null;
                                try {
                                    os = propertiesFo.getOutputStream();
                                    ep.store(os);
                                } finally {
                                    if (os != null) {
                                        os.close();
                                    }
                                }
                            }
                            return null;
                        }
                    });
        } catch (MutexException ex) {
        }
    }

    /**
     * Checks a GWT module name for validity.
     *
     * @param name name of a GWT module
     * @return true = valid
     */
    public static boolean isValidGWTModuleName(String name) {
        boolean validModule = true;
        String parts[] = name.split("\\."); // NOI18N

        if (parts.length < 2 || name.endsWith(".")) { // NOI18N
            validModule = false;
        } else {
            for (String part : parts) {
                if (!GWT4NBUtil.isValidJavaIdentifier(part)) {
                    validModule = false;
                    break;
                }
            }
        }
        return validModule;
    }

    /**
     * Finds version of a GWT installation.
     *
     * @param gwtFolder something like C:\libs\gwt-windows-1.7.0
     * @return version or null if it cannot be determined
     */
    public static String findGWTVersion(final File gwtFolder) {
        try {
            final FindGWTVersionAction findGWTVersion
                    = new FindGWTVersionAction(gwtFolder);

            FileUtil.runAtomicAction(findGWTVersion);

            return findGWTVersion.getVersion();
        } catch (final IOException ex) {
            Exceptions.printStackTrace(ex);
        }

        return null;
    }

    /**
     * Searches for "gwt-dev-\\w*.jar" in a directory.
     *
     * @param gwtRoot something like C:\libs\gwt-windows-1.7.0
     * @return "gwt-dev-\\w*.jar" or null
     */
    public static FileObject getGWTDevArchive(final File gwtRoot) {
        assert gwtRoot != null;

        final File[] files = gwtRoot.listFiles();
        if (files != null) {
            final Pattern pattern = Pattern.compile("gwt-dev(-\\w*)?.jar", // NOI18N
                    Pattern.CASE_INSENSITIVE);

            for (final File file : files) {
                if (pattern.matcher(file.getName()).matches()) {
                    return FileUtil.toFileObject(FileUtil.normalizeFile(file));
                }
            }
        }
        return null;
    }

    private static class FindGWTVersionAction implements AtomicAction {

        private String version = null;

        private final File gwtFolder;

        public FindGWTVersionAction(final File gwtFolder) {
            this.gwtFolder = gwtFolder;
        }

        @SuppressWarnings("rawtypes")
        public void run() throws IOException {
            ClassLoader cl = null;
            try {
                final FileObject devjar = getGWTDevArchive(gwtFolder);
                if (devjar != null) {
                    final File devjar_ = FileUtil.toFile(devjar);
                    if (devjar_ != null) {
                        final URL url = devjar_.toURI().toURL();
                        cl = AccessController.doPrivileged(
                                new PrivilegedAction<ClassLoader>() {
                                    public ClassLoader run() {
                                        return new URLClassLoader(new URL[]{url});
                                    }
                                });

                        final Class cls = cl.loadClass(
                                "com.google.gwt.dev.About"); // NOI18N
                        final Field gwtVers = cls.getField("GWT_VERSION_NUM"); // NOI18N
                        this.version = gwtVers.get(null).toString();
                    }
                }
            } catch (IllegalArgumentException ex) {
                Exceptions.printStackTrace(ex);
            } catch (IllegalAccessException ex) {
                Exceptions.printStackTrace(ex);
            } catch (NoSuchFieldException ex) {
                // gwt2.6.0
                // https://github.com/ksfreitas/gwt4nb/issues/26
                // I'm not sure if this function exists in older releases
                if (cl != null) {
                    try {
                        final Class cls = cl.loadClass(
                                "com.google.gwt.dev.About"); // NOI18N
                        final Method method = cls.getMethod("getGwtVersionNum", new Class[]{});
                        this.version = (String) method.invoke(null, new Object[]{});
                    } catch (Exception ex1) {
                        Exceptions.printStackTrace(ex1);
                    }
                } else {
                    Exceptions.printStackTrace(ex);
                }
            } catch (SecurityException ex) {
                Exceptions.printStackTrace(ex);
            } catch (ClassNotFoundException ex) {
                Exceptions.printStackTrace(ex);
            }
        }

        /**
         * @return GWT version or null if it cannot be determined
         */
        private String getVersion() {
            return version;
        }
    }

    /**
     * Joins multiple strings together.
     *
     * @param values strings
     * @param sep separator
     * @return created string
     */
    public static String join(Collection<String> values, String sep) {
        StringBuilder sb = new StringBuilder();
        for (String s : values) {
            if (sb.length() != 0) {
                sb.append(sep);
            }
            sb.append(s);
        }

        return sb.toString();
    }
}
