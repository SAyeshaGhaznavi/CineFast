package com.example.cinemabookingapplication;

import android.content.*;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.*;
import android.widget.*;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class TicketSummaryFragment extends Fragment {

    private String movieName;
    private String selectedSeats;
    private String snacksDetails;
    private double snacksTotal;
    private double seatTotal;
    private String bookingDetails;
    private double total;
    private int moviePosterResId;

    public static TicketSummaryFragment newInstance(String bookingDetails, double total, String movieName,
                                                    String selectedSeats, String snacksDetails,
                                                    double snacksTotal, int moviePosterResId,
                                                    String date, String time, long timestamp) {
        Bundle args = new Bundle();
        args.putString("bookingDetails", bookingDetails);
        args.putDouble("total", total);
        args.putString("movieName", movieName);
        args.putString("selectedSeats", selectedSeats);
        args.putString("snacksDetails", snacksDetails);
        args.putDouble("snacksTotal", snacksTotal);
        args.putInt("moviePosterResId", moviePosterResId);
        args.putString("date", date);
        args.putString("time", time);
        args.putLong("timestamp", timestamp);

        TicketSummaryFragment fragment = new TicketSummaryFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public static TicketSummaryFragment newInstance(String bookingDetails, double total, String movieName,
                                                    String selectedSeats, String snacksDetails, double snacksTotal, int moviePosterResId) {
        Bundle args = new Bundle();
        args.putString("bookingDetails", bookingDetails);
        args.putDouble("total", total);
        args.putString("movieName", movieName);
        args.putString("selectedSeats", selectedSeats);
        args.putString("snacksDetails", snacksDetails);
        args.putDouble("snacksTotal", snacksTotal);
        args.putInt("moviePosterResId", moviePosterResId);

        TicketSummaryFragment fragment = new TicketSummaryFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ticket_summary, container, false);

        ImageView ivMoviePoster = view.findViewById(R.id.ivMoviePoster);
        TextView tvMovieName = view.findViewById(R.id.tvMovieName);
        TextView tvBookingDetails = view.findViewById(R.id.tvBookingDetails);
        TextView tvSnacks = view.findViewById(R.id.tvSnacks);
        TextView tvTotal = view.findViewById(R.id.tvTotalAmount);
        Button btnDone = view.findViewById(R.id.btnDone);
        Button btnShareWhatsApp = view.findViewById(R.id.btnShareWhatsApp);

        bookingDetails = getArguments().getString("bookingDetails");
        total = getArguments().getDouble("total");
        movieName = getArguments().getString("movieName");
        selectedSeats = getArguments().getString("selectedSeats");
        snacksDetails = getArguments().getString("snacksDetails");
        snacksTotal = getArguments().getDouble("snacksTotal");
        moviePosterResId = getArguments().getInt("moviePosterResId", R.drawable.poster);
        seatTotal = total - snacksTotal;

        if (moviePosterResId != 0) {
            ivMoviePoster.setImageResource(moviePosterResId);
        }
        tvMovieName.setText(movieName);

        tvBookingDetails.setText(bookingDetails);

        if (snacksDetails != null && !snacksDetails.isEmpty() && !snacksDetails.equals("No snacks selected")) {
            tvSnacks.setText(snacksDetails);
        } else {
            tvSnacks.setText("No snacks selected");
        }

        tvTotal.setText("$" + String.format("%.2f", total));

        String snacksInfo;
        if (snacksDetails != null && !snacksDetails.isEmpty() && !snacksDetails.equals("No snacks selected")) {
            snacksInfo = snacksDetails;
        } else {
            snacksInfo = "No snacks selected";
        }

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

        saveBookingToFirebase();

        btnShareWhatsApp.setOnClickListener(v -> shareViaWhatsApp());

        btnDone.setOnClickListener(v -> {
            requireActivity().getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);

            HomeFragment homeFragment = new HomeFragment();
            homeFragment.setFragmentManager(requireActivity().getSupportFragmentManager());

            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, homeFragment)
                    .commit();
        });

        return view;
    }

    private void shareViaWhatsApp() {
        String message = "🎬 *CINEMA FAST - TICKET BOOKING* 🎬\n\n" +
                "━━━━━━━━━━━━━━━━━━━━━━\n" +
                "🎥 *MOVIE:* " + movieName + "\n" +
                "━━━━━━━━━━━━━━━━━━━━━━\n" +
                "💺 *SEATS:* " + selectedSeats + "\n" +
                "💰 *SEATS TOTAL:* $" + String.format("%.2f", seatTotal) + "\n" +
                "━━━━━━━━━━━━━━━━━━━━━━\n";

        // Add snacks information if any
        if (snacksDetails != null && !snacksDetails.isEmpty() && !snacksDetails.equals("No snacks selected")) {
            message += "🍿 *SNACKS:*\n" + snacksDetails +
                    "💰 *SNACKS TOTAL:* $" + String.format("%.2f", snacksTotal) + "\n" +
                    "━━━━━━━━━━━━━━━━━━━━━━\n";
        } else {
            message += "🍿 *SNACKS:* No snacks selected\n" +
                    "━━━━━━━━━━━━━━━━━━━━━━\n";
        }

        message += "💵 *GRAND TOTAL:* $" + String.format("%.2f", total) + "\n" +
                "━━━━━━━━━━━━━━━━━━━━━━\n\n" +
                "✅ *Booking Confirmed!*\n" +
                "📅 *Date:* " + getCurrentDate() + "\n" +
                "⏰ *Time:* " + getCurrentTime() + "\n\n" +
                "Thank you for choosing Cinema FAST! 🎉";

        // Try to send via WhatsApp
        try {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.setPackage("com.whatsapp");
            intent.putExtra(Intent.EXTRA_TEXT, message);

            if (intent.resolveActivity(requireActivity().getPackageManager()) != null) {
                startActivity(intent);
            } else {
                showShareOptions(message);
            }
        } catch (Exception e) {
            showShareOptions(message);
        }
    }

    private void showShareOptions(String message) {
        new android.app.AlertDialog.Builder(requireContext())
                .setTitle("Share Booking")
                .setMessage("WhatsApp is not installed. Would you like to share via another app?")
                .setPositiveButton("Share via Other", (dialog, which) -> {
                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    shareIntent.setType("text/plain");
                    shareIntent.putExtra(Intent.EXTRA_TEXT, message);
                    startActivity(Intent.createChooser(shareIntent, "Share Ticket via"));
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    private void saveBookingToFirebase() {
        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            return;
        }

        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference bookingsRef = FirebaseDatabase.getInstance()
                .getReference("bookings")
                .child(userId)
                .push();

        String bookingId = bookingsRef.getKey();
        int ticketCount = selectedSeats.split(",").length;

        // Get timestamp from arguments
        long timestamp = getArguments().getLong("timestamp", System.currentTimeMillis());
        String date = getArguments().getString("date", "13.04.2025");
        String time = getArguments().getString("time", "22:15");

        Booking booking = new Booking(
                bookingId,
                movieName,
                String.valueOf(moviePosterResId),
                date,
                time,
                ticketCount,
                total,
                timestamp,
                selectedSeats,
                snacksDetails != null ? snacksDetails : "No snacks selected"
        );

        bookingsRef.setValue(booking);
    }

    private String getCurrentDate() {
        java.text.SimpleDateFormat dateFormat = new java.text.SimpleDateFormat("dd/MM/yyyy", java.util.Locale.getDefault());
        return dateFormat.format(new java.util.Date());
    }

    private String getCurrentTime() {
        java.text.SimpleDateFormat timeFormat = new java.text.SimpleDateFormat("HH:mm", java.util.Locale.getDefault());
        return timeFormat.format(new java.util.Date());
    }
}