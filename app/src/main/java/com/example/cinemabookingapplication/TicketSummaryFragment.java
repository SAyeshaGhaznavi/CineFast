package com.example.cinemabookingapplication;

import android.content.*;
import android.os.Bundle;
import android.view.*;
import android.widget.*;

import androidx.fragment.app.Fragment;

public class TicketSummaryFragment extends Fragment {

    public static TicketSummaryFragment newInstance(String snacks, double total) {
        Bundle args = new Bundle();
        args.putString("snacks", snacks);
        args.putDouble("total", total);

        TicketSummaryFragment fragment = new TicketSummaryFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_ticket_summary, container, false);

        TextView tvSnacks = view.findViewById(R.id.tvSnacks);
        TextView tvTotal = view.findViewById(R.id.tvTotalAmount);

        String snacks = getArguments().getString("snacks");
        double total = getArguments().getDouble("total");

        tvSnacks.setText(snacks);
        tvTotal.setText("Total: $" + total);

        // SAVE TO SharedPreferences
        SharedPreferences prefs = requireContext().getSharedPreferences("booking", Context.MODE_PRIVATE);

        prefs.edit()
                .putString("movie", "Sample Movie")
                .putString("seats", "3")
                .putString("price", String.valueOf(total))
                .apply();

        return view;
    }
}