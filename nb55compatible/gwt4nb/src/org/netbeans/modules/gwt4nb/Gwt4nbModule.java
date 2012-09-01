/*
 * Gwt4nbModule.java
 *
 * Created on May 18, 2007, 12:32 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.netbeans.modules.gwt4nb;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import org.netbeans.api.project.Project;
import org.netbeans.api.project.ProjectInformation;
import org.netbeans.api.project.ProjectUtils;
import org.netbeans.api.project.ui.OpenProjects;
import org.openide.filesystems.FileObject;
import org.openide.modules.ModuleInstall;

/**
 * Module installation class for gwt4nb
 * @author prem
 */

public class Gwt4nbModule extends ModuleInstall {

    private static final String PATTERN_PRJ_NAME = "__PROJECT_NAME__"; 
    private PropertyChangeListener openProjectsListener;
    private List lastOpenProjects;

    public void restored()  {
        
        // update build-gwt.xml when a project is opened
        openProjectsListener = new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent evt) {
                List openProjects = new ArrayList(Arrays.asList(OpenProjects.getDefault().getOpenProjects()));
                
                // lastOpenProjects will contain the open projects before the event was triggered
                openProjects.removeAll(lastOpenProjects);
                updateGwtProject(openProjects);
                lastOpenProjects = new ArrayList(openProjects);
            }
        };
        OpenProjects op = OpenProjects.getDefault();
        lastOpenProjects = new ArrayList(Arrays.asList(op.getOpenProjects()));
        op.addPropertyChangeListener(openProjectsListener);
        
        //update build-gwt.xml of the open projects when the ide starts up
        updateGwtProject(lastOpenProjects);
        
    }
    
    // Iterate through open gwt projects and update the build-gwt.xml file
    private void updateGwtProject(List openProjects){
        for (Iterator it = openProjects.iterator(); it.hasNext();) {
                    Project p = (Project)it.next();
                    if(GWTProjectInfo.isGWTProject(p)){
                        assert p != null;
                        ProjectInformation projectInfo = ProjectUtils.getInformation(p);
                        String projectName = projectInfo.getName();
                        
                        FileObject nbprj = p.getProjectDirectory().getFileObject(GWTProjectInfo.PRJ_DIR);
                        try {
                            // COPY build-gwt.xml
                            FileObject buildGwt = nbprj.getFileObject(GWTProjectInfo.BUILD_GWT);
                            buildGwt.delete();
                            buildGwt = nbprj.createData(GWTProjectInfo.BUILD_GWT);
                            Util.copyResource(GWTProjectInfo.RESOURCE_BASE + GWTProjectInfo.BUILD_GWT, buildGwt, new String[] {PATTERN_PRJ_NAME}, new String[] {projectName});
                            FileObject foBuildXML = nbprj.getParent().getFileObject("build.xml");
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    }
                }
    }
    
   

}
    
    
    

    
