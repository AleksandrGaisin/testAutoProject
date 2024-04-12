import controllers.SuperheroController;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;
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
    void simpleGetControllerTest() {
        Response response = controller.getHero();
        Assertions.assertEquals(200, response.getStatusCode());
    }

    @Test
    void postControllerTest() {
        Response response = controller.addHero();
        System.setProperty("env", "dev");
        int status = response.getStatusCode();
        Superhero superhero = controller.parseHero(response);
        Assertions.assertEquals(200, status);
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(superhero.getPhone()).as("Phone value is invalid").isEqualTo("+9959550431");
            softly.assertThat(superhero.getFullName()).as("Full name value is invalid").isEqualTo("Blade");
            softly.assertThat(superhero.getBirthDate()).as("Birth date value is invalid").isEqualTo("2002-03-15");
            // Code without .assertAll()
        });
    }

    @Test
    @Tags({@Tag("Failed"), @Tag("Flaky")})
    void simplePutControllerTest() {
        Response response = controller.addHero();
        System.setProperty("env", "dev");
        int status = response.getStatusCode();
        Assertions.assertEquals(200, status);
        Superhero superhero = controller.parseHero(response);
        int heroID = superhero.getId();
        Superhero createdHero = getUpdatedHero2(heroID);
        Response updateResponse = controller.updateHeroByID(heroID, createdHero);
        Superhero updatedHero = controller.parseHero(updateResponse);
        Response getHero = controller.getHeroByID(updatedHero.getId());
        Superhero updHero = controller.parseHero(getHero);
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(updatedHero.getPhone()).isEqualTo(updHero.getPhone());
            softly.assertThat(updatedHero.getFullName()).isEqualTo(updHero.getFullName());
            softly.assertThat(updatedHero.getBirthDate()).isEqualTo(updHero.getBirthDate());
            // Code without .assertAll()
        });
    }

    @Test
    void simpleDeleteHeroControllerTest() {
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
