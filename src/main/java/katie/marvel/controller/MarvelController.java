package katie.marvel.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import katie.marvel.MarvelAPIConnector;
import katie.marvel.data.MarvelCharacter;
import katie.marvel.data.MarvelCharacterIDs;
import katie.marvel.util.Translator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Collectors;

@RestController
public class MarvelController {

    @Autowired
    private MarvelCharacterIDs marvelCharacterIDs;

    @Autowired
    private MarvelAPIConnector marvelAPIConnector;

    @GetMapping("/characters")
    public String getCharacters() {
        return "[ " +
                marvelCharacterIDs.getCharacterSet().stream()
                        .map(Object::toString).collect(Collectors.joining(", ")) +
                " ]";
    }

    @GetMapping("/characters/{characterId}")
    public String getCharacter(@PathVariable Long characterId, @RequestParam (required = false, defaultValue = "en") String languageCode) {
        if (!marvelCharacterIDs.getCharacterSet().contains(characterId)) {
            return "Character with id [" + characterId + "] does not exist";
        }
        MarvelCharacter marvelCharacter = marvelAPIConnector.getCharacterFromAPI(characterId);
        if (!languageCode.equals("en")) {
            marvelCharacter.setDescription("hi");
            marvelCharacter.setDescription(Translator.translate(marvelCharacter.getDescription(), languageCode));
        }
        ObjectMapper mapper = new ObjectMapper();
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        try {
            return ow.writeValueAsString(marvelCharacter);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error parsing character JSON", e);
        }
    }
}
