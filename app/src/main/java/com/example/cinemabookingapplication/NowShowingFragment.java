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

    private String selectedDate = "Today";

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

        if (getArguments() != null) {
            selectedDate = getArguments().getString("selectedDate", "Today");
        }

        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        ArrayList<Movie> movieList = JSONParser.parseNowShowingMovies(getContext(), selectedDate);

        MovieAdapter adapter = new MovieAdapter(
                movieList,
                requireActivity().getSupportFragmentManager(),
                selectedDate
        );
        recyclerView.setAdapter(adapter);

        return view;
    }
}