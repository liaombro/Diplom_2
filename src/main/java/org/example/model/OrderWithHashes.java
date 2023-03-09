package org.example.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OrderWithHashes extends Order {
    @SerializedName("ingredients")
    List<String> ingredientHashes;
    public List<String> getIngredientHashes() {

        return ingredientHashes;
    }

    public void setIngredientHashes(List<String> ingredientHashes) {
        this.ingredientHashes = ingredientHashes;
    }

    @Override
    public void setIngredients(List<Ingredient> ingredients) {
        assert false;
    }

    public String toString() {
        return String.format("Order with parameters: ingredients=%s, name=%s, status=%s, number=%s", ingredientHashes, name, status, number);
    }
}
