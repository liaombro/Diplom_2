import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.example.model.ErrorMessage;
import org.example.model.User;
import org.example.model.UserResponse;
import org.example.service.LoginUserApi;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.notNullValue;

@DisplayName("Авторизация пользователя")
public class LoginUserTest extends TestBase {

    private LoginUserApi api = new LoginUserApi();


    public static final String LOGIN_DATA_INCORRECT = "email or password are incorrect";

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
    @DisplayName("При авторизации пользователя в ответе передается accessToken")
    public void authTokenIsPresentInResponseWhenUserLogsIn() {

        Response response = api.logUserIn(user);

        response.then()
                .statusCode(200)
                .and()
                .body("accessToken", notNullValue());
    }

    @Test
    @DisplayName("При авторизации пользователя в ответе передается refreshToken")
    public void refreshTokenIsPresentInResponseWhenUserLogsIn() {
        Response response = api.logUserIn(user);

        response.then()
                .statusCode(200)
                .and()
                .body("refreshToken", notNullValue());
    }

    @Test
    @DisplayName("При авторизации пользователя в ответе передается success = true")
    public void successFieldIsTrueInResponseWhenUserLogsIn() {
        UserResponse expectedResponse = prepareExpectedResponse(user);

        Response response = api.logUserIn(user);
        UserResponse actualResponse = response.as(UserResponse.class);

        response.then()
                .statusCode(200);
        UserResponse.assertEqualResponses(actualResponse, expectedResponse);
    }

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Показывается сообщение об ошибке, если пароль неверный")
    public void cannotLoginWithIncorrectPassword() {
        String email = user.getEmail();
        User user2 = new User(true);
        user2.setEmail(email);
        ErrorMessage expectedResponse = prepareExpectedErrorResponse(LOGIN_DATA_INCORRECT);

        Response response = api.logUserIn(user2);
        ErrorMessage actualResponse = response.as(ErrorMessage.class);

        response.then()
                .statusCode(401);
        ErrorMessage.assertEqualResponses(actualResponse, expectedResponse);
    }

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Показывается сообщение об ошибке, если email неверный")
    public void cannotLoginWithIncorrectEmail() {
        String password = user.getPassword();
        User user2 = new User(true);
        user2.setPassword(password);
        ErrorMessage expectedResponse = prepareExpectedErrorResponse(LOGIN_DATA_INCORRECT);

        Response response = api.logUserIn(user2);
        ErrorMessage actualResponse = response.as(ErrorMessage.class);

        response.then()
                .statusCode(401);
        ErrorMessage.assertEqualResponses(actualResponse, expectedResponse);

    }
}
