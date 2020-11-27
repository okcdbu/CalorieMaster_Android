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
        //실제 리스트뷰가 어댑터에 연결된 다음에 뷰홀더를 최초로 만들어냄

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item,parent,false);
        ViewHolder holder=new ViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //실질적으로 매칭시켜주는 역할
        Glide.with(holder.itemView).load(arrayList.get(position).getPicture())
                .into(holder.iv_picture);

        holder.tv_name.setText(arrayList.get(position).getName());
        holder.tv_kcal.setText(String.valueOf(arrayList.get(position).getKcal()));


    }

    @Override
    public int getItemCount() {
        return (arrayList!=null? arrayList.size():0);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView iv_picture;
        TextView tv_name;
        TextView tv_kcal;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.iv_picture=itemView.findViewById(R.id.iv_picture);
            this.tv_name=itemView.findViewById(R.id.tv_name);
            this.tv_kcal=itemView.findViewById(R.id.tv_kcal);
        }
    }
}

