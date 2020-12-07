package com.donga.caloriemaster_android;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Home extends AppCompatActivity {

    static final int DATE_ID=0;

    public int y,mon,d;
    private int mYear,mMonth,mDay;

    TextView tv_date,tv_diet1,tv_diet2,tv_diet3,tv_kcal1,tv_kcal2,tv_kcal3,tv_nute1,tv_nute2,tv_nute3;
    LinearLayout layout_diet1,layout_diet2,layout_diet3;
    Button btn_left,btn_right;

    Spinner spinner;

    myDBHelper myHelper;
    SQLiteDatabase sqlDB;


    public Home(){

        final Calendar c=Calendar.getInstance();
        mYear= c.get(Calendar.YEAR);
        mMonth=c.get(Calendar.MONTH);
        mDay=c.get(Calendar.DAY_OF_MONTH);

        y=mYear;
        mon=mMonth;
        d=mDay;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        tv_date=(TextView)findViewById(R.id.tv_date);
        tv_diet1=(TextView)findViewById(R.id.tv_diet1);
        tv_diet2=(TextView)findViewById(R.id.tv_diet2);
        tv_diet3=(TextView)findViewById(R.id.tv_diet3);

        tv_kcal1=(TextView)findViewById(R.id.tv_kcal1);
        tv_kcal2=(TextView)findViewById(R.id.tv_kcal2);
        tv_kcal3=(TextView)findViewById(R.id.tv_kcal3);

        tv_nute1=(TextView)findViewById(R.id.tv_nute1);
        tv_nute2=(TextView)findViewById(R.id.tv_nute2);
        tv_nute3=(TextView)findViewById(R.id.tv_nute3);

        layout_diet1=(LinearLayout)findViewById(R.id.layout_diet1);
        layout_diet2=(LinearLayout)findViewById(R.id.layout_diet2);
        layout_diet3=(LinearLayout)findViewById(R.id.layout_diet3);

        btn_left=(Button)findViewById(R.id.btn_left);
        btn_right=(Button)findViewById(R.id.btn_right);

        spinner=(Spinner)findViewById(R.id.spinner);
        myHelper=new myDBHelper(this);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i==0){
                    layout_diet1.setVisibility(View.VISIBLE);
                    layout_diet2.setVisibility(View.GONE);
                    layout_diet3.setVisibility(View.GONE);
                }
                else if(i==1){
                    layout_diet1.setVisibility(View.GONE);
                    layout_diet2.setVisibility(View.VISIBLE);
                    layout_diet3.setVisibility(View.GONE);
                }
                else{
                    layout_diet1.setVisibility(View.GONE);
                    layout_diet2.setVisibility(View.GONE);
                    layout_diet3.setVisibility(View.VISIBLE);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }

        });

        btn_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Calendar day = Calendar.getInstance();
                day.set(y,mon,d);
                day.add(Calendar.DATE , -1);

                y=day.get(Calendar.YEAR);
                mon=day.get(Calendar.MONTH);
                d=day.get(Calendar.DAY_OF_MONTH);


                tv_date.setText(y+"년 " +(mon+1)+"월 " +d+"일");

                String dbdate=""+y+(mon+1)+d;

                sqlDB=myHelper.getReadableDatabase();
                Cursor cursor;
                cursor=sqlDB.rawQuery("SELECT * FROM diet WHERE date='"+dbdate+"';",null);

                tv_diet1.setText(cursor.getString(1));
                tv_kcal1.setText("칼로리 : "+cursor.getInt(2)+"kcal");
                tv_nute1.setText("탄수화물 : "+cursor.getInt(3)+"g\n단백질 : "+cursor.getInt(4)+"g\n지방 : "+cursor.getInt(5)+"g");

                tv_diet2.setText(cursor.getString(6));
                tv_kcal2.setText("칼로리 : "+cursor.getInt(7)+"kcal");
                tv_nute2.setText("탄수화물 : "+cursor.getInt(8)+"g\n단백질 : "+cursor.getInt(9)+"g\n지방 : "+cursor.getInt(10)+"g");

                tv_diet3.setText(cursor.getString(11));
                tv_kcal3.setText("칼로리 : "+cursor.getInt(12)+"kcal");
                tv_nute3.setText("탄수화물 : "+cursor.getInt(13)+"g\n단백질 : "+cursor.getInt(14)+"g\n지방 : "+cursor.getInt(15)+"g");

                sqlDB.close();
            }
        });

        btn_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar day = Calendar.getInstance();
                day.set(y,mon,d);
                day.add(Calendar.DATE , 1);

                y=day.get(Calendar.YEAR);
                mon=day.get(Calendar.MONTH);
                d=day.get(Calendar.DAY_OF_MONTH);


                tv_date.setText(y+"년 " +(mon+1)+"월 " +d+"일");

                tv_date.setText(y+"년 " +(mon+1)+"월 " +d+"일");

                String dbdate=""+y+(mon+1)+d;

                sqlDB=myHelper.getReadableDatabase();
                Cursor cursor;
                cursor=sqlDB.rawQuery("SELECT * FROM diet WHERE date='"+dbdate+"';",null);

                tv_diet1.setText(cursor.getString(1));
                tv_kcal1.setText("칼로리 : "+cursor.getInt(2)+"kcal");
                tv_nute1.setText("탄수화물 : "+cursor.getInt(3)+"g\n단백질 : "+cursor.getInt(4)+"g\n지방 : "+cursor.getInt(5)+"g");

                tv_diet2.setText(cursor.getString(6));
                tv_kcal2.setText("칼로리 : "+cursor.getInt(7)+"kcal");
                tv_nute2.setText("탄수화물 : "+cursor.getInt(8)+"g\n단백질 : "+cursor.getInt(9)+"g\n지방 : "+cursor.getInt(10)+"g");

                tv_diet3.setText(cursor.getString(11));
                tv_kcal3.setText("칼로리 : "+cursor.getInt(12)+"kcal");
                tv_nute3.setText("탄수화물 : "+cursor.getInt(13)+"g\n단백질 : "+cursor.getInt(14)+"g\n지방 : "+cursor.getInt(15)+"g");

                sqlDB.close();
            }
        });

    }

    public void datePick(View view){

        DatePickerDialog datepicker=new DatePickerDialog(Home.this,mDatesetListener,mYear,mMonth,mDay);
        datepicker.show();
    }

    private DatePickerDialog.OnDateSetListener mDatesetListener=new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            y=year;
            mon=month;
            d=dayOfMonth;
            tv_date.setText(y+"년 " +(mon+1)+"월 " +d+"일");

            String dbdate=""+y+(mon+1)+d;

            sqlDB=myHelper.getReadableDatabase();
            Cursor cursor;
            cursor=sqlDB.rawQuery("SELECT * FROM diet WHERE date='"+dbdate+"';",null);

            tv_diet1.setText(cursor.getString(1));
            tv_kcal1.setText("칼로리 : "+cursor.getInt(2)+"kcal");
            tv_nute1.setText("탄수화물 : "+cursor.getInt(3)+"g\n단백질 : "+cursor.getInt(4)+"g\n지방 : "+cursor.getInt(5)+"g");

            tv_diet2.setText(cursor.getString(6));
            tv_kcal2.setText("칼로리 : "+cursor.getInt(7)+"kcal");
            tv_nute2.setText("탄수화물 : "+cursor.getInt(8)+"g\n단백질 : "+cursor.getInt(9)+"g\n지방 : "+cursor.getInt(10)+"g");

            tv_diet3.setText(cursor.getString(11));
            tv_kcal3.setText("칼로리 : "+cursor.getInt(12)+"kcal");
            tv_nute3.setText("탄수화물 : "+cursor.getInt(13)+"g\n단백질 : "+cursor.getInt(14)+"g\n지방 : "+cursor.getInt(15)+"g");

            sqlDB.close();
        }
    };


    public class myDBHelper extends SQLiteOpenHelper{
        public myDBHelper(Context context){
            super(context,"dietgroup",null,1);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {

            db.execSQL("CREATE TABLE diet (date TEXT PRIMARY KEY,bf TEXT,bfkcal INTEGER,bfcarbo INTEGER,bfprotein INTEGER,bffat INTEGER," +
                    "lun TEXT,lunkcal INTEGER,luncarbo INTEGER,lunprotein INTEGER,lunfat INTEGER," +
                    "di TEXT,dikacl INTEGER,dicarbo INTEGER,diprotein INTEGER,difat INTEGER)");

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS diet");
            onCreate(db);
        }
    }
}