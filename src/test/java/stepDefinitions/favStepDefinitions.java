package stepDefinitions;

import io.cucumber.java.en.*;
import io.restassured.specification.RequestSpecification;
import pojo.Root;
import utils.CatUtils;

import java.util.HashMap;

public class favStepDefinitions extends CatUtils {

    @Given("user {string} their favourite cat ID")
    public void user_their_favourite_cat_ID(String httpMethod) {
        for(Root object: objects){
            System.out.println(object.getId());
        }
    }
}
