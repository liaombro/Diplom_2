package org.example.service;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;

public class BaseApi {
    public static final String BASE_ENDPOINT = "https://stellarburgers.nomoreparties.site";

    public static final RequestSpecification SPEC  = new RequestSpecBuilder()
            .addHeader("Content-Type", "application/json")
            .setBaseUri(BASE_ENDPOINT)
            .build();


}

