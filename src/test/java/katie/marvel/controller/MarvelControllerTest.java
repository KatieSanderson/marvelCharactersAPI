package katie.marvel.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class MarvelControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    void getCharacters__returnsIDs() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/characters"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andReturn();
    }

    @Test
    void getCharacter_nonexistentID_throwsException() throws Exception {
        int characterId = 0;
        mvc.perform(MockMvcRequestBuilders.get("/characters/" + characterId))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.message").value("Character id [" + characterId + "]"));
    }

    @Test
    void getCharacter_existingID_returnsError() throws Exception {
        int characterId = 1009146;
        mvc.perform(MockMvcRequestBuilders.get("/characters/" + characterId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1009146))
                .andExpect(jsonPath("$.name").value("Abomination (Emil Blonsky)"))
                .andExpect(jsonPath("$.description").value("Formerly known as Emil Blonsky, a spy of Soviet Yugoslavian origin working for the KGB, the Abomination gained his powers after receiving a dose of gamma radiation similar to that which transformed Bruce Banner into the incredible Hulk."))
                .andExpect(jsonPath("$.thumbnail.path").value("http://i.annihil.us/u/prod/marvel/i/mg/9/50/4ce18691cbf04"))
                .andReturn();
    }
}