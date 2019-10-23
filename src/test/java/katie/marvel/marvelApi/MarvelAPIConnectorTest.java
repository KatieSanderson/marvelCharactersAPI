package katie.marvel.marvelApi;

import org.apache.commons.codec.digest.DigestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

//@RunWith(SpringRunner.class)
//@RunWith(value = .class)
class MarvelAPIConnectorTest {

    MarvelAPIConnector marvelAPIConnector;

    @BeforeEach
    void init() {
        marvelAPIConnector = new MarvelAPIConnector();
    }

    @Test
    void getCharacterFromAPI() {
    }

    @Test
    void givenPassword_whenHashingUsingCommons_thenVerifying()  {
        String hash = "afad70b4d08cffe0c2096fd503d42bc2";
        String ts = new Date(0).toString();
        String privateKey = "privateKey";
        String publicKey = "publicKey";

        String md5Hex = DigestUtils.md5Hex(ts + privateKey + publicKey);

        assertEquals(hash, md5Hex);
    }
}