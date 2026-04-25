package com.example.cinemabookingapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class BookingAdapter extends RecyclerView.Adapter<BookingAdapter.ViewHolder> {

    private List<Booking> bookingList;
    private Context context;
    private OnBookingCancelledListener listener;

    public interface OnBookingCancelledListener {
        void onBookingCancelled(String bookingId);
    }

    public BookingAdapter(List<Booking> bookingList, Context context, OnBookingCancelledListener listener) {
        this.bookingList = bookingList;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_booking, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Booking booking = bookingList.get(position);

        holder.tvMovieName.setText(booking.getMovieName());
        holder.tvDateTime.setText(booking.getDate() + " • " + booking.getTime());
        holder.tvTickets.setText("Tickets: " + booking.getTicketCount());
        holder.tvSeats.setText("Seats: " + booking.getSeats());

        // Load movie poster
        try {
            int posterResId = context.getResources().getIdentifier(
                    booking.getMoviePoster(), "drawable", context.getPackageName());
            if (posterResId != 0) {
                holder.ivMoviePoster.setImageResource(posterResId);
            } else {
                holder.ivMoviePoster.setImageResource(R.drawable.poster);
            }
        } catch (Exception e) {
            holder.ivMoviePoster.setImageResource(R.drawable.poster);
        }

        // Check if booking can be cancelled using timestamp
        boolean isFutureBooking = isBookingInFuture(booking.getTimestamp());

        if (!isFutureBooking) {
            // Past booking - disable cancel button
            holder.btnCancel.setEnabled(false);
            holder.btnCancel.setAlpha(0.5f);
            holder.btnCancel.setText("Past");
        } else {
            holder.btnCancel.setEnabled(true);
            holder.btnCancel.setAlpha(1f);
            holder.btnCancel.setText("Cancel");
        }

        // Cancel button click listener
        holder.btnCancel.setOnClickListener(v -> {
            if (!isFutureBooking) {
                Toast.makeText(context, "Past bookings cannot be cancelled", Toast.LENGTH_SHORT).show();
                return;
            }

            new AlertDialog.Builder(context)
                    .setTitle("Cancel Booking")
                    .setMessage("Are you sure you want to cancel this booking?")
                    .setPositiveButton("Yes", (dialog, which) -> {
                        // Delete from Firebase
                        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
                        DatabaseReference bookingRef = FirebaseDatabase.getInstance()
                                .getReference("bookings")
                                .child(userId)
                                .child(booking.getBookingId());

                        bookingRef.removeValue().addOnSuccessListener(aVoid -> {
                            listener.onBookingCancelled(booking.getBookingId());
                        }).addOnFailureListener(e -> {
                            Toast.makeText(context, "Failed to cancel: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        });
                    })
                    .setNegativeButton("No", null)
                    .show();
        });
    }

    @Override
    public int getItemCount() {
        return bookingList.size();
    }

    private boolean isBookingInFuture(long timestamp) {
        long currentTime = System.currentTimeMillis();
        return timestamp > currentTime;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivMoviePoster;
        TextView tvMovieName, tvDateTime, tvTickets, tvSeats;
        Button btnCancel;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivMoviePoster = itemView.findViewById(R.id.ivMoviePoster);
            tvMovieName = itemView.findViewById(R.id.tvMovieName);
            tvDateTime = itemView.findViewById(R.id.tvDateTime);
            tvTickets = itemView.findViewById(R.id.tvTickets);
            tvSeats = itemView.findViewById(R.id.tvSeats);
            btnCancel = itemView.findViewById(R.id.btnCancel);
        }
    }
}