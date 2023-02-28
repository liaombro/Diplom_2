package org.example.service;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.example.model.User;

import static io.restassured.RestAssured.given;

public class UpdateUserApi extends BaseApi {


    public static final String UPDATE_USER_ENDPOINT = String.format("%s/api/auth/user", BASE_ENDPOINT);


    private Response patchRequest(String authToken, User user) {
        if (authToken.length() > 0) {
            return given()
                    .spec(SPEC)
                    .header("Authorization", authToken)
                    .body(user)
                    .when()
                    .patch(UPDATE_USER_ENDPOINT);
        } else {
            return given()
                    .spec(SPEC)
                    .body(user)
                    .when()
                    .patch(UPDATE_USER_ENDPOINT);
        }
    }

    @Step("Запрос на изменение имени")
    public Response changeName(String authToken, User user) {
        User clonedUser = user.copy();
        clonedUser.setPassword(null);
        clonedUser.setEmail(null);

        return patchRequest(authToken, clonedUser);
    }


    @Step("Запрос на изменение email")
    public Response changeEmail(String authToken, User user) {
        User clonedUser = user.copy();
        clonedUser.setPassword(null);
        clonedUser.setName(null);

        return patchRequest(authToken, clonedUser);
    }

    @Step("Запрос на изменение пароля")
    public Response changePassword(String authToken, User user) {
        User clonedUser = user.copy();
        clonedUser.setName(null);
        clonedUser.setEmail(null);

        return patchRequest(authToken, clonedUser);
    }

}
