package org.example.model;

import org.apache.commons.lang3.RandomStringUtils;

import java.util.LinkedList;
import java.util.List;

public class OrderRequest {


    List<String> ingredients;
    public static final String BUN = "bun";
    public static final String MAIN = "main";
    public static final String SAUCE = "sauce";

    public OrderRequest(List<Ingredient> ingredients) {
        for (Ingredient ingredient : ingredients) {
            this.ingredients.add(ingredient.get_id());
        }
    }

    public OrderRequest() {
        this.ingredients = new LinkedList<>();
    }

    public void addRandomBun(IngredientsResponse allIngredients) {
        for (Ingredient ingredient : allIngredients.getData()) {
            String type = ingredient.getType();

            if (type.equals(BUN)) {
                String ingredientHash = ingredient.get_id();
                ingredients.add(ingredientHash);
                break;
            }
        }
    }

    public void addRandomFilling(IngredientsResponse allIngredients) {
        for (Ingredient ingredient : allIngredients.getData()) {
            String type = ingredient.getType();

            if (type.equals(MAIN)) {
                String ingredientHash = ingredient.get_id();
                ingredients.add(ingredientHash);
                break;
            }
        }
    }

    public void addRandomSauce(IngredientsResponse allIngredients) {
        for (Ingredient ingredient : allIngredients.getData()) {
            String type = ingredient.getType();

            if (type.equals(SAUCE)) {
                String ingredientHash = ingredient.get_id();
                ingredients.add(ingredientHash);
                break;
            }
        }
    }

    public void addIncorrectIngredient() {
        String ingredientHash = RandomStringUtils.randomNumeric(24);
        ingredients.add(ingredientHash);
    }

    public void addAllIngredients(IngredientsResponse allIngredients) {
        for (Ingredient ingredient : allIngredients.getData()) {
            String ingredientHash = ingredient.get_id();
            ingredients.add(ingredientHash);
        }
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }

    @Override
    public String toString() {
        return String.format("OrderRequest with parameters: ingredients=%s", ingredients);
    }
}
