package com.donga.caloriemaster_android;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class menu extends AppCompatActivity {
    private DatePickerDialog.OnDateSetListener callbackMethod;
    menu menu =this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        ImageView imageView =(ImageView)findViewById(R.id.imageView_plus);
        imageView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                show_date();
                show_time();
                show_gram();
            }
        });

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

        DatePickerDialog dialog = new DatePickerDialog(this, callbackMethod, 2019, 5, 24);

        dialog.show();

    }

    void show_gram(){
        final EditText edittext = new EditText(this);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("식사량 입력(g)");
        builder.setView(edittext);
        builder.setPositiveButton("입력",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(), edittext.getText().toString() ,Toast.LENGTH_LONG).show();
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

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
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
                        Toast.makeText(getApplicationContext(),
                                "Items Selected.\n"+ msg , Toast.LENGTH_LONG)
                                .show();
                    }//Toast 필요없음(수정해야함) ok시 home으로 데이터 전송되도록 만들기
                });

        builder.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

        builder.show();

    }

    public void clickback(View view){
        Intent intent = new Intent(getApplicationContext(), CookingRecyclerView.class);
        startActivity(intent);
    }


}