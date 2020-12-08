package com.donga.caloriemaster_android;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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
import java.util.Calendar;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MenuFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MenuFragment extends Fragment {
    private DatePickerDialog.OnDateSetListener callbackMethod;
    public int y,mon,d;
    private int mYear,mMonth,mDay;

     TextView tv_name,tv_kcal,tv_nutrient;
     ImageView food,backbtn;
     String picture,name;
     int kcal,carb,protein,fat;

     String dbname,dbdate;
     int dbkcal,dbcarb,dbprotein,dbfat,checkEatTime;

     HomeFragment.myDBHelper myHelper;
    SQLiteDatabase sqlDB;

    public MenuFragment() {

        final Calendar c=Calendar.getInstance();
        mYear= c.get(Calendar.YEAR);
        mMonth=c.get(Calendar.MONTH);
        mDay=c.get(Calendar.DAY_OF_MONTH);
        y=mYear;
        mon=mMonth;
        d=mDay;

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
        myHelper=new HomeFragment.myDBHelper(getContext());
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
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                y=year;
                mon=month;
                d=dayOfMonth;

                dbdate=""+y+(mon+1)+d;
                dbname=name;

                insert();
            }
        };

        DatePickerDialog dialog = new DatePickerDialog(getContext(), callbackMethod, mYear, mMonth, mDay);
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
                        int temp=Integer.parseInt(edittext.getText().toString());
                        dbkcal=(kcal*temp/100);
                        dbcarb=(carb*temp/100);
                        dbprotein=(protein*temp/100);
                        dbfat=(fat*temp/100);
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
                        checkEatTime=which;
                    }
                });
        builder.setPositiveButton("Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        String msg="";

                        if (!SelectedItems.isEmpty()) {
                            int index = (int) SelectedItems.get(0);
                            msg = ListItems.get(index);
                            //checkEatTime=which;
                        }
                        //((MainActivity)getActivity()).loadFragment(HomeFragment.newInstance());
                    }
                });

        builder.setNegativeButton("Cancel",null);
        builder.show();

    }

    public void insert(){

        if(select(dbdate)) {

            sqlDB=myHelper.getWritableDatabase();

            if(checkEatTime==0){//조식
                Toast.makeText(getContext(),"조식",Toast.LENGTH_LONG).show();
                sqlDB.execSQL("INSERT INTO diet VALUES('" + dbdate + "','" + dbname + "'," + dbkcal + "," + dbcarb + "," + dbprotein + "," + dbfat + ",'None',-1,-1,-1,-1,'None',-1,-1,-1,-1);");
            }
            else if(checkEatTime==1){//중식
                Toast.makeText(getContext(),"중식",Toast.LENGTH_LONG).show();
                sqlDB.execSQL("INSERT INTO diet VALUES('" + dbdate + "','None',-1,-1,-1,-1,'"+dbname + "'," + dbkcal + "," + dbcarb + "," + dbprotein + "," + dbfat + ",'None',-1,-1,-1,-1 );");
            }
            else{//석식
                Toast.makeText(getContext(),"석식",Toast.LENGTH_LONG).show();
                sqlDB.execSQL("INSERT INTO diet VALUES('" + dbdate + "','None',-1,-1,-1,-1,'None',-1,-1,-1,-1 ,'"+dbname + "'," + dbkcal + "," + dbcarb + "," + dbprotein + "," + dbfat + ");");
            }

        }
        else{//이미 데이터 있는 경우 ㅇ

            sqlDB=myHelper.getWritableDatabase();

            Cursor cursor;
            cursor=sqlDB.rawQuery("SELECT * FROM diet WHERE date='"+dbdate+"';",null);
            cursor.moveToFirst();

            if(checkEatTime==0){//조식
                Toast.makeText(getContext(),"조식",Toast.LENGTH_LONG).show();
                String checkName = cursor.getString(cursor.getColumnIndex("bf"));
                String sqlUpdate;
                if(checkName.equals("None")){
                    sqlUpdate = "UPDATE diet SET bf='"+dbname+"',bfkcal="+dbkcal+",bfcarbo="+dbcarb+",bfprotein="+dbprotein+",bffat="+dbfat+" WHERE date='"+dbdate+"';";
                }
                else{
                    dbname = cursor.getString(cursor.getColumnIndex("bf"))+"\n"+dbname;
                    dbkcal = dbkcal+cursor.getInt(cursor.getColumnIndex("bfkcal"));
                    dbcarb = dbcarb+ cursor.getInt(cursor.getColumnIndex("bfcarbo"));
                    dbprotein = dbprotein+ cursor.getInt(cursor.getColumnIndex("bfprotein"));
                    dbfat = dbfat + cursor.getInt(cursor.getColumnIndex("bffat"));

                    sqlUpdate = "UPDATE diet SET bf='"+dbname+"',bfkcal="+dbkcal+",bfcarbo="+dbcarb+",bfprotein="+dbprotein+",bffat="+dbfat+" WHERE date='"+dbdate+"';";
                }
                sqlDB.execSQL(sqlUpdate);
            }
            else if(checkEatTime==1){//중식
                Toast.makeText(getContext(),"중식",Toast.LENGTH_LONG).show();

                String checkName = cursor.getString(cursor.getColumnIndex("lun"));
                String sqlUpdate;
                if(checkName.equals("None")){
                    sqlUpdate = "UPDATE diet SET lun='"+dbname+"',lunkcal="+dbkcal+",luncarbo="+dbcarb+",lunprotein="+dbprotein+",lunfat="+dbfat+" WHERE date='"+dbdate+"';";
                }
                else{
                    dbname = cursor.getString(cursor.getColumnIndex("lun"))+"\n"+dbname;
                    dbkcal = dbkcal+cursor.getInt(cursor.getColumnIndex("lunkcal"));
                    dbcarb = dbcarb+cursor.getInt(cursor.getColumnIndex("luncarbo"));
                    dbprotein = dbprotein+cursor.getInt(cursor.getColumnIndex("lunprotein"));
                    dbfat = dbfat+cursor.getInt(cursor.getColumnIndex("lunfat"));

                    sqlUpdate = "UPDATE diet SET lun='"+dbname+"',lunkcal="+dbkcal+",luncarbo="+dbcarb+",lunprotein="+dbprotein+",lunfat="+dbfat+" WHERE date='"+dbdate+"';";
                }
                sqlDB.execSQL(sqlUpdate);
            }
            else{//석식
                Toast.makeText(getContext(),"석식",Toast.LENGTH_LONG).show();

                String checkName = cursor.getString(cursor.getColumnIndex("di"));
                String sqlUpdate;
                if(checkName.equals("None")){
                    sqlUpdate = "UPDATE diet SET di='"+dbname+"',dikcal="+dbkcal+",dicarbo="+dbcarb+",diprotein="+dbprotein+",difat="+dbfat+" WHERE date='"+dbdate+"';";
                }
                else{
                    dbname = cursor.getString(cursor.getColumnIndex("di"))+"\n"+dbname;
                    dbkcal = dbkcal+cursor.getInt(cursor.getColumnIndex("dikcal"));
                    dbcarb = dbcarb+cursor.getInt(cursor.getColumnIndex("dicarbo"));
                    dbprotein = dbprotein+cursor.getInt(cursor.getColumnIndex("diprotein"));
                    dbfat = dbfat+cursor.getInt(cursor.getColumnIndex("difat"));

                    sqlUpdate = "UPDATE diet SET di='"+dbname+"',dikcal="+dbkcal+",dicarbo="+dbcarb+",diprotein="+dbprotein+",difat="+dbfat+" WHERE date='"+dbdate+"';";
                }
                sqlDB.execSQL(sqlUpdate);
            }

        }

        sqlDB.close();

    }

    public boolean select(String dbdate){

        sqlDB=myHelper.getReadableDatabase();
        Cursor cursor;

        cursor=sqlDB.rawQuery("SELECT * FROM diet WHERE date='"+dbdate+"';",null);

        if(cursor.moveToFirst()) {
            sqlDB.close();
            return false;
        }
        else {
            sqlDB.close();
            return true;
        }

    }
}