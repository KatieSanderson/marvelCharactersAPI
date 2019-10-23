package katie.marvel.util;

import co.aurasphere.jyandex.Jyandex;
import co.aurasphere.jyandex.dto.Language;

public class Translator {

    private final Jyandex client;

    public Translator(Jyandex client) {
        this.client = client;
    }

    /** Translates the textToTranslate into the desiredLanguage.
     * Queries the translation API (Jyandex) for non-empty textToTranslate and returns result.
     * Returns empty string on empty string input.
     *
     * @param textToTranslate - text to be translated by translation API
     * @param desiredLanguage - desired language
     * @return translated text
     */
    public String translate(String textToTranslate, String desiredLanguage) {
        if (textToTranslate.equals("")) {
            return "";
        } else if (!client.supportedLanguages().getSupportedLanguages().containsKey(desiredLanguage)) {
            throw new IllegalArgumentException("Language provided [" + desiredLanguage +"] not supported.");
        }
        return client.translateText(textToTranslate, Language.ENGLISH, desiredLanguage).getTranslatedText()[0];
    }
}
