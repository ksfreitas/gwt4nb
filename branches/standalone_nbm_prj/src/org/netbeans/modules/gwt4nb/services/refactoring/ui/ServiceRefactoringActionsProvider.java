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

package org.netbeans.modules.gwt4nb.services.refactoring.ui;

import com.sun.source.tree.CompilationUnitTree;
import com.sun.source.util.TreePath;
import java.io.IOException;
import java.util.Collection;
import javax.swing.JOptionPane;
import org.netbeans.api.java.source.CancellableTask;
import org.netbeans.api.java.source.CompilationController;
import org.netbeans.api.java.source.CompilationInfo;
import org.netbeans.api.java.source.JavaSource;
import org.netbeans.api.java.source.JavaSource.Phase;
import org.netbeans.api.java.source.TreePathHandle;
import org.netbeans.api.project.FileOwnerQuery;
import org.netbeans.api.project.Project;
import org.netbeans.modules.refactoring.spi.ui.RefactoringUI;
import org.openide.cookies.EditorCookie;
import org.openide.loaders.DataObject;
import org.openide.nodes.Node;
import org.openide.util.Exceptions;
import org.openide.util.Lookup;
import org.netbeans.modules.refactoring.spi.ui.UI;
import org.netbeans.modules.j2ee.dd.api.web.DDProvider;
import org.netbeans.modules.j2ee.dd.api.web.WebApp;
import org.netbeans.modules.web.api.webmodule.WebModule;
import org.openide.filesystems.FileObject;

/**
 *
 * @author Jan Becicka
 */
public class ServiceRefactoringActionsProvider {
    
    
    public static void doDelete(final Lookup lookup) {
        Runnable task;
        EditorCookie ec = lookup.lookup(EditorCookie.class);
        task = new NodeToElementTask(lookup.lookupAll(Node.class)) {
            
            @Override
            protected RefactoringUI createRefactoringUI(TreePathHandle selectedElement, CompilationInfo info) {
                return new ServiceDeleteRefactoringUI(new TreePathHandle[]{selectedElement}, info);
            }
        };
        task.run();
        
    }
    
    
    public static boolean canDelete(Lookup lookup) {
        try {
            Collection<? extends Node> nodes = lookup.lookupAll(Node.class);
            if (nodes.size() != 1) {
                return false;
            }
            Node n = nodes.iterator().next();
            DataObject dob = n.getCookie(DataObject.class);
            if (dob == null) {
                return false;
            }
            String fileName = dob.getPrimaryFile().getName();
            String serviceName = fileName;
            Project p = FileOwnerQuery.getOwner(dob.getPrimaryFile());
            
            //Get the service name by trimming Async and Impl
            if (fileName.endsWith("Async")) {
                serviceName = fileName.substring(0, fileName.length() - 5);
            }
            if (fileName.endsWith("Impl")) {
                serviceName = fileName.substring(0, fileName.length() - 4);
            }
            
            //check if all three service class files are present
            FileObject svcSrcDir = dob.getPrimaryFile().getParent().getParent();
            
            if (svcSrcDir == null){
                return false;
            }
            
            if(svcSrcDir.getFileObject("client").getFileObject(serviceName,"java") == null){
                return false;
            }
            if(svcSrcDir.getFileObject("client").getFileObject(serviceName+"Async","java") == null){
                return false;
            }
            if(svcSrcDir.getFileObject("server").getFileObject(serviceName+"Impl","java")==null){
                return false;
            }
            
            //search for a servlet entry in web.xml for this service name
            WebModule webModule = WebModule.getWebModule(p.getProjectDirectory());
            WebApp webApp = DDProvider.getDefault().getDDRoot(webModule.getDeploymentDescriptor());
            if (webApp != null) {
                org.netbeans.modules.j2ee.dd.api.web.Servlet[] servlets = webApp.getServlet();
                for (org.netbeans.modules.j2ee.dd.api.web.Servlet s : servlets) {
                    if (s.getServletName().equals(serviceName)) {
                        return true;
                    }
                }
            }
            
        } catch (IOException ex) {
            Exceptions.printStackTrace(ex);
        }
        return false;
    }
    public static void doRename(final Lookup lookup) {
        Runnable task;
        EditorCookie ec = lookup.lookup(EditorCookie.class);
        task = new NodeToElementTask(lookup.lookupAll(Node.class)) {
            
            @Override
            protected RefactoringUI createRefactoringUI(TreePathHandle selectedElement, CompilationInfo info) {
                return new ServiceRenameRefactoringUI(new TreePathHandle[]{selectedElement}, info);
            }
        };
        task.run();
        
    }
    
    
    public static boolean canRename(Lookup lookup) {
        try {
            Collection<? extends Node> nodes = lookup.lookupAll(Node.class);
            if (nodes.size() != 1) {
                return false;
            }
            Node n = nodes.iterator().next();
            DataObject dob = n.getCookie(DataObject.class);
            if (dob == null) {
                return false;
            }
            String fileName = dob.getPrimaryFile().getName();
            String serviceName = fileName;
            Project p = FileOwnerQuery.getOwner(dob.getPrimaryFile());
            
            //Get the service name by trimming Async anc Impl
            if (fileName.endsWith("Async")) {
                serviceName = fileName.substring(0, fileName.length() - 5);
            }
            if (fileName.endsWith("Impl")) {
                serviceName = fileName.substring(0, fileName.length() - 4);
            }
            
            //check if all three service class files are present
            if(dob.getPrimaryFile().getParent().getParent().getFileObject("client").getFileObject(serviceName,"java") == null){
                return false;
            }
            if(dob.getPrimaryFile().getParent().getParent().getFileObject("client").getFileObject(serviceName+"Async","java") == null){
                return false;
            }
            if(dob.getPrimaryFile().getParent().getParent().getFileObject("server").getFileObject(serviceName+"Impl","java")==null){
                return false;
            }
            
            //search for a servlet entry in web.xml for this service name
            WebModule webModule = WebModule.getWebModule(p.getProjectDirectory());
            WebApp webApp = DDProvider.getDefault().getDDRoot(webModule.getDeploymentDescriptor());
            if (webApp != null) {
                org.netbeans.modules.j2ee.dd.api.web.Servlet[] servlets = webApp.getServlet();
                for (org.netbeans.modules.j2ee.dd.api.web.Servlet s : servlets) {
                    if (s.getServletName().equals(serviceName)) {
                        return true;
                    }
                }
            }
            
        } catch (IOException ex) {
            Exceptions.printStackTrace(ex);
        }
        return false;
    }
    public static abstract class NodeToElementTask implements Runnable, CancellableTask<CompilationController>  {
        private Node node;
        private RefactoringUI ui;
        
        public NodeToElementTask(Collection<? extends Node> nodes) {
            assert nodes.size() == 1;
            this.node = nodes.iterator().next();
        }
        
        public void cancel() {
        }
        
        public void run(CompilationController info) throws Exception {
            info.toPhase(Phase.ELEMENTS_RESOLVED);
            CompilationUnitTree unit = info.getCompilationUnit();
            if (unit.getTypeDecls().isEmpty()) {
                ui = createRefactoringUI(null, info);
            } else {
                TreePathHandle representedObject = TreePathHandle.create(TreePath.getPath(unit, unit.getTypeDecls().get(0)),info);
                ui = createRefactoringUI(representedObject, info);
            }
        }
        
        public final void run() {
            DataObject o = node.getCookie(DataObject.class);
            JavaSource source = JavaSource.forFileObject(o.getPrimaryFile());
            assert source != null;
            try {
                source.runUserActionTask(this, false);
            } catch (IllegalArgumentException ex) {
                ex.printStackTrace();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            if (ui!=null) {
                UI.openRefactoringUI(ui);
            } else {
                JOptionPane.showMessageDialog(null,"ERR_NOTYPEDECLS");
            }
        }
        protected abstract RefactoringUI createRefactoringUI(TreePathHandle selectedElement, CompilationInfo info);
    }
    
    
}
