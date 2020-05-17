package com.leeds.jackal.data;

// Simple class for representing the request to be made to the Nutrition API.
public class RecipeRequest {
    private String query;

    public RecipeRequest(String text) {
        this.query = text;

    }
}

