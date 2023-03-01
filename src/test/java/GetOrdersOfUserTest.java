import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.example.model.*;
import org.example.service.CreateOrderApi;
import org.example.service.GetIngredientsApi;
import org.example.service.GetOrdersApi;
import org.junit.Before;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

import static org.hamcrest.CoreMatchers.not;
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
        GetOrderResponse.assertEqualComparingField(expectedResponse, actualResponse,"orders.ingredients");
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
        GetOrderResponse.assertEqualComparingField(expectedResponse, actualResponse,"orders.number");
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
        GetOrderResponse.assertEqualComparingField(expectedResponse, actualResponse,"orders.name");
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
        GetOrderResponse.assertEqualComparingField(expectedResponse, actualResponse,"orders.status");
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
        GetOrderResponse.assertEqualComparingField(expectedResponse, actualResponse,"orders.createdAt");
        GetOrderResponse.assertEqualComparingField(expectedResponse, actualResponse,"orders.updatedAt");
    }


    @Test
    @DisplayName("Поле total != 0")
    public void totalFieldGreaterThanZero() {

        Response response = api.getOrdersOfUser(authToken);

        response.then().statusCode(200)
                .and()
                .body("total", not(0));
    }


    @Test
    @DisplayName("Поле totalToday != 0")
    public void totalTodayFieldIsGreaterThanZero() {
        Response response = api.getOrdersOfUser(authToken);

        response.then().statusCode(200)
                .and()
                .body("totalToday", not(0));
    }

    //Тоталы увеличиваются на 1 при создании заказа
    //

}

