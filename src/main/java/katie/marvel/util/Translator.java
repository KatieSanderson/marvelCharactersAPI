package katie.marvel.util;

import co.aurasphere.jyandex.Jyandex;
import co.aurasphere.jyandex.dto.Language;

public class Translator {

    private final Jyandex client;

    public Translator(Jyandex client) {
        this.client = client;
    }

    public String translate(String textToTranslate, String desiredLanguage) {
        // todo translate from desired language (possibly not ISO) to ISO
        if (textToTranslate.equals("")) {
            return "";
        } else if (!client.supportedLanguages().getSupportedLanguages().containsKey(desiredLanguage)) {
            throw new IllegalArgumentException("Language provided [" + desiredLanguage +"] not supported.");
        }
        return client.translateText(textToTranslate, Language.ENGLISH, desiredLanguage).getTranslatedText()[0];
    }
}
