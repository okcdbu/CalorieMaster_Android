package com.donga.caloriemaster_android;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    Fragment Recipefragment,Homefragment,Profilefragment;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.action_recipe: loadFragment(Recipefragment);
                return true;
                case R.id.action_home: loadFragment(Homefragment);
                return true;
                case R.id.action_profile: loadFragment(Profilefragment);
                return true;
            }
            return false;
        }
    };
    public boolean loadFragment(Fragment fragment) {
        //switching fragment

        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
            return true;
        }
        return false;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        Recipefragment = RecipeFragment.newInstance();
        Homefragment = HomeFragment.newInstance();
        Profilefragment = LoginFragment.newInstance();
        loadFragment(Homefragment);
        bottomNavigationView.getMenu().findItem(R.id.action_home).setChecked(true);
    }
}