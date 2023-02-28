import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.example.model.User;
import org.example.service.CreateUserApi;
import org.example.service.DeleteUserApi;
import org.junit.After;
import org.junit.Before;

public class TestBase {
    protected CreateUserApi createUserApi = new CreateUserApi();
    protected DeleteUserApi deleteUserApi = new DeleteUserApi();
    User user;
    String authToken;
    @Before
    @Step("Создание пользователя")
    public void createUser() {
        user = new User(true);
        Response response = createUserApi.registerUser(user);
        authToken = response.then().extract().path("accessToken");
    }

    @After
    @Step("Удаление пользователя")
    public void deleteUser() {
        deleteUserApi.deleteUser(authToken);
    }
}
