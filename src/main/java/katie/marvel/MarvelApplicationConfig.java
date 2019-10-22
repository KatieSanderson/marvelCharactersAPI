package katie.marvel;

import katie.marvel.data.MarvelCharacterIDs;
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
}
