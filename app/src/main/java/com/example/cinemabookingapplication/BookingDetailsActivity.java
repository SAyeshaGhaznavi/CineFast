package com.example.cinemabookingapplication;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class BookingDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_details);

        String movieName = getIntent().getStringExtra("movie_name");
        String selectedSeats = getIntent().getStringExtra("selected_seats");
        String totalSeats = getIntent().getStringExtra("total_seats");
        String snacksOrder = getIntent().getStringExtra("snacks_order");
        String totalAmount = getIntent().getStringExtra("total_amount");

        TextView tvMovieName = findViewById(R.id.tvMovieName);
        TextView tvSeats = findViewById(R.id.tvSeats);
        TextView tvSnacks = findViewById(R.id.tvSnacks);
        TextView tvTotalAmount = findViewById(R.id.tvTotalAmount);
        TextView tvThankYou = findViewById(R.id.tvThankYou);

        tvMovieName.setText("com.example.cinemabookingapplication.Movie: " + movieName);
        tvSeats.setText("Seats: " + selectedSeats + " (" + totalSeats + " seats)");

        if (snacksOrder != null && !snacksOrder.isEmpty()) {
            tvSnacks.setText("Snacks Order:\n" + snacksOrder);
        } else {
            tvSnacks.setText("Snacks Order: None");
        }

        if (totalAmount != null) {
            tvTotalAmount.setText("Total Amount: $" + totalAmount);
        }

        tvThankYou.setText("Thank you for booking with CineFAST!");
    }
}