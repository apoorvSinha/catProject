package stepDefinitions;

import io.cucumber.java.en.*;
import io.restassured.http.ContentType;
import utils.CatUtils;

import java.util.HashMap;

import static io.restassured.RestAssured.*;

public class favStepDefinitions extends CatUtils {
    private static final String favouriteEndpoint = "/v1/favourites";

    private String getFavIdOfCats() {
        return objects.get(objects.size() - 1).getId();
    }

    @Given("user {string} their favourite cat ID")
    public void user_their_favourite_cat_ID(String httpMethod) {
        map = new HashMap<>();
        requestSpecification = setRequestSpecification(map, ContentType.JSON.toString());
        responseSpecification = setResponseSpecification(httpMethod, ContentType.JSON.toString());
        requestSpecBuilder = given().spec(requestSpecification).log().all();
        if (httpMethod.equalsIgnoreCase("GET")) {
            response = requestSpecBuilder.when().get(favouriteEndpoint)
                    .then().log().all().spec(responseSpecification)
                    .extract().response();
        }
    }
}
