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

    public NowShowingFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_movie_list, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // 🔥 MOVIE LIST (replaces your old array)
        ArrayList<Movie> movieList = new ArrayList<>();

        movieList.add(new Movie(
                "The Dark Knight",
                "Action / 152 min",
                "https://www.youtube.com/watch?v=EXeTwQWrcwY",
                R.drawable.poster,
                false
        ));

        movieList.add(new Movie(
                "Inception",
                "Sci-Fi / 148 min",
                "https://www.youtube.com/watch?v=YoHD9XEInc0",
                R.drawable.poster,
                false
        ));

        movieList.add(new Movie(
                "Interstellar",
                "Sci-Fi / 169 min",
                "https://www.youtube.com/watch?v=zSWdZVtXT7E",
                R.drawable.poster,
                false
        ));

        // ✅ SET ADAPTER
        MovieAdapter adapter = new MovieAdapter(movieList, getParentFragmentManager());
        recyclerView.setAdapter(adapter);

        return view;
    }
}