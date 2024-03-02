package stepDefinitions;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import io.cucumber.java.en.*;
import io.restassured.path.json.JsonPath;
import pojo.Root;
import utils.CatUtils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import static io.restassured.RestAssured.given;

public class stepDefinitions extends CatUtils{
    public static String id;

    @Given("user is able to {string} the random {string} images from the server")
    public void user_is_able_to_the_random_images_from_the_server(String httpMethod, String limit) throws JsonProcessingException {
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
        /*
        2 ways to deserialize
        */
//        List<Root> cat =new ObjectMapper().readValue(response.asString(), new TypeReference<List<Root>>(){});
//        for(Root st: cat) {
//            System.out.println(st.getHeight());
//        }
        objects =  deserializeMyResponse(response);
        // Now, 'objects' contains the deserialized JSON array
        for (Root obj : objects) {
            // Perform actions with each object
            System.out.println(obj.getHeight());
        }
        map.clear();
    }

    @When("user is able to {string} the image on server")
    public void user_is_able_to_the_image_on_server(String httpMethod) throws IOException {
        requestSpecification = setRequestSpecification(map, "FORM");
        requestSpecBuilder = given().spec(requestSpecification)
                .log().all().multiPart("file", new File("/Users/sinhapoo/Downloads/test-files/cato.jpg"),"image/jpeg").relaxedHTTPSValidation();
        if(httpMethod.equalsIgnoreCase("POST")){
            responseSpecification = setResponseSpecification("POST", "JSON");
            response = requestSpecBuilder.when()
                    .post("v1/images/upload")
                    .then().log().all().spec(responseSpecification).extract().response();
        }
        js = new JsonPath(response.asString());
        id = js.getString("id");
    }

    @Then("user is able to {string} the image from the server")
    public void user_is_able_to_the_image_from_the_server(String httpMethod) {
        requestSpecification = setRequestSpecification(map, "JSON");
        requestSpecBuilder = given().spec(requestSpecification).log().all();
        if (httpMethod.equalsIgnoreCase("GET")){
            responseSpecification = setResponseSpecification("GET", "JSON");
            response = requestSpecBuilder.when()
                    .get("v1/images/"+id)
                    .then().log().all().spec(responseSpecification).extract().response();
        } else if (httpMethod.equalsIgnoreCase("DELETE")) {
            responseSpecification = setResponseSpecification("DELETE", "HTML");
            response = requestSpecBuilder.when().delete("v1/images/"+id)
                    .then().log().all().spec(responseSpecification).extract().response();
        }
    }
}
