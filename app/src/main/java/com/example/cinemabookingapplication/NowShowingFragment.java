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
            // Movies playing TODAY with their own booked seats

            // Sinners
            Movie sinners = new Movie(
                    "Sinners",
                    "Action / 152 min",
                    "https://youtu.be/tQGN0Ws2Hms?si=B0oDLSYUaA1GDGoM",
                    R.drawable.sinners,
                    false
            );
            sinners.bookedSeats.add("B1");
            sinners.bookedSeats.add("B6");
            sinners.bookedSeats.add("C3");
            sinners.bookedSeats.add("C4");
            sinners.bookedSeats.add("D2");
            sinners.bookedSeats.add("E7");
            movieList.add(sinners);

            // Marty Supreme
            Movie martySupreme = new Movie(
                    "Marty Supreme",
                    "Drama / 148 min",
                    "https://youtu.be/pwE3ZG1kIqw?si=NWyLoGPPCUHlH8iT",
                    R.drawable.marty_supreme,
                    false
            );
            martySupreme.bookedSeats.add("A1");
            martySupreme.bookedSeats.add("A8");
            martySupreme.bookedSeats.add("C2");
            martySupreme.bookedSeats.add("D4");
            martySupreme.bookedSeats.add("D5");
            martySupreme.bookedSeats.add("E1");
            movieList.add(martySupreme);

            // One Battle After Another
            Movie oneBattle = new Movie(
                    "One Battle After Another",
                    "Action / 169 min",
                    "https://youtu.be/sNKVffxD_9o?si=bNPpX9HPLuNMFMH9",
                    R.drawable.one_battle_after_another,
                    false
            );
            oneBattle.bookedSeats.add("B2");
            oneBattle.bookedSeats.add("B3");
            oneBattle.bookedSeats.add("C5");
            oneBattle.bookedSeats.add("C6");
            oneBattle.bookedSeats.add("D7");
            oneBattle.bookedSeats.add("E4");
            movieList.add(oneBattle);

            // Call me by your name
            Movie callMe = new Movie(
                    "Call me by your name",
                    "Drama / 169 min",
                    "https://youtu.be/Mzq8MXzhBdw?si=b8mtXi6I1X6o944U",
                    R.drawable.call_me_by_your_name,
                    false
            );
            callMe.bookedSeats.add("A4");
            callMe.bookedSeats.add("A5");
            callMe.bookedSeats.add("B4");
            callMe.bookedSeats.add("C7");
            callMe.bookedSeats.add("D6");
            callMe.bookedSeats.add("E3");
            movieList.add(callMe);

        } else {
            // Movies playing TOMORROW with their own booked seats

            // Project Hail Mary
            Movie projectHailMary = new Movie(
                    "Project Hail Mary",
                    "Sci-Fi / 122 min",
                    "https://youtu.be/P0XN3-n-2Lo?si=uthk67ZXEAHp33Cl",
                    R.drawable.project_hail_mary,
                    false
            );
            projectHailMary.bookedSeats.add("A2");
            projectHailMary.bookedSeats.add("A3");
            projectHailMary.bookedSeats.add("B5");
            projectHailMary.bookedSeats.add("C1");
            projectHailMary.bookedSeats.add("D8");
            projectHailMary.bookedSeats.add("E2");
            movieList.add(projectHailMary);

            // Wuthering Heights
            Movie wutheringHeights = new Movie(
                    "Wuthering Heights",
                    "Drama / 155 min",
                    "https://youtu.be/DZV1woNnJGU?si=0LvIAQ17lnWmP7pH",
                    R.drawable.wuthering_heigts,
                    false
            );
            wutheringHeights.bookedSeats.add("B7");
            wutheringHeights.bookedSeats.add("B8");
            wutheringHeights.bookedSeats.add("C4");
            wutheringHeights.bookedSeats.add("D1");
            wutheringHeights.bookedSeats.add("E5");
            wutheringHeights.bookedSeats.add("E6");
            movieList.add(wutheringHeights);

            // Weapons
            Movie weapons = new Movie(
                    "Weapons",
                    "Horror / 148 min",
                    "https://youtu.be/GTowMnn-8sk?si=L5G2eSE6p-1aSkPq",
                    R.drawable.weapons,
                    false
            );
            weapons.bookedSeats.add("A6");
            weapons.bookedSeats.add("A7");
            weapons.bookedSeats.add("C8");
            weapons.bookedSeats.add("D3");
            weapons.bookedSeats.add("E1");
            weapons.bookedSeats.add("E8");
            movieList.add(weapons);

            // Bugonia
            Movie bugonia = new Movie(
                    "Bugonia",
                    "Drama / 163 min",
                    "https://youtu.be/GYanIV0BNyY?si=cOc4kFdzLap_dIHi",
                    R.drawable.bugonia,
                    false
            );
            bugonia.bookedSeats.add("B2");
            bugonia.bookedSeats.add("C5");
            bugonia.bookedSeats.add("C6");
            bugonia.bookedSeats.add("D4");
            bugonia.bookedSeats.add("E2");
            bugonia.bookedSeats.add("E3");
            movieList.add(bugonia);
        }

        MovieAdapter adapter = new MovieAdapter(
                movieList,
                requireActivity().getSupportFragmentManager()
        );
        recyclerView.setAdapter(adapter);

        return view;
    }
}