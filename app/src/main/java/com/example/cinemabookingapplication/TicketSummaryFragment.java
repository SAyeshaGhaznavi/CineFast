package com.example.cinemabookingapplication;

import android.content.*;
import android.os.Bundle;
import android.view.*;
import android.widget.*;

import androidx.fragment.app.Fragment;

public class TicketSummaryFragment extends Fragment {

    private String movieName;
    private String selectedSeats;

    public static TicketSummaryFragment newInstance(String bookingDetails, double total, String movieName, String selectedSeats) {
        Bundle args = new Bundle();
        args.putString("bookingDetails", bookingDetails);
        args.putDouble("total", total);
        args.putString("movieName", movieName);
        args.putString("selectedSeats", selectedSeats);

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

        tvBookingDetails.setText(bookingDetails);
        tvTotal.setText("Total Amount: $" + String.format("%.2f", total));

        // SAVE TO SharedPreferences
        SharedPreferences prefs = requireContext().getSharedPreferences("booking", Context.MODE_PRIVATE);

        prefs.edit()
                .putString("movie", movieName)
                .putString("seats", selectedSeats)
                .putString("total_amount", String.format("%.2f", total))
                .apply();

        // Done button to go back to Home
        btnDone.setOnClickListener(v -> {
            // Clear back stack and go to HomeFragment
            requireActivity().getSupportFragmentManager().popBackStack(null, androidx.fragment.app.FragmentManager.POP_BACK_STACK_INCLUSIVE);

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