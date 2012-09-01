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
package org.netbeans.modules.gwt4nb.hints;

import java.util.List;
import java.util.ArrayList;
import java.util.Collection;

import javax.lang.model.element.TypeElement;
import javax.lang.model.element.ExecutableElement;

import javax.lang.model.util.ElementFilter;

import org.netbeans.api.java.source.SourceUtils;
import org.netbeans.api.java.source.ElementHandle;
import org.netbeans.api.java.source.ClasspathInfo;
import org.netbeans.api.java.source.CompilationInfo;


import org.netbeans.modules.gwt4nb.services.ServiceClassSet;

import org.netbeans.spi.editor.hints.ErrorDescription;

import org.openide.filesystems.FileObject;

import org.openide.util.NbBundle;

/**
 * Generates a hint if an GWT RPC interface is out-of-sync with it's asynchron
 * version.
 */
public class ServiceAsyncConsistentWithService implements
        GWTHintsProvider.GWTProblemFinder {

    @SuppressWarnings("rawtypes")
    public Collection<ErrorDescription> check(CompilationInfo info,
            TypeElement javaClass, ServiceClassSet serviceClassSet) {
        if (serviceClassSet.getService() == null || serviceClassSet.
                getServiceAsync() == null) {
            return null;
        }

        Collection<ErrorDescription> problemsFound =
                new ArrayList<ErrorDescription>();

        ClasspathInfo classpathInfo = ClasspathInfo.create(info.getFileObject());
        FileObject asyncfo = SourceUtils.getFile(ElementHandle.create(
                serviceClassSet.getServiceAsync()), classpathInfo);
        FileObject syncfo = SourceUtils.getFile(ElementHandle.create(
                serviceClassSet.getService()), classpathInfo);

        boolean errorFound = false;

        ArrayList<ElementHandle> methods = new ArrayList<ElementHandle>();
        for (ExecutableElement serviceMethod : ElementFilter.methodsIn(
                serviceClassSet.getService().getEnclosedElements())) {

            String methodName = serviceMethod.getSimpleName().toString();

            ExecutableElement methodsWithMatchingName[] =
                    JavaModelUtils.getMethod(serviceClassSet.getServiceAsync(),
                    methodName);

            ElementHandle serviceMethodHandle = ElementHandle.create(
                    serviceMethod);

            if (methodsWithMatchingName.length == 0) {
                // no matching methods found, set error
                errorFound = true;
                methods.add(serviceMethodHandle);
            } else {
                //check method signature as well
                if (!JavaModelUtils.isMethodSignatureEqual(serviceMethod,
                        methodsWithMatchingName)) {
                    errorFound = true;
                    methods.add(serviceMethodHandle);
                }
            }
        }

        List<ElementHandle> unmatchedMethods = JavaModelUtils.
                getUnmatchedMethods(
                serviceClassSet.getServiceAsync(), serviceClassSet.getService());

        if (!unmatchedMethods.isEmpty()) {
            errorFound = true;
        }

        if (errorFound) {
            String errorMsg = NbBundle.getMessage(
                    ServiceAsyncConsistentWithService.class,
                    "ERROR_SYNC"); // NOI18N

            ErrorDescription error = HintsUtils.createProblem(javaClass,
                    info, errorMsg, new FixAsyncInterfaceImpl(
                    asyncfo,
                    syncfo,
                    methods,
                    unmatchedMethods));
            problemsFound.add(error);

        }

        return problemsFound;
    }
}
