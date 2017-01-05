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

import java.util.Collections;
import java.util.List;

import javax.lang.model.element.Element;

import com.sun.source.tree.Tree;
import com.sun.source.tree.ClassTree;
import com.sun.source.tree.MethodTree;
import com.sun.source.tree.VariableTree;

import com.sun.source.util.SourcePositions;

import org.netbeans.api.java.source.CompilationInfo;

import org.netbeans.spi.editor.hints.Fix;
import org.netbeans.spi.editor.hints.ErrorDescription;
import org.netbeans.spi.editor.hints.ErrorDescriptionFactory;

import org.netbeans.api.lexer.Token;
import org.netbeans.api.lexer.TokenSequence;

import org.netbeans.api.java.lexer.JavaTokenId;

import org.netbeans.spi.editor.hints.Severity;

/**
 *
 * @author Tomasz.Slota@Sun.COM
 */
public class HintsUtils {
    public static ErrorDescription createProblem(Element subject, CompilationInfo cinfo,
            String description){
        return createProblem(subject, cinfo, description, Severity.ERROR, Collections.<Fix>emptyList());
    }
    
    public static ErrorDescription createProblem(Element subject, CompilationInfo cinfo,
            String description, Severity severity){
        return createProblem(subject, cinfo, description, severity, Collections.<Fix>emptyList());
    }
    
    public static ErrorDescription createProblem(Element subject, CompilationInfo cinfo, String description,
            Severity severity, Fix fix){
        return createProblem(subject, cinfo, description, severity, Collections.singletonList(fix));
    }
    
    public static ErrorDescription createProblem(Element subject, CompilationInfo cinfo, String description, Fix fix){
        return createProblem(subject, cinfo, description, Severity.ERROR, Collections.singletonList(fix));
    }
    
    public static ErrorDescription createProblem(Element subject, CompilationInfo cinfo,
            String description, Severity severity, List<Fix> fixes){
        ErrorDescription err = null;
        List<Fix> fixList = fixes == null ? Collections.<Fix>emptyList() : fixes;
        
        // by default place error annotation on the element being checked
        Tree elementTree = cinfo.getTrees().getTree(subject);
        
        if (elementTree != null){
            TextSpan underlineSpan = getUnderlineSpan(cinfo, elementTree);
            
            err = ErrorDescriptionFactory.createErrorDescription(
                    severity, description, fixList, cinfo.getFileObject(),
                    underlineSpan.getStartOffset(), underlineSpan.getEndOffset());
            
        } else{
            // report problem
        }
        
        return err;
    }
    
    /**
     * This method returns the part of the syntax tree to be highlighted.
     * It will be usually the class/method/variable identifier.
     */
    @SuppressWarnings("rawtypes")
    public static TextSpan getUnderlineSpan(CompilationInfo info, Tree tree){
        SourcePositions srcPos = info.getTrees().getSourcePositions();
        
        int startOffset = (int) srcPos.getStartPosition(info.getCompilationUnit(), tree);
        int endOffset = (int) srcPos.getEndPosition(info.getCompilationUnit(), tree);
        
        Tree startSearchingForNameIndentifierBehindThisTree = null;
        
        if (tree.getKind() == Tree.Kind.CLASS){
            startSearchingForNameIndentifierBehindThisTree = ((ClassTree)tree).getModifiers();
            
        } else if (tree.getKind() == Tree.Kind.METHOD){
            startSearchingForNameIndentifierBehindThisTree = ((MethodTree)tree).getReturnType();
        } else if (tree.getKind() == Tree.Kind.VARIABLE){
            startSearchingForNameIndentifierBehindThisTree = ((VariableTree)tree).getType();
        }
        
        if (startSearchingForNameIndentifierBehindThisTree != null){
            int searchStart = (int) srcPos.getEndPosition(info.getCompilationUnit(),
                    startSearchingForNameIndentifierBehindThisTree);
            
            TokenSequence tokenSequence = info.getTreeUtilities().tokensFor(tree);
            
            if (tokenSequence != null){
                boolean eob = false;
                tokenSequence.move(searchStart);
                
                do{
                    eob = !tokenSequence.moveNext();
                }
                while (!eob && tokenSequence.token().id() != JavaTokenId.IDENTIFIER);
                
                if (!eob){
                    Token<?> identifier = tokenSequence.token();
                    startOffset = identifier.offset(info.getTokenHierarchy());
                    endOffset = startOffset + identifier.length();
                }
            }
        }
        
        return new TextSpan(startOffset, endOffset);
    }
    
    /**
     * Represents a span of text
     */
    public static class TextSpan{
        private int startOffset;
        private int endOffset;
        
        public TextSpan(int startOffset, int endOffset){
            this.startOffset = startOffset;
            this.endOffset = endOffset;
        }
        
        public int getStartOffset(){
            return startOffset;
        }
        
        public int getEndOffset(){
            return endOffset;
        }
    }
}
