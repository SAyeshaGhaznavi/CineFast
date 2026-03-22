package com.example.cinemabookingapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

public class SeatBookingActivity extends AppCompatActivity {

    private String movieName;
    private String movieDetails;
    private List<String> selectedSeats = new ArrayList<>();
    private GridLayout seatGrid;
    private TextView tvMovieName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seat_booking);

        initializeViews();

        getIntentData();

        setupClickListeners();
    }

    private void initializeViews() {
        tvMovieName = findViewById(R.id.tvMovieName);
        seatGrid = findViewById(R.id.seatGrid);

        if (seatGrid == null) {
            Toast.makeText(this, "Error: seatGrid not found", Toast.LENGTH_LONG).show();
        }
    }

    private void getIntentData() {
        movieName = getIntent().getStringExtra("movie_name");
        movieDetails = getIntent().getStringExtra("movie_details");

        if (movieName == null) {
            movieName = "Movie";
        }

        tvMovieName.setText(movieName);
    }

    private void setupClickListeners() {
        ImageView btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> finish());

        setupSeatClickListeners();

        Button btnBookSeats = findViewById(R.id.btnBookSeats);
        btnBookSeats.setOnClickListener(v -> {
            if (selectedSeats.isEmpty()) {
                Toast.makeText(this, "Please select at least one seat", Toast.LENGTH_SHORT).show();
                return;
            }

            Intent intent = new Intent(SeatBookingActivity.this, BookingDetailsActivity.class);
            intent.putExtra("movie_name", movieName);
            intent.putExtra("selected_seats", getSelectedSeatsString());
            intent.putExtra("total_seats", String.valueOf(selectedSeats.size()));
            startActivity(intent);
        });

        Button btnProceedToSnacks = findViewById(R.id.btnProceedToSnacks);
        btnProceedToSnacks.setOnClickListener(v -> {
            if (selectedSeats.isEmpty()) {
                Toast.makeText(this, "Please select at least one seat", Toast.LENGTH_SHORT).show();
                return;
            }

            Intent intent = new Intent(SeatBookingActivity.this, SnacksActivity.class);
            intent.putExtra("movie_name", movieName);
            intent.putExtra("selected_seats", getSelectedSeatsString());
            intent.putExtra("total_seats", String.valueOf(selectedSeats.size()));
            startActivity(intent);
        });
    }

    private void setupSeatClickListeners() {
        if (seatGrid == null) return;

        for (int i = 0; i < seatGrid.getChildCount(); i++) {
            View seatView = seatGrid.getChildAt(i);

            if (seatView instanceof TextView) {
                TextView seat = (TextView) seatView;
                final String seatNumber = seat.getText().toString();
                String seatTag = seat.getTag() != null ? seat.getTag().toString() : "";

                // If seat is booked, make it non-clickable
                if ("booked".equals(seatTag)) {
                    seat.setClickable(false);
                    seat.setAlpha(0.5f);
                    continue;
                }

                if ("selected".equals(seatTag)) {
                    selectedSeats.add(seatNumber);
                }

                seat.setOnClickListener(v -> {
                    TextView clickedSeat = (TextView) v;
                    String seatNum = clickedSeat.getText().toString();

                    if (selectedSeats.contains(seatNum)) {
                        // Deselect seat
                        selectedSeats.remove(seatNum);
                        clickedSeat.setBackgroundColor(ContextCompat.getColor(this, android.R.color.darker_gray));
                        clickedSeat.setTextColor(ContextCompat.getColor(this, android.R.color.white));
                        Toast.makeText(this, "Deselected seat: " + seatNum, Toast.LENGTH_SHORT).show();
                    } else {
                        // Select seat
                        selectedSeats.add(seatNum);
                        clickedSeat.setBackgroundColor(ContextCompat.getColor(this, R.color.white));
                        clickedSeat.setTextColor(ContextCompat.getColor(this, android.R.color.black));
                        Toast.makeText(this, "Selected seat: " + seatNum, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
    }

    private String getSelectedSeatsString() {
        if (selectedSeats.isEmpty()) {
            return "No seats selected";
        }

        StringBuilder seats = new StringBuilder();
        for (int i = 0; i < selectedSeats.size(); i++) {
            if (i > 0) {
                seats.append(", ");
            }
            seats.append(selectedSeats.get(i));
        }
        return seats.toString();
    }
}