package org.example.service;

import io.qameta.allure.Param;
import io.restassured.response.Response;

import static io.qameta.allure.model.Parameter.Mode.MASKED;
import static io.restassured.RestAssured.given;

public class GetOrdersApi extends BaseApi {

    public static final String GET_ORDERS_ENDPOINT = String.format("%s/api/orders", BASE_ENDPOINT);

    public Response getOrdersOfUser( @Param(mode=MASKED) String authToken) {
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
