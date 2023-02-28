package org.example.service;

import io.qameta.allure.Step;

import static io.restassured.RestAssured.given;

public class DeleteUserApi extends BaseApi {

    public static final String DELETE_USER_ENDPOINT = String.format("%s/api/auth/user", BASE_ENDPOINT);

    @Step("Запрос на удаление пользователя")
    public void deleteUser(String authToken){
            given()
                    .spec(SPEC)
                    .header("Authorization", "Bearer " + authToken)
                    .when()
                    .delete(DELETE_USER_ENDPOINT);
    }
}
