package katie.marvel.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import katie.marvel.MarvelAPIConnector;
import katie.marvel.data.MarvelCharacter;
import katie.marvel.data.MarvelCharacterIDs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Collectors;

@RestController
public class MarvelController {

    @Autowired
    private MarvelCharacterIDs marvelCharacterIDs;

    @Autowired
    private MarvelAPIConnector marvelAPIConnector;

    public MarvelController() {

    }

    @GetMapping("/characters")
    public String getCharacters() {
        return "[ " +
                marvelCharacterIDs.getCharacterSet().stream()
                        .map(Object::toString).collect(Collectors.joining(", ")) +
                " ]";
    }

    @GetMapping("/characters/{id}")
    public String get(@PathVariable Long id) throws JsonProcessingException {
        if (!marvelCharacterIDs.getCharacterSet().contains(id)) {
            return "Character with id [" + id + "] does not exist";
        }
        MarvelCharacter character = marvelAPIConnector.getCharacterFromAPI(id);
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        return ow.writeValueAsString(character);
    }
}
