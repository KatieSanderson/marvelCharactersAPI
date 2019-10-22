package katie.marvel.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Thumbnail {

    @JsonProperty("path")
    public String path;

    @JsonProperty("extension")
    public String extension;
}
