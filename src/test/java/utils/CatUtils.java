package utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.kohsuke.rngom.binary.Pattern;
import pojo.Root;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.util.*;

public class CatUtils {
    private String baseUri = "https://api.thecatapi.com";
    public RequestSpecification requestSpecification;
    public RequestSpecification requestSpecBuilder;
    public ResponseSpecification responseSpecification;
    static Properties properties;
    public Response response;
    public Map<String, String> map;
    public JsonPath js;
    public ObjectMapper objectMapper;
    FileInputStream fis;
    static public List<Root> objects;

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
    
    public List<Root> deserializeMyResponse(Response response) {
        objectMapper = new ObjectMapper();
        List<Root> objects = Collections.<Root>emptyList();
        try {
            objects = objectMapper.readValue(
                    response.asString(),
                    TypeFactory.defaultInstance().constructCollectionType(List.class, Root.class)
            );
        }catch (JsonProcessingException e){
            e.printStackTrace();
        }
        return objects;
    }

}
