import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.marvel.models.Superhero;

import static io.restassured.RestAssured.given;
import static org.marvel.Constants.BASE_URL;
import static org.marvel.TestData.DEFAULT_HERO;

public class BaseTests {

    SoftAssertions softly = new SoftAssertions();

    @Test
    void simpleGetTest () {
        var response = given().baseUri(BASE_URL).contentType(ContentType.JSON)
                .when().get("/superheroes")
                .then().statusCode(200).extract().response();
        System.out.println("Response: " + response.asPrettyString());
    }

    @Test
    void simplePostCheckJavaObjectTest() {
        Response response = RestAssured.given().baseUri(BASE_URL).contentType(ContentType.JSON)
                .body(DEFAULT_HERO)
                .when().post("/superheroes")
                .then().extract().response();
        System.out.println(response.asPrettyString());
        Superhero createdHero = response.as(Superhero.class);
        System.out.println(createdHero);
        Assertions.assertEquals(200, response.statusCode());
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(createdHero.getPhone()).as("Phone value is invalid").isEqualTo("+9959550431");
            softly.assertThat(createdHero.getFullName()).as("Full name value is invalid").isEqualTo("Blade");
            softly.assertThat(createdHero.getBirthDate()).as("Birth date value is invalid").isEqualTo("2002-03-15");
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
    void simplePutHeroDataUpdate() {
        Response response = RestAssured.given().baseUri(BASE_URL).contentType(ContentType.JSON)
                .body(DEFAULT_HERO)
                .when().post("/superheroes")
                .then().statusCode(200).extract().response();
        System.out.println(response.asPrettyString());

        int createdHeroId = response.path("id");
        Superhero createdHero = new Superhero();
        createdHero.setId(createdHeroId);

        createdHero.setFullName("Updated Blade");
        createdHero.setCity("Boston");
        createdHero.setBirthDate("1990-01-01");
        createdHero.setMainSkill("New Mortal skill");
        createdHero.setGender("F");
        createdHero.setPhone("88005500050");

       given().baseUri(BASE_URL).contentType(ContentType.JSON)
                .body(createdHero)
                .when().put("/superheroes/" + createdHero.getId())
                .then().statusCode(200).extract().response();

        Response getResponse = given().baseUri(BASE_URL)
                .contentType(ContentType.JSON)
                .when().get("/superheroes/" + createdHero.getId())
                .then().statusCode(200).extract().response();
        System.out.println(getResponse.asPrettyString());
    }
}
