package com.donga.caloriemaster_android;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MenuFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MenuFragment extends Fragment {
    private DatePickerDialog.OnDateSetListener callbackMethod;
     TextView tv_name,tv_kcal,tv_nutrient;
     ImageView food,backbtn;
     String picture,name;
     int kcal,carb,protein,fat;
    public MenuFragment() {
    }

    // TODO: Rename and change types and number of parameters
    public static MenuFragment newInstance() {
        MenuFragment fragment = new MenuFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            picture = getArguments().getString("picture");
            name = getArguments().getString("name");
            kcal = getArguments().getInt("kcal");
            carb = getArguments().getInt("carb");
            protein = getArguments().getInt("protein");
            fat = getArguments().getInt("fat");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_menu, container, false);
        ImageView imageView =(ImageView)v.findViewById(R.id.imageView_plus);
        food = (ImageView)v.findViewById(R.id.food_image);
        backbtn = (ImageView)v.findViewById(R.id.back);
        tv_kcal = (TextView)v.findViewById(R.id.tv_kcal);
        tv_name = (TextView)v.findViewById(R.id.tv_name);
        tv_nutrient = (TextView)v.findViewById(R.id.food_nutrient);
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)getActivity()).loadFragment(RecipeFragment.newInstance());
            }
        });
        imageView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                show_date();
                show_time();
                show_gram();
            }
        });
        Glide.with(getActivity().getApplicationContext())
                .load(picture)
                .override(406,339)
                .into(food);
        tv_kcal.setText(kcal + "");
        tv_name.setText(name);
        tv_nutrient.setText("탄수화물 : " + carb + " 단백질 : " + protein + " 지방 : " + fat);
        return v;
    }
    void show_date(){
        callbackMethod = new DatePickerDialog.OnDateSetListener(){
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth)
            {
                //다이얼로그 창의 완료 버튼을 클릭하였을 때 CallBack함수 실행됨
                //사용자가 입력한 날짜 정보를 연월일 단위로 넘어오게 됩니다.
                //db에 넣게 수정
            }
        };

        DatePickerDialog dialog = new DatePickerDialog(getContext(), callbackMethod, 2019, 5, 24);

        dialog.show();

    }

    void show_gram(){
        final EditText edittext = new EditText(getContext());

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("식사량 입력(g)");
        builder.setView(edittext);
        builder.setPositiveButton("입력",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getContext(), edittext.getText().toString() ,Toast.LENGTH_LONG).show();
                    }//Toast 필요없음(수정해야함)
                });
        builder.setNegativeButton("취소",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        builder.show();
    }

    void show_time(){

        final List<String> ListItems = new ArrayList<>();
        ListItems.add("조식");
        ListItems.add("중식");
        ListItems.add("석식");
        final CharSequence[] items =  ListItems.toArray(new String[ ListItems.size()]);

        final List SelectedItems  = new ArrayList();
        int defaultItem = 0;
        SelectedItems.add(defaultItem);

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("식사시간 선택");
        builder.setSingleChoiceItems(items, defaultItem,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SelectedItems.clear();
                        SelectedItems.add(which);
                    }
                });
        builder.setPositiveButton("Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        String msg="";

                        if (!SelectedItems.isEmpty()) {
                            int index = (int) SelectedItems.get(0);
                            msg = ListItems.get(index);
                        }
                        ((MainActivity)getActivity()).loadFragment(HomeFragment.newInstance());
                    }
                });

        builder.setNegativeButton("Cancel",null);
        builder.show();

    }
}