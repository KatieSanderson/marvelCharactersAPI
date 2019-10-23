package katie.marvel.config;

import co.aurasphere.jyandex.Jyandex;
import katie.marvel.data.MarvelCharacterIDs;
import katie.marvel.marvelApi.MarvelAPIConnector;
import katie.marvel.util.Translator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:application.yml")
public class MarvelApplicationConfig {

    @Value("${translate.key}")
    private String apiKey = "a";

    @Bean
    @Scope("singleton")
    MarvelCharacterIDs getMarvelCharacters() {
        return new MarvelCharacterIDs();
    }

    @Bean
    MarvelAPIConnector getMarvelAPIConnector() {
        return new MarvelAPIConnector();
    }


    @Bean
    Translator getTranslator(Jyandex jyandex) {
        return new Translator(jyandex);
    }

    @Bean
    Jyandex getJyandex() {
        return new Jyandex(apiKey);
    }
}
