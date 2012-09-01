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

import com.sun.source.tree.ClassTree;
import com.sun.source.tree.CompilationUnitTree;
import com.sun.source.tree.Tree;
import java.io.IOException;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.sql.rowset.serial.SerialBlob;
import org.netbeans.api.java.source.CancellableTask;
import org.netbeans.api.java.source.ClasspathInfo;
import org.netbeans.api.java.source.CompilationController;
import org.netbeans.api.java.source.CompilationInfo;
import org.netbeans.api.java.source.ElementHandle;
import org.netbeans.api.java.source.JavaSource;
import org.netbeans.api.java.source.JavaSource.Phase;
import org.netbeans.api.java.source.SourceUtils;
import org.netbeans.api.java.source.TreeMaker;
import org.netbeans.api.java.source.WorkingCopy;
import org.netbeans.api.project.FileOwnerQuery;
import org.netbeans.api.project.Project;
import org.netbeans.modules.gwt4nb.services.ServiceClassSet;
import org.netbeans.modules.gwt4nb.services.ServiceClassSetUtils;
import org.netbeans.modules.gwt4nb.services.refactoring.ServiceRenameRefactoring;
import org.netbeans.modules.j2ee.dd.api.web.DDProvider;
import org.netbeans.modules.j2ee.dd.api.web.Servlet;
import org.netbeans.modules.j2ee.dd.api.web.ServletMapping;
import org.netbeans.modules.j2ee.dd.api.web.WebApp;
import org.netbeans.modules.refactoring.spi.RefactoringElementsBag;
import org.netbeans.modules.refactoring.spi.SimpleRefactoringElementImplementation;
import org.netbeans.modules.web.api.webmodule.WebModule;
import org.openide.filesystems.FileLock;
import org.openide.filesystems.FileObject;
import org.openide.text.PositionBounds;
import org.openide.util.Exceptions;
import org.openide.util.Lookup;
import org.openide.util.lookup.Lookups;

/**
 *
 * @author prem
 */
class ServiceRenameElementImpl extends SimpleRefactoringElementImplementation {
    
    private ServiceClassSet serviceClassSet;
    private RefactoringElementsBag session;
    private ServiceRenameRefactoring refactoring;
    private final String USAGE_EXAMPLE = "UsageExample"; // NOI18N
    
    
    public ServiceRenameElementImpl(ServiceRenameRefactoring refactoring, RefactoringElementsBag session) {
        this.refactoring = refactoring;
        this.session = session;
        this.serviceClassSet = refactoring.getMembers();
        
    }
    
    public String getText() {
        return "Rename Service " + serviceClassSet.getService().getQualifiedName().toString();
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
        
        ClasspathInfo classpathInfo = ClasspathInfo.create(fo);
        
        FileObject servFo = SourceUtils.getFile(ElementHandle.create(serviceClassSet.getService()), classpathInfo);
        FileObject servAsyncFo = SourceUtils.getFile(ElementHandle.create(serviceClassSet.getServiceAsync()), classpathInfo);
        FileObject servImplFo = SourceUtils.getFile(ElementHandle.create(serviceClassSet.getServiceImpl()), classpathInfo);
        FileObject servUsgExFo = SourceUtils.getFile(ElementHandle.create(serviceClassSet.getServiceUsageExample()), classpathInfo);
        
        //rename the service classes
        new ServiceRenameTask(element,servFo,serviceClassSet).run();
        new ServiceRenameTask(element,servAsyncFo,serviceClassSet).run();
        new ServiceRenameTask(element,servImplFo,serviceClassSet).run();
        new ServiceRenameTask(element,servUsgExFo,serviceClassSet).run();
        
        //rename the entry in web.xml
        try{
            String servletName = serviceClassSet.getService().getSimpleName().toString();
            Project project = FileOwnerQuery.getOwner(fo);
            WebModule webModule = WebModule.getWebModule(project.getProjectDirectory());
            WebApp webApp = DDProvider.getDefault().getDDRoot(webModule.getDeploymentDescriptor());
            
            if (webApp != null){
               Servlet[] servlets = webApp.getServlet();
                for(Servlet s : servlets){
                    if(s.getServletName().equals(servletName)){
                        webApp.removeServlet(s);
                        s.setServletName(refactoring.getNewServiceName());
                        String sClass = s.getServletClass();
                        sClass = sClass.substring(0, sClass.lastIndexOf(".")+1)+refactoring.getNewServiceName()+ServiceClassSetUtils.IMPL_SUFFIX;
                        s.setServletClass(sClass);
                        webApp.addServlet(s);
                    }
                }
                ServletMapping[] smappings = webApp.getServletMapping();
                for(ServletMapping sm : smappings){
                    if(sm.getServletName().equals(servletName)){
                        webApp.removeServletMapping(sm);
                        sm.setServletName(refactoring.getNewServiceName());
                        String smUrl = sm.getUrlPattern();
                        smUrl = smUrl.substring(0,smUrl.lastIndexOf("/")+1)+refactoring.getNewServiceName();
                        sm.setUrlPattern(smUrl);
                        webApp.addServletMapping(sm);
                    }
                }
                webApp.write(webModule.getDeploymentDescriptor());
               }
        } catch (Exception e){
            e.printStackTrace();
        }
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
    
    private class ServiceRenameTask implements Runnable, CancellableTask<WorkingCopy>  {
        private FileObject fo;
        private TypeElement clazz;
        private ServiceClassSet serviceClassSet;
        
        public ServiceRenameTask(TypeElement clazz, FileObject fo,ServiceClassSet serviceClassSet) {
            this.clazz = clazz;
            this.fo = fo;
            this.serviceClassSet = serviceClassSet;
        }
        
        public void cancel() {
        }
        
        public void run(WorkingCopy workingCopy) throws Exception {
            workingCopy.toPhase(Phase.RESOLVED);
            CompilationUnitTree cut = workingCopy.getCompilationUnit();
            TreeMaker make = workingCopy.getTreeMaker();
            //ServiceClassSet serviceClassSet = ServiceClassSetUtils.resolveServiceClassSet(workingCopy,clazz);
            
            //Rename the service, serviceAsync and serviceImpl classes
            for (Tree typeDecl : cut.getTypeDecls()) {
                if (true) {
                    ClassTree clazz = (ClassTree) typeDecl;
                    ClassTree modifiedClazz = null;
                    if(clazz.getSimpleName().toString().equals(serviceClassSet.getService().getSimpleName().toString())){
                        modifiedClazz = make.setLabel(clazz, refactoring.getNewServiceName());
                        FileObject servFo = SourceUtils.getFile(ElementHandle.create(serviceClassSet.getService()),workingCopy.getClasspathInfo());
                        if(servFo!=null){
                            FileLock lock = servFo.lock();
                            servFo.rename(lock,refactoring.getNewServiceName(),"java");
                            lock.releaseLock();
                        }
                    }else if(clazz.getSimpleName().toString().equals(serviceClassSet.getServiceAsync().getSimpleName().toString())){
                        modifiedClazz = make.setLabel(clazz, refactoring.getNewServiceName()+ServiceClassSetUtils.ASYNC_SUFFIX);
                        FileObject servAsyncFo = SourceUtils.getFile(ElementHandle.create(serviceClassSet.getServiceAsync()),workingCopy.getClasspathInfo());
                        if(servAsyncFo!=null){
                            FileLock lock = servAsyncFo.lock();
                            servAsyncFo.rename(lock,refactoring.getNewServiceName()+ServiceClassSetUtils.ASYNC_SUFFIX,"java");
                            lock.releaseLock();
                        }
                    }else if(clazz.getSimpleName().toString().equals(serviceClassSet.getServiceImpl().getSimpleName().toString())){
                        modifiedClazz = make.setLabel(clazz, refactoring.getNewServiceName()+ServiceClassSetUtils.IMPL_SUFFIX);
                        FileObject servImplFo = SourceUtils.getFile(ElementHandle.create(serviceClassSet.getServiceImpl()),workingCopy.getClasspathInfo());
                        if(servImplFo!=null){
                            FileLock lock = servImplFo.lock();
                            servImplFo.rename(lock,refactoring.getNewServiceName()+ServiceClassSetUtils.IMPL_SUFFIX,"java");
                            lock.releaseLock();
                        }
                    }else if(clazz.getSimpleName().toString().equals(serviceClassSet.getServiceUsageExample().getSimpleName().toString())){
                        modifiedClazz = make.setLabel(clazz, refactoring.getNewServiceName()+ServiceClassSetUtils.USAGE_EXAMPLE);
                        FileObject servUsgExFo = SourceUtils.getFile(ElementHandle.create(serviceClassSet.getServiceUsageExample()),workingCopy.getClasspathInfo());
                        if(servUsgExFo!=null){
                            FileLock lock = servUsgExFo.lock();
                            servUsgExFo.rename(lock,refactoring.getNewServiceName()+ServiceClassSetUtils.USAGE_EXAMPLE,"java");
                            lock.releaseLock();
                        }
                    }
                    if(modifiedClazz != null){
                        workingCopy.rewrite(clazz, modifiedClazz);
                    }
                }
            }
        }
        
        public final void run() {
            JavaSource source = JavaSource.forFileObject(fo);
            assert source != null;
            try {
                source.runModificationTask(this).commit();
                
            } catch (IllegalArgumentException ex) {
                ex.printStackTrace();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            
        }
    }
    
}

