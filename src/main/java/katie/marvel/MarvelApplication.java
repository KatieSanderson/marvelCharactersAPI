package katie.marvel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class MarvelApplication {

	@Autowired
	private MarvelAPIConnector marvelAPIConnector;

	@Autowired
	private MarvelCharacters marvelCharacters;

	public static void main(String[] args) {
		SpringApplication.run(MarvelApplication.class, args);
	}

	/**
	 * Loads application config and character IDs
	 */
	@PostConstruct
	public void load() {
//		String configPath = "src\\main\\resources\\config.json";
//		try (FileReader reader = new FileReader(configPath)) {
//			JSONParser jsonParser = new JSONParser();
//			Object obj = jsonParser.parse(reader);
//
//			JSONObject jsonObject = (JSONObject) obj;
//			marvelAPIConnector.setPublicKey((String) jsonObject.get("public key"));
//			marvelAPIConnector.setPrivateKey((String) jsonObject.get("private key"));
//		} catch (IOException | ParseException e1) {
//			e1.printStackTrace();
//		}
		marvelAPIConnector.getCharacterIDsFromAPI();

		marvelCharacters.setCharacterIDs(marvelAPIConnector.getCharacterIDs());
	}
}
