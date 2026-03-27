package com.example.cinemabookingapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
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
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

public class SeatSelectionFragment extends Fragment {

    private Movie movie;
    private List<String> selectedSeats = new ArrayList<>();
    private double seatPrice = 12.99;
    private GridLayout seatGrid;

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
        seatGrid = view.findViewById(R.id.seatGrid);
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
            // Disable all seats
            for (int i = 0; i < seatGrid.getChildCount(); i++) {
                View child = seatGrid.getChildAt(i);
                if (child instanceof TextView) {
                    child.setClickable(false);
                    child.setEnabled(false);
                }
            }

            btnBook.setText("Coming Soon");
            btnBook.setEnabled(false);

            btnSnacks.setText("Watch Trailer");
            btnSnacks.setOnClickListener(v -> {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(trailer));
                startActivity(intent);
            });

        } else {
            // Setup seat clicks
            setupSeatClicks();

            btnBook.setOnClickListener(v -> {
                if (selectedSeats.isEmpty()) {
                    Toast.makeText(getContext(), "Please select at least one seat", Toast.LENGTH_SHORT).show();
                } else {
                    double totalSeatPrice = selectedSeats.size() * seatPrice;
                    String seatsFormatted = formatSelectedSeats();

                    String bookingDetails = "Movie: " + name +
                            "\n\nSeats: " + seatsFormatted +
                            "\nSeats Total: $" + String.format("%.2f", totalSeatPrice) +
                            "\n\nSnacks: No snacks selected" +
                            "\n\nTotal Amount: $" + String.format("%.2f", totalSeatPrice);

                    TicketSummaryFragment fragment = TicketSummaryFragment.newInstance(
                            bookingDetails,
                            totalSeatPrice,
                            name,
                            seatsFormatted,
                            "No snacks selected",
                            0
                    );

                    requireActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fragment_container, fragment)
                            .addToBackStack(null)
                            .commit();
                }
            });

            btnSnacks.setOnClickListener(v -> {
                if (selectedSeats.isEmpty()) {
                    Toast.makeText(getContext(), "Please select at least one seat first", Toast.LENGTH_SHORT).show();
                } else {
                    double totalSeatPrice = selectedSeats.size() * seatPrice;
                    String seatsFormatted = formatSelectedSeats();

                    SnacksFragment snacksFragment = SnacksFragment.newInstance(
                            name,
                            seatsFormatted,
                            selectedSeats.size(),
                            totalSeatPrice
                    );

                    requireActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fragment_container, snacksFragment)
                            .addToBackStack(null)
                            .commit();
                }
            });
        }

        return view;
    }

    private String formatSelectedSeats() {
        StringBuilder formatted = new StringBuilder();
        for (int i = 0; i < selectedSeats.size(); i++) {
            formatted.append(selectedSeats.get(i));
            if (i < selectedSeats.size() - 1) {
                formatted.append(", ");
            }
        }
        return formatted.toString();
    }

    private void setupSeatClicks() {
        for (int i = 0; i < seatGrid.getChildCount(); i++) {
            View seatView = seatGrid.getChildAt(i);

            if (seatView instanceof TextView) {
                TextView seat = (TextView) seatView;
                String seatTag = (String) seat.getTag();

                // Skip if seat is already booked (check by tag or background)
                if (seatTag == null) continue;

                // Check if seat is booked by checking if it has booked background
                // You can also store a separate "booked" tag
                if (seat.getBackground().getConstantState() ==
                        getResources().getDrawable(R.drawable.bg_seat_booked).getConstantState()) {
                    seat.setClickable(false);
                    seat.setEnabled(false);
                    continue;
                }

                // Make available seats clickable
                seat.setOnClickListener(v -> {
                    TextView clickedSeat = (TextView) v;
                    String seatNum = (String) clickedSeat.getTag(); // Get seat number from tag

                    if (selectedSeats.contains(seatNum)) {
                        // Deselect seat
                        selectedSeats.remove(seatNum);
                        clickedSeat.setBackgroundResource(R.drawable.bg_seat_available);
                    } else {
                        // Select seat
                        selectedSeats.add(seatNum);
                        clickedSeat.setBackgroundResource(R.drawable.bg_seat_selected);
                    }
                });
            }
        }
    }
}