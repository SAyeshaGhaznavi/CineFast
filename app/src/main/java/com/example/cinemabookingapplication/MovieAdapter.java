package com.example.cinemabookingapplication;

import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {

    private List<Movie> movieList;
    private FragmentManager fragmentManager;
    private String selectedDate;

    public MovieAdapter(List<Movie> movieList, FragmentManager fragmentManager, String selectedDate) {
        this.movieList = movieList;
        this.fragmentManager = fragmentManager;
        this.selectedDate = selectedDate;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView title, details;
        Button btnBook, btnTrailer;
        ImageView image;

        public ViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.movieTitle);
            details = itemView.findViewById(R.id.movieDetails);
            btnBook = itemView.findViewById(R.id.btnBookSeats);
            btnTrailer = itemView.findViewById(R.id.btnTrailer);
            image = itemView.findViewById(R.id.movieImage);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_movie, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Movie movie = movieList.get(position);

        holder.title.setText(movie.name);
        holder.details.setText(movie.details);
        holder.image.setImageResource(movie.posterResId);

        holder.btnTrailer.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(movie.trailer));
            v.getContext().startActivity(intent);
        });

        holder.btnBook.setOnClickListener(v -> {
            SeatSelectionFragment fragment = SeatSelectionFragment.newInstance(movie, selectedDate);
            fragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .addToBackStack(null)
                    .commit();
        });
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }
}