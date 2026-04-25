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
            return NowShowingFragment.newInstance(selectedDate);
        } else {
            return ComingSoonFragment.newInstance();
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean containsItem(long itemId) {
        return itemId < getItemCount();
    }
}