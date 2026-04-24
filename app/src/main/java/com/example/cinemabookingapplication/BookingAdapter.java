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

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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

        if (booking.getMoviePoster() != null && !booking.getMoviePoster().isEmpty()) {
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
        }

        boolean isFutureBooking = isBookingInFuture(booking.getDate(), booking.getTime());

        if (!isFutureBooking) {
            holder.btnCancel.setEnabled(false);
            holder.btnCancel.setAlpha(0.5f);
            holder.btnCancel.setText("Past");
        } else {
            holder.btnCancel.setEnabled(true);
            holder.btnCancel.setAlpha(1f);
            holder.btnCancel.setText("Cancel");
        }

        holder.btnCancel.setOnClickListener(v -> {
            if (!isFutureBooking) {
                Toast.makeText(context, "Past bookings cannot be cancelled", Toast.LENGTH_SHORT).show();
                return;
            }

            new AlertDialog.Builder(context)
                    .setTitle("Cancel Booking")
                    .setMessage("Are you sure you want to cancel this booking?")
                    .setPositiveButton("Yes", (dialog, which) -> {
                        DatabaseReference bookingRef = FirebaseDatabase.getInstance()
                                .getReference("bookings")
                                .child(booking.getBookingId());

                        bookingRef.removeValue().addOnSuccessListener(aVoid -> {
                            Toast.makeText(context, "Booking Cancelled Successfully", Toast.LENGTH_SHORT).show();
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

    private boolean isBookingInFuture(String date, String time) {
        try {
            String dateTimeString = date + " " + time;
            SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault());
            Date bookingDateTime = sdf.parse(dateTimeString);
            Date currentDateTime = new Date();

            return bookingDateTime != null && bookingDateTime.after(currentDateTime);
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
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