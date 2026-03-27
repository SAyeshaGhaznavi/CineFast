package com.example.cinemabookingapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

public class SeatSelectionFragment extends Fragment {

    private Movie movie;
    private List<String> selectedSeats = new ArrayList<>();

    public static SeatSelectionFragment newInstance(Movie movie) {
        SeatSelectionFragment fragment = new SeatSelectionFragment();
        Bundle args = new Bundle();
        args.putString("name", movie.name);
        args.putString("details", movie.details);
        args.putString("trailer", movie.trailer);
        args.putBoolean("comingSoon", movie.isComingSoon);
        fragment.setArguments(args);
        return fragment;
    }

    @SuppressLint("SetTextI18n")
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_seat_booking, container, false);

        TextView tvMovieName = view.findViewById(R.id.tvMovieName);
        GridLayout seatGrid = view.findViewById(R.id.seatGrid);
        Button btnBook = view.findViewById(R.id.btnBookSeats);
        Button btnSnacks = view.findViewById(R.id.btnProceedToSnacks);
        ImageView btnBack = view.findViewById(R.id.btnBack);

        assert getArguments() != null;
        String name = getArguments().getString("name");
        boolean isComingSoon = getArguments().getBoolean("comingSoon");
        String trailer = getArguments().getString("trailer");

        tvMovieName.setText(name);

        btnBack.setOnClickListener(v ->
                requireActivity().getSupportFragmentManager().popBackStack()
        );

        if (isComingSoon) {
            // DISABLE SEATS
            for (int i = 0; i < seatGrid.getChildCount(); i++) {
                seatGrid.getChildAt(i).setClickable(false);
            }

            btnBook.setText("Coming Soon");
            btnBook.setEnabled(false);

            btnSnacks.setText("Watch Trailer");
            btnSnacks.setOnClickListener(v -> {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(trailer));
                startActivity(intent);
            });

        } else {
            // NORMAL FLOW - Setup seat clicks
            setupSeatClicks(seatGrid);

            btnBook.setOnClickListener(v -> {
                if (selectedSeats.isEmpty()) {
                    Toast.makeText(getContext(), "Please select at least one seat", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Booking Confirmed for " + selectedSeats.size() + " seats!", Toast.LENGTH_SHORT).show();
                }
            });

            btnSnacks.setOnClickListener(v ->
                    Toast.makeText(getContext(), "Go to Snacks (next step)", Toast.LENGTH_SHORT).show()
            );
        }

        return view;
    }

    private void setupSeatClicks(GridLayout seatGrid) {
        for (int i = 0; i < seatGrid.getChildCount(); i++) {
            View seatView = seatGrid.getChildAt(i);

            if (seatView instanceof TextView) {
                TextView seat = (TextView) seatView;
                String seatTag = (String) seat.getTag();

                // Skip if seat is already booked
                if ("booked".equals(seatTag)) {
                    seat.setClickable(false);
                    seat.setAlpha(0.6f);
                    continue;
                }

                // Make available seats clickable
                seat.setOnClickListener(v -> {
                    TextView clickedSeat = (TextView) v;
                    String seatNum = clickedSeat.getText().toString();

                    if (selectedSeats.contains(seatNum)) {
                        // Deselect seat
                        selectedSeats.remove(seatNum);
                        clickedSeat.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.grayBg));
                        clickedSeat.setTextColor(ContextCompat.getColor(getContext(), R.color.white));
                        clickedSeat.setTag("available");
                    } else {
                        // Select seat
                        selectedSeats.add(seatNum);
                        clickedSeat.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.green));
                        clickedSeat.setTextColor(ContextCompat.getColor(getContext(), android.R.color.black));
                        clickedSeat.setTag("selected");
                    }
                });
            }
        }
    }
}