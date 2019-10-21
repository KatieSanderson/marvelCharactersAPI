package katie.marvel;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MarvelController {

    @GetMapping("/characters")
    public String get() {
        return "Hello!";
    }
}
