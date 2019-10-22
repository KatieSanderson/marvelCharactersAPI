package katie.marvel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Collectors;

@RestController
public class MarvelController {

    @Autowired
    MarvelCharacters marvelCharacters;

    public MarvelController() {

    }

    @GetMapping("/characters")
    public String getCharacters() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[ ");
        stringBuilder.append(marvelCharacters.getCharacterIDs().stream()
                .map(Object::toString).collect(Collectors.joining(", ")));
        stringBuilder.append(" ]");
        return stringBuilder.toString();
    }

    @GetMapping("/characters/")
    public String get() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[ ");
        stringBuilder.append(marvelCharacters.getCharacterIDs().stream()
                .map(Object::toString).collect(Collectors.joining(", ")));
        stringBuilder.append(" ]");
        return stringBuilder.toString();
    }
}
