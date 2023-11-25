package org.example.metods;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class Specification {
    public final static String URL = "https://stellarburgers.nomoreparties.site/";
    public static RequestSpecification requestSpec(){
        return new RequestSpecBuilder()
                .setBaseUri(URL)
                .setContentType(ContentType.JSON)
                .build();
    }
}
