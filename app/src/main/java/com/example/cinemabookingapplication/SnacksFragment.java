package com.example.cinemabookingapplication;

import android.os.Bundle;
import android.view.*;
import android.widget.*;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

public class SnacksFragment extends Fragment {

    List<Snack> snackList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_snacks, container, false);

        ListView listView = view.findViewById(R.id.snackListView);
        Button btnConfirm = view.findViewById(R.id.btnConfirm);

        snackList = new ArrayList<>();

        snackList.add(new Snack("Popcorn", 8.99, R.drawable.popcorn));
        snackList.add(new Snack("Nachos", 7.99, R.drawable.nachos));
        snackList.add(new Snack("Drink", 5.99, R.drawable.drink));
        snackList.add(new Snack("Candy", 6.99, R.drawable.candy));

        SnackAdapter adapter = new SnackAdapter(getContext(), snackList);
        listView.setAdapter(adapter);

        btnConfirm.setOnClickListener(v -> {

            double total = 0;
            StringBuilder summary = new StringBuilder();

            for (Snack snack : snackList) {
                if (snack.quantity > 0) {
                    total += snack.quantity * snack.price;
                    summary.append(snack.name).append(" x").append(snack.quantity).append("\n");
                }
            }

            TicketSummaryFragment fragment =
                    TicketSummaryFragment.newInstance(summary.toString(), total);

            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .addToBackStack(null)
                    .commit();
        });

        return view;
    }
}