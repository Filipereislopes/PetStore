package petstore;

import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static io.restassured.RestAssured.given;

public class Pet {
    String uri = "https://petstore.swagger.io/v2/pet";

    public String lerJson(String caminhoJson) throws IOException {

        return new String(Files.readAllBytes(Paths.get(caminhoJson)));
    }

    //Incluir - Create - Post
    @Test // Identifica o Método ou função como um teste para o testNG
    public void incluirPet() throws IOException {
        String JsonBody = lerJson("db/pet1.json");

        // Sintaxe Gherkin
        //Dado - Quando - Então
        //Given - When - Then

        given() //Dado

                .contentType("application/json") //comum em API REST - antigas era "text/xml'
                .log().all()
                .body(JsonBody)

        .when() //Quando

                .post(uri)

        .then() //Então

                .log().all()
                .statusCode(200)
        ;
    }

}
