package org.example.service;

import io.qameta.allure.Param;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.example.model.PostOrderRequest;

import static io.qameta.allure.model.Parameter.Mode.MASKED;
import static io.restassured.RestAssured.given;

public class CreateOrderApi extends BaseApi {

    public static final String CREATE_ORDER_ENDPOINT = String.format("%s/api/orders", BASE_ENDPOINT);

    @Step("Запрос на создание заказа")
    public Response createOrder(@Param(mode=MASKED) String authToken, PostOrderRequest order) {
        if (authToken.length() > 0) {
            return given()
                    .spec(SPEC)
                    .header("Authorization", authToken)
                    .body(order)
                    .when()
                    .post(CREATE_ORDER_ENDPOINT);
        } else {
            return given()
                    .spec(SPEC)
                    .body(order)
                    .when()
                    .post(CREATE_ORDER_ENDPOINT);
        }
    }
}
