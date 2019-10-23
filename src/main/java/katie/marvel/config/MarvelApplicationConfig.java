package katie.marvel.config;

import katie.marvel.data.MarvelCharacterIDs;
import katie.marvel.marvelApi.MarvelAPIConnector;
import katie.marvel.util.Translator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
public class MarvelApplicationConfig {

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
    Translator getTranslator() {
        return new Translator();
    }
}
