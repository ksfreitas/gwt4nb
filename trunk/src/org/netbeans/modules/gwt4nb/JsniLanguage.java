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
 */
package org.netbeans.modules.gwt4nb;

import java.util.Collection;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import org.netbeans.api.java.lexer.JavaStringTokenId;
import org.netbeans.api.java.lexer.JavaTokenId;
import static org.netbeans.api.java.lexer.JavaTokenId.BLOCK_COMMENT;
import static org.netbeans.api.java.lexer.JavaTokenId.JAVADOC_COMMENT;
import static org.netbeans.api.java.lexer.JavaTokenId.STRING_LITERAL;
import org.netbeans.api.java.lexer.JavadocTokenId;
import org.netbeans.api.lexer.InputAttributes;
import org.netbeans.api.lexer.Language;
import org.netbeans.api.lexer.LanguagePath;
import org.netbeans.api.lexer.PartType;
import org.netbeans.api.lexer.Token;
import org.netbeans.api.lexer.TokenUtilities;
import org.netbeans.lib.java.lexer.JavaCharacterTokenId;
import org.netbeans.lib.java.lexer.JavaLexer;
import org.netbeans.spi.lexer.LanguageEmbedding;
import org.netbeans.spi.lexer.LanguageHierarchy;
import org.netbeans.spi.lexer.Lexer;
import org.netbeans.spi.lexer.LexerRestartInfo;

//@LanguageRegistration(mimeType={"text/x-java"}) // NOI18N
public class JsniLanguage {

    @SuppressWarnings("rawtypes")
    public static Language language() {

        return new LanguageHierarchy<JavaTokenId>() {

            @Override
            protected Collection<JavaTokenId> createTokenIds() {
                return EnumSet.allOf(JavaTokenId.class);
            }

            @Override
            protected Lexer<JavaTokenId> createLexer(LexerRestartInfo<JavaTokenId> info) {
                return new JavaLexer(info);
            }

            @Override
            protected Map<String, Collection<JavaTokenId>> createTokenCategories() {
                Map<String, Collection<JavaTokenId>> cats = new HashMap<String, Collection<JavaTokenId>>();
                // Additional literals being a lexical error
                cats.put("error", EnumSet.of(
                        JavaTokenId.FLOAT_LITERAL_INVALID
                ));
                // Literals category
                EnumSet<JavaTokenId> l = EnumSet.of(
                        JavaTokenId.INT_LITERAL,
                        JavaTokenId.LONG_LITERAL,
                        JavaTokenId.FLOAT_LITERAL,
                        JavaTokenId.DOUBLE_LITERAL,
                        JavaTokenId.CHAR_LITERAL
                );
                l.add(JavaTokenId.STRING_LITERAL);
                cats.put("literal", l);

                return cats;
            }

            @SuppressWarnings("unchecked")
            @Override
            protected LanguageEmbedding embedding(
                    Token<JavaTokenId> token, LanguagePath languagePath, InputAttributes inputAttributes) {

                // Test language embedding in the block comment
                switch (token.id()) {
                    case JAVADOC_COMMENT:
                        return LanguageEmbedding.create(JavadocTokenId.language(), 3,
                                (token.partType() == PartType.COMPLETE) ? 2 : 0);
                    case STRING_LITERAL:
                        return LanguageEmbedding.create(JavaStringTokenId.language(), 1,
                                (token.partType() == PartType.COMPLETE) ? 1 : 0);
                    case CHAR_LITERAL:
                        return LanguageEmbedding.create(JavaCharacterTokenId.language(), 1,
                                (token.partType() == PartType.COMPLETE) ? 1 : 0);
                    case BLOCK_COMMENT:
                        // JavaScript embedding? JSNI - in GWT
                        CharSequence t = token.text();
                        if (t != null && TokenUtilities.startsWith(t,
                                "/*-{") && TokenUtilities.endsWith(t, "}-*/")) { // NOI18N
                            Language lang = Language.find(
                                    "text/javascript"); // NOI18N
                            if (lang != null) {
                                return LanguageEmbedding.create(lang, 3, 3, true);
                            }
                        }
                }
                return null;
            }

            @Override
            protected String mimeType() {
                return "text/x-java";
            }
        }.language();
    }
}
