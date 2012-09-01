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

import org.netbeans.api.java.lexer.JavaTokenId;
import org.netbeans.api.lexer.PartType;
import org.netbeans.api.lexer.Token;
import org.netbeans.spi.lexer.Lexer;
import org.netbeans.spi.lexer.LexerInput;
import org.netbeans.spi.lexer.LexerRestartInfo;
import org.netbeans.spi.lexer.TokenFactory;

/**
 * Lexical analyzer for java language.
 * <br/>
 * It recognizes "version" attribute and expects <code>java.lang.Integer</code>
 * value for it. The default value is Integer.valueOf(5). The lexer changes
 * its behavior in the following way:
 * <ul>
 *     <li> Integer.valueOf(4) - "assert" recognized as keyword (not identifier)
 *     <li> Integer.valueOf(5) - "enum" recognized as keyword (not identifier)
 * </ul>
 *
 * @author Miloslav Metelka
 * @version 1.00
 */

public class JavaLexer implements Lexer<JavaTokenId> {
    
    private static final int EOF = LexerInput.EOF;

    private final LexerInput input;
    
    private final TokenFactory<JavaTokenId> tokenFactory;
    
    private final int version;

    public JavaLexer(LexerRestartInfo<JavaTokenId> info) {
        this.input = info.input();
        this.tokenFactory = info.tokenFactory();
        assert (info.state() == null); // never set to non-null value in state()
        
        Integer ver = (Integer)info.getAttributeValue("version"); // NOI18N
        this.version = (ver != null) ? ver.intValue() : 5; // Use Java 1.5 by default
    }
    
    public Object state() {
        return null; // always in default state after token recognition
    }
    
    @SuppressWarnings("fallthrough")
    public Token<JavaTokenId> nextToken() {
        while(true) {
            int c = input.read();
            switch (c) {
                case '"': // string literal
                    while (true)
                        switch (input.read()) {
                            case '"': // NOI18N
                                return token(JavaTokenId.STRING_LITERAL);
                            case '\\':
                                input.read();
                                break;
                            case '\r': input.consumeNewline();
                            case '\n':
                            case EOF:
                                return tokenFactory.createToken(JavaTokenId.STRING_LITERAL,
                                        input.readLength(), PartType.START);
                        }

                case '\'': // char literal
                    while (true)
                        switch (input.read()) {
                            case '\'': // NOI18N
                                return token(JavaTokenId.CHAR_LITERAL);
                            case '\\':
                                input.read(); // read escaped char
                                break;
                            case '\r': input.consumeNewline();
                            case '\n':
                            case EOF:
                                return tokenFactory.createToken(JavaTokenId.CHAR_LITERAL,
                                        input.readLength(), PartType.START);
                        }

                case '/':
                    switch (input.read()) {
                        case '/': // in single-line comment
                            while (true)
                                switch (input.read()) {
                                    case '\r': input.consumeNewline();
                                    case '\n':
                                    case EOF:
                                        return token(JavaTokenId.LINE_COMMENT);
                                }
                        case '=': // found /=
                            return token(JavaTokenId.SLASHEQ);
                        case '*': // in multi-line or javadoc comment
                            c = input.read();
                            if (c == '*') { // either javadoc comment or empty multi-line comment /**/
                                    c = input.read();
                                    if (c == '/')
                                        return token(JavaTokenId.BLOCK_COMMENT);
                                    while (true) { // in javadoc comment
                                        while (c == '*') {
                                            c = input.read();
                                            if (c == '/')
                                                return token(JavaTokenId.JAVADOC_COMMENT);
                                            else if (c == EOF)
                                                return tokenFactory.createToken(JavaTokenId.JAVADOC_COMMENT,
                                                        input.readLength(), PartType.START);
                                        }
                                        if (c == EOF)
                                            return tokenFactory.createToken(JavaTokenId.JAVADOC_COMMENT,
                                                        input.readLength(), PartType.START);
                                        c = input.read();
                                    }

                            } else { // in multi-line comment (and not after '*')
                                while (true) {
                                    c = input.read();
                                    while (c == '*') {
                                        c = input.read();
                                        if (c == '/')
                                            return token(JavaTokenId.BLOCK_COMMENT);
                                        else if (c == EOF)
                                            return tokenFactory.createToken(JavaTokenId.BLOCK_COMMENT,
                                                    input.readLength(), PartType.START);
                                    }
                                    if (c == EOF)
                                        return tokenFactory.createToken(JavaTokenId.BLOCK_COMMENT,
                                                input.readLength(), PartType.START);
                                }
                            }
                    } // end of switch()
                    input.backup(1);
                    return token(JavaTokenId.SLASH);

                case '=':
                    if (input.read() == '=')
                        return token(JavaTokenId.EQEQ);
                    input.backup(1);
                    return token(JavaTokenId.EQ);

                case '>':
                    switch (input.read()) {
                        case '>': // after >>
                            switch (c = input.read()) {
                                case '>': // after >>>
                                    if (input.read() == '=')
                                        return token(JavaTokenId.GTGTGTEQ);
                                    input.backup(1);
                                    return token(JavaTokenId.GTGTGT);
                                case '=': // >>=
                                    return token(JavaTokenId.GTGTEQ);
                            }
                            input.backup(1);
                            return token(JavaTokenId.GTGT);
                        case '=': // >=
                            return token(JavaTokenId.GTEQ);
                    }
                    input.backup(1);
                    return token(JavaTokenId.GT);

                case '<':
                    switch (input.read()) {
                        case '<': // after <<
                            if (input.read() == '=')
                                return token(JavaTokenId.LTLTEQ);
                            input.backup(1);
                            return token(JavaTokenId.LTLT);
                        case '=': // <=
                            return token(JavaTokenId.LTEQ);
                    }
                    input.backup(1);
                    return token(JavaTokenId.LT);

                case '+':
                    switch (input.read()) {
                        case '+':
                            return token(JavaTokenId.PLUSPLUS);
                        case '=':
                            return token(JavaTokenId.PLUSEQ);
                    }
                    input.backup(1);
                    return token(JavaTokenId.PLUS);

                case '-':
                    switch (input.read()) {
                        case '-':
                            return token(JavaTokenId.MINUSMINUS);
                        case '=':
                            return token(JavaTokenId.MINUSEQ);
                    }
                    input.backup(1);
                    return token(JavaTokenId.MINUS);

                case '*':
                    switch (input.read()) {
                        case '/': // invalid comment end - */
                            return token(JavaTokenId.INVALID_COMMENT_END);
                        case '=':
                            return token(JavaTokenId.STAREQ);
                    }
                    input.backup(1);
                    return token(JavaTokenId.STAR);

                case '|':
                    switch (input.read()) {
                        case '|':
                            return token(JavaTokenId.BARBAR);
                        case '=':
                            return token(JavaTokenId.BAREQ);
                    }
                    input.backup(1);
                    return token(JavaTokenId.BAR);

                case '&':
                    switch (input.read()) {
                        case '&':
                            return token(JavaTokenId.AMPAMP);
                        case '=':
                            return token(JavaTokenId.AMPEQ);
                    }
                    input.backup(1);
                    return token(JavaTokenId.AMP);

                case '%':
                    if (input.read() == '=')
                        return token(JavaTokenId.PERCENTEQ);
                    input.backup(1);
                    return token(JavaTokenId.PERCENT);

                case '^':
                    if (input.read() == '=')
                        return token(JavaTokenId.CARETEQ);
                    input.backup(1);
                    return token(JavaTokenId.CARET);

                case '!':
                    if (input.read() == '=')
                        return token(JavaTokenId.BANGEQ);
                    input.backup(1);
                    return token(JavaTokenId.BANG);

                case '.':
                    if ((c = input.read()) == '.')
                        if (input.read() == '.') { // ellipsis ...
                            return token(JavaTokenId.ELLIPSIS);
                        } else
                            input.backup(2);
                    else if ('0' <= c && c <= '9') { // float literal
                        return finishNumberLiteral(input.read(), true);
                    } else
                        input.backup(1);
                    return token(JavaTokenId.DOT);

                case '~':
                    return token(JavaTokenId.TILDE);
                case ',':
                    return token(JavaTokenId.COMMA);
                case ';':
                    return token(JavaTokenId.SEMICOLON);
                case ':':
                    return token(JavaTokenId.COLON);
                case '?':
                    return token(JavaTokenId.QUESTION);
                case '(':
                    return token(JavaTokenId.LPAREN);
                case ')':
                    return token(JavaTokenId.RPAREN);
                case '[':
                    return token(JavaTokenId.LBRACKET);
                case ']':
                    return token(JavaTokenId.RBRACKET);
                case '{':
                    return token(JavaTokenId.LBRACE);
                case '}':
                    return token(JavaTokenId.RBRACE);
                case '@':
                    return token(JavaTokenId.AT);

                case '0': // in a number literal
		    c = input.read();
                    if (c == 'x' || c == 'X') { // in hexadecimal (possibly floating-point) literal
                        boolean inFraction = false;
                        while (true) {
                            switch (input.read()) {
                                case '0': case '1': case '2': case '3': case '4':
                                case '5': case '6': case '7': case '8': case '9':
                                case 'a': case 'b': case 'c': case 'd': case 'e': case 'f':
                                case 'A': case 'B': case 'C': case 'D': case 'E': case 'F':
                                    break;
                                case '.': // hex float literal
                                    if (!inFraction) {
                                        inFraction = true;
                                    } else { // two dots in the float literal
                                        return token(JavaTokenId.FLOAT_LITERAL_INVALID);
                                    }
                                    break;
                                case 'p': case 'P': // binary exponent
                                    return finishFloatExponent();
                                default:
                                    input.backup(1);
                                    // if float then before mandatory binary exponent => invalid
                                    return token(inFraction ? JavaTokenId.FLOAT_LITERAL_INVALID
                                            : JavaTokenId.INT_LITERAL);
                            }
                        } // end of while(true)
                    }
                    return finishNumberLiteral(c, false);
                    
                case '1': case '2': case '3': case '4':
                case '5': case '6': case '7': case '8': case '9':
                    return finishNumberLiteral(input.read(), false);

                    
                // Keywords lexing    
                case 'a':
                    switch (c = input.read()) {
                        case 'b':
                            if ((c = input.read()) == 's'
                             && (c = input.read()) == 't'
                             && (c = input.read()) == 'r'
                             && (c = input.read()) == 'a'
                             && (c = input.read()) == 'c'
                             && (c = input.read()) == 't')
                                return keywordOrIdentifier(JavaTokenId.ABSTRACT);
                            break;
                        case 's':
                            if ((c = input.read()) == 's'
                             && (c = input.read()) == 'e'
                             && (c = input.read()) == 'r'
                             && (c = input.read()) == 't')
                                return (version >= 4)
                                        ? keywordOrIdentifier(JavaTokenId.ASSERT)
                                        : finishIdentifier();
                            break;
                    }
                    return finishIdentifier(c);

                case 'b':
                    switch (c = input.read()) {
                        case 'o':
                            if ((c = input.read()) == 'o'
                             && (c = input.read()) == 'l'
                             && (c = input.read()) == 'e'
                             && (c = input.read()) == 'a'
                             && (c = input.read()) == 'n')
                                return keywordOrIdentifier(JavaTokenId.BOOLEAN);
                            break;
                        case 'r':
                            if ((c = input.read()) == 'e'
                             && (c = input.read()) == 'a'
                             && (c = input.read()) == 'k')
                                return keywordOrIdentifier(JavaTokenId.BREAK);
                            break;
                        case 'y':
                            if ((c = input.read()) == 't'
                             && (c = input.read()) == 'e')
                                return keywordOrIdentifier(JavaTokenId.BYTE);
                            break;
                    }
                    return finishIdentifier(c);

                case 'c':
                    switch (c = input.read()) {
                        case 'a':
                            switch (c = input.read()) {
                                case 's':
                                    if ((c = input.read()) == 'e')
                                        return keywordOrIdentifier(JavaTokenId.CASE);
                                    break;
                                case 't':
                                    if ((c = input.read()) == 'c'
                                     && (c = input.read()) == 'h')
                                        return keywordOrIdentifier(JavaTokenId.CATCH);
                                    break;
                            }
                            break;
                        case 'h':
                            if ((c = input.read()) == 'a'
                             && (c = input.read()) == 'r')
                                return keywordOrIdentifier(JavaTokenId.CHAR);
                            break;
                        case 'l':
                            if ((c = input.read()) == 'a'
                             && (c = input.read()) == 's'
                             && (c = input.read()) == 's')
                                return keywordOrIdentifier(JavaTokenId.CLASS);
                            break;
                        case 'o':
                            if ((c = input.read()) == 'n') {
                                switch (c = input.read()) {
                                    case 's':
                                        if ((c = input.read()) == 't')
                                            return keywordOrIdentifier(JavaTokenId.CONST);
                                        break;
                                    case 't':
                                        if ((c = input.read()) == 'i'
                                         && (c = input.read()) == 'n'
                                         && (c = input.read()) == 'u'
                                         && (c = input.read()) == 'e')
                                            return keywordOrIdentifier(JavaTokenId.CONTINUE);
                                        break;
                                }
                            }
                            break;
                    }
                    return finishIdentifier(c);

                case 'd':
                    switch (c = input.read()) {
                        case 'e':
                            if ((c = input.read()) == 'f'
                             && (c = input.read()) == 'a'
                             && (c = input.read()) == 'u'
                             && (c = input.read()) == 'l'
                             && (c = input.read()) == 't')
                                return keywordOrIdentifier(JavaTokenId.DEFAULT);
                            break;
                        case 'o':
                            switch (c = input.read()) {
                                case 'u':
                                    if ((c = input.read()) == 'b'
                                     && (c = input.read()) == 'l'
                                     && (c = input.read()) == 'e')
                                        return keywordOrIdentifier(JavaTokenId.DOUBLE);
                                    break;
                                default:
                                    return keywordOrIdentifier(JavaTokenId.DO, c);
                            }
                            break;
                    }
                    return finishIdentifier(c);

                case 'e':
                    switch (c = input.read()) {
                        case 'l':
                            if ((c = input.read()) == 's'
                             && (c = input.read()) == 'e')
                                return keywordOrIdentifier(JavaTokenId.ELSE);
                            break;
                        case 'n':
                            if ((c = input.read()) == 'u'
                             && (c = input.read()) == 'm')
                                return (version >= 5)
                                        ? keywordOrIdentifier(JavaTokenId.ENUM)
                                        : finishIdentifier();
                            break;
                        case 'x':
                            if ((c = input.read()) == 't'
                             && (c = input.read()) == 'e'
                             && (c = input.read()) == 'n'
                             && (c = input.read()) == 'd'
                             && (c = input.read()) == 's')
                                return keywordOrIdentifier(JavaTokenId.EXTENDS);
                            break;
                    }
                    return finishIdentifier(c);

                case 'f':
                    switch (c = input.read()) {
                        case 'a':
                            if ((c = input.read()) == 'l'
                             && (c = input.read()) == 's'
                             && (c = input.read()) == 'e')
                                return keywordOrIdentifier(JavaTokenId.FALSE);
                            break;
                        case 'i':
                            if ((c = input.read()) == 'n'
                             && (c = input.read()) == 'a'
                             && (c = input.read()) == 'l')
                                switch (c = input.read()) {
                                    case 'l':
                                        if ((c = input.read()) == 'y')
                                            return keywordOrIdentifier(JavaTokenId.FINALLY);
                                        break;
                                    default:
                                        return keywordOrIdentifier(JavaTokenId.FINAL, c);
                                }
                            break;
                        case 'l':
                            if ((c = input.read()) == 'o'
                             && (c = input.read()) == 'a'
                             && (c = input.read()) == 't')
                                return keywordOrIdentifier(JavaTokenId.FLOAT);
                            break;
                        case 'o':
                            if ((c = input.read()) == 'r')
                                return keywordOrIdentifier(JavaTokenId.FOR);
                            break;
                    }
                    return finishIdentifier(c);

                case 'g':
                    if ((c = input.read()) == 'o'
                     && (c = input.read()) == 't'
                     && (c = input.read()) == 'o')
                        return keywordOrIdentifier(JavaTokenId.GOTO);
                    return finishIdentifier(c);
                    
                case 'i':
                    switch (c = input.read()) {
                        case 'f':
                            return keywordOrIdentifier(JavaTokenId.IF);
                        case 'm':
                            if ((c = input.read()) == 'p') {
                                switch (c = input.read()) {
                                    case 'l':
                                        if ((c = input.read()) == 'e'
                                         && (c = input.read()) == 'm'
                                         && (c = input.read()) == 'e'
                                         && (c = input.read()) == 'n'
                                         && (c = input.read()) == 't'
                                         && (c = input.read()) == 's')
                                            return keywordOrIdentifier(JavaTokenId.IMPLEMENTS);
                                        break;
                                    case 'o':
                                        if ((c = input.read()) == 'r'
                                         && (c = input.read()) == 't')
                                            return keywordOrIdentifier(JavaTokenId.IMPORT);
                                        break;
                                }
                            }
                            break;
                        case 'n':
                            switch (c = input.read()) {
                                case 's':
                                    if ((c = input.read()) == 't'
                                     && (c = input.read()) == 'a'
                                     && (c = input.read()) == 'n'
                                     && (c = input.read()) == 'c'
                                     && (c = input.read()) == 'e'
                                     && (c = input.read()) == 'o'
                                     && (c = input.read()) == 'f')
                                        return keywordOrIdentifier(JavaTokenId.INSTANCEOF);
                                    break;
                                case 't':
                                    switch (c = input.read()) {
                                        case 'e':
                                            if ((c = input.read()) == 'r'
                                             && (c = input.read()) == 'f'
                                             && (c = input.read()) == 'a'
                                             && (c = input.read()) == 'c'
                                             && (c = input.read()) == 'e')
                                                return keywordOrIdentifier(JavaTokenId.INTERFACE);
                                            break;
                                        default:
                                            return keywordOrIdentifier(JavaTokenId.INT, c);
                                    }
                                    break;
                            }
                            break;
                    }
                    return finishIdentifier(c);

                case 'l':
                    if ((c = input.read()) == 'o'
                     && (c = input.read()) == 'n'
                     && (c = input.read()) == 'g')
                        return keywordOrIdentifier(JavaTokenId.LONG);
                    return finishIdentifier(c);

                case 'n':
                    switch (c = input.read()) {
                        case 'a':
                            if ((c = input.read()) == 't'
                             && (c = input.read()) == 'i'
                             && (c = input.read()) == 'v'
                             && (c = input.read()) == 'e')
                                return keywordOrIdentifier(JavaTokenId.NATIVE);
                            break;
                        case 'e':
                            if ((c = input.read()) == 'w')
                                return keywordOrIdentifier(JavaTokenId.NEW);
                            break;
                        case 'u':
                            if ((c = input.read()) == 'l'
                             && (c = input.read()) == 'l')
                                return keywordOrIdentifier(JavaTokenId.NULL);
                            break;
                    }
                    return finishIdentifier(c);

                case 'p':
                    switch (c = input.read()) {
                        case 'a':
                            if ((c = input.read()) == 'c'
                             && (c = input.read()) == 'k'
                             && (c = input.read()) == 'a'
                             && (c = input.read()) == 'g'
                             && (c = input.read()) == 'e')
                                return keywordOrIdentifier(JavaTokenId.PACKAGE);
                            break;
                        case 'r':
                            switch (c = input.read()) {
                                case 'i':
                                    if ((c = input.read()) == 'v'
                                     && (c = input.read()) == 'a'
                                     && (c = input.read()) == 't'
                                     && (c = input.read()) == 'e')
                                        return keywordOrIdentifier(JavaTokenId.PRIVATE);
                                    break;
                                case 'o':
                                    if ((c = input.read()) == 't'
                                     && (c = input.read()) == 'e'
                                     && (c = input.read()) == 'c'
                                     && (c = input.read()) == 't'
                                     && (c = input.read()) == 'e'
                                     && (c = input.read()) == 'd')
                                        return keywordOrIdentifier(JavaTokenId.PROTECTED);
                                    break;
                            }
                            break;
                        case 'u':
                            if ((c = input.read()) == 'b'
                             && (c = input.read()) == 'l'
                             && (c = input.read()) == 'i'
                             && (c = input.read()) == 'c')
                                return keywordOrIdentifier(JavaTokenId.PUBLIC);
                            break;
                    }
                    return finishIdentifier(c);

                case 'r':
                    if ((c = input.read()) == 'e'
                     && (c = input.read()) == 't'
                     && (c = input.read()) == 'u'
                     && (c = input.read()) == 'r'
                     && (c = input.read()) == 'n')
                        return keywordOrIdentifier(JavaTokenId.RETURN);
                    return finishIdentifier(c);

                case 's':
                    switch (c = input.read()) {
                        case 'h':
                            if ((c = input.read()) == 'o'
                             && (c = input.read()) == 'r'
                             && (c = input.read()) == 't')
                                return keywordOrIdentifier(JavaTokenId.SHORT);
                            break;
                        case 't':
                            switch (c = input.read()) {
                                case 'a':
                                    if ((c = input.read()) == 't'
                                     && (c = input.read()) == 'i'
                                     && (c = input.read()) == 'c')
                                        return keywordOrIdentifier(JavaTokenId.STATIC);
                                    break;
                                case 'r':
                                    if ((c = input.read()) == 'i'
                                     && (c = input.read()) == 'c'
                                     && (c = input.read()) == 't'
                                     && (c = input.read()) == 'f'
                                     && (c = input.read()) == 'p')
                                        return keywordOrIdentifier(JavaTokenId.STRICTFP);
                                    break;
                            }
                            break;
                        case 'u':
                            if ((c = input.read()) == 'p'
                             && (c = input.read()) == 'e'
                             && (c = input.read()) == 'r')
                                return keywordOrIdentifier(JavaTokenId.SUPER);
                            break;
                        case 'w':
                            if ((c = input.read()) == 'i'
                             && (c = input.read()) == 't'
                             && (c = input.read()) == 'c'
                             && (c = input.read()) == 'h')
                                return keywordOrIdentifier(JavaTokenId.SWITCH);
                            break;
                        case 'y':
                            if ((c = input.read()) == 'n'
                             && (c = input.read()) == 'c'
                             && (c = input.read()) == 'h'
                             && (c = input.read()) == 'r'
                             && (c = input.read()) == 'o'
                             && (c = input.read()) == 'n'
                             && (c = input.read()) == 'i'
                             && (c = input.read()) == 'z'
                             && (c = input.read()) == 'e'
                             && (c = input.read()) == 'd')
                                return keywordOrIdentifier(JavaTokenId.SYNCHRONIZED);
                            break;
                    }
                    return finishIdentifier(c);

                case 't':
                    switch (c = input.read()) {
                        case 'h':
                            switch (c = input.read()) {
                                case 'i':
                                    if ((c = input.read()) == 's')
                                        return keywordOrIdentifier(JavaTokenId.THIS);
                                    break;
                                case 'r':
                                    if ((c = input.read()) == 'o'
                                     && (c = input.read()) == 'w')
                                        switch (c = input.read()) {
                                            case 's':
                                                return keywordOrIdentifier(JavaTokenId.THROWS);
                                            default:
                                                return keywordOrIdentifier(JavaTokenId.THROW, c);
                                        }
                                    break;
                            }
                            break;
                        case 'r':
                            switch (c = input.read()) {
                                case 'a':
                                    if ((c = input.read()) == 'n'
                                     && (c = input.read()) == 's'
                                     && (c = input.read()) == 'i'
                                     && (c = input.read()) == 'e'
                                     && (c = input.read()) == 'n'
                                     && (c = input.read()) == 't')
                                        return keywordOrIdentifier(JavaTokenId.TRANSIENT);
                                    break;
                                case 'u':
                                    if ((c = input.read()) == 'e')
                                        return keywordOrIdentifier(JavaTokenId.TRUE);
                                    break;
                                case 'y':
                                    return keywordOrIdentifier(JavaTokenId.TRY);
                            }
                            break;
                    }
                    return finishIdentifier(c);

                case 'v':
                    if ((c = input.read()) == 'o') {
                        switch (c = input.read()) {
                            case 'i':
                                if ((c = input.read()) == 'd')
                                    return keywordOrIdentifier(JavaTokenId.VOID);
                                break;
                            case 'l':
                                if ((c = input.read()) == 'a'
                                 && (c = input.read()) == 't'
                                 && (c = input.read()) == 'i'
                                 && (c = input.read()) == 'l'
                                 && (c = input.read()) == 'e')
                                    return keywordOrIdentifier(JavaTokenId.VOLATILE);
                                break;
                        }
                    }
                    return finishIdentifier(c);

                case 'w':
                    if ((c = input.read()) == 'h'
                     && (c = input.read()) == 'i'
                     && (c = input.read()) == 'l'
                     && (c = input.read()) == 'e')
                        return keywordOrIdentifier(JavaTokenId.WHILE);
                    return finishIdentifier(c);

                // Rest of lowercase letters starting identifiers
                case 'h': case 'j': case 'k': case 'm': case 'o':
                case 'q': case 'u': case 'x': case 'y': case 'z':
                // Uppercase letters starting identifiers
                case 'A': case 'B': case 'C': case 'D': case 'E':
                case 'F': case 'G': case 'H': case 'I': case 'J':
                case 'K': case 'L': case 'M': case 'N': case 'O':
                case 'P': case 'Q': case 'R': case 'S': case 'T':
                case 'U': case 'V': case 'W': case 'X': case 'Y':
                case 'Z':
                case '$': case '_':
                    return finishIdentifier();
                    
                // All Character.isWhitespace(c) below 0x80 follow
                // ['\t' - '\r'] and [0x1c - ' ']
                case '\t':
                case '\n':
                case 0x0b:
                case '\f':
                case '\r':
                case 0x1c:
                case 0x1d:
                case 0x1e:
                case 0x1f:
                    return finishWhitespace();
                case ' ':
                    c = input.read();
                    if (c == EOF || !Character.isWhitespace(c)) { // Return single space as flyweight token
                        input.backup(1);
                        return tokenFactory.getFlyweightToken(JavaTokenId.WHITESPACE, 
                                " "); // NOI18N
                    }
                    return finishWhitespace();

                case EOF:
                    return null;

                default:
                    if (c >= 0x80) { // lowSurr ones already handled above
                        c = translateSurrogates(c);
                        if (Character.isJavaIdentifierStart(c))
                            return finishIdentifier();
                        if (Character.isWhitespace(c))
                            return finishWhitespace();
                    }

                    // Invalid char
                    return token(JavaTokenId.ERROR);
            } // end of switch (c)
        } // end of while(true)
    }
    
    private int translateSurrogates(int c) {
        if (Character.isHighSurrogate((char)c)) {
            int lowSurr = input.read();
            if (lowSurr != EOF && Character.isLowSurrogate((char)lowSurr)) {
                // c and lowSurr form the integer unicode char.
                c = Character.toCodePoint((char)c, (char)lowSurr);
            } else {
                // Otherwise it's error: Low surrogate does not follow the high one.
                // Leave the original character unchanged.
                // As the surrogates do not belong to any
                // specific unicode category the lexer should finally
                // categorize them as a lexical error.
                input.backup(1);
            }
        }
        return c;
    }

    private Token<JavaTokenId> finishWhitespace() {
        while (true) {
            int c = input.read();
            // There should be no surrogates possible for whitespace
            // so do not call translateSurrogates()
            if (c == EOF || !Character.isWhitespace(c)) {
                input.backup(1);
                return tokenFactory.createToken(JavaTokenId.WHITESPACE);
            }
        }
    }
    
    private Token<JavaTokenId> finishIdentifier() {
        return finishIdentifier(input.read());
    }
    
    private Token<JavaTokenId> finishIdentifier(int c) {
        while (true) {
            if (c == EOF || !Character.isJavaIdentifierPart(c = translateSurrogates(c))) {
                // For surrogate 2 chars must be backed up
                input.backup((c >= Character.MIN_SUPPLEMENTARY_CODE_POINT) ? 2 : 1);
                return tokenFactory.createToken(JavaTokenId.IDENTIFIER);
            }
            c = input.read();
        }
    }

    private Token<JavaTokenId> keywordOrIdentifier(JavaTokenId keywordId) {
        return keywordOrIdentifier(keywordId, input.read());
    }

    private Token<JavaTokenId> keywordOrIdentifier(JavaTokenId keywordId, int c) {
        // Check whether the given char is non-ident and if so then return keyword
        if (c == EOF || !Character.isJavaIdentifierPart(c = translateSurrogates(c))) {
            // For surrogate 2 chars must be backed up
            input.backup((c >= Character.MIN_SUPPLEMENTARY_CODE_POINT) ? 2 : 1);
            return token(keywordId);
        } else // c is identifier part
            return finishIdentifier();
    }
    
    private Token<JavaTokenId> finishNumberLiteral(int c, boolean inFraction) {
        while (true) {
            switch (c) {
                case '.':
                    if (!inFraction) {
                        inFraction = true;
                    } else { // two dots in the literal
                        return token(JavaTokenId.FLOAT_LITERAL_INVALID);
                    }
                    break;
                case 'l': case 'L': // 0l or 0L
                    return token(JavaTokenId.LONG_LITERAL);
                case 'd': case 'D':
                    return token(JavaTokenId.DOUBLE_LITERAL);
                case 'f': case 'F':
                    return token(JavaTokenId.FLOAT_LITERAL);
                case '0': case '1': case '2': case '3': case '4':
                case '5': case '6': case '7': case '8': case '9':
                    break;
                case 'e': case 'E': // exponent part
                    return finishFloatExponent();
                default:
                    input.backup(1);
                    return token(inFraction ? JavaTokenId.DOUBLE_LITERAL
                            : JavaTokenId.INT_LITERAL);
            }
            c = input.read();
        }
    }
    
    private Token<JavaTokenId> finishFloatExponent() {
        int c = input.read();
        if (c == '+' || c == '-') {
            c = input.read();
        }
        if (c < '0' || '9' < c)
            return token(JavaTokenId.FLOAT_LITERAL_INVALID);
        do {
            c = input.read();
        } while ('0' <= c && c <= '9'); // reading exponent
        switch (c) {
            case 'd': case 'D':
                return token(JavaTokenId.DOUBLE_LITERAL);
            case 'f': case 'F':
                return token(JavaTokenId.FLOAT_LITERAL);
            default:
                input.backup(1);
                return token(JavaTokenId.DOUBLE_LITERAL);
        }
    }
    
    private Token<JavaTokenId> token(JavaTokenId id) {
        String fixedText = id.fixedText();
        return (fixedText != null)
                ? tokenFactory.getFlyweightToken(id, fixedText)
                : tokenFactory.createToken(id);
    }
    
    public void release() {
    }

}
