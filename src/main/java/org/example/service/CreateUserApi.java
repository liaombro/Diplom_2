package org.example.service;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.example.model.User;

import static io.restassured.RestAssured.given;

public class CreateUserApi extends BaseApi {

    public static final String CREATE_USER_ENDPOINT = String.format("%s/api/auth/register", BASE_ENDPOINT);

    @Step("Запрос на регистрацию пользователя")
    public Response registerUser(User user) {
        return given()
                .spec(SPEC)
                .body(user)
                .when()
                .post(CREATE_USER_ENDPOINT);
    }

}
