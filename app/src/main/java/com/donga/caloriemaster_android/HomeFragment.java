package com.donga.caloriemaster_android;

import android.app.DatePickerDialog;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {
    static final int DATE_ID=0;

    public int y,mon,d;
    private int mYear,mMonth,mDay;

    TextView tv_date,tv_diet1,tv_diet2,tv_diet3,tv_kcal1,tv_kcal2,tv_kcal3,tv_nute1,tv_nute2,tv_nute3;
    LinearLayout layout_diet1,layout_diet2,layout_diet3;
    Button btn_left,btn_right;


    Spinner spinner;

    myDBHelper myHelper;
    SQLiteDatabase sqlDB;

    public HomeFragment() {
        final Calendar c=Calendar.getInstance();
        mYear= c.get(Calendar.YEAR);
        mMonth=c.get(Calendar.MONTH);
        mDay=c.get(Calendar.DAY_OF_MONTH);
        y=mYear;
        mon=mMonth;
        d=mDay;
    }

    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
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
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        tv_date=(TextView)v.findViewById(R.id.tv_date);
        tv_diet1=(TextView)v.findViewById(R.id.tv_diet1);
        tv_diet2=(TextView)v.findViewById(R.id.tv_diet2);
        tv_diet3=(TextView)v.findViewById(R.id.tv_diet3);

        tv_kcal1=(TextView)v.findViewById(R.id.tv_kcal1);
        tv_kcal2=(TextView)v.findViewById(R.id.tv_kcal2);
        tv_kcal3=(TextView)v.findViewById(R.id.tv_kcal3);

        tv_nute1=(TextView)v.findViewById(R.id.tv_nute1);
        tv_nute2=(TextView)v.findViewById(R.id.tv_nute2);
        tv_nute3=(TextView)v.findViewById(R.id.tv_nute3);

        layout_diet1=(LinearLayout)v.findViewById(R.id.layout_diet1);
        layout_diet2=(LinearLayout)v.findViewById(R.id.layout_diet2);
        layout_diet3=(LinearLayout)v.findViewById(R.id.layout_diet3);

        btn_left=(Button)v.findViewById(R.id.btn_left);
        btn_right=(Button)v.findViewById(R.id.btn_right);

        spinner=(Spinner)v.findViewById(R.id.spinner);
        myHelper=new myDBHelper(getContext());


        tv_date.setText(y+"년 " +(mon+1)+"월 " +d+"일");

        String dbdate=""+y+(mon+1)+d;
        select(dbdate);
        tv_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datepicker=new DatePickerDialog(getContext(),mDatesetListener,mYear,mMonth,mDay);
                datepicker.show();
            }
        });



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
                select(dbdate);
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
                select(dbdate);
            }
        });
        return v;
    }


    private DatePickerDialog.OnDateSetListener mDatesetListener=new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            y=year;
            mon=month;
            d=dayOfMonth;
            tv_date.setText(y+"년 " +(mon+1)+"월 " +d+"일");

            String dbdate=""+y+(mon+1)+d;
            select(dbdate);
        }
    };


    public static class myDBHelper extends SQLiteOpenHelper {

        public myDBHelper(Context context){
            super(context,"dietGroup",null,1);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {

            db.execSQL("DROP TABLE IF EXISTS diet");

            db.execSQL("CREATE TABLE diet (date TEXT PRIMARY KEY,bf TEXT,bfkcal INTEGER,bfcarbo INTEGER,bfprotein INTEGER,bffat INTEGER," +
                    "lun TEXT,lunkcal INTEGER,luncarbo INTEGER,lunprotein INTEGER,lunfat INTEGER," +
                    "di TEXT,dikcal INTEGER,dicarbo INTEGER,diprotein INTEGER,difat INTEGER)");

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS diet");
            onCreate(db);
        }
    }

    public void select(String dbdate){

        sqlDB=myHelper.getReadableDatabase();
        Cursor cursor;

        cursor=sqlDB.rawQuery("SELECT * FROM diet WHERE date='"+dbdate+"';",null);

        if(cursor.moveToFirst()) {

            cursor.moveToFirst();

            String diet = cursor.getString(cursor.getColumnIndex("bf"));
            int kcal = cursor.getInt(cursor.getColumnIndex("bfkcal"));
            int carbo = cursor.getInt(cursor.getColumnIndex("bfcarbo"));
            int protein = cursor.getInt(cursor.getColumnIndex("bfprotein"));
            int fat = cursor.getInt(cursor.getColumnIndex("bffat"));

            if(diet.equals("None")){
                tv_diet1.setText("식단이 없어요!");
                tv_kcal1.setText("");
                tv_nute1.setText("");
            }
            else {
                tv_diet1.setText(diet);
                tv_kcal1.setText("칼로리 : " + kcal + "kcal");
                tv_nute1.setText("탄수화물 : " + carbo + "g\n단백질 : " + protein + "g\n지방 : " + fat + "g");
            }

            diet = cursor.getString(cursor.getColumnIndex("lun"));
            kcal = cursor.getInt(cursor.getColumnIndex("lunkcal"));
            carbo = cursor.getInt(cursor.getColumnIndex("luncarbo"));
            protein = cursor.getInt(cursor.getColumnIndex("lunprotein"));
            fat = cursor.getInt(cursor.getColumnIndex("lunfat"));

            if(diet.equals("None")) {
                tv_diet2.setText("식단이 없어요!");
                tv_kcal2.setText("");
                tv_nute2.setText("");
            }
            else {
                tv_diet2.setText(diet);
                tv_kcal2.setText("칼로리 : " + kcal + "kcal");
                tv_nute2.setText("탄수화물 : " + carbo + "g\n단백질 : " + protein + "g\n지방 : " + fat + "g");
            }
            diet = cursor.getString(cursor.getColumnIndex("di"));
            kcal=cursor.getInt(cursor.getColumnIndex("dikcal"));
            carbo = cursor.getInt(cursor.getColumnIndex("dicarbo"));
            protein = cursor.getInt(cursor.getColumnIndex("diprotein"));
            fat = cursor.getInt(cursor.getColumnIndex("difat"));

            if(diet.equals("None")) {
                tv_diet3.setText("식단이 없어요!");
                tv_kcal3.setText("");
                tv_nute3.setText("");
            }
            else {
                tv_diet3.setText(diet);
                tv_kcal3.setText("칼로리 : " + kcal + "kcal");
                tv_nute3.setText("탄수화물 : " + carbo + "g\n단백질 : " + protein + "g\n지방 : " + fat + "g");
            }

        }

        else{
            tv_diet1.setText("식단이 없어요!");
            tv_kcal1.setText("");
            tv_nute1.setText("");
            tv_diet2.setText("식단이 없어요!");
            tv_kcal2.setText("");
            tv_nute2.setText("");
            tv_diet3.setText("식단이 없어요!");
            tv_kcal3.setText("");
            tv_nute3.setText("");
        }
        sqlDB.close();

    }
}