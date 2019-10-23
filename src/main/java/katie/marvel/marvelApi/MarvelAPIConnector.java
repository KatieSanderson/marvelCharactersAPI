package katie.marvel.marvelApi;

import com.fasterxml.jackson.databind.ObjectMapper;
import katie.marvel.data.CharacterDataWrapper;
import katie.marvel.data.MarvelCharacter;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashSet;
import java.util.Set;

@PropertySource("file:application.yml")
public class MarvelAPIConnector {

    @Value("${marvel.domain}")
    private String marvelDomain;
    @Value("${marvel.charactersPath}")
    private String marvelCharactersPath;
    @Value("${user.publicKey}")
    private String publicKey;
    @Value("${user.privateKey}")
    private String privateKey;

    /** Queries Marvel API  and returns all character IDs
     *
     * @return set of character IDs
     */
    public Set<Long> getCharacterIDsFromAPI() {
        Set<Long> marvelCharacterIDs = new HashSet<>();
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            long offset = 0;
            // initialized larger than offset to ensure first ping (and avoid do while loop)
            long total = 1;
            while (offset < total) {
                String ts = Long.toString(System.currentTimeMillis());
                URI uri = new URIBuilder(marvelDomain + marvelCharactersPath)
                        .addParameter("limit", "100")
                        .addParameter("offset", Long.toString(offset))
                        .addParameter("apikey", publicKey)
                        .addParameter("ts", ts)
                        .addParameter("hash", DigestUtils.md5Hex(ts + privateKey + publicKey))
                        .build();
                HttpGet request = new HttpGet(uri);

                try (CloseableHttpResponse response = client.execute(request)) {
                    if (response.getStatusLine().getStatusCode() != 200) {
                        throw new RuntimeException("Connection error; status code [" + response.getStatusLine().getStatusCode() + "]");
                    }
                    HttpEntity entity = response.getEntity();
                    if (entity == null) {
                        throw new RuntimeException("Response entity is null. Response: [" + response.toString() + "].");
                    }
                    String result = EntityUtils.toString(entity);

                    // response entity expected to be JSON formatted to /data objects
                    ObjectMapper mapper = new ObjectMapper();
                    CharacterDataWrapper characterDataWrapper = mapper.readValue(result, CharacterDataWrapper.class);

                    total = characterDataWrapper.getData().getTotal();
                    offset += characterDataWrapper.getData().getCount();
                    characterDataWrapper.getData().getResults()
                            .forEach(i -> marvelCharacterIDs.add(i.getId()));
                } catch (ClientProtocolException e) {
                    throw new RuntimeException(e.getMessage());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return marvelCharacterIDs;
    }

    /** Queries Marvel API and returns MarvelCharacter containing query result
     *
     * @return MarvelCharacter
     */
    public MarvelCharacter getCharacterFromAPI(long id) {
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            String ts = Long.toString(System.currentTimeMillis());
            URI uri = new URIBuilder(marvelDomain + marvelCharactersPath + "/" + id)
                    .addParameter("apikey", publicKey)
                    .addParameter("ts", ts)
                    .addParameter("hash", DigestUtils.md5Hex(ts + privateKey + publicKey))
                    .build();
            HttpGet request = new HttpGet(uri);

            try (CloseableHttpResponse response = client.execute(request)) {
                HttpEntity entity = response.getEntity();
                if (entity == null) {
                    throw new RuntimeException("Response entity is null. Response: [" + response.toString() + "].");
                }
                String result = EntityUtils.toString(entity);

                // response entity expected to be JSON formatted to /data objects
                ObjectMapper mapper = new ObjectMapper();
                CharacterDataWrapper characterDataWrapper = mapper.readValue(result, CharacterDataWrapper.class);

                return characterDataWrapper.getData().getResults().get(0);
            }
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException("Failed when querying Marvel API.", e);
        }
    }
}