package com.example.cinemabookingapplication;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;


public class HomeFragment extends Fragment {

    private Button btnToday, btnTomorrow;
    private TabLayout tabLayout;
    private ViewPager2 viewPager;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        btnToday = view.findViewById(R.id.btnToday);
        btnTomorrow = view.findViewById(R.id.btnTomorrow);
        tabLayout = view.findViewById(R.id.tabLayout);
        viewPager = view.findViewById(R.id.viewPager);

        btnToday.setOnClickListener(v -> {
            btnToday.setBackgroundTintList(getResources().getColorStateList(android.R.color.holo_red_dark));
            btnTomorrow.setBackgroundTintList(getResources().getColorStateList(android.R.color.darker_gray));
            Toast.makeText(getContext(), "Today selected", Toast.LENGTH_SHORT).show();
        });

        btnTomorrow.setOnClickListener(v -> {
            btnTomorrow.setBackgroundTintList(getResources().getColorStateList(android.R.color.holo_red_dark));
            btnToday.setBackgroundTintList(getResources().getColorStateList(android.R.color.darker_gray));
            Toast.makeText(getContext(), "Tomorrow selected", Toast.LENGTH_SHORT).show();
        });

        setupViewPager();

        return view;
    }

    private void setupViewPager() {
        HomePagerAdapter adapter = new HomePagerAdapter(this);
        viewPager.setAdapter(adapter);

        new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> {
                    if (position == 0) tab.setText("Now Showing");
                    else tab.setText("Coming Soon");
                }).attach();
    }
}