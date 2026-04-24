package com.example.cinemabookingapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class Home1Activity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home1);

        Toast.makeText(this, "CineFAST", Toast.LENGTH_SHORT).show();

        if (savedInstanceState == null) {
            HomeFragment fragment = new HomeFragment();
            fragment.setFragmentManager(getSupportFragmentManager());

            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
        }

        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.navigationView);

        navigationView.setNavigationItemSelectedListener(item -> {

            int id = item.getItemId();

            if (id == R.id.nav_home) {
                loadFragment(new HomeFragment());
            }
            else if (id == R.id.nav_bookings) {
                loadFragment(new MyBookingsFragment());
            }
            else if (id == R.id.nav_logout) {
                logout();
            }

            drawerLayout.closeDrawers();
            return true;
        });
    }

    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();
    }

    private void logout() {
        SharedPreferences preferences = getSharedPreferences("cinefast_session_pref_v3", MODE_PRIVATE);
        preferences.edit().clear().apply();

        FirebaseAuth.getInstance().signOut();

        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }
}