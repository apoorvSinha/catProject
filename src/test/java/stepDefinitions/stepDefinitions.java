package stepDefinitions;

import io.cucumber.java.en.*;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import pojo.Root;
import pojo.SuperRoot;
import utils.CatUtils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class stepDefinitions extends CatUtils{
    RequestSpecification requestSpecification;
    RequestSpecification requestSpecBuilder;
    ResponseSpecification responseSpecification;
    Response response;
    Map<String, String> map;

    @Given("user is able to {string} the random {string} images from the server")
    public void user_is_able_to_the_random_images_from_the_server(String httpMethod, String limit) {
        map = new HashMap<>();
        map.put("size", "med");
        map.put("mime_types", "jpg");
        map.put("has_breeds", "0");
        map.put("order", "RANDOM");
        map.put("limit", limit);
        requestSpecification = setRequestSpecification(map, "JSON");
        requestSpecBuilder = given().spec(requestSpecification).log().all();
        if (httpMethod.equalsIgnoreCase("GET")){
            responseSpecification = setResponseSpecification("GET", "JSON");
            response = requestSpecBuilder.when()
                    .get("v1/images/search")
                    .then().log().all().spec(responseSpecification).extract().response();
        }
        SuperRoot cat = response.as(SuperRoot.class);
        for(Root st: cat.getRoot()) {
            System.out.println(st.getHeight());
        }
        map.clear();
    }

    @When("user is able to {string} the image on server")
    public void user_is_able_to_the_image_on_server(String httpMethod) throws IOException {
        requestSpecification = setRequestSpecification(map, "FORM");
        requestSpecBuilder = given().spec(requestSpecification)
                .log().all().multiPart("file", new File("/Users/sinhapoo/Downloads/cato.jpg"),"image/jpeg").relaxedHTTPSValidation();
        if(httpMethod.equalsIgnoreCase("POST")){
            responseSpecification = setResponseSpecification("POST", "JSON");
            response = requestSpecBuilder.when()
                    .post("v1/images/upload")
                    .then().log().all().spec(responseSpecification).extract().response();
        }

    }

    @Then("user is able to {string} the image from the server")
    public void user_is_able_to_the_image_from_the_server(String string) {

    }
}
