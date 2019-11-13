package katie.marvel.controller;

import katie.marvel.service.MarvelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MarvelController {

    @Autowired
    private MarvelService marvelService;

    /**Serves an endpoint /characters that returns all the Marvel character ids
     *
     * @return JSON list of all character IDs
     */
    @GetMapping("/characters")
    // TODO - convert String return val to object (MarvelCharacters) with RestExceptionHandler
    public String getCharacters() {
        return marvelService.getMarvelCharacters();
    }

    /** Serves an endpoint /characters/{characterId} with optional query param languageCode.
     * Queries the real-time data from the Marvel API /v1/public/characters/{characterId} to return character information
     *
     * @param characterId - id of desired character
     * @param languageCode - language code - must be in ISO-639-1 format
     * @return JSON formatted character information - id, name, description, thumbnail
     */
    @GetMapping("/characters/{characterId}")
    // TODO - convert String return val to object (MarvelCharacter) with RestExceptionHandler
    public String getCharacter(@PathVariable Long characterId, @RequestParam (required = false, defaultValue = "en") String languageCode) {
        return marvelService.getMarvelCharacter(characterId, languageCode);
    }
}
