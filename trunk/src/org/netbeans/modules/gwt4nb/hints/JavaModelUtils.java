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

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;

import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.type.DeclaredType;

import javax.lang.model.util.ElementFilter;

import org.netbeans.api.java.source.ElementHandle;

/**
 *
 * @author Tomasz.Slota@Sun.COM
 * @author see https://gwt4nb.dev.java.net/
 */
public class JavaModelUtils {
    /**
     * <p>
     * Returns the name of the non-primitive wrapper for the given
     * {@code TypeMirror}. This method will either return the fully-qualified
     * name of the given type, or the name of it's wrapper (if it's
     * a {@link TypeKind#isPrimitive() primitive type}).
     * </p><p>
     * For example, passing in a {@code TypeMirror} of kind
     * {@link TypeKind#BOOLEAN} will return {@literal "Boolean"} for
     * an un-qualified-name, or {@literal "java.lang.Boolean"} in the case
     * of a qualified-name.
     * </p>
     *
     * @param normalType
     * @param qualifiedName 
     * @return
     */
    public static String getNonPrimitiveTypeName(
            final TypeMirror normalType,
            final boolean qualifiedName) {        
        final TypeKind kind = normalType.getKind();

        if(kind.isPrimitive()) {
            String name = "java.lang."; // NOI18N
            
            switch(normalType.getKind()) {
                case BOOLEAN:
                    name += "Boolean"; // NOI18N
                    break;
                case CHAR:
                    name += "Character"; // NOI18N
                    break;
                case BYTE:
                    name += "Byte"; // NOI18N
                    break;
                case SHORT:
                    name += "Short"; // NOI18N
                    break;
                case INT:
                    name += "Integer"; // NOI18N
                    break;
                case LONG:
                    name += "Long"; // NOI18N
                    break;
                case FLOAT:
                    name += "Float"; // NOI18N
                    break;
                case DOUBLE:
                    name += "Double"; // NOI18N
                    break;
                default:
                    throw new IllegalArgumentException(
                            "Unknown primitive type: " +  // NOI18N
                            normalType.toString());
            }

            return name;
        }

        return normalType.toString();
    }

    public static ExecutableElement[] getMethod(
            final TypeElement clazz,
            final String methodName) {

        final List<ExecutableElement> methods = new ArrayList<ExecutableElement>();

        for (final ExecutableElement method :
            ElementFilter.methodsIn(clazz.getEnclosedElements())){
                
            if (method.getSimpleName().contentEquals(methodName)){
                methods.add(method);
            }
        }

        return methods.toArray(new ExecutableElement[methods.size()]);
    }

    /**
     * Tests to see if the given {@code syncMethod} matches the signature of {@code asyncMethod}.
     * This method tests that all the parameters are correct, and that the {@code asyncMethod}
     * has an additional {@code AsyncCallback} parameter on the end (which may include a
     * generic).
     *
     * @param syncMethod the synchronous version of the method to test
     * @param asyncMethod the async version of the method to test
     * @return {@literal true} if the {@code asyncMethod} is the async duplicate of the
     *      specified {@code syncMethod}
     */
    public static boolean isMethodMatched(
            final ExecutableElement syncMethod,
            final ExecutableElement asyncMethod) {
        final List<? extends VariableElement> syncMethodParamsList =
                syncMethod.getParameters();
        final List<? extends VariableElement> asyncMethodParamsList =
                asyncMethod.getParameters();

        final int syncParameterCount = syncMethodParamsList.size();
        final int asyncCallbackIndex = asyncMethodParamsList.size() - 1;

        //if number of parameters don't match or AysnCallback parameter is missing, skip the method
        if(syncParameterCount != asyncCallbackIndex) {
            return false;
        }

        final VariableElement lastParamElement = asyncMethodParamsList.get(asyncCallbackIndex);
        final TypeMirror lastParamTypeMirror = lastParamElement.asType();

        final CharSequence lastParamType = lastParamTypeMirror.getKind() == TypeKind.DECLARED
                ? ((DeclaredType)lastParamTypeMirror).asElement().toString()
                : lastParamTypeMirror.toString();

        if (!"com.google.gwt.user.client.rpc.AsyncCallback". // NOI18N
                contentEquals(lastParamType)) {
            return false;
        } else if(syncMethodParamsList.size() == 0) {
            return true;
        }

        // test to see if the generic type of the AsyncCallback is the same
        // as the return type of the sync method (if the AsyncCallback has a
        // declared
        // generic type for the onSuccess method).
        if (lastParamTypeMirror.getKind() == TypeKind.DECLARED) {
            final DeclaredType declaredType = 
                    (DeclaredType) lastParamTypeMirror;
            final List<? extends TypeMirror> types =
                    declaredType.getTypeArguments();

            if (syncMethod.getReturnType().getKind() == TypeKind.VOID) {
                if (types.size() == 1 && !types.get(0).toString().equals(
                        "java.lang.Void")) // NOI18N
                    return false;
            } else {
                final String expectedGenericType = getNonPrimitiveTypeName(
                        syncMethod.getReturnType(), true);

                if (types.size() == 1 && !expectedGenericType.equals(
                        types.get(0).toString())) {
                    return false;
                }
            }
        }

        for (int i = 0; i < syncParameterCount; i++) {
            final TypeMirror syncParameter = syncMethodParamsList.get(i).asType();
            final TypeMirror asyncParameter = asyncMethodParamsList.get(i).asType();

            //if the class of the corresponding parameters don't match, we skip the method
            if(!syncParameter.toString().equals(asyncParameter.toString())) {
                return false;
            }
        }

        return true;
    }

    /** 
     * Matches syncMethod's signature with the signatures of the array of
     * asyncMethods passed
     *
     * @returns true if a match is found
     */
    public static boolean isMethodSignatureEqual(
            final ExecutableElement syncMethod,
            final ExecutableElement[] asyncMethods){

        for (final ExecutableElement async: asyncMethods){
            if (isMethodMatched(syncMethod, async)) {
                return true;
            }
        }

        return false;
    }

    /** 
     * Matches asyncMethod's signature with the signatures of the array of
     * syncMethods passed
     *
     * @returns true if a match is found
     */
    public static boolean isMathcingServiceMethodFound(
            final ExecutableElement asyncMethod,
            final ExecutableElement[] syncMethods){
        for(final ExecutableElement sync : syncMethods) {
            if(isMethodMatched(sync, asyncMethod)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Method to compare the methods of clazzA with those of clazzB
     *
     * @returns list of element handles of methods in A but not in B
     */
    @SuppressWarnings("rawtypes")
    public static List<ElementHandle> getUnmatchedMethods(
            TypeElement clazzA, TypeElement clazzB){
        List<ElementHandle> unmatchedMethods = new ArrayList<ElementHandle>();

        for (ExecutableElement aMethod: ElementFilter.methodsIn(
                clazzA.getEnclosedElements())){
            String amethodName = aMethod.getSimpleName().toString();

            ExecutableElement methodsWithMatchingName[] =
                    JavaModelUtils.getMethod(clazzB,amethodName);

            if (methodsWithMatchingName.length==0){
                ElementHandle aMethodHandle = ElementHandle.create(aMethod);
                unmatchedMethods.add(aMethodHandle);
            } else if (!JavaModelUtils.isMathcingServiceMethodFound(aMethod,
                    methodsWithMatchingName)){
                ElementHandle aMethodHandle = ElementHandle.create(aMethod);
                unmatchedMethods.add(aMethodHandle);
            }
        }
        return unmatchedMethods;
    }

    public static String extractClassNameFromType(TypeMirror type){
        if (type.getKind() == TypeKind.DECLARED){
            Element elem = ((DeclaredType)type).asElement();

            if (elem.getKind() == ElementKind.CLASS
                    || elem.getKind() == ElementKind.INTERFACE){
                return ((TypeElement)elem).getQualifiedName().toString();
            }
        }

        return null;
    }

    public static String getShortClassName(String qualifiedClassName){
        return qualifiedClassName.substring(
                qualifiedClassName.lastIndexOf(".") + 1); // NOI18N
    }
}
