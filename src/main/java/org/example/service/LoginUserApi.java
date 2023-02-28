package org.example.service;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.example.model.User;

import static io.restassured.RestAssured.given;

public class LoginUserApi extends BaseApi {

    public static final String USER_LOGIN_ENDPOINT = String.format("%s/api/auth/login", BASE_ENDPOINT);

    @Step("Запрос на авторизацию пользователя")
    public Response logUserIn(User user) {
        user.setName(null);

        return given()
                .spec(SPEC)
                .body(user)
                .when()
                .post(USER_LOGIN_ENDPOINT);
    }
}
