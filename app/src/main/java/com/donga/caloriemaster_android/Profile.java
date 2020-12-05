package com.donga.caloriemaster_android;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Profile extends AppCompatActivity {

    EditText edt_age;
    TextView tv_age,tv_job;
    Spinner spinner;
    Button btn_edit,btn_confirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);

        edt_age=(EditText)findViewById(R.id.edt_age);
        tv_age=(TextView)findViewById(R.id.tv_age);
        tv_job=(TextView)findViewById(R.id.tv_job);
        spinner=(Spinner)findViewById(R.id.spinner);
        btn_edit=(Button)findViewById(R.id.btn_edit);
        btn_confirm=(Button)findViewById(R.id.btn_confirm);

        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edt_age.setVisibility(View.VISIBLE);
                tv_age.setVisibility(View.GONE);
                edt_age.setText(tv_age.getText().toString());

                spinner.setVisibility(View.VISIBLE);
                tv_job.setVisibility(View.GONE);

                btn_confirm.setVisibility(View.VISIBLE);
                btn_edit.setVisibility(View.GONE);
            }
        });

        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                edt_age.setVisibility(View.GONE);
                tv_age.setVisibility(View.VISIBLE);
                tv_age.setText(edt_age.getText().toString());

                spinner.setVisibility(View.GONE);
                tv_job.setVisibility(View.VISIBLE);

                btn_confirm.setVisibility(View.GONE);
                btn_edit.setVisibility(View.VISIBLE);
            }
        });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tv_job.setText(parent.getItemAtPosition(position).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }
}