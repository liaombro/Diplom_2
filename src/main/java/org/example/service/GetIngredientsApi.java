package org.example.service;

import io.qameta.allure.Step;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class GetIngredientsApi extends BaseApi {

    public static final String GET_INGREDIENTS_ENDPOINT = String.format("%s/api/ingredients", BASE_ENDPOINT);


    @Step("Запрос на получение списка ингредиентов")
    public Response getIngredients(){
        return given()
                .spec(SPEC)
                .when()
                .get(GET_INGREDIENTS_ENDPOINT);
    }
}
