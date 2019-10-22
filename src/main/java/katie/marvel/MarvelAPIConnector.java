package katie.marvel;

import com.fasterxml.jackson.databind.ObjectMapper;
import katie.marvel.characterSetResponse.Character;
import katie.marvel.characterSetResponse.CharacterDataWrapper;
import katie.marvel.util.HashBuilder;
import lombok.Getter;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@PropertySource("file:application.properties")
class MarvelAPIConnector {

    @Value("${marvel.domain}")
    private String marvelDomain;
    @Value("${marvel.charactersPath}")
    private String marvelCharactersPath;
    @Value("${user.publicKey}")
    private String publicKey;
    @Value("${user.privateKey}")
    private String privateKey;
    @Getter
    private List<Long> characterIDs;

    public MarvelAPIConnector() {
        characterIDs = new ArrayList<>();
    }

    void getCharacterIDsFromAPI() {
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            long offset = 0;
            // initialized larger than offset to ensure first ping (and avoid do while loop)
            long total = 200;
            while (offset < total) {
                System.out.println("getting with offset " + offset + " total " + total);

                String ts = Long.toString(System.currentTimeMillis());
                URI uri = new URIBuilder(marvelDomain + marvelCharactersPath)
                        .addParameter("limit", "100")
                        .addParameter("apikey", publicKey)
                        .addParameter("ts", ts)
                        .addParameter("hash", HashBuilder.getHash(ts, privateKey, publicKey))
                        .build();
                HttpGet request = new HttpGet(uri);

                try (CloseableHttpResponse response = client.execute(request)) {
                    HttpEntity entity = response.getEntity();
                    if (entity == null) {
                        throw new RuntimeException("Response entity is null. Response: [" + response.toString() + "].");
                    }
                    String result = EntityUtils.toString(entity);

                    // response entity expected to be JSON formatted to /characterSetResponse objects
                    ObjectMapper mapper = new ObjectMapper();
                    CharacterDataWrapper characterDataWrapper = mapper.readValue(result, CharacterDataWrapper.class);

//                total = json.getCharacterDataContainer().getTotal();
                    offset += characterDataWrapper.getData().getCount();
                    characterIDs.addAll(characterDataWrapper.getData().getResults().stream()
                            .map(Character::getId).collect(Collectors.toList()));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}