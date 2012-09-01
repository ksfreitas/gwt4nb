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

import java.util.ArrayList;
import java.util.List;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.ElementFilter;
import org.netbeans.api.java.source.ElementHandle;

/**
 *
 * @author Tomasz.Slota@Sun.COM
 */
public class JavaModelUtils {
    public static ExecutableElement[] getMethod(TypeElement clazz, String methodName){
        List<ExecutableElement> methods = new ArrayList<ExecutableElement>();
        
        for (ExecutableElement method : ElementFilter.methodsIn(clazz.getEnclosedElements())){
            if (method.getSimpleName().contentEquals(methodName)){
                methods.add(method);
            }
        }
        
        return methods.toArray(new ExecutableElement[methods.size()]);
    }
    /** Matches syncMethod's signature with the signatures of the array of asyncMethods passed
     *   @returns true if a match is found
     */
    public static boolean isMethodSignatureEqual(ExecutableElement syncMethod, ExecutableElement[] asyncMethods){
        boolean matchFound=false;
        for(ExecutableElement exeEle : asyncMethods){
            List<? extends VariableElement> syncMethodParamsList = syncMethod.getParameters();
            List<? extends VariableElement> asyncMethodParamsList = exeEle.getParameters();
            VariableElement[] asyncMethodParamArray =
                    asyncMethodParamsList.toArray(new VariableElement[asyncMethodParamsList.size()]);
            
            //if number of parameters don't match or AysnCallback parameter is missing, skip the method
            if(syncMethodParamsList.size() != (asyncMethodParamsList.size()-1)){
                continue;
            }
            VariableElement lastParamElement =
                    asyncMethodParamsList.get(asyncMethodParamsList.size()-1);
            String lastParamType = lastParamElement.asType().toString();
            
            if(!lastParamType.equals("com.google.gwt.user.client.rpc.AsyncCallback") ){
                continue;
            }else if(syncMethodParamsList.size()==0){
                //sync method has no params and async method has one param(AsyncCallback)
                matchFound=true;
                continue;
            }
           
            int syncParamIndex=0;
            for( VariableElement syncMethodParam : syncMethodParamsList){
                //if the class of the corresponding parameters don't match, we skip the method
                if(!syncMethodParam.asType().toString().equals
                        (asyncMethodParamArray[syncParamIndex].asType().toString())){
                    break;
                }
                if(syncParamIndex==syncMethodParamsList.size()-1){
                    matchFound=true;
                }
                syncParamIndex++;
            }
        }
        return matchFound;
    }
    
    /** Matches asyncMethod's signature with the signatures of the array of syncMethods passed
     *   @returns true if a match is found
     */
    public static boolean isMathcingServiceMethodFound(ExecutableElement asyncMethod, ExecutableElement[] syncMethods){
        boolean matchFound=false;
        List<? extends VariableElement> asyncMethodParamsList = asyncMethod.getParameters();
        VariableElement[] asyncMethodParamArray =
                asyncMethodParamsList.toArray(new VariableElement[asyncMethodParamsList.size()]);
        //If AysnCallback parameter is missing return false
        VariableElement lastParamElement =
                asyncMethodParamsList.get(asyncMethodParamsList.size()-1);
        String lastParamType = lastParamElement.asType().toString();
        if(!lastParamType.equals("com.google.gwt.user.client.rpc.AsyncCallback") ){
            return false;
        }
        for(ExecutableElement exeEle : syncMethods){
            List<? extends VariableElement> syncMethodParamsList = exeEle.getParameters();
            //if number of parameters don't match, skip the loop
            if(syncMethodParamsList.size() != (asyncMethodParamsList.size()-1)){
                continue;
            } else if (syncMethodParamsList.size()==0){
                matchFound=true;
            }
            int syncParamIndex=0;
            for( VariableElement syncMethodParam : syncMethodParamsList){
                //if the class of the corresponding parameters don't match, we skip the method
                if(!syncMethodParam.asType().toString().equals
                        (asyncMethodParamArray[syncParamIndex].asType().toString())){
                    break;
                }
                if(syncParamIndex==syncMethodParamsList.size()-1){
                    matchFound=true;
                }
                syncParamIndex++;
            }
        }
        return matchFound;
    }
    /** Method to compare the methods of clazzA with those of clazzB
     *   @returns list of element handles of methods in A but not in B
     */
    public static List<ElementHandle> getUnmatchedMethods(TypeElement clazzA,TypeElement clazzB){
        List<ElementHandle> unmatchedMethods = new ArrayList<ElementHandle>();
        for (ExecutableElement aMethod : ElementFilter.methodsIn(clazzA.getEnclosedElements())){
            String amethodName = aMethod.getSimpleName().toString();
            
            ExecutableElement methodsWithMatchingName[] =
                    JavaModelUtils.getMethod(clazzB,amethodName);
            if(methodsWithMatchingName.length==0){
                ElementHandle aMethodHandle = ElementHandle.create(aMethod);
                unmatchedMethods.add(aMethodHandle);
            } else if(!JavaModelUtils.isMathcingServiceMethodFound(aMethod, methodsWithMatchingName)){
                ElementHandle aMethodHandle = ElementHandle.create(aMethod);
                unmatchedMethods.add(aMethodHandle);
            }
        }
        return unmatchedMethods;
    }
    
    public static String extractClassNameFromType(TypeMirror type){
        if (type instanceof DeclaredType){
            Element elem = ((DeclaredType)type).asElement();
            
            if (elem.getKind() == ElementKind.CLASS
                    || elem.getKind() == ElementKind.INTERFACE){
                return ((TypeElement)elem).getQualifiedName().toString();
            }
        }
        
        return null;
    }
    
    public static String getShortClassName(String qualifiedClassName){
        return qualifiedClassName.substring(qualifiedClassName.lastIndexOf(".") + 1); //NOI18N
    }
}
