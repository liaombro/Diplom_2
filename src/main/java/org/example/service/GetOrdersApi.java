package org.example.service;

import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class GetOrdersApi extends BaseApi {

    public static final String GET_ORDERS_ENDPOINT = String.format("%s/api/orders", BASE_ENDPOINT);

    public Response getOrdersOfUser(String authToken) {
        if (authToken.length() > 0) {
            return given()
                    .spec(SPEC)
                    .header("Authorization", authToken)
                    .when()
                    .get(GET_ORDERS_ENDPOINT);
        } else {
            return given()
                    .spec(SPEC)
                    .when()
                    .get(GET_ORDERS_ENDPOINT);
        }
    }
}
