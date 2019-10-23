package katie.marvel.util;

import co.aurasphere.jyandex.Jyandex;
import co.aurasphere.jyandex.dto.Language;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;

import java.util.Arrays;

@PropertySource("file:application.properties")
public class Translator {

    @Value("${translate.key}")
    private static String apiKey;

    public static String translate(String textToTranslate, String desiredLanguage) {
        Jyandex client = new Jyandex(apiKey);
        System.out.println(textToTranslate + " " + desiredLanguage);
        System.out.println(Arrays.toString(client.translateText(textToTranslate, Language.ENGLISH, desiredLanguage).getTranslatedText()));
        return "";
    }
}
