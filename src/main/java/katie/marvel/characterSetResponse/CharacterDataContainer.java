package katie.marvel.characterSetResponse;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CharacterDataContainer {

    @JsonProperty("results")
    public List<Character> results;

    @JsonProperty("count")
    public long count;

    @JsonProperty("total")
    public long total;
}
