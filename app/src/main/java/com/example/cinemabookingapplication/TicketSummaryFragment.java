package com.example.cinemabookingapplication;

import android.content.*;
import android.os.Bundle;
import android.view.*;
import android.widget.*;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public class TicketSummaryFragment extends Fragment {

    private String movieName;
    private String selectedSeats;
    private String snacksDetails;
    private double snacksTotal;
    private double seatTotal;

    public static TicketSummaryFragment newInstance(String bookingDetails, double total, String movieName, String selectedSeats) {
        return newInstance(bookingDetails, total, movieName, selectedSeats, "", 0);
    }

    public static TicketSummaryFragment newInstance(String bookingDetails, double total, String movieName,
                                                    String selectedSeats, String snacksDetails, double snacksTotal) {
        Bundle args = new Bundle();
        args.putString("bookingDetails", bookingDetails);
        args.putDouble("total", total);
        args.putString("movieName", movieName);
        args.putString("selectedSeats", selectedSeats);
        args.putString("snacksDetails", snacksDetails);
        args.putDouble("snacksTotal", snacksTotal);

        TicketSummaryFragment fragment = new TicketSummaryFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ticket_summary, container, false);

        TextView tvBookingDetails = view.findViewById(R.id.tvBookingDetails);
        TextView tvTotal = view.findViewById(R.id.tvTotalAmount);
        Button btnDone = view.findViewById(R.id.btnDone);

        String bookingDetails = getArguments().getString("bookingDetails");
        double total = getArguments().getDouble("total");
        movieName = getArguments().getString("movieName");
        selectedSeats = getArguments().getString("selectedSeats");
        snacksDetails = getArguments().getString("snacksDetails");
        snacksTotal = getArguments().getDouble("snacksTotal");
        seatTotal = total - snacksTotal;

        tvBookingDetails.setText(bookingDetails);
        tvTotal.setText("Total Amount: $" + String.format("%.2f", total));

        // Format snacks information for SharedPreferences
        String snacksInfo;
        if (snacksDetails != null && !snacksDetails.isEmpty() && !snacksDetails.equals("No snacks selected")) {
            snacksInfo = snacksDetails;
        } else {
            snacksInfo = "No snacks selected";
        }

        // SAVE TO SharedPreferences - Store all required information including snacks
        SharedPreferences prefs = requireContext().getSharedPreferences("booking", Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("movie", movieName);
        editor.putString("seats", selectedSeats);
        editor.putInt("seat_count", selectedSeats.split(",").length);
        editor.putFloat("seat_price_total", (float) seatTotal);
        editor.putString("snacks", snacksInfo);
        editor.putFloat("snacks_total", (float) snacksTotal);
        editor.putString("total_amount", String.format("%.2f", total));
        editor.apply();

        // Done button to go back to Home
        btnDone.setOnClickListener(v -> {
            // Clear back stack and go to HomeFragment
            requireActivity().getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);

            // Replace with HomeFragment
            HomeFragment homeFragment = new HomeFragment();
            homeFragment.setFragmentManager(requireActivity().getSupportFragmentManager());

            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, homeFragment)
                    .commit();
        });

        return view;
    }
}