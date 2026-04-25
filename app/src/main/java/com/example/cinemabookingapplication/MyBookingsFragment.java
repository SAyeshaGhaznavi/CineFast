package com.example.cinemabookingapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MyBookingsFragment extends Fragment implements BookingAdapter.OnBookingCancelledListener {

    private RecyclerView rvBookings;
    private TextView tvNoBookings;
    private BookingAdapter bookingAdapter;
    private List<Booking> bookingList;
    private DatabaseReference bookingsRef;
    private String userId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bookings, container, false);

        rvBookings = view.findViewById(R.id.rvBookings);
        tvNoBookings = view.findViewById(R.id.tvNoBookings);

        rvBookings.setLayoutManager(new LinearLayoutManager(getContext()));
        bookingList = new ArrayList<>();
        bookingAdapter = new BookingAdapter(bookingList, getContext(), this);
        rvBookings.setAdapter(bookingAdapter);

        // Get current user ID
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
            bookingsRef = FirebaseDatabase.getInstance().getReference("bookings").child(userId);
            loadBookings();
        } else {
            tvNoBookings.setVisibility(View.VISIBLE);
            tvNoBookings.setText("Please login to view your bookings");
        }

        return view;
    }

    private void loadBookings() {
        bookingsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                bookingList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Booking booking = dataSnapshot.getValue(Booking.class);
                    if (booking != null) {
                        booking.setBookingId(dataSnapshot.getKey());
                        bookingList.add(booking);
                    }
                }

                if (bookingList.isEmpty()) {
                    rvBookings.setVisibility(View.GONE);
                    tvNoBookings.setVisibility(View.VISIBLE);
                    tvNoBookings.setText("No bookings found.\nBook a movie to see it here!");
                } else {
                    rvBookings.setVisibility(View.VISIBLE);
                    tvNoBookings.setVisibility(View.GONE);
                    bookingAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                tvNoBookings.setVisibility(View.VISIBLE);
                tvNoBookings.setText("Error loading bookings: " + error.getMessage());
                Toast.makeText(getContext(), "Failed to load bookings", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBookingCancelled(String bookingId) {
        for (int i = 0; i < bookingList.size(); i++) {
            if (bookingList.get(i).getBookingId().equals(bookingId)) {
                bookingList.remove(i);
                break;
            }
        }
        bookingAdapter.notifyDataSetChanged();

        if (bookingList.isEmpty()) {
            rvBookings.setVisibility(View.GONE);
            tvNoBookings.setVisibility(View.VISIBLE);
            tvNoBookings.setText("No bookings found.\nBook a movie to see it here!");
        }

        Toast.makeText(getContext(), "Booking Cancelled Successfully", Toast.LENGTH_SHORT).show();
    }
}