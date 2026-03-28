package com.example.cinemabookingapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
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
    private Button btnBook;
    private Button btnSnacks;

    public static SeatSelectionFragment newInstance(Movie movie) {
        SeatSelectionFragment fragment = new SeatSelectionFragment();
        Bundle args = new Bundle();
        args.putString("name", movie.name);
        args.putString("details", movie.details);
        args.putString("trailer", movie.trailer);
        args.putBoolean("comingSoon", movie.isComingSoon);
        args.putInt("posterResId", movie.posterResId);

        // Store booked seats as ArrayList in Bundle
        ArrayList<String> bookedSeatsList = new ArrayList<>(movie.bookedSeats);
        args.putStringArrayList("bookedSeats", bookedSeatsList);

        fragment.setArguments(args);
        return fragment;
    }

    @SuppressLint("SetTextI18n")
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_seat_booking, container, false);

        TextView tvMovieName = view.findViewById(R.id.tvMovieName);
        seatGrid = view.findViewById(R.id.seatGrid);
        btnBook = view.findViewById(R.id.btnBookSeats);
        btnSnacks = view.findViewById(R.id.btnProceedToSnacks);
        ImageView btnBack = view.findViewById(R.id.btnBack);

        assert getArguments() != null;
        String name = getArguments().getString("name");
        boolean isComingSoon = getArguments().getBoolean("comingSoon");
        String trailer = getArguments().getString("trailer");
        int posterResId = getArguments().getInt("posterResId", R.drawable.poster);

        // Get booked seats from arguments
        ArrayList<String> bookedSeatsList = getArguments().getStringArrayList("bookedSeats");
        if (bookedSeatsList == null) {
            bookedSeatsList = new ArrayList<>();
        }

        tvMovieName.setText(name);

        btnBack.setOnClickListener(v ->
                requireActivity().getSupportFragmentManager().popBackStack()
        );

        if (isComingSoon) {
            // Coming Soon: Disable all seats
            for (int i = 0; i < seatGrid.getChildCount(); i++) {
                View child = seatGrid.getChildAt(i);
                if (child instanceof TextView) {
                    child.setClickable(false);
                    child.setEnabled(false);
                    child.setBackgroundResource(R.drawable.bg_seat_available);
                }
            }

            btnBook.setText("Coming Soon");
            btnBook.setOnClickListener(v -> {
                requireActivity().getSupportFragmentManager().popBackStack(null,
                        androidx.fragment.app.FragmentManager.POP_BACK_STACK_INCLUSIVE);

                HomeFragment homeFragment = new HomeFragment();
                homeFragment.setFragmentManager(requireActivity().getSupportFragmentManager());

                Bundle argsBundle = new Bundle();
                argsBundle.putBoolean("showComingSoon", true);
                homeFragment.setArguments(argsBundle);

                requireActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, homeFragment)
                        .commit();
            });
            btnBook.setEnabled(true);

            btnSnacks.setText("Watch Trailer");
            btnSnacks.setOnClickListener(v -> {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(trailer));
                startActivity(intent);
            });
        } else {
            // Now Showing: Setup seat clicks with dynamic booked seats
            setupSeatClicks(bookedSeatsList);

            btnBook.setOnClickListener(v -> {
                Log.d("SeatSelection", "Book button clicked. Selected seats: " + selectedSeats.toString());
                if (selectedSeats.isEmpty()) {
                    Toast.makeText(getContext(), "Please select at least one seat", Toast.LENGTH_LONG).show();
                } else {
                    double totalSeatPrice = selectedSeats.size() * seatPrice;
                    String seatsFormatted = formatSelectedSeats();

                    Log.d("SeatSelection", "Formatted seats: " + seatsFormatted);

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
                            0,
                            posterResId
                    );

                    requireActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fragment_container, fragment)
                            .addToBackStack(null)
                            .commit();
                }
            });

            btnSnacks.setOnClickListener(v -> {
                Log.d("SeatSelection", "Snacks button clicked. Selected seats: " + selectedSeats.toString());
                if (selectedSeats.isEmpty()) {
                    Toast.makeText(getContext(), "Please select at least one seat first", Toast.LENGTH_LONG).show();
                } else {
                    double totalSeatPrice = selectedSeats.size() * seatPrice;
                    String seatsFormatted = formatSelectedSeats();

                    SnacksFragment snacksFragment = SnacksFragment.newInstance(
                            name,
                            seatsFormatted,
                            selectedSeats.size(),
                            totalSeatPrice,
                            posterResId
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

    private void setupSeatClicks(ArrayList<String> bookedSeatsList) {
        Log.d("SeatSelection", "Setting up seat clicks. Booked seats: " + bookedSeatsList.toString());

        for (int i = 0; i < seatGrid.getChildCount(); i++) {
            View seatView = seatGrid.getChildAt(i);

            if (seatView instanceof TextView) {
                final TextView seat = (TextView) seatView;

                // IMPORTANT: Get the seat number from the TAG (not from text)
                final String seatNum = (String) seat.getTag();

                if (seatNum == null) {
                    Log.d("SeatSelection", "Seat at index " + i + " has null tag, skipping");
                    continue;
                }

                Log.d("SeatSelection", "Found seat: " + seatNum);

                // Check if this seat is pre-booked
                if (bookedSeatsList.contains(seatNum)) {
                    seat.setBackgroundResource(R.drawable.bg_seat_booked);
                    seat.setClickable(false);
                    seat.setEnabled(false);
                    Log.d("SeatSelection", "Seat " + seatNum + " is booked (disabled)");
                    continue;
                }

                // Set initial background for available seats
                seat.setBackgroundResource(R.drawable.bg_seat_available);
                seat.setClickable(true);
                seat.setEnabled(true);

                // Make available seats clickable
                seat.setOnClickListener(v -> {
                    // Get the seat number from the clicked view's tag
                    String clickedSeatNum = (String) seat.getTag();

                    Log.d("SeatSelection", "Seat clicked: " + clickedSeatNum);
                    Log.d("SeatSelection", "Current selected seats before: " + selectedSeats.toString());

                    if (selectedSeats.contains(clickedSeatNum)) {
                        // Deselect seat
                        selectedSeats.remove(clickedSeatNum);
                        seat.setBackgroundResource(R.drawable.bg_seat_available);
                        Toast.makeText(getContext(), "Deselected " + clickedSeatNum, Toast.LENGTH_SHORT).show();
                        Log.d("SeatSelection", "Deselected: " + clickedSeatNum);
                    } else {
                        // Select seat
                        selectedSeats.add(clickedSeatNum);
                        seat.setBackgroundResource(R.drawable.bg_seat_selected);
                        Toast.makeText(getContext(), "Selected " + clickedSeatNum, Toast.LENGTH_SHORT).show();
                        Log.d("SeatSelection", "Selected: " + clickedSeatNum);
                    }

                    Log.d("SeatSelection", "Current selected seats after: " + selectedSeats.toString());
                });
            }
        }

        Log.d("SeatSelection", "Seat setup complete. Total clickable seats: " +
                (seatGrid.getChildCount() - bookedSeatsList.size()));
    }
}