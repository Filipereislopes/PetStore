package petstore;

import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.contains;


public class Pet {
    String uri = "https://petstore.swagger.io/v2/pet";

    public String lerJson(String caminhoJson) throws IOException {

        return new String(Files.readAllBytes(Paths.get(caminhoJson)));
    }

    //Incluir - Create - Post
    @Test (priority =1) // Identifica o Método ou função como um teste para o testNG
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
                .body("name", is ("Rex"))
                .body("status", is("available"))
                .body("category.name", is("dog"))
                .body("tags.name", contains("VACI"))
        ;
    }

    @Test (priority = 2)
    public void consultarPet(){
        String petId = "9326888661";
        String token =

        given()
                .contentType("application/json")
                .log().all()

        .when()
                .get(uri + "/" + petId)

        .then()
                .log().all()
                .statusCode(200)
                .body("name", is ("Rex"))
                .body("status", is("available"))
                .body("category.name", is("dog"))
                .extract()
                .path("category.name")

        ;

        System.out.println("o token é " + token);
    }
    @Test (priority = 3)
    public void alterarPet() throws IOException {
        String jsonBody = lerJson("db/pet2.json");

        given()
                .contentType("application/json")
                .log().all()
                .body(jsonBody)

        .when()
                .put(uri)

        .then()
                .log().all()
                .statusCode(200)
                .body("name", is("Rex"))
                .body("status", is("sold"))
        ;
    }

    @Test (priority = 4)
    public void excluirPet(){
        String petId = "9326888661";

        given()
                .contentType("application/json")
                .log().all()

        .when()
                .delete(uri + "/" + petId)
        .then()
                .log().all()
                .statusCode(200)
                .body("code", is (200))
                .body("type", is ("unknown"))
        ;
        System.out.println("Foi Excluido com sucesso!");
    }
}
