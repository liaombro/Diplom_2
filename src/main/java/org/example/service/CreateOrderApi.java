package org.example.service;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.example.model.Order;
import org.example.model.OrderRequest;

import static io.restassured.RestAssured.given;

public class CreateOrderApi extends BaseApi {

    public static final String CREATE_ORDER_ENDPOINT = String.format("%s/api/orders", BASE_ENDPOINT);

    @Step("Запрос на создание заказа")
    public Response createOrder(String authToken, OrderRequest order) {
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
