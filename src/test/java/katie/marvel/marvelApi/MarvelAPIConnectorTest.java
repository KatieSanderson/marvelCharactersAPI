package katie.marvel.marvelApi;

import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Assert;
import org.junit.Test;

import java.util.Date;

//@RunWith(SpringRunner.class)
public class MarvelAPIConnectorTest {

    @Test
    public void getCharacterIDsFromAPI() {
    }

    @Test
    public void getCharacterFromAPI() {
    }

    @Test
    public void givenPassword_whenHashingUsingCommons_thenVerifying()  {
        String hash = "afad70b4d08cffe0c2096fd503d42bc2";
        String ts = new Date(0).toString();
        String privateKey = "privateKey";
        String publicKey = "publicKey";

        String md5Hex = DigestUtils.md5Hex(ts + privateKey + publicKey);

        Assert.assertEquals(hash, md5Hex);
    }
}