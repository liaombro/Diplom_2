import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.example.model.*;
import org.example.service.CreateOrderApi;
import org.example.service.GetIngredientsApi;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;

@DisplayName("Создание заказа")
public class CreateOrderTest extends TestBase {

    CreateOrderApi api = new CreateOrderApi();

    GetIngredientsApi getIngredientsApi = new GetIngredientsApi();

    OrderRequest order;

    IngredientsResponse allIngredients;

    public static final String NO_AUTH_TOKEN = "";

    public static final String NO_INGREDIENTS_ERROR = "Ingredient ids must be provided";
    public static final String INCORRECT_INGREDIENTS_ERROR = "One or more ids provided are incorrect";

    @Before
    @Step("Получение ингредиентов")
    public void getIngredients() {
        allIngredients = getIngredientsApi.getIngredients().as(IngredientsResponse.class);
    }

    private OrderResponse prepareExpectedResponse(OrderRequest order) {
        OrderResponse expectedResponse = new OrderResponse();
        List<String> ingredientHashes = order.getIngredients();
        expectedResponse.setIngredientHashes(ingredientHashes);
        expectedResponse.setSuccess(true);
        return expectedResponse;
    }

    @Test
    @DisplayName("Авторизованный пользователь может создать заказ с 1 булкой")
    public void successIsTrueWhenUserCreatesOrderWithBun() {
        order = new OrderRequest();
        order.addRandomBun(allIngredients);
        OrderResponse expectedResponse = prepareExpectedResponse(order);

        Response response = api.createOrder(authToken, order);
        OrderResponse actualResponse = response.as(OrderResponse.class);

        response.then().statusCode(200);
        OrderResponse.assertEqualResponsesByIngredientsAndSuccess(actualResponse, expectedResponse);
    }


    @Test
    @DisplayName("Авторизованный пользователь может создать заказ с 1 начинкой")
    public void successIsTrueWhenUserCreatesOrderWithFilling() {
        order = new OrderRequest();
        order.addRandomFilling(allIngredients);
        OrderResponse expectedResponse = prepareExpectedResponse(order);

        Response response = api.createOrder(authToken, order);
        OrderResponse actualResponse = response.as(OrderResponse.class);

        response.then().statusCode(200);
        OrderResponse.assertEqualResponsesByIngredientsAndSuccess(actualResponse, expectedResponse);

    }

    @Test
    @DisplayName("Авторизованный пользователь может создать заказ с 1 соусом")
    public void successIsTrueWhenUserCreatesOrderWithSauce() {
        order = new OrderRequest();
        order.addRandomSauce(allIngredients);
        OrderResponse expectedResponse = prepareExpectedResponse(order);

        Response response = api.createOrder(authToken, order);
        OrderResponse actualResponse = response.as(OrderResponse.class);

        response.then().statusCode(200);
        OrderResponse.assertEqualResponsesByIngredientsAndSuccess(actualResponse, expectedResponse);

    }

    @Test
    @DisplayName("Авторизованный пользователь может создать заказ со всеми видами ингредиентов")

    public void correctIngredientListIsInResponseWhenUserCreatesOrderWithAllTypesOfIngredients() {
        order = new OrderRequest();
        order.addRandomSauce(allIngredients);
        order.addRandomBun(allIngredients);
        order.addRandomFilling(allIngredients);
        OrderResponse expectedResponse = prepareExpectedResponse(order);

        Response response = api.createOrder(authToken, order);
        OrderResponse actualResponse = response.as(OrderResponse.class);

        response.then()
                .statusCode(200);
        OrderResponse.assertEqualResponsesByIngredientsAndSuccess(actualResponse, expectedResponse);
    }

    @Test
    @DisplayName("Неавторизованный пользователь может создать заказ с 1 булкой")
    public void successIsTrueWhenGuestCreatesOrderWithBun() {
        order = new OrderRequest();
        order.addRandomBun(allIngredients);
        OrderResponse expectedResponse = prepareExpectedResponse(order);

        Response response = api.createOrder(NO_AUTH_TOKEN, order);
        OrderResponse actualResponse = response.as(OrderResponse.class);

        response.then().statusCode(200);
        OrderResponse.assertEqualResponsesBySuccess(actualResponse, expectedResponse);

    }

    @Test
    @DisplayName("Неавторизованный пользователь может создать заказ с 1 начинкой")

    public void successIsTrueWhenGuestCreatesOrderWithFilling() {
        order = new OrderRequest();
        order.addRandomFilling(allIngredients);
        OrderResponse expectedResponse = prepareExpectedResponse(order);

        Response response = api.createOrder(NO_AUTH_TOKEN, order);
        OrderResponse actualResponse = response.as(OrderResponse.class);

        response.then().statusCode(200);
        OrderResponse.assertEqualResponsesBySuccess(actualResponse, expectedResponse);

    }

    @Test
    @DisplayName("Неавторизованный пользователь может создать заказ с 1 соусом")

    public void successIsTrueWhenGuestCreatesOrderWithSauce() {
        order = new OrderRequest();
        order.addRandomSauce(allIngredients);
        OrderResponse expectedResponse = prepareExpectedResponse(order);

        Response response = api.createOrder(NO_AUTH_TOKEN, order);
        OrderResponse actualResponse = response.as(OrderResponse.class);

        response.then().statusCode(200);
        OrderResponse.assertEqualResponsesBySuccess(actualResponse, expectedResponse);


    }

    @Test
    @DisplayName("Неавторизованный пользователь может создать заказ со всеми видами ингредиентов")

    public void successIsTrueWhenGuestCreatesOrderWithAllTypesOfIngredients() {
        order = new OrderRequest();
        order.addRandomSauce(allIngredients);
        order.addRandomBun(allIngredients);
        order.addRandomFilling(allIngredients);
        OrderResponse expectedResponse = prepareExpectedResponse(order);

        Response response = api.createOrder(NO_AUTH_TOKEN, order);
        OrderResponse actualResponse = response.as(OrderResponse.class);

        response.then()
                .statusCode(200);
        OrderResponse.assertEqualResponsesBySuccess(actualResponse, expectedResponse);

    }


    @Test
    @DisplayName("Показывается сообщение об ошибке, если в запросе не указаны ингредиенты")
    public void cannotCreateOrderWithoutIngredients() {
        order = new OrderRequest();
        ErrorMessage expectedResponse = new ErrorMessage(false, NO_INGREDIENTS_ERROR);

        Response response = api.createOrder(authToken, order);
        ErrorMessage actualResponse = response.as(ErrorMessage.class);

        response.then().statusCode(400);
        ErrorMessage.assertEqualResponses(actualResponse, expectedResponse);
    }

    @Test
    @DisplayName("Показывается сообщение об ошибке, если в запросе указан несуществующий ингредиент")
    public void cannotCreateOrderWithIncorrectIngredientHash() {
        order = new OrderRequest();
        order.addIncorrectIngredient();
        ErrorMessage expectedResponse = new ErrorMessage(false, INCORRECT_INGREDIENTS_ERROR);

        Response response = api.createOrder(authToken, order);
        ErrorMessage actualResponse = response.as(ErrorMessage.class);

        response.then().statusCode(400);
        ErrorMessage.assertEqualResponses(actualResponse, expectedResponse);


    }

    @Test
    @DisplayName("В теле ответа передается номер заказа")
    public void orderNumberIsNotNullInResponseBody() {
        order = new OrderRequest();
        order.addRandomBun(allIngredients);

        Response response = api.createOrder(authToken, order);

        response.then()
                .statusCode(200)
                .and()
                .body("order.number", notNullValue());

    }
    @Test
    @DisplayName("В теле ответа передается имя заказа")
    public void orderNameIsNotNullInResponseBody(){
        order = new OrderRequest();
        order.addRandomBun(allIngredients);

        Response response = api.createOrder(authToken, order);

        response.then()
                .statusCode(200)
                .and()
                .body("order.name", notNullValue());
    }
    @Test
    @DisplayName("В теле ответа передается статус заказа, если заказ сделан авторизованным пользователем")
    public void orderStatusIsNotNullInResponseBodyWhenUserMakesOrder(){
        order = new OrderRequest();
        order.addRandomBun(allIngredients);

        Response response = api.createOrder(authToken, order);

        response.then()
                .statusCode(200)
                .and()
                .body("order.status", notNullValue());
    }
    @Test
    @DisplayName("В теле ответа передается статус заказа, если заказ сделан авторизованным пользователем")
    public void orderPriceIsNotNullInResponseBodyWhenUserMakesOrder(){
        order = new OrderRequest();
        order.addRandomBun(allIngredients);

        Response response = api.createOrder(authToken, order);

        response.then()
                .statusCode(200)
                .and()
                .body("order.price", notNullValue());
    }

    @Test
    @DisplayName("Можно создать заказ со всеми возможными ингредиентами")
    public void correctIngredientListReturnedWhenUserCreatesOrderWithAllAvailableIngredients(){
        order = new OrderRequest();
        order.addAllIngredients(allIngredients);
        OrderResponse expectedResponse = prepareExpectedResponse(order);

        Response response = api.createOrder(authToken, order);
        OrderResponse actualResponse = response.as(OrderResponse.class);

        response.then().statusCode(200);
        OrderResponse.assertEqualResponsesByIngredientsAndSuccess(expectedResponse, actualResponse);

    }
}

