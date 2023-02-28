package org.example.model;

import org.apache.commons.lang3.RandomStringUtils;

import java.util.LinkedList;
import java.util.List;

public class Order {

    List<String> ingredients;

    public static final String BUN = "bun";
    public static final String MAIN = "main";
    public static final String SAUCE = "sauce";

    public Order(List<Ingredient> ingredients) {
        for (Ingredient ingredient : ingredients) {
            this.ingredients.add(ingredient.get_id());
        }
    }

    public Order() {
        this.ingredients = new LinkedList<>();
    }

    public void addRandomBun(IngredientsResponse allIngredients) {
        for (Ingredient ingredient : allIngredients.getData()) {
            String type = ingredient.getType();

            if (type.equals(BUN)) {
                String ingredientHash = ingredient.get_id();
                ingredients.add(ingredientHash);
            }
        }
    }

    public void addRandomFilling(IngredientsResponse allIngredients) {
        for (Ingredient ingredient : allIngredients.getData()) {
            String type = ingredient.getType();

            if (type.equals(MAIN)) {
                String ingredientHash = ingredient.get_id();
                ingredients.add(ingredientHash);
            }
        }
    }

    public void addRandomSauce(IngredientsResponse allIngredients) {
        for (Ingredient ingredient : allIngredients.getData()) {
            String type = ingredient.getType();

            if (type.equals(SAUCE)) {
                String ingredientHash = ingredient.get_id();
                ingredients.add(ingredientHash);
            }
        }
    }

    public void addIncorrectIngredient() {
        String ingredientHash = RandomStringUtils.randomNumeric(24);
        ingredients.add(ingredientHash);
    }

}
