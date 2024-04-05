import controllers.SuperheroController;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.api.Test;
import org.marvel.models.Superhero;

import static io.restassured.RestAssured.given;
import static org.marvel.Constants.BASE_URL;
import static org.marvel.TestData.*;

public class BaseTests {

    SoftAssertions softly = new SoftAssertions();
    SuperheroController controller = new SuperheroController();


    @Test
    void simpleGetTest() {
        var response = given().baseUri(BASE_URL).contentType(ContentType.JSON)
                .when().get("/superheroes")
                .then().statusCode(200).extract().response();
        System.out.println("Response: " + response.asPrettyString());
    }

    @Test
    void simpleGetControllerTest() {
        Response response = controller.getHero();
        Assertions.assertEquals(200, response.getStatusCode());
    }

    @Test
    void postControllerTest() {
        Response response = controller.addHero();
        int status = response.getStatusCode();
        Superhero superhero = response.as(Superhero.class);
        Assertions.assertEquals(200, status);
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(superhero.getPhone()).as("Phone value is invalid").isEqualTo("+9959550431");
            softly.assertThat(superhero.getFullName()).as("Full name value is invalid").isEqualTo("Blade");
            softly.assertThat(superhero.getBirthDate()).as("Birth date value is invalid").isEqualTo("2002-03-15");
            // Code without .assertAll()
        });
    }

    @Test
    void simplePostCheckJsonTest() {
        Response response = RestAssured.given().baseUri(BASE_URL).contentType(ContentType.JSON)
                .body(DEFAULT_HERO)
                .when().post("/superheroes")
                .then().statusCode(200).extract().response();
        System.out.println(response.asPrettyString());
        JsonPath json = response.jsonPath();
        String phone = json.get("phone");
        String fn = json.get("fullName");
        softly.assertThat(phone).isEqualTo("+9959550431");
        softly.assertThat(fn).isEqualTo("Blade");
        softly.assertAll();
    }

    @Test
    @Tags({@Tag("Failed"), @Tag("Flaky")})
    void simplePutHeroDataUpdate() {
        Response response = RestAssured.given().baseUri(BASE_URL).contentType(ContentType.JSON)
                .body(DEFAULT_HERO)
                .when().post("/superheroes")
                .then().statusCode(200).extract().response();
        System.out.println(response.asPrettyString());
        Superhero createdHeroObj = response.as(Superhero.class);

        int createdHeroId = createdHeroObj.getId();
        Superhero createdHero = getUpdatedHero2(createdHeroId);

        given().baseUri(BASE_URL).contentType(ContentType.JSON)
                .body(createdHero)
                .when().put("/superheroes/" + createdHero.getId())
                .then()
                .extract().response();

        Response getResponse = given().baseUri(BASE_URL)
                .contentType(ContentType.JSON)
                .when().get("/superheroes/" + createdHero.getId())
                .then().statusCode(200).extract().response();
        Superhero updHero = getResponse.as(Superhero.class);
        System.out.println(getResponse.asPrettyString());


        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(updHero.getPhone()).isEqualTo(createdHero.getPhone());
            softly.assertThat(updHero.getFullName()).isEqualTo(createdHero.getFullName());
            softly.assertThat(updHero.getBirthDate()).isEqualTo(createdHero.getBirthDate());
            // Code without .assertAll()
        });


    }

    @Test
    void simpleDeleteHero() {
        Response response = controller.addHero();
        Superhero superheroCreated = response.as(Superhero.class);
        int createdHeroId = superheroCreated.getId();
        int status = response.getStatusCode();
        Assertions.assertEquals(200, status);

        Response responseDelete = controller.deleteHero(createdHeroId);
        Assertions.assertEquals(200, responseDelete.getStatusCode());

        Response getResponse = controller.getHeroByID(createdHeroId);
        System.out.println(superheroCreated);
        Assertions.assertEquals(400, getResponse.getStatusCode());
    }
}
