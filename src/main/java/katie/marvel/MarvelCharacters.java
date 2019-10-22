package katie.marvel;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
class MarvelCharacters {

    private List<Long> characterIDs = new ArrayList<>();
}
