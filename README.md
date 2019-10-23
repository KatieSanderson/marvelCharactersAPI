README

marvelCharactersAPI utilizes the Marvel Comics API to:
1.  Serve an endpoint that returns all the Marvel character ids only, in a JSON array of numbers.
    * Accessed via "/characters"
 2. Serve an endpoint that contains the real-time character information from the Marvel API /v1/public/characters/{characterId} with flexibility to translate the description to a desired language via query parameter.
    * Accessed via "/character/{characterID}?languageCode={languageID}"
    * Included character information: id, name, description, thumbnail
    * Default languageCode if not provided is English
    
The API includes Swagger specification that can be viewed with Swagger UI or imported to Postman

DEPLOY INSTRUCTIONS
Directions compiling and running this project from project root:
1. Command: mvn package
    * All required dependencies are managed by Maven
2. Command: java -jar target/marvel-1.0.jar
3. Navigate to http://localhost:8080/
4. Utilize and enjoy the supported endpoints for Marvel characters API!

Third party API
1. Jyandex - translation API
2. Swagger - visualisation API
