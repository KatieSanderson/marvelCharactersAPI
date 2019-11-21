package katie.marvel.controller;

import katie.marvel.data.MarvelCharacter;
import katie.marvel.service.MarvelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
public class MarvelController {

    @Autowired
    private MarvelService marvelService;

    /**Serves an endpoint /characters that returns all the Marvel character ids
     *
     * @return JSON list of all character IDs
     */
    @GetMapping("/characters")
    @ResponseBody
    public Set<Long> getCharacters() {
        return marvelService.getMarvelCharacters();
    }

    /** Serves an endpoint /characters/{characterId} with optional query param languageCode.
     * Queries the real-time data from the Marvel API /v1/public/characters/{characterId} to return character information
     *
     * @param characterId - id of desired character
     * @param languageCode - language code - must be in ISO-639-1 format
     * @return JSON formatted character information - id, name, description, thumbnail
     */
    @GetMapping(value = "/characters/{characterId}")
    @ResponseBody
    public MarvelCharacter getCharacter(
            @PathVariable Long characterId, @RequestParam (required = false, defaultValue = "en") String languageCode) {
        return marvelService.getMarvelCharacter(characterId, languageCode);
    }
}
