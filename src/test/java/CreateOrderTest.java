import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.example.model.IngredientsResponse;
import org.example.model.Order;
import org.example.service.CreateOrderApi;
import org.example.service.GetIngredientsApi;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;

@DisplayName("Создание заказа")
public class CreateOrderTest extends TestBase {

    CreateOrderApi api = new CreateOrderApi();

    GetIngredientsApi getIngredientsApi = new GetIngredientsApi();

    Order order;

    IngredientsResponse allIngredients;

    String authToken;
    @Before
    @Step("Получение ингредиентов")
    public void getIngredients(){
        allIngredients = getIngredientsApi.getIngredients().as(IngredientsResponse.class);
    }

    @Test
    public void successIsTrueWhenUserCreatesOrderWithBun(){

    }


    @Test
    public void successIsTrueWhenUserCreatesOrderWithFilling(){

    }

    @Test
    public void successIsTrueWhenUserCreatesOrderWithSauce(){

    }

    @Test
    public void successIsTrueWhenUserCreatesOrderWithAllTypesOfIngredients(){
        order = new Order();
        order.addRandomSauce(allIngredients);
        order.addRandomBun(allIngredients);
        order.addRandomFilling(allIngredients);

        Response response = api.createOrder(authToken, order);

        response.then()
                .statusCode(200)
                .and()
                .body("success", is(true));
    }
    @Test
    public void successIsTrueWhenGuestCreatesOrderWithBun(){

    }
    @Test
    public void successIsTrueWhenGuestCreatesOrderWithFilling(){

    }

    @Test
    public void successIsTrueWhenGuestCreatesOrderWithSauce(){

    }

    @Test
    public void successIsTrueWhenGuestCreatesOrderWithAllTypesOfIngredients(){

    }



    @Test
    public void cannotCreateOrderWithoutIngredients(){

    }

    @Test
    public void cannotCreateOrderWithIncorrectIngredientHash(){


    }
}

