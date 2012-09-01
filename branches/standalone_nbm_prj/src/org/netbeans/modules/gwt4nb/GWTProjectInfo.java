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

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;
import org.openide.ErrorManager;
import org.netbeans.api.project.Project;
import org.openide.filesystems.FileObject;

/**
 *
 * @author Tomasz Slota
 */
public class GWTProjectInfo {
    public static final String BUILD_GWT   = "build-gwt.xml"; //NOI18N
    public static final String PRJ_DIR =  "nbproject"; //NOI18N
    public static final String GWT_PROPERTIES = "gwt.properties"; //NOI18N
    public static final String RESOURCE_BASE = "org/netbeans/modules/gwt4nb/resources/"; //NOI18N
    public static final String WELCOME_FILE = "welcomeGWT.html"; //NOI18N
    
    public static boolean isGWTProject(Project project){
        if(project==null){
            return false;
        }
        FileObject buildGWT = project.getProjectDirectory().getFileObject(PRJ_DIR
                + "/" + BUILD_GWT); //NOI18N
        
        return buildGWT != null;
    }
    
    public static String readGWTProperty(Project project, String propName){
        String val = null;
        
        if (isGWTProject(project)){
            Properties props = new Properties();
            FileObject propsFile = project.getProjectDirectory().getFileObject(PRJ_DIR).getFileObject(GWT_PROPERTIES);
            try{
                InputStream is = propsFile.getInputStream();
                props.load(is);
                val = props.getProperty(propName);
                is.close();
            } catch (IOException e){
                e.printStackTrace();
            }
        }
        
        return val;
    }
    
    public static void writeGWTProperty(Project project, String propName, String propValue) {
        if (isGWTProject(project)) {
            Properties props = new Properties();
            FileObject propsFile = project.getProjectDirectory().getFileObject(PRJ_DIR).getFileObject(GWT_PROPERTIES);
            try {
                InputStream is = propsFile.getInputStream();
                props.load(is);
                is.close();
                props.setProperty(propName, propValue);
                OutputStream os = propsFile.getOutputStream();
                props.store(os, null);
                os.close();
            } catch (IOException e) {
                ErrorManager.getDefault().log(e.getMessage());
                e.printStackTrace();
            }
        }
    }
    
    public static String getGWTModule(Project project){
        return readGWTProperty(project, "gwt.module"); //NOI18N
    }
    
    public static String getGWTOutputDir(Project project){
        String val = readGWTProperty(project, "gwt.output.dir"); //NOI18N
        return val == null ? ('/' + getGWTModule(project)) : val;
    }
    
    public static String getModulePackage(String module){
        int end = module.lastIndexOf(".");
        return module.substring(0, end);
    }
    
    public static String getClientPackage(Project project){
        String clientPckg = null;
        String module = getGWTModule(project);
        
        if (module != null){
            clientPckg = getModulePackage(module) + ".client"; //NOI18N
        }
        
        return clientPckg;
    }
    
    public static String getServerPackage(Project project){
        String clientPckg = null;
        String module = getGWTModule(project);
        
        if (module != null){
            clientPckg = getModulePackage(module) + ".server"; //NOI18N
        }
        
        return clientPckg;
    }
    
    public static String getPublicPackage(Project project){
        String clientPckg = null;
        String module = getGWTModule(project);
        
        if (module != null){
            clientPckg = getModulePackage(module) + ".public"; //NOI18N
        }
        
        return clientPckg;
    }
    
    public static FileObject getSourcesDir(Project project){
        // TODO: access project.properties
        return project.getProjectDirectory().getFileObject("src/java"); //NOI18N
    }
}
