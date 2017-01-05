package org.netbeans.modules.gwt4nb;

import org.netbeans.api.editor.mimelookup.MimeLookup;
import org.netbeans.api.java.lexer.JavaTokenId;
import org.netbeans.api.lexer.InputAttributes;
import org.netbeans.api.lexer.Language;
import org.netbeans.api.lexer.LanguagePath;
import org.netbeans.api.lexer.Token;
import org.netbeans.api.lexer.TokenUtilities;
import org.netbeans.spi.lexer.LanguageEmbedding;
import org.netbeans.spi.lexer.LanguageProvider;
import org.openide.util.lookup.ServiceProvider;

/**
 *
 * @author Chris2011
 */
@ServiceProvider(service = LanguageProvider.class)
public class JSNISupport extends LanguageProvider {

    private Language embeddedLanguage;
    private static final String START_FRAGMENT = "/*-{";
    private static final String END_FRAGMENT = "}-*/";

    @Override
    public Language<?> findLanguage(String mimeType) {
        return null;
    }

    @Override
    public LanguageEmbedding<?> findLanguageEmbedding(Token<?> token, LanguagePath languagePath, InputAttributes inputAttributes) {
        if (embeddedLanguage == null) {
            initLanguage();
        }

        if (JavaTokenId.BLOCK_COMMENT == token.id()) {
            if (token.text() != null
                    && TokenUtilities.startsWith(token.text(), START_FRAGMENT)
                    && TokenUtilities.endsWith(token.text(), END_FRAGMENT)) {
                return LanguageEmbedding.create(embeddedLanguage, START_FRAGMENT.length(), END_FRAGMENT.length(), true);
            }
        }

        return null;
    }

    private void initLanguage() {
        embeddedLanguage = MimeLookup.getLookup("text/javascript").lookup(Language.class);
    }
}