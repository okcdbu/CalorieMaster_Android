package com.donga.caloriemaster_android;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<cooking> arrayList;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView=findViewById(R.id.RecyclerView);
        recyclerView.setHasFixedSize(true);//리사이클러뷰 기존 성능 강화

        layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        arrayList=new ArrayList<>(); //cooking객체를 담을 어레이 리스트(어댑터 쪽으로)

        database=FirebaseDatabase.getInstance();//파이어베이스 데이터베이스 연동

        databaseReference=database.getReference("cooking"); //DB테이블 연결
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //실제적으로 파이어베이스 데이터베이스의 데이터를 받아오는 곳

                arrayList.clear(); //기존 배열리스트가 존재하지않도록 초기화

                for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                    //반복문으로 데이터 List를 추출해냄
                    cooking cooking_=snapshot.getValue(cooking.class);//만들어뒀던 cooking 객체에 데이터를 담음
                    arrayList.add(cooking_);//담은 데이터들을 배열리스트에 넣고 리사이클러뷰로 보낼 준비

                }

                adapter.notifyDataSetChanged();//리스트 저장 및 새로고침

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                //디비 가져오다가 에러발생시
                Log.e("MainActivity",String.valueOf(databaseError.toException()));

            }
        });

        adapter=new cookingAdapter(arrayList,this);
        recyclerView.setAdapter(adapter); //리사이클러뷰에 어댑터 연결
    }
}