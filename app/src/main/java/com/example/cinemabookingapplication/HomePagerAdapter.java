package com.example.cinemabookingapplication;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class HomePagerAdapter extends FragmentStateAdapter {

    private HomeFragment homeFragment;
    private String selectedDate = "Today";

    public HomePagerAdapter(@NonNull HomeFragment fragment) {
        super(fragment);
        this.homeFragment = fragment;
    }

    public void updateSelectedDate(String date) {
        this.selectedDate = date;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == 0) {
            // Now Showing tab - passes date to show different movies
            return NowShowingFragment.newInstance(selectedDate);
        } else {
            // Coming Soon tab - no date parameter, always shows same movies
            return ComingSoonFragment.newInstance();
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }

    @Override
    public long getItemId(int position) {
        return position; // Return position as ID to force refresh
    }

    @Override
    public boolean containsItem(long itemId) {
        return itemId < getItemCount();
    }
}