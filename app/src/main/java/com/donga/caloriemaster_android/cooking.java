package com.donga.caloriemaster_android;

public class cooking {

    private String picture;
    private String name;
    private int kcal;
    private int carb;
    private int protein;
    private int fat;

    public int getProtein() {
        return protein;
    }

    public void setProtein(int protein) {
        this.protein = protein;
    }

    public int getFat() {
        return fat;
    }

    public void setFat(int fat) {
        this.fat = fat;
    }


    public int getCarb() {
        return carb;
    }

    public void setCarb(int carb) {
        this.carb = carb;
    }

    public cooking(){}

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getKcal() {
        return kcal;
    }

    public void setKcal(int kcal) {
        this.kcal = kcal;
    }
}


