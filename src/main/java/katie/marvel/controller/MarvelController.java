package katie.marvel.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import katie.marvel.data.MarvelCharacter;
import katie.marvel.data.MarvelCharacterIDs;
import katie.marvel.marvelApi.MarvelAPIConnector;
import katie.marvel.util.Translator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@RestController
public class MarvelController {

    @Autowired
    private Translator translator;

    @Autowired
    private MarvelCharacterIDs marvelCharacterIDs;

    @Autowired
    private MarvelAPIConnector marvelAPIConnector;

    @GetMapping("/characters")
    public String getCharacters() {
        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        final ObjectMapper mapper = new ObjectMapper();

        try {
            mapper.writer().writeValue(out, marvelCharacterIDs.getCharacterSet());
        } catch (IOException e) {
            throw new RuntimeException("Error parsing character JSON", e);
        }

        final byte[] data = out.toByteArray();
        return new String(data);
    }

    @GetMapping("/characters/{characterId}")
    public String getCharacter(@PathVariable Long characterId, @RequestParam (required = false, defaultValue = "en") String languageCode) {
        if (!marvelCharacterIDs.getCharacterSet().contains(characterId)) {
            return "Character id [" + characterId + "] does not exist";
        }
        MarvelCharacter marvelCharacter = marvelAPIConnector.getCharacterFromAPI(characterId);

        try {
            marvelCharacter.setDescription(translator.translate(marvelCharacter.getDescription(), languageCode));
        } catch (IllegalArgumentException e) {
            return e.getMessage();
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
