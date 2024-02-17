package utils;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.kohsuke.rngom.binary.Pattern;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Properties;

public class CatUtils {
    private String baseUri = "https://api.thecatapi.com";
    RequestSpecification requestSpecification;
    ResponseSpecification responseSpecification;
    static Properties properties;
    FileInputStream fis;

    public RequestSpecification setRequestSpecification(int limit) {
        requestSpecification = new RequestSpecBuilder()
                .setBaseUri(baseUri)
                .addQueryParam("size", "med")
                .addQueryParam("mime_types", "jpg")
                .addQueryParam("order", "RANDOM")
                .addQueryParam("limit", limit)
                .addHeader("x-api-key", readApiKey())
                .setContentType(ContentType.JSON).build();
        return requestSpecification;
    }

    public ResponseSpecification setResponseSpecification(String httpMethod) {
        int code = httpMethod.equalsIgnoreCase("GET") ? 200 : 201;
        responseSpecification = new ResponseSpecBuilder()
                .expectStatusCode(code)
                .expectContentType(ContentType.JSON).build();
        return responseSpecification;
    }

    public String readApiKey() {
        properties = new Properties();
        try {
            fis = new FileInputStream("./src/main/resources/hidden.properties");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        try {
            properties.load(fis);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return properties.getProperty("x-api-key");
    }

}
