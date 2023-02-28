import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.example.model.ErrorMessage;
import org.example.model.User;
import org.example.model.UserResponse;
import org.example.service.LoginUserApi;
import org.example.service.UpdateUserApi;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.notNullValue;

@DisplayName("Обновление информации о пользователе")
public class UpdateUserTest extends TestBase {

    UpdateUserApi api = new UpdateUserApi();

    LoginUserApi loginUserApi = new LoginUserApi();
    public static final String UNAUTHORIZED_ERROR = "You should be authorised";


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
    @DisplayName("При изменении имени в ответе присутствует новое значение name")
    @Severity(SeverityLevel.NORMAL)
    public void nameIsPresentInResponseWhenAuthorizedUserChangesName() {
        String expectedName = "New name";
        user.setName(expectedName);
        UserResponse expectedResponse = prepareExpectedResponse(user);

        Response response = api.changeName(authToken, user);
        UserResponse actualResponse = response.as(UserResponse.class);

        response.then()
                .statusCode(200);
        UserResponse.assertEqualResponses(expectedResponse, actualResponse);
    }

    @Test
    @DisplayName("При изменении email в ответе присутствует новое значение email")
    @Severity(SeverityLevel.NORMAL)
    public void emailIsPresentInResponseWhenAuthorizedUserChangesEmail() {
        user.setRandomEmail();
        UserResponse expectedResponse = prepareExpectedResponse(user);

        Response response = api.changeEmail(authToken, user);
        UserResponse actualResponse = response.as(UserResponse.class);

        response.then()
                .statusCode(200);
        UserResponse.assertEqualResponses(expectedResponse, actualResponse);
    }


    @Test
    @DisplayName("Пользователь может войти в систему с измененным паролем")
    @Severity(SeverityLevel.CRITICAL)
    public void userCanLoginWithChangedPassword() {
        user.setRandomPassword();
        UserResponse expectedResponse = prepareExpectedResponse(user);

        api.changePassword(authToken, user);
        Response loginResponse = loginUserApi.logUserIn(user);
        UserResponse actualResponse = loginResponse.as(UserResponse.class);

        loginResponse.then()
                .statusCode(200);
        UserResponse.assertEqualResponses(expectedResponse, actualResponse);
    }



    @Test
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Пользователь может войти в систему с измененным email")
    public void userCanLoginWithChangedEmail() {
        user.setRandomEmail();
        String expectedEmail = user.getEmail();

        api.changeEmail(authToken, user);
        Response loginResponse = loginUserApi.logUserIn(user);

        loginResponse.then()
                .statusCode(200)
                .and()
                .body("accessToken", notNullValue());
    }

    @Test
    @DisplayName("Для изменения имени требуется авторизационный токен")
    @Severity(SeverityLevel.MINOR)
    public void unauthorizedUserCannotChangeName() {
        user.setRandomName();
        ErrorMessage expectedResponse = prepareExpectedErrorResponse(UNAUTHORIZED_ERROR);

        Response response = api.changeName("", user);
        ErrorMessage actualResponse = response.as(ErrorMessage.class);

        response.then()
                .statusCode(401);
        ErrorMessage.assertEqualResponses(actualResponse, expectedResponse);


    }

    @Test
    @DisplayName("Для изменения email требуется авторизационный токен")
    @Severity(SeverityLevel.MINOR)
    public void unauthorizedUserCannotChangeEmail() {

        user.setRandomEmail();
        ErrorMessage expectedResponse = prepareExpectedErrorResponse(UNAUTHORIZED_ERROR);

        Response response = api.changeEmail("", user);
        ErrorMessage actualResponse = response.as(ErrorMessage.class);

        response.then()
                .statusCode(401);
        ErrorMessage.assertEqualResponses(actualResponse, expectedResponse);

    }

    @Test
    @DisplayName("Для изменения пароля требуется авторизационный токен")
    @Severity(SeverityLevel.MINOR)
    public void unauthorizedUserCannotChangePassword() {
        user.setRandomPassword();
        ErrorMessage expectedResponse = prepareExpectedErrorResponse(UNAUTHORIZED_ERROR);

        Response response = api.changePassword("", user);
        ErrorMessage actualResponse = response.as(ErrorMessage.class);

        response.then()
                .statusCode(401);
        ErrorMessage.assertEqualResponses(actualResponse, expectedResponse);

    }
}