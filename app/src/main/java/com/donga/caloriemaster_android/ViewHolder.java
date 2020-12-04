package com.donga.caloriemaster_android;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ViewHolder extends RecyclerView.ViewHolder {

    ImageView iv_picture;
    TextView tv_name;
    TextView tv_kcal;

    public ViewHolder (@NonNull View itemView) {
        super(itemView);
        this.iv_picture = itemView.findViewById(R.id.iv_picture);
        this.tv_name = itemView.findViewById(R.id.tv_name);
        this.tv_kcal = itemView.findViewById(R.id.tv_kcal);
    }

    public void setItem(cooking item){

    }
}