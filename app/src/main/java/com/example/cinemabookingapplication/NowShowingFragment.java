package com.example.cinemabookingapplication;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class NowShowingFragment extends Fragment {

    private String selectedDate = "Today"; // Default to Today

    public NowShowingFragment() {}

    public static NowShowingFragment newInstance(String date) {
        NowShowingFragment fragment = new NowShowingFragment();
        Bundle args = new Bundle();
        args.putString("selectedDate", date);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie_list, container, false);

        // Get selected date from arguments
        if (getArguments() != null) {
            selectedDate = getArguments().getString("selectedDate", "Today");
        }

        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        ArrayList<Movie> movieList = new ArrayList<>();

        // Add movies based on selected date
        if (selectedDate.equals("Today")) {
            // Movies playing TODAY
            movieList.add(new Movie(
                    "Sinners",
                    "Action / 152 min",
                    "https://youtu.be/tQGN0Ws2Hms?si=B0oDLSYUaA1GDGoM",
                    R.drawable.sinners,
                    false
            ));

            movieList.add(new Movie(
                    "Marty Supreme",
                    "Drama / 148 min",
                    "https://youtu.be/pwE3ZG1kIqw?si=NWyLoGPPCUHlH8iT",
                    R.drawable.marty_supreme,
                    false
            ));

            movieList.add(new Movie(
                    "One Battle After Another",
                    "Action / 169 min",
                    "https://youtu.be/sNKVffxD_9o?si=bNPpX9HPLuNMFMH9",
                    R.drawable.one_battle_after_another,
                    false
            ));

            movieList.add(new Movie(
                    "Call me by your name",
                    "Drama / 169 min",
                    "https://youtu.be/Mzq8MXzhBdw?si=b8mtXi6I1X6o944U",
                    R.drawable.call_me_by_your_name,
                    false
            ));
        } else {
            // Movies playing TOMORROW
            movieList.add(new Movie(
                    "Project Hail Mary",
                    "Sci-Fi / 122 min",
                    "https://youtu.be/P0XN3-n-2Lo?si=uthk67ZXEAHp33Cl",
                    R.drawable.project_hail_mary,
                    false
            ));

            movieList.add(new Movie(
                    "Wuthering Heights",
                    "Drama / 155 min",
                    "https://youtu.be/DZV1woNnJGU?si=0LvIAQ17lnWmP7pH",
                    R.drawable.wuthering_heigts,
                    false
            ));

            movieList.add(new Movie(
                    "Weapons",
                    "Horror / 148 min",
                    "https://youtu.be/GTowMnn-8sk?si=L5G2eSE6p-1aSkPq",
                    R.drawable.weapons,
                    false
            ));

            movieList.add(new Movie(
                    "Bugonia",
                    "Drama / 163 min",
                    "https://youtu.be/GYanIV0BNyY?si=cOc4kFdzLap_dIHi",
                    R.drawable.bugonia,
                    false
            ));
        }

        MovieAdapter adapter = new MovieAdapter(
                movieList,
                requireActivity().getSupportFragmentManager()
        );
        recyclerView.setAdapter(adapter);

        return view;
    }
}