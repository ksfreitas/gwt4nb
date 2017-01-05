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
package org.netbeans.modules.gwt4nb.services;

import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;
import org.netbeans.api.java.source.CompilationInfo;
import org.netbeans.modules.gwt4nb.hints.JavaModelUtils;


/**
 *
 * @author Tomasz.Slota@Sun.COM
 */
public class ServiceClassSetUtils {
    public static final String ASYNC_SUFFIX = "Async"; // NOI18N
    public static final String IMPL_SUFFIX = "Impl"; // NOI18N
    public static final String USAGE_EXAMPLE = "UsageExample"; // NOI18N
    
    private static boolean isService(TypeElement iface){
        if (iface.getKind() != ElementKind.INTERFACE){
            return false;
        }
        
        for (TypeMirror extendedIface : iface.getInterfaces()){
            String ifaceName = JavaModelUtils.extractClassNameFromType(
                    extendedIface);
            
            if ("com.google.gwt.user.client.rpc.RemoteService".equals(ifaceName)){ // NOI18N
                return true;
            }
        }
        
        return false;
    }
    
    private static boolean isAsyncService(CompilationInfo info,
            TypeElement iface){
        return findService(info, iface) != null;
    }
    
    private static boolean isServiceImpl(TypeElement clazz){
        if (clazz.getKind() != ElementKind.CLASS){
            return false;
        }
        
        if(clazz.getSuperclass()!=null){
            String className = JavaModelUtils.extractClassNameFromType(
                    clazz.getSuperclass());
            
            if ("com.google.gwt.user.server.rpc.RemoteServiceServlet".equals(className)){ // NOI18N
                return true;
            }
        }
        
        return false;
    }
    
    private static TypeElement findService(CompilationInfo info,
            TypeElement svcAsync){
        String svcAsyncName = svcAsync.getQualifiedName().toString();
        
        if (!svcAsyncName.endsWith(ASYNC_SUFFIX)){
            return null;
        }
        
        String serviceClassName = svcAsyncName.substring(0,
                svcAsyncName.length() - ASYNC_SUFFIX.length());
        TypeElement serviceClass = info.getElements().getTypeElement(
                serviceClassName);

        if (serviceClass != null)
            return isService(serviceClass) ? serviceClass : null;
        else
            return null;
    }

    /**
     * Finds the Async-Service interface element.
     *
     * @param info compilation unit with the asynchronous interface
     * @param service Synchronous service interface
     * @return the found interface or null
     */
    private static TypeElement findServiceAsync(CompilationInfo info,
            TypeElement service){
        String svcAsyncName = service.getQualifiedName().toString() +
                ASYNC_SUFFIX;
        return info.getElements().getTypeElement(svcAsyncName);
    }
    
    // finds the Service Implementation for the given Service class
    private static TypeElement findServiceImpl(CompilationInfo info,
            TypeElement service){
        String svcImplName = service.getQualifiedName().toString() + IMPL_SUFFIX;
        svcImplName = svcImplName.replace(".client.", ".server."); // NOI18N
        return info.getElements().getTypeElement(svcImplName);
    }
    
    private static TypeElement findServiceUsageExample(CompilationInfo info,
            TypeElement service){
        String svcUsgExName = service.getQualifiedName().toString() + USAGE_EXAMPLE;
        return info.getElements().getTypeElement(svcUsgExName);
    }
    
    // finds the Service for the given Service Implementation
    private static TypeElement findServiceFromImpl(CompilationInfo info,
            TypeElement serviceImpl){
        String svcImplName = serviceImpl.getQualifiedName().toString();
        String serviceName = svcImplName.substring(0,
                svcImplName.length() - IMPL_SUFFIX.length());
        serviceName = serviceName.replace(".server.", ".client."); // NOI18N
        return info.getElements().getTypeElement(serviceName);
    }

    /**
     * Finds all the parts of a GWT RPC service definition for a class.
     *
     * @param info compilation unit for the class
     * @param clazz this class may be the asynchronous or synchronous GWT
     *     RPC interface or the implementation class or any other, GWT-unrelated
     *     class
     * @return the found information or null
     */
    public static ServiceClassSet resolveServiceClassSet(CompilationInfo info,
            TypeElement clazz){
        ServiceClassSet r = null;
        if (isService(clazz)){
            r = new ServiceClassSet();
            r.setService(clazz);
            r.setServiceAsync(findServiceAsync(info, clazz));
            r.setServiceImpl(findServiceImpl(info, clazz));
            r.setServiceUsageExample(findServiceUsageExample(
                    info, clazz));
            r.setClasspathInfo(info.getClasspathInfo());
        } else if (isAsyncService(info, clazz)){
            r = new ServiceClassSet();
            r.setService(findService(info, clazz));
            r.setServiceAsync(clazz);
            if (r.getService() != null) {
                r.setServiceImpl(findServiceImpl(info, r.getService()));
                r.setServiceUsageExample(findServiceUsageExample(
                        info, r.getService()));
            }
            r.setClasspathInfo(info.getClasspathInfo());
        } else if (isServiceImpl(clazz)){
            r = new ServiceClassSet();
            r.setServiceImpl(clazz);
            r.setService(findServiceFromImpl(info, clazz));
            if (r.getService() != null) {
                r.setServiceAsync(findServiceAsync(info, r.getService()));
                r.setServiceUsageExample(findServiceUsageExample(
                        info,r.getService()));
            }
            r.setClasspathInfo(info.getClasspathInfo());
        }
        
        return r;
    }
}
