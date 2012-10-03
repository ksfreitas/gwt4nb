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
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.netbeans.api.java.project.JavaProjectConstants;
import org.netbeans.api.project.Project;
import org.netbeans.api.project.ProjectUtils;
import org.netbeans.api.project.SourceGroup;
import org.netbeans.api.project.ui.OpenProjects;
import org.openide.util.Exceptions;

/**
 *
 * @author Cássio de Freitas e Silva
 */
public class ProjectPackageCatalog {
    public static File createXSD(String packagePath) {
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
                        Logger.getLogger(ProjectPackageCatalog.class.getName()).log(Level.INFO, 
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
