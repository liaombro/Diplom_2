import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.example.model.*;
import org.example.service.CreateOrderApi;
import org.example.service.GetIngredientsApi;
import org.example.service.GetOrdersApi;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

@DisplayName("Получение списка заказов")
public class GetOrdersOfUserTest extends TestBase {

    OrderWithHashes order;
    IngredientsResponse allIngredients;

    GetIngredientsApi getIngredientsApi = new GetIngredientsApi();

    CreateOrderApi createOrderApi = new CreateOrderApi();

    GetOrdersApi api = new GetOrdersApi();

    @Step("Получение ингредиентов")
    public void getIngredients() {
        allIngredients = getIngredientsApi.getIngredients().as(IngredientsResponse.class);
    }

    public void setupOrderField(OrderWithObjects order) {
        List<String> ingredientHashes = new LinkedList<>();
        for (Ingredient ingredient : order.getIngredients()) {
            ingredientHashes.add(ingredient.get_id());
        }
        OrderWithHashes orderWithHashes = new OrderWithHashes();

        orderWithHashes.setIngredientHashes(ingredientHashes);
        orderWithHashes.set_id(order.get_id());
        orderWithHashes.setName(order.getName());
        orderWithHashes.setNumber(order.getNumber());
        orderWithHashes.setStatus(order.getStatus());
        orderWithHashes.setUpdatedAt(order.getUpdatedAt());
        orderWithHashes.setCreatedAt(order.getCreatedAt());

        this.order = orderWithHashes;
    }

    @Before
    @Step("Создание заказа")
    public void createOrder() {
        getIngredients();
        PostOrderRequest orderRequest = new PostOrderRequest();
        orderRequest.addRandomBun(allIngredients);

        Response response = createOrderApi.createOrder(authToken, orderRequest);
        PostOrderResponse responseObject = response.as(PostOrderResponse.class);
        setupOrderField(responseObject.getOrder());
    }


    @Test
    @DisplayName("Показывается сообщение об ошибке, если не передан авторизационный токен")
    public void cannotGetOrdersWithoutAuthorization() {
        ErrorMessage expectedResponse = prepareExpectedErrorResponse(UNAUTHORIZED_ERROR);

        Response response = api.getOrdersOfUser(NO_AUTH_TOKEN);
        ErrorMessage actualResponse = response.as(ErrorMessage.class);

        ErrorMessage.assertEqualResponses(expectedResponse, actualResponse);
    }

    @Test
    @DisplayName("Созданный заказ отображается в списке заказов вместе с содержимым")
    public void ordersListShowsCreatedOrderWithIngredients() {
        GetOrderResponse expectedResponse = new GetOrderResponse();
        expectedResponse.setOrders(List.of(order));
        expectedResponse.setSuccess(true);

        Response response = api.getOrdersOfUser(authToken);
        GetOrderResponse actualResponse = response.as(GetOrderResponse.class);

        response.then().statusCode(200);
        GetOrderResponse.assertEqualComparingField(expectedResponse, actualResponse, "orders.ingredients");
    }

    @Test
    @DisplayName("Созданный заказ отображается в списке заказов вместе с номером")
    public void ordersListShowsCreatedOrderWithNumber() {
        GetOrderResponse expectedResponse = new GetOrderResponse();
        expectedResponse.setOrders(List.of(order));
        expectedResponse.setSuccess(true);

        Response response = api.getOrdersOfUser(authToken);
        GetOrderResponse actualResponse = response.as(GetOrderResponse.class);

        response.then().statusCode(200);
        GetOrderResponse.assertEqualComparingField(expectedResponse, actualResponse, "orders.number");
    }

    @Test
    @DisplayName("Созданный заказ отображается в списке заказов вместе с именем")
    public void ordersListShowsCreatedOrderWithName() {
        GetOrderResponse expectedResponse = new GetOrderResponse();
        expectedResponse.setOrders(List.of(order));
        expectedResponse.setSuccess(true);

        Response response = api.getOrdersOfUser(authToken);
        GetOrderResponse actualResponse = response.as(GetOrderResponse.class);

        response.then().statusCode(200);
        GetOrderResponse.assertEqualComparingField(expectedResponse, actualResponse, "orders.name");
    }

    @Test
    @DisplayName("Созданный заказ отображается в списке заказов вместе со статусом")
    public void ordersListShowsCreatedOrderWithStatus() {
        GetOrderResponse expectedResponse = new GetOrderResponse();
        expectedResponse.setOrders(List.of(order));
        expectedResponse.setSuccess(true);

        Response response = api.getOrdersOfUser(authToken);
        GetOrderResponse actualResponse = response.as(GetOrderResponse.class);

        response.then().statusCode(200);
        GetOrderResponse.assertEqualComparingField(expectedResponse, actualResponse, "orders.status");
    }

    @Test
    @DisplayName("Созданный заказ отображается в списке заказов вместе с датами создания и обновления")
    public void ordersListShowsCreatedOrderWithDates() {
        GetOrderResponse expectedResponse = new GetOrderResponse();
        expectedResponse.setOrders(List.of(order));
        expectedResponse.setSuccess(true);

        Response response = api.getOrdersOfUser(authToken);
        GetOrderResponse actualResponse = response.as(GetOrderResponse.class);

        response.then().statusCode(200);
        GetOrderResponse.assertEqualComparingField(expectedResponse, actualResponse, "orders.createdAt");
        GetOrderResponse.assertEqualComparingField(expectedResponse, actualResponse, "orders.updatedAt");
    }

    @Test
    @DisplayName("Поле total увеличивается на 1 при совершении заказа")
    public void totalFieldIsIncrementingAfterOrderIsMade() {

        Response response1 = api.getOrdersOfUser(authToken);
        int expectedTotal =(Integer) response1.then().extract().path("total") + 1;

        createOrder();
        Response response2 = api.getOrdersOfUser(authToken);
        int actualTotal = response2.then().extract().path("total");

        Assert.assertEquals("Поле total должно увеличиться на 1 после совершения заказа", expectedTotal, actualTotal);
    }


    @Test
    @DisplayName("Поле totalToday увеличивается на 1 при совершении заказа")
    public void totalTodayFieldIsIncrementingAfterOrderIsMade() {

        Response response1 = api.getOrdersOfUser(authToken);
        int expectedTotal = (Integer) response1.then().extract().path("totalToday") + 1;

        createOrder();
        Response response2 = api.getOrdersOfUser(authToken);
        int actualTotal = response2.then().extract().path("totalToday");

        Assert.assertEquals("Поле todayTotal должно увеличиться на 1 после совершения заказа", expectedTotal, actualTotal);
    }


    @Test
    @DisplayName("Эндпойнт возвращает не более 50 заказов")
    public void noMoreThan50OrdersAreReturned(){
        int expectedSize = 50;
        for (int i = 0; i < 1 + expectedSize; i++){
            createOrder();
        }

        Response response = api.getOrdersOfUser(authToken);
        GetOrderResponse actualResponse = response.as(GetOrderResponse.class);
        int actualSize = actualResponse.getOrders().size();

        Assert.assertEquals("Метод должен возвращать не более 50 заказов " + authToken, expectedSize, actualSize);

    }
}

