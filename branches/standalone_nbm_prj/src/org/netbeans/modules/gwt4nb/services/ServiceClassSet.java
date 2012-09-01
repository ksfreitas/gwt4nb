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

import javax.lang.model.element.TypeElement;
import org.netbeans.api.java.source.ClasspathInfo;


/**
 *
 * @author Tomasz.Slota@Sun.COM
 */
public class ServiceClassSet {
    private TypeElement service;
    private TypeElement serviceImpl;
    private TypeElement serviceAsync;
    private TypeElement serviceUsageExample;
    private ClasspathInfo classpathInfo;

    public TypeElement getService() {
        return service;
    }

    public void setService(TypeElement service) {
        this.service = service;
    }

    public TypeElement getServiceImpl() {
        return serviceImpl;
    }

    public void setServiceImpl(TypeElement serviceImpl) {
        this.serviceImpl = serviceImpl;
    }

    public TypeElement getServiceAsync() {
        return serviceAsync;
    }

    public void setServiceAsync(TypeElement serviceAsync) {
        this.serviceAsync = serviceAsync;
    }
    
    public TypeElement getServiceUsageExample() {
        return serviceUsageExample;
    }

    public void setServiceUsageExample(TypeElement serviceUsageExample) {
        this.serviceUsageExample = serviceUsageExample;
    }

    public ClasspathInfo getClasspathInfo() {
        return classpathInfo;
    }

    public void setClasspathInfo(ClasspathInfo classpathInfo) {
        this.classpathInfo = classpathInfo;
    }
}
