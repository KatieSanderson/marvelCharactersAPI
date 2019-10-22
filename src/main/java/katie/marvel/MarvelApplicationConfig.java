package katie.marvel;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
public class MarvelApplicationConfig {

    @Bean
    @Scope("singleton")
    MarvelCharacters getMarvelCharacters() {
        return new MarvelCharacters();
    }

    @Bean
    MarvelAPIConnector getMarvelAPIConnector() {
        return new MarvelAPIConnector();
    }
}
