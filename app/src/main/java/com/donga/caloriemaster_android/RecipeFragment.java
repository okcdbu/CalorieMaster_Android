package com.donga.caloriemaster_android;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Queue;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RecipeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RecipeFragment extends Fragment {
    private RecyclerView recyclerView;
    private cookingAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<cooking> arrayList;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    private Button search,add;
    private EditText searchtv;
    private Bundle bundle;
    private String name, picture;
    private int kcal,protein,carb,fat;
    public RecipeFragment() { }

    public static RecipeFragment newInstance() {
        RecipeFragment fragment = new RecipeFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_recipe, container, false);
        recyclerView= v.findViewById(R.id.RecyclerView);
        search = (Button)v.findViewById(R.id.search_btn);
        searchtv = (EditText)v.findViewById(R.id.tv_search);
        add = (Button)v.findViewById(R.id.add_btn);
        recyclerView.setHasFixedSize(true);//리사이클러뷰 기존 성능 강화
        layoutManager=new LinearLayoutManager(getContext());
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

        adapter=new cookingAdapter(arrayList,getContext());
        adapter.setOnItemClickListener(new cookingAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                name = arrayList.get(position).getName();
                picture = arrayList.get(position).getPicture();
                kcal = arrayList.get(position).getKcal();
                protein = arrayList.get(position).getProtein();
                carb = arrayList.get(position).getCarb();
                fat = arrayList.get(position).getFat();
                bundle = new Bundle();
                bundle.putString("name",name);
                bundle.putString("picture",picture);
                bundle.putInt("kcal",kcal);
                bundle.putInt("protein",protein);
                bundle.putInt("carb",carb);
                bundle.putInt("fat",fat);
                Fragment fragment = MenuFragment.newInstance();
                fragment.setArguments(bundle);
                ((MainActivity)getActivity()).loadFragment(fragment);
            }
        });
        recyclerView.setAdapter(adapter); //리사이클러뷰에 어댑터 연결

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String query = searchtv.getText().toString();
                if(query == null || query.equals(""))
                    return;
                Query ref = database.getReference("cooking").orderByChild("name").startAt(query).endAt(query + "\uf8ff");
                ref.addListenerForSingleValueEvent(new ValueEventListener() {
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
            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //((MainActivity)getActivity()).loadFragment(MenuAddFragment.newInstance());
            }
        });
        return v;
    }

}