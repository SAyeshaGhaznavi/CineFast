package com.example.cinemabookingapplication;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class ComingSoonFragment extends Fragment {

    public ComingSoonFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_movie_list, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        ArrayList<Movie> movieList = new ArrayList<>();


        movieList.add(new Movie(
                "Avatar 3",
                "Sci-Fi / Coming Soon",
                "https://www.youtube.com/watch?v=d9MyW72ELq0",
                R.drawable.poster,
                true
        ));

        movieList.add(new Movie(
                "Joker 2",
                "Drama / Coming Soon",
                "https://www.youtube.com/watch?v=xy8aJw1vYHo",
                R.drawable.poster,
                true
        ));

        movieList.add(new Movie(
                "Dune Part 2",
                "Sci-Fi / Coming Soon",
                "https://www.youtube.com/watch?v=Way9Dexny3w",
                R.drawable.poster,
                true
        ));

        MovieAdapter adapter = new MovieAdapter(movieList, getParentFragmentManager());
        recyclerView.setAdapter(adapter);

        return view;
    }
}