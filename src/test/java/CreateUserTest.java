import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.example.model.ErrorMessage;
import org.example.model.User;
import org.example.model.UserResponse;
import org.example.service.CreateUserApi;
import org.example.service.DeleteUserApi;
import org.junit.After;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.hamcrest.CoreMatchers.notNullValue;

@DisplayName("Регистрация пользователя")
public class CreateUserTest {

    CreateUserApi api = new CreateUserApi();
    List<String> authTokens = new ArrayList<>();

    DeleteUserApi deleteApi = new DeleteUserApi();

    public static final String USER_ALREADY_EXISTS_ERROR = "User already exists";
    public static final String REQUIRED_FIELD_MISSING_ERROR = "Email, password and name are required fields";

    @After
    @Step("Удаление пользователя")
    public void deleteUser() {
        for (String authToken : authTokens) {
            deleteApi.deleteUser(authToken);
        }
    }

    @Step("Сохранение accessToken")
    public void saveAuthToken(Response response) {
        String authToken = response
                .then()
                .extract()
                .path("accessToken");
        authTokens.add(authToken);
    }

    public UserResponse prepareExpectedResponse(User user) {
        UserResponse response = new UserResponse();
        response.setSuccess(true);
        User userWithoutPassword = user.copy().setPassword(null);
        response.setUser(userWithoutPassword);
        return response;
    }

    public ErrorMessage prepareExpectedErrorResponse(String message) {
        return new ErrorMessage(false, message);
    }

    @Test
    @DisplayName("При создании пользователя в ответе передается accessToken")
    public void authTokenIsPresentInResponseWhenUserIsCreated() {
        User user = new User(true);

        Response response = api.registerUser(user);
        saveAuthToken(response);

        response.then()
                .statusCode(200)
                .and()
                .body("accessToken", notNullValue());
    }

    @Test
    @DisplayName("При создании пользователя в теле ответа передается refreshToken")
    public void refreshTokenIsPresentInResponseWhenUserIsCreated() {
        User user = new User(true);

        Response response = api.registerUser(user);
        saveAuthToken(response);


        response.then()
                .statusCode(200)
                .and()
                .body("refreshToken", notNullValue());
    }

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("При создании пользователя в теле ответа передаются поля success, user")
    public void successFieldIsTrueInResponseWhenUserIsCreated() {
        User user = new User(true);
        UserResponse expectedResponse = prepareExpectedResponse(user);

        Response response = api.registerUser(user);
        saveAuthToken(response);
        UserResponse actualResponse = response.as(UserResponse.class);


        response.then()
                .statusCode(200);
        UserResponse.assertEqualResponses(actualResponse, expectedResponse);
    }

    @Test
    @DisplayName("Нельзя зарегистрировать пользователя с уже использованным email")
    public void cannotRegisterUserWithUsedEmail() {
        String email = String.format("email-%d@gmail.com", (new Random()).nextInt());
        User user1 = new User(true).setEmail(email);
        User user2 = new User(true).setEmail(email);
        Response temporaryResponse = api.registerUser(user1);
        saveAuthToken(temporaryResponse);
        ErrorMessage expectedResponse = prepareExpectedErrorResponse(USER_ALREADY_EXISTS_ERROR);

        Response response = api.registerUser(user2);
        ErrorMessage actualResponse = response.as(ErrorMessage.class);

        response.then().statusCode(403);
        ErrorMessage.assertEqualResponses(actualResponse, expectedResponse);
    }

    @Test
    @DisplayName("Показывается сообщение об ошибке, если поле email пусто")
    public void cannotRegisterWithEmptyEmail() {
        User user = new User(true).setEmail("");
        ErrorMessage expectedResponse = prepareExpectedErrorResponse(REQUIRED_FIELD_MISSING_ERROR);

        Response response = api.registerUser(user);
        saveAuthToken(response);
        ErrorMessage actualResponse = response.as(ErrorMessage.class);

        response.then()
                .statusCode(403);
        ErrorMessage.assertEqualResponses(actualResponse, expectedResponse);
    }

    @Test
    @DisplayName("Показывается сообщение об ошибке, если не передано поле email")
    public void cannotRegisterWithoutEmail() {
        User user = new User(true).setEmail(null);
        ErrorMessage expectedResponse = prepareExpectedErrorResponse(REQUIRED_FIELD_MISSING_ERROR);

        Response response = api.registerUser(user);
        saveAuthToken(response);
        ErrorMessage actualResponse = response.as(ErrorMessage.class);

        response.then()
                .statusCode(403);
        ErrorMessage.assertEqualResponses(actualResponse, expectedResponse);
    }

    @Test
    @DisplayName("Показывается сообщение об ошибке, если поле password пусто")
    public void cannotRegisterWithEmptyPassword() {
        User user = new User(true).setPassword("");
        ErrorMessage expectedResponse = prepareExpectedErrorResponse(REQUIRED_FIELD_MISSING_ERROR);

        Response response = api.registerUser(user);
        saveAuthToken(response);
        ErrorMessage actualResponse = response.as(ErrorMessage.class);

        response.then()
                .statusCode(403);
        ErrorMessage.assertEqualResponses(actualResponse, expectedResponse);

    }

    @Test
    @DisplayName("Показывается сообщение об ошибке, если не передано поле password")
    public void cannotRegisterWithoutPassword() {
        User user = new User(true).setPassword(null);
        ErrorMessage expectedResponse = prepareExpectedErrorResponse(REQUIRED_FIELD_MISSING_ERROR);

        Response response = api.registerUser(user);
        saveAuthToken(response);
        ErrorMessage actualResponse = response.as(ErrorMessage.class);

        response.then()
                .statusCode(403);
        ErrorMessage.assertEqualResponses(actualResponse, expectedResponse);
    }


    @Test
    @DisplayName("Показывается сообщение об ошибке, если поле name пусто")
    public void cannotRegisterWithEmptyName() {
        User user = new User(true).setName("");
        ErrorMessage expectedResponse = prepareExpectedErrorResponse(REQUIRED_FIELD_MISSING_ERROR);

        Response response = api.registerUser(user);
        saveAuthToken(response);
        ErrorMessage actualResponse = response.as(ErrorMessage.class);

        response.then()
                .statusCode(403);
        ErrorMessage.assertEqualResponses(actualResponse, expectedResponse);

    }

    @Test
    @DisplayName("Показывается сообщение об ошибке, если не передано поле name")
    public void cannotRegisterWithoutName() {
        User user = new User(true).setName(null);
        ErrorMessage expectedResponse = prepareExpectedErrorResponse(REQUIRED_FIELD_MISSING_ERROR);

        Response response = api.registerUser(user);
        saveAuthToken(response);
        ErrorMessage actualResponse = response.as(ErrorMessage.class);

        response.then()
                .statusCode(403);
        ErrorMessage.assertEqualResponses(actualResponse, expectedResponse);
    }

}
