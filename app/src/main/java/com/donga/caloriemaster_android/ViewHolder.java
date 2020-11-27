package com.donga.caloriemaster_android;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ViewHolder extends RecyclerView.ViewHolder {

    ImageView iv_picture;
    TextView tv_name;
    TextView tv_nutritionFacts;
    TextView tv_ingredients;

    public ViewHolder (@NonNull View itemView) {
        super(itemView);
        this.iv_picture = itemView.findViewById(R.id.iv_picture);
        this.tv_name = itemView.findViewById(R.id.tv_name);
        this.tv_nutritionFacts = itemView.findViewById(R.id.tv_nutritionFacts);
        this.tv_ingredients = itemView.findViewById(R.id.tv_ingredients);
    }

    public void setItem(cooking item){

    }
}