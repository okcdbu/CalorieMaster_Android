package com.donga.caloriemaster_android;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class cookingAdapter extends RecyclerView.Adapter<cookingAdapter.ViewHolder> {

    private ArrayList<cooking> arrayList;
    private Context context;

    public cookingAdapter(ArrayList<cooking> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.item,parent,false);
        ViewHolder holder=new ViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Glide.with(holder.itemView)
                .load(arrayList.get(position).getPicture())
                .into(holder.iv_picture);

        holder.tv_name.setText(arrayList.get(position).getName());
        holder.tv_kcal.setText(String.valueOf(arrayList.get(position).getKcal()));
    }

    @Override
    public int getItemCount() {
        return (arrayList !=null? arrayList.size():0);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView iv_picture;
        TextView tv_name,tv_kcal;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_picture=itemView.findViewById(R.id.iv_picture);
            tv_name=itemView.findViewById(R.id.tv_name);
            tv_kcal=itemView.findViewById(R.id.tv_kcal);
        }
    }
}

