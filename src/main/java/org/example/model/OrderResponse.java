package org.example.model;

import org.assertj.core.api.Assertions;

import java.util.LinkedList;
import java.util.List;

public class OrderResponse {
        boolean success;
        String name;
        Order order;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public void setIngredientHashes(List<String> ingredientHashes){
        List<Ingredient> ingredients = new LinkedList<>();
        Order order = new Order();

        for (String ingredientHash : ingredientHashes){
            Ingredient ingredient = new Ingredient();
            ingredient.set_id(ingredientHash);
            ingredients.add(ingredient);
        }
        order.setIngredients(ingredients);
        this.order = order;
    }
    public static void assertEqualResponsesByIngredientsAndSuccess(OrderResponse actual, OrderResponse expected){
        Assertions.assertThat(actual)
                .usingRecursiveComparison()
                .comparingOnlyFields("order.ingredients._id", "success")
                .isEqualTo(expected);
    }

    public static void assertEqualResponsesBySuccess(OrderResponse actual, OrderResponse expected){
        Assertions.assertThat(actual)
                .usingRecursiveComparison()
                .comparingOnlyFields("success")
                .isEqualTo(expected);

    }
    public String toString(){
        return String.format("OrderResponse with parameters: success=%s, name=%s, order=%s", success, name, order);
    }
}
