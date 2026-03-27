package com.example.cinemabookingapplication;

import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class HomeFragment extends Fragment {

    private Button btnToday, btnTomorrow;
    private TabLayout tabLayout;
    private ViewPager2 viewPager;
    private Toolbar toolbar;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        btnToday = view.findViewById(R.id.btnToday);
        btnTomorrow = view.findViewById(R.id.btnTomorrow);
        tabLayout = view.findViewById(R.id.tabLayout);
        viewPager = view.findViewById(R.id.viewPager);
        toolbar = view.findViewById(R.id.toolbar);

        // Set up the toolbar with menu
        setupToolbar();

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

    private void setupToolbar() {
        // Enable the toolbar as action bar
        if (getActivity() != null) {
            // Set up toolbar with menu
            toolbar.inflateMenu(R.menu.home_menu);
            toolbar.setOnMenuItemClickListener(item -> {
                if (item.getItemId() == R.id.action_view_last_booking) {
                    showLastBookingDialog();
                    return true;
                }
                return false;
            });
        }
    }

    // Update the showLastBookingDialog method in HomeFragment.java
    private void showLastBookingDialog() {
        // Retrieve data from SharedPreferences
        SharedPreferences prefs = requireContext().getSharedPreferences("booking", android.content.Context.MODE_PRIVATE);

        String movieName = prefs.getString("movie", "");
        String seats = prefs.getString("seats", "");
        String snacks = prefs.getString("snacks", "");
        String totalPrice = prefs.getString("total_amount", "");

        // Check if booking exists
        if (movieName.isEmpty() || seats.isEmpty() || totalPrice.isEmpty()) {
            // No previous booking found
            new AlertDialog.Builder(requireContext())
                    .setTitle("Last Booking")
                    .setMessage("No previous booking found.")
                    .setPositiveButton("OK", null)
                    .show();
        } else {
            // Display booking information including snacks
            String message = "Movie: " + movieName +
                    "\nSeats: " + seats +
                    "\n\nSnacks:\n" + snacks +
                    "\n\nTotal Price: $" + totalPrice;

            new AlertDialog.Builder(requireContext())
                    .setTitle("Last Booking")
                    .setMessage(message)
                    .setPositiveButton("OK", null)
                    .show();
        }
    }

    private FragmentManager fragmentManager;

    public void setFragmentManager(FragmentManager fm) {
        this.fragmentManager = fm;
    }

    public FragmentManager getFragmentManagerFromActivity() {
        return fragmentManager;
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