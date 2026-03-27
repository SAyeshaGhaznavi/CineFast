package com.example.cinemabookingapplication;

import android.os.Bundle;
import android.view.*;
import android.widget.*;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

public class SnacksFragment extends Fragment {

    List<Snack> snackList;
    private String movieName;
    private String selectedSeats;
    private int seatCount;
    private double seatTotalPrice;
    private int moviePosterResId; // Add this field

    public static SnacksFragment newInstance(String movieName, String selectedSeats, int seatCount, double seatTotalPrice, int moviePosterResId) {
        SnacksFragment fragment = new SnacksFragment();
        Bundle args = new Bundle();
        args.putString("movieName", movieName);
        args.putString("selectedSeats", selectedSeats);
        args.putInt("seatCount", seatCount);
        args.putDouble("seatTotalPrice", seatTotalPrice);
        args.putInt("moviePosterResId", moviePosterResId); // Add this
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_snacks, container, false);

        if (getArguments() != null) {
            movieName = getArguments().getString("movieName");
            selectedSeats = getArguments().getString("selectedSeats");
            seatCount = getArguments().getInt("seatCount");
            seatTotalPrice = getArguments().getDouble("seatTotalPrice");
            moviePosterResId = getArguments().getInt("moviePosterResId", R.drawable.poster); // Get poster
        }

        ListView listView = view.findViewById(R.id.snackListView);
        Button btnConfirm = view.findViewById(R.id.btnConfirm);

        TextView tvSelectedSeats = view.findViewById(R.id.tvSelectedSeats);
        if (tvSelectedSeats != null) {
            tvSelectedSeats.setText("Selected Seats: " + selectedSeats + " (" + seatCount + " seats - $" + String.format("%.2f", seatTotalPrice) + ")");
        }

        snackList = new ArrayList<>();

        snackList.add(new Snack("Popcorn", 8.99, R.drawable.popcorn));
        snackList.add(new Snack("Nachos", 7.99, R.drawable.nachos));
        snackList.add(new Snack("Drink", 5.99, R.drawable.drink));
        snackList.add(new Snack("Candy", 6.99, R.drawable.candy));

        SnackAdapter adapter = new SnackAdapter(getContext(), snackList);
        listView.setAdapter(adapter);

        btnConfirm.setOnClickListener(v -> {
            double snacksTotal = 0;
            StringBuilder snacksSummary = new StringBuilder();
            StringBuilder snacksDetails = new StringBuilder();

            for (Snack snack : snackList) {
                if (snack.quantity > 0) {
                    snacksTotal += snack.quantity * snack.price;
                    snacksSummary.append(snack.name).append(" x").append(snack.quantity)
                            .append(" = $").append(String.format("%.2f", snack.quantity * snack.price))
                            .append("\n");
                    snacksDetails.append(snack.name).append(" x").append(snack.quantity)
                            .append(" ($").append(String.format("%.2f", snack.quantity * snack.price))
                            .append(")\n");
                }
            }

            double grandTotal = seatTotalPrice + snacksTotal;

            String snacksDetailsStr;
            if (snacksSummary.length() > 0) {
                snacksDetailsStr = snacksDetails.toString();
            } else {
                snacksDetailsStr = "No snacks selected";
            }

            String bookingDetails = "Movie: " + movieName +
                    "\n\nSeats: " + selectedSeats +
                    "\nSeats Total: $" + String.format("%.2f", seatTotalPrice) +
                    "\n\nSnacks:\n" + snacksSummary.toString() +
                    "\nTotal Amount: $" + String.format("%.2f", grandTotal);

            // Pass the movie poster resource ID
            TicketSummaryFragment fragment = TicketSummaryFragment.newInstance(
                    bookingDetails,
                    grandTotal,
                    movieName,
                    selectedSeats,
                    snacksDetailsStr,
                    snacksTotal,
                    moviePosterResId // Pass the poster here
            );

            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .addToBackStack(null)
                    .commit();
        });

        return view;
    }
}