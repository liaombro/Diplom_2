package org.example.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OrderWithObjects extends Order {

    @SerializedName("ingredients")
    List<Ingredient> ingredients;

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    @Override
    public void setIngredientHashes(List<String> ingredientHashes) {
        assert false;
    }

    @Override
    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public String toString() {
        return String.format("Order with parameters: ingredients=%s, name=%s, status=%s, number=%s", ingredients, name, status, number);
    }
}
