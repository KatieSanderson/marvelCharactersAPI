package katie.marvel.service;

import katie.marvel.data.MarvelCharacter;
import katie.marvel.data.MarvelCharacterIDs;
import katie.marvel.marvelApi.MarvelAPIConnector;
import katie.marvel.util.Translator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class MarvelService {

    @Autowired
    private Translator translator;

    @Autowired
    private MarvelCharacterIDs marvelCharacterIDs;

    @Autowired
    private MarvelAPIConnector marvelAPIConnector;

    public Set<Long> getMarvelCharacters() {
        return marvelCharacterIDs.getCharacterSet();
    }

    public MarvelCharacter getMarvelCharacter(long characterId, String languageCode) {
        if (!marvelCharacterIDs.getCharacterSet().contains(characterId)) {
            // TODO - convert String return val to object with RestExceptionHandler
//            return "Character id [" + characterId + "] does not exist";
        }
        MarvelCharacter marvelCharacter = marvelAPIConnector.getCharacterFromAPI(characterId);

        try {
            marvelCharacter.setDescription(translator.translate(marvelCharacter.getDescription(), languageCode));
        } catch (IllegalArgumentException e) {
//            return e.getMessage();
        }
        return marvelCharacter;
    }
}
