package com.example.cinemabookingapplication;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class Home1Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home1);

        if (savedInstanceState == null) {
            HomeFragment fragment = new HomeFragment();
            fragment.setFragmentManager(getSupportFragmentManager());

            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
        }
    }
}