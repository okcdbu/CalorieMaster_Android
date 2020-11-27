package com.donga.caloriemaster_android;

public class cooking {

    String name;
    String nutritionFacts;
    String ingredients;

    public cooking(String name, String nutritionFacts, String ingredients) {
        this.name = name;
        this.nutritionFacts = nutritionFacts;
        this.ingredients = ingredients;
    }

    public cooking(){}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNutritionFacts() {
        return nutritionFacts;
    }

    public void setNutritionFacts(String nutritionFacts) {
        this.nutritionFacts = nutritionFacts;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }
}


