package controllers;

import configs.TestPropertiesConfig;
import io.qameta.allure.Attachment;
import io.qameta.allure.Step;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.http.ContentType;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.aeonbits.owner.ConfigFactory;
import org.marvel.models.Superhero;
import static io.restassured.RestAssured.given;
import static org.marvel.TestData.DEFAULT_HERO;

public class SuperheroController {
    TestPropertiesConfig testPropertiesConfig = ConfigFactory.create(TestPropertiesConfig.class, System.getProperties());

    RequestSpecification requestSpecification = given();

    public SuperheroController() {
        requestSpecification.baseUri(testPropertiesConfig.getBaseUrl());
        requestSpecification.contentType(ContentType.JSON);
        requestSpecification.filter(new AllureRestAssured());
    }

    @Attachment
    @Step("Create a new hero")
    public Response addHero() {
        return this.requestSpecification
                .body(DEFAULT_HERO)
                .when()
                .post("/superheroes")
                .then()
                .extract().response();
    }

    @Step("Parsing to obj")
    @Attachment
    public Superhero parseHero(Response response) {
        return response.as(Superhero.class);
    }

    @Step("Get all heroes")
    public Response getHero() {
        return this.requestSpecification
                .when()
                .get("/superheroes")
                .then()
                .extract().response();
    }

    @Step("Get a hero by ID")
    public Response getHeroByID(int id) {
        return this.requestSpecification
                .when()
                .get("/superheroes/" + id)
                .then()
                .extract().response();
    }

    @Step("Update hero's data")
    public Response updateHeroByID(int id, Superhero updatedHero) {
        return this.requestSpecification
                .body(updatedHero)
                .when()
                .put("/superheroes/" + id)
                .then()
                .extract().response();
    }

    @Step("Delete a hero by ID")
    public Response deleteHero(int id) {
        return this.requestSpecification
                .when()
                .delete("/superheroes/" + id)
                .then()
                .extract().response();
    }
}
