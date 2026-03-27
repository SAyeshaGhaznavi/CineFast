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

    public static ComingSoonFragment newInstance() {
        return new ComingSoonFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie_list, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        ArrayList<Movie> movieList = new ArrayList<>();

        // Coming soon movies - same for both Today and Tomorrow
        movieList.add(new Movie(
                "Dune Part 3",
                "Sci-Fi / 190 min",
                "https://youtu.be/3aAvQxtvKiA?si=4_m0v6cK2Pqqwu8Z",
                R.drawable.dune3,
                true
        ));

        movieList.add(new Movie(
                "The Odyssey",
                "Action / 169 min",
                "https://youtu.be/l54pzz3-Yl8?si=NTWo-dMcL8Vt3s1S",
                R.drawable.odyssey,
                true
        ));

        movieList.add(new Movie(
                "The Drama",
                "Drama / 164 min",
                "https://youtu.be/t95Mng97rkI?si=-ulFUd1Tj2mja39I",
                R.drawable.thedrama,
                true
        ));

        movieList.add(new Movie(
                "Dracula",
                "Sci-Fi / 141 min",
                "https://youtu.be/5GK_cFZ5XgE?si=LV4gUCMUinN_RbeK",
                R.drawable.dracula,
                true
        ));

        MovieAdapter adapter = new MovieAdapter(
                movieList,
                requireActivity().getSupportFragmentManager()
        );
        recyclerView.setAdapter(adapter);

        return view;
    }
}