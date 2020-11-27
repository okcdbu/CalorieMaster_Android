package com.donga.caloriemaster_android;

public class cooking {

    String picture;
    String name;
    String kcal;

    public cooking(String picture, String name, String kcal) {

        this.picture=picture;
        this.name = name;
        this.kcal=kcal;

    }

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

    public String getKcal() {
        return kcal;
    }

    public void setKcal(String kcal) {
        this.kcal = kcal;
    }
}


