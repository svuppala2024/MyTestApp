package com.example.mytestapp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

/* The purpose of this class is to load all the other fragments into the frameview with the navigation bar*/

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation); /* Object refernce for bottom navigation bar */
        bottomNav.setOnItemSelectedListener(item -> {
            Fragment selectedFragment = null;
            switch (item.getItemId()) {

                case R.id.closetFragment:
                    selectedFragment = new ClosetFragment();
                    break;

                case R.id.cameraFragment:
                    selectedFragment = new CameraFragment();
                    break;

                case R.id.suggestionsFragment:
                    selectedFragment = new SuggestionsFragment();
                    break;
            }

            if (selectedFragment != null) {
                getSupportFragmentManager().beginTransaction().replace(R.id.screen, selectedFragment).commit();
            }
            return true;
        });


        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.screen,
                    new CameraFragment()).commit();
        }


    }

}