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
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

public class CatUtils {
    private String baseUri = "https://api.thecatapi.com";
    RequestSpecification requestSpecification;
    ResponseSpecification responseSpecification;
    static Properties properties;
    FileInputStream fis;

    public RequestSpecification setRequestSpecification(Map<String, String> map, String contentType) {
        String content = "";
        if(contentType.equalsIgnoreCase("JSON")) {
            content = ContentType.JSON.toString();
        } else if (contentType.equalsIgnoreCase("FORM")) {
            content = "multipart/form-data";
        }
        requestSpecification = new RequestSpecBuilder()
                .setBaseUri(baseUri)
                .addQueryParams(map)
                .addHeader("x-api-key", readApiKey())
                .setContentType(content).build();
        return requestSpecification;
    }

    public ResponseSpecification setResponseSpecification(String httpMethod, String contentType) {
        int code;
        switch (httpMethod.toUpperCase()) {
            case "GET": code = 200; break;
            case "DELETE": code = 204; break;
            case "POST": code = 201; break;
            default: code = 900;
        }
        String content = "";
        if(contentType.equalsIgnoreCase("HTML")){
            content = ContentType.HTML.toString();
        } else if (contentType.equalsIgnoreCase("JSON")) {
            content = ContentType.JSON.toString();
        }
        responseSpecification = new ResponseSpecBuilder()
                .expectStatusCode(code)
                .expectContentType(content).build();
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
