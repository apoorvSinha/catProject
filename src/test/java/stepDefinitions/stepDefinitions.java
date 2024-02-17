package stepDefinitions;

import io.cucumber.java.en.*;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import utils.CatUtils;

import static io.restassured.RestAssured.given;

public class stepDefinitions extends CatUtils{
    RequestSpecification requestSpecification;
    RequestSpecification requestSpecBuilder;
    ResponseSpecification responseSpecification;
    Response response;

    @Given("user is able to {string} the random {string} images from the server")
    public void user_is_able_to_the_random_images_from_the_server(String httpMethod, String limit) {
        requestSpecification = setRequestSpecification(10);
        requestSpecBuilder = given().spec(requestSpecification).log().all();
        if (httpMethod.equalsIgnoreCase("GET")){
            responseSpecification = setResponseSpecification("GET");
            response = requestSpecification.when()
                    .get("/v1/images/search")
                    .then().log().all().spec(responseSpecification).extract().response();
        }

    }

    @When("user is able to upload the image on server")
    public void user_is_able_to_upload_the_image_on_server() {

    }

    @Then("user is able to {string} the image from the server")
    public void user_is_able_to_the_image_from_the_server(String string) {

    }
}
