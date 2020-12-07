package com.donga.caloriemaster_android;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {
Button btnlogout;
    EditText edt_age;
    TextView tv_age,tv_job;
    Spinner spinner;
    Button btn_edit,btn_confirm;
    ImageView iv_select1,iv_select2,iv_unselect1,iv_unselect2;
    RadioButton btn_radio1,btn_radio2;
    boolean checkGen=false;
    public ProfileFragment() {
        // Required empty public constructor
    }

    public static ProfileFragment newInstance() {
        ProfileFragment fragment = new ProfileFragment();
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
        View v = inflater.inflate(R.layout.fragment_profile, container, false);
        btnlogout = v.findViewById(R.id.logout);
        edt_age=(EditText)v.findViewById(R.id.edt_age);
        tv_age=(TextView)v.findViewById(R.id.tv_age);
        tv_job=(TextView)v.findViewById(R.id.tv_job);
        spinner=(Spinner)v.findViewById(R.id.spinner);
        btn_edit=(Button)v.findViewById(R.id.btn_edit);
        btn_confirm=(Button)v.findViewById(R.id.btn_confirm);
        iv_select1=(ImageView)v.findViewById(R.id.iv_select1);
        iv_select2=(ImageView)v.findViewById(R.id.iv_select2);
        iv_unselect1=(ImageView)v.findViewById(R.id.iv_unselect1);
        iv_unselect2=(ImageView)v.findViewById(R.id.iv_unselect2);
        btn_radio1=(RadioButton)v.findViewById(R.id.btn_radio1);
        btn_radio2=(RadioButton)v.findViewById(R.id.btn_radio2);

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

                btn_radio1.setVisibility(View.VISIBLE);
                btn_radio2.setVisibility(View.VISIBLE);
                iv_select1.setVisibility(View.GONE);
                iv_select2.setVisibility(View.GONE);
                iv_unselect1.setVisibility(View.GONE);
                iv_unselect2.setVisibility(View.GONE);

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

                btn_radio1.setVisibility(View.GONE);
                btn_radio2.setVisibility(View.GONE);

                if(checkGen==false){
                    iv_select1.setVisibility(View.VISIBLE);
                    iv_select2.setVisibility(View.GONE);
                    iv_unselect1.setVisibility(View.GONE);
                    iv_unselect2.setVisibility(View.VISIBLE);

                }
                else{
                    iv_select1.setVisibility(View.GONE);
                    iv_select2.setVisibility(View.VISIBLE);
                    iv_unselect1.setVisibility(View.VISIBLE);
                    iv_unselect2.setVisibility(View.GONE);
                }

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

    public void RtnClicked(View view){
        boolean checked=((RadioButton)view).isChecked();
        switch (view.getId()){
            case R.id.btn_radio1:
                if(checked){
                    checkGen=false;
                    break;
                }
            case R.id.btn_radio2:
                if(checked){
                    checkGen=true;
                    break;
                }

        }
    }
        btnlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                ((MainActivity)getActivity()).loadFragment(LoginFragment.newInstance());
            }
        });

        return v;
    }
}