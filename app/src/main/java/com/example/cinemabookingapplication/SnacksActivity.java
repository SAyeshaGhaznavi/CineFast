//package com.example.cinemabookingapplication;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.Button;
//import android.widget.ImageView;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import java.text.DecimalFormat;
//import java.util.HashMap;
//import java.util.Map;
//
//public class SnacksActivity extends AppCompatActivity {
//
//    private final Map<String, Double> snackPrices = new HashMap<String, Double>() {{
//        put("popcorn", 8.99);
//        put("nachos", 7.99);
//        put("drink", 5.99);
//        put("candy", 6.99);
//    }};
//
//    private int popcornQty = 0;
//    private int nachosQty = 0;
//    private int drinkQty = 0;
//    private int candyQty = 0;
//
//    private TextView tvPopcornQty, tvNachosQty, tvDrinkQty, tvCandyQty;
//    private TextView tvTotalAmount, tvMovieInfo, tvSelectedSeats;
//    private Button btnConfirm;
//    private ImageView btnBack;
//
//    private String movieName;
//    private String selectedSeats;
//    private String totalSeats;
//    private DecimalFormat df = new DecimalFormat("#0.00");
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_snacks);
//
//        getIntentData();
//
//        initializeViews();
//
//        setInitialData();
//
//        setupClickListeners();
//    }
//
//    private void getIntentData() {
//        movieName = getIntent().getStringExtra("movie_name");
//        selectedSeats = getIntent().getStringExtra("selected_seats");
//        totalSeats = getIntent().getStringExtra("total_seats");
//
//        if (movieName == null) movieName = "com.example.cinemabookingapplication.Movie";
//        if (selectedSeats == null) selectedSeats = "No seats selected";
//        if (totalSeats == null) totalSeats = "0";
//    }
//
//    private void initializeViews() {
//        tvPopcornQty = findViewById(R.id.tvPopcornQuantity);
//        tvNachosQty = findViewById(R.id.tvNachosQuantity);
//        tvDrinkQty = findViewById(R.id.tvDrinkQuantity);
//        tvCandyQty = findViewById(R.id.tvCandyQuantity);
//
//        tvMovieInfo = findViewById(R.id.tvMovieInfo);
//        tvSelectedSeats = findViewById(R.id.tvSelectedSeats);
//        tvTotalAmount = findViewById(R.id.tvTotalAmount);
//
//        btnConfirm = findViewById(R.id.btnConfirm);
//        btnBack = findViewById(R.id.btnBack);
//
//        Button btnPopcornAdd = findViewById(R.id.btnPopcornAdd);
//        Button btnNachosAdd = findViewById(R.id.btnNachosAdd);
//        Button btnDrinkAdd = findViewById(R.id.btnDrinkAdd);
//        Button btnCandyAdd = findViewById(R.id.btnCandyAdd);
//
//        btnPopcornAdd.setOnClickListener(v -> updateQuantity("popcorn", 1));
//        btnNachosAdd.setOnClickListener(v -> updateQuantity("nachos", 1));
//        btnDrinkAdd.setOnClickListener(v -> updateQuantity("drink", 1));
//        btnCandyAdd.setOnClickListener(v -> updateQuantity("candy", 1));
//
//        Button btnPopcornMinus = findViewById(R.id.btnPopcornMinus);
//        Button btnNachosMinus = findViewById(R.id.btnNachosMinus);
//        Button btnDrinkMinus = findViewById(R.id.btnDrinkMinus);
//        Button btnCandyMinus = findViewById(R.id.btnCandyMinus);
//
//        btnPopcornMinus.setOnClickListener(v -> updateQuantity("popcorn", -1));
//        btnNachosMinus.setOnClickListener(v -> updateQuantity("nachos", -1));
//        btnDrinkMinus.setOnClickListener(v -> updateQuantity("drink", -1));
//        btnCandyMinus.setOnClickListener(v -> updateQuantity("candy", -1));
//    }
//
//    private void setInitialData() {
//        tvMovieInfo.setText("com.example.cinemabookingapplication.Movie: " + movieName);
//        tvSelectedSeats.setText("Selected Seats: " + selectedSeats);
//        updateTotalDisplay();
//    }
//
//    private void setupClickListeners() {
//
//        btnConfirm.setOnClickListener(v -> {
//            if (popcornQty == 0 && nachosQty == 0 && drinkQty == 0 && candyQty == 0) {
//                Toast.makeText(this, "Please select at least one item", Toast.LENGTH_SHORT).show();
//                return;
//            }
//
//            // Create order summary
//            String orderSummary = createOrderSummary();
//            double totalAmount = calculateTotal();
//
//            // Navigate to Booking Details
//            Intent intent = new Intent(SnacksActivity.this, BookingDetailsActivity.class);
//            intent.putExtra("movie_name", movieName);
//            intent.putExtra("selected_seats", selectedSeats);
//            intent.putExtra("total_seats", totalSeats);
//            intent.putExtra("snacks_order", orderSummary);
//            intent.putExtra("total_amount", df.format(totalAmount));
//            startActivity(intent);
//        });
//    }
//
//    private void updateQuantity(String item, int change) {
//        switch (item) {
//            case "popcorn":
//                popcornQty = Math.max(0, popcornQty + change);
//                tvPopcornQty.setText(String.valueOf(popcornQty));
//                break;
//            case "nachos":
//                nachosQty = Math.max(0, nachosQty + change);
//                tvNachosQty.setText(String.valueOf(nachosQty));
//                break;
//            case "drink":
//                drinkQty = Math.max(0, drinkQty + change);
//                tvDrinkQty.setText(String.valueOf(drinkQty));
//                break;
//            case "candy":
//                candyQty = Math.max(0, candyQty + change);
//                tvCandyQty.setText(String.valueOf(candyQty));
//                break;
//        }
//        updateTotalDisplay();
//    }
//
//    private void updateTotalDisplay() {
//        double total = calculateTotal();
//        tvTotalAmount.setText("$" + df.format(total));
//    }
//
//    private double calculateTotal() {
//        return (popcornQty * snackPrices.get("popcorn")) +
//                (nachosQty * snackPrices.get("nachos")) +
//                (drinkQty * snackPrices.get("drink")) +
//                (candyQty * snackPrices.get("candy"));
//    }
//
//    private String createOrderSummary() {
//        StringBuilder summary = new StringBuilder();
//        if (popcornQty > 0) summary.append("Popcorn x").append(popcornQty).append("\n");
//        if (nachosQty > 0) summary.append("Nachos x").append(nachosQty).append("\n");
//        if (drinkQty > 0) summary.append("Soft Drink x").append(drinkQty).append("\n");
//        if (candyQty > 0) summary.append("Candy Mix x").append(candyQty).append("\n");
//        return summary.toString();
//    }
//}