package katie.marvel.util;

import co.aurasphere.jyandex.Jyandex;
import co.aurasphere.jyandex.dto.Language;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;

@PropertySource("file:application.properties")
public class Translator {

    @Value("${translate.key}")
    private String apiKey;

    public String translate(String textToTranslate, String desiredLanguage) {
        Jyandex client = new Jyandex(apiKey);
        return client.translateText(textToTranslate, Language.ENGLISH, desiredLanguage).getTranslatedText()[0];
    }
}
