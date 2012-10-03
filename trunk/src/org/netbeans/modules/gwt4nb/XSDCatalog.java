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

import java.awt.Image;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.netbeans.api.java.project.JavaProjectConstants;
import org.netbeans.api.project.Project;
import org.netbeans.api.project.ProjectUtils;
import org.netbeans.api.project.SourceGroup;
import org.netbeans.api.project.ui.OpenProjects;
import org.netbeans.modules.xml.catalog.spi.CatalogDescriptor;
import org.netbeans.modules.xml.catalog.spi.CatalogListener;
import org.netbeans.modules.xml.catalog.spi.CatalogReader;
import org.openide.util.Exceptions;
import org.openide.util.ImageUtilities;
import org.openide.util.NbBundle;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * Register Schema files to runtime tab. See
 * http://blogs.sun.com/vivek/entry/schema_based_code_completion_tutorial
 */
public class XSDCatalog implements CatalogReader, CatalogDescriptor,
        EntityResolver {

    private static final String UI_PUBLIC_ID =
            "urn:import:com.google.gwt.user.client.ui"; // NOI18N
    private static final String UI_URL =
            "nbres:/org/netbeans/modules/gwt4nb/resources/" + // NOI18N
            "com.google.gwt.user.client.ui.xsd"; // NOI18N
    private static final String UI_BINDER_PUBLIC_ID =
            "urn:ui:com.google.gwt.uibinder"; // NOI18N
    private static final String UI_BINDER_URL =
            "nbres:/org/netbeans/modules/gwt4nb/resources/" + // NOI18N
            "UiBinder.xsd"; // NOI18N
    private static final String XHTML_SYSTEM_ID =
            "http://dl.google.com/gwt/DTD/xhtml.ent"; // NOI18N
    private static final String XHTML_URL =
            "nbres:/org/netbeans/modules/gwt4nb/resources/xhtml.ent"; // NOI18N
    private static final String UI_IMPORT_PACKAGE =
            "urn:import:"; // NOI18N

    @Override
    public Iterator<String> getPublicIDs() {
        List<String> list = new ArrayList<String>();
        list.add(UI_PUBLIC_ID);
        list.add(UI_BINDER_PUBLIC_ID);

        return list.listIterator();
    }

    @Override
    public void refresh() {
    }

    @Override
    public String getSystemID(String publicId) {
        if (publicId.equals(UI_PUBLIC_ID)) {
            return UI_URL;
        } else if (publicId.equals(UI_BINDER_PUBLIC_ID)) {
            return UI_BINDER_URL;
        } else {
            return null;
        }
    }

    @Override
    public String resolveURI(String name) {
        return null;
    }

    @Override
    public String resolvePublic(String string) {
        return null;
    }

    @Override
    public void addCatalogListener(CatalogListener catalogListener) {
    }

    @Override
    public void removeCatalogListener(CatalogListener catalogListener) {
    }

    @Override
    public Image getIcon(int i) {
        return ImageUtilities.loadImage(
                "org/netbeans/modules/gwt4nb/gwticon.png"); // NOI18N
    }

    @Override
    public String getDisplayName() {
        return NbBundle.getMessage(XSDCatalog.class, "GWTCat"); // NOI18N
    }

    @Override
    public String getShortDescription() {
        return NbBundle.getMessage(XSDCatalog.class, "XMLCat"); // NOI18N
    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener pcl) {
    }

    @Override
    public void removePropertyChangeListener(PropertyChangeListener pcl) {
    }

    @Override
    public InputSource resolveEntity(String publicId, String systemId)
            throws SAXException, IOException {
        if (UI_PUBLIC_ID.equals(publicId)) {
            return new org.xml.sax.InputSource(UI_URL);
        }
        if (systemId != null && systemId.contains(UI_PUBLIC_ID)) {
            return new org.xml.sax.InputSource(UI_URL);
        }
        if (systemId != null && systemId.contains(UI_BINDER_PUBLIC_ID)) {
            return new org.xml.sax.InputSource(UI_BINDER_URL);
        }
        if (XHTML_SYSTEM_ID.equals(systemId)) {
            return new org.xml.sax.InputSource(XHTML_URL);
        }

        if (systemId != null && systemId.contains(UI_IMPORT_PACKAGE)) {
            File f = createXSD(systemId);
            if (f != null) {
                return new org.xml.sax.InputSource("file:" + f.getPath());
            }
        }

        return null;
    }
    
    /**
     * Create a XSD schema from package. The file will be auto-removed on exit.
     */
    private File createXSD(String packagePath) {
        packagePath = packagePath.split(":")[2].split("\\?")[0];
        
        String originalPackagePath = packagePath;
        packagePath = packagePath.replaceAll("\\.", "\\/");
        for (Project p : OpenProjects.getDefault().getOpenProjects()) {
            for (SourceGroup sg : ProjectUtils.getSources(p).getSourceGroups(JavaProjectConstants.SOURCES_TYPE_JAVA)) {
                String path = sg.getRootFolder().getPath() + "/"
                        + packagePath;
                File folder = new File(path);
                if (folder.exists() && folder.isDirectory()) {
                    try {
                        File xsd = File.createTempFile("package", "xsd");
                        Logger.getLogger(XSDCatalog.class.getName()).log(Level.INFO, 
                                "Schema based code-completion created: {0}", xsd.getPath());
                        xsd.deleteOnExit();
                        PrintWriter pw = new PrintWriter(xsd);

                        pw.println("<?xml version=\"1.0\"?>");
                        pw.println("<xs:schema");
                        pw.println("    xmlns:xs=\"http://www.w3.org/2001/XMLSchema\"");
                        pw.println("    targetNamespace=\"urn:import:" + originalPackagePath + "\"");
                        pw.println("    xmlns=\"urn:import:" + originalPackagePath + "\"");
                        pw.println("    elementFormDefault=\"qualified\">");
                        pw.println("    <xs:complexType\n"
                                + "        name=\"ElementContentType\">\n"
                                + "\n"
                                + "        <xs:choice\n"
                                + "            minOccurs=\"0\"\n"
                                + "            maxOccurs=\"unbounded\">\n"
                                + "            <xs:any\n"
                                + "                processContents=\"lax\" />\n"
                                + "        </xs:choice>\n"
                                + "\n"
                                + "        <xs:anyAttribute\n"
                                + "            processContents=\"lax\" />\n"
                                + "    </xs:complexType>\n"
                                + "");

                        for (File f : folder.listFiles()) {
                            if (f.isFile() && f.getName().toLowerCase().endsWith(".java")) {
                                pw.println("    <xs:element");
                                pw.println("        name=\"" + f.getName().split("\\.")[0] + "\"");
                                pw.println("        type=\"ElementContentType\" />");
                            }
                        }
                        pw.println("</xs:schema>");
                        pw.close();

                        return xsd;
                    } catch (IOException ex) {
                        Exceptions.printStackTrace(ex);
                    }
                }
            }
        }
        return null;
    }
}
