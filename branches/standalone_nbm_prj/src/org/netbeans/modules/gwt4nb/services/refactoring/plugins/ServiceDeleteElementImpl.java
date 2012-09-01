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
 * Portions Copyrighted 2007 Sun Microsystems, Inc.
 */
package org.netbeans.modules.gwt4nb.services.refactoring.plugins;

import java.io.IOException;
import javax.lang.model.element.TypeElement;
import org.netbeans.api.java.source.CancellableTask;
import org.netbeans.api.java.source.CompilationController;
import org.netbeans.api.java.source.ElementHandle;
import org.netbeans.api.java.source.JavaSource;
import org.netbeans.api.java.source.JavaSource.Phase;
import org.netbeans.api.java.source.SourceUtils;
import org.netbeans.api.project.FileOwnerQuery;
import org.netbeans.api.project.Project;
import org.netbeans.modules.gwt4nb.services.ServiceClassSet;
import org.netbeans.modules.gwt4nb.services.ServiceClassSetUtils;
import org.netbeans.modules.gwt4nb.services.refactoring.ServiceDeleteRefactoring;
import org.netbeans.modules.j2ee.dd.api.web.DDProvider;
import org.netbeans.modules.j2ee.dd.api.web.Servlet;
import org.netbeans.modules.j2ee.dd.api.web.ServletMapping;
import org.netbeans.modules.j2ee.dd.api.web.WebApp;
import org.netbeans.modules.refactoring.spi.RefactoringElementsBag;
import org.netbeans.modules.refactoring.spi.SimpleRefactoringElementImplementation;
import org.netbeans.modules.web.api.webmodule.WebModule;
import org.openide.filesystems.FileObject;
import org.openide.text.PositionBounds;
import org.openide.util.Lookup;
import org.openide.util.lookup.Lookups;

/**
 *
 * @author prem
 */
class ServiceDeleteElementImpl extends SimpleRefactoringElementImplementation {
        
        private ServiceClassSet serviceClassSet;
        private RefactoringElementsBag session;
        private ServiceDeleteRefactoring refactoring;
        private final String USAGE_EXAMPLE = "UsageExample"; // NOI18N
    
        
        public ServiceDeleteElementImpl(ServiceDeleteRefactoring refactoring, RefactoringElementsBag session) {
            this.refactoring = refactoring;
            this.session = session;
            this.serviceClassSet = refactoring.getMembers();
            
        }
        
        public String getText() {
            return "Delete Service " + serviceClassSet.getService().getQualifiedName().toString();
        }
        
        public String getDisplayText() {
            return getText();
        }
        
        public void performChange() {
            TypeElement element = serviceClassSet.getService();
            FileObject fo = SourceUtils.getFile(ElementHandle.create(element),serviceClassSet.getClasspathInfo());
            if(fo == null){
                element = serviceClassSet.getServiceAsync();
                fo = SourceUtils.getFile(ElementHandle.create(element),serviceClassSet.getClasspathInfo());
            }
            if(fo == null){
                element = serviceClassSet.getServiceImpl();
                fo = SourceUtils.getFile(ElementHandle.create(element),serviceClassSet.getClasspathInfo());
            }
            
            new ServiceDeleteTask(element,fo).run();
        }
        
        public void undoChange() {
            
        }
        
        public Lookup getLookup() {
            return Lookups.singleton(SourceUtils.getFile(ElementHandle.create(serviceClassSet.getService()),serviceClassSet.getClasspathInfo()).getParent());
        }
        
        public FileObject getParentFile() {
            return SourceUtils.getFile(ElementHandle.create(serviceClassSet.getService()),serviceClassSet.getClasspathInfo()).getParent();
        }
        
        public PositionBounds getPosition() {
            return null;
        }
        
        
        public void setName(String name) {
        }
        
        private class ServiceDeleteTask implements Runnable, CancellableTask<CompilationController>  {
        private FileObject fo;
        private TypeElement clazz;
        
        public ServiceDeleteTask(TypeElement clazz, FileObject fo) {
            this.clazz = clazz;
            this.fo = fo;
        }
        
        public void cancel() {
        }
        
        public void run(CompilationController info) throws Exception {
            info.toPhase(Phase.ELEMENTS_RESOLVED);
            ServiceClassSet serviceClassSet = ServiceClassSetUtils.resolveServiceClassSet(info, clazz);
            //delete the service, serviceAsync and serviceImpl classes
            FileObject servFo = SourceUtils.getFile(ElementHandle.create(serviceClassSet.getService()),info.getClasspathInfo());
            if(servFo!=null){
                servFo.delete();
            }
            FileObject servAsyncFo = SourceUtils.getFile(ElementHandle.create(serviceClassSet.getServiceAsync()),info.getClasspathInfo());
            if(servAsyncFo!=null){
                servAsyncFo.delete();
            }
            FileObject servImplFo = SourceUtils.getFile(ElementHandle.create(serviceClassSet.getServiceImpl()),info.getClasspathInfo());
            if(servImplFo!=null){
                servImplFo.delete();
            }
            
            //delete the usage example if present
            String usageExampleName = serviceClassSet.getService().getQualifiedName().toString() + USAGE_EXAMPLE;
            TypeElement usageExample = info.getElements().getTypeElement(usageExampleName);
            if(usageExample != null){
                SourceUtils.getFile(usageExample,info.getClasspathInfo()).delete();
            }
            
            //delete the entry in web.xml
            String servletName = serviceClassSet.getService().getSimpleName().toString(); 
            Project project = FileOwnerQuery.getOwner(fo);
            WebModule webModule = WebModule.getWebModule(project.getProjectDirectory());
            WebApp webApp = DDProvider.getDefault().getDDRoot(webModule.getDeploymentDescriptor());
            
            if (webApp != null){
                try{
                   Servlet[] servlets = webApp.getServlet();
                    for(Servlet s : servlets){
                        if(s.getServletName().equals(servletName)){
                            webApp.removeServlet(s);
                        }
                    }
                    ServletMapping[] smappings = webApp.getServletMapping();
                    for(ServletMapping sm : smappings){
                        if(sm.getServletName().equals(servletName)){
                            webApp.removeServletMapping(sm);
                        }
                    }
                    webApp.write(webModule.getDeploymentDescriptor());
                    
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
        
        public final void run() {
            JavaSource source = JavaSource.forFileObject(fo);
            assert source != null;
            try {
                source.runUserActionTask(this, false);
            } catch (IllegalArgumentException ex) {
                ex.printStackTrace();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            
        }
    }
        
    }
    
