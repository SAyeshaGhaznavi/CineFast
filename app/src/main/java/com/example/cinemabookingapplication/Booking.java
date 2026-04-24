package com.example.cinemabookingapplication;

public class Booking {
    private String bookingId;
    private String movieName;
    private String moviePoster;
    private String date;
    private String time;
    private int ticketCount;
    private double totalPrice;
    private long timestamp;
    private String seats;
    private String snacks;

    public Booking() {}

    public Booking(String bookingId, String movieName, String moviePoster, String date, String time,
                   int ticketCount, double totalPrice, long timestamp, String seats, String snacks) {
        this.bookingId = bookingId;
        this.movieName = movieName;
        this.moviePoster = moviePoster;
        this.date = date;
        this.time = time;
        this.ticketCount = ticketCount;
        this.totalPrice = totalPrice;
        this.timestamp = timestamp;
        this.seats = seats;
        this.snacks = snacks;
    }

    public String getBookingId() { return bookingId; }
    public void setBookingId(String bookingId) { this.bookingId = bookingId; }

    public String getMovieName() { return movieName; }
    public void setMovieName(String movieName) { this.movieName = movieName; }

    public String getMoviePoster() { return moviePoster; }
    public void setMoviePoster(String moviePoster) { this.moviePoster = moviePoster; }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }

    public String getTime() { return time; }
    public void setTime(String time) { this.time = time; }

    public int getTicketCount() { return ticketCount; }
    public void setTicketCount(int ticketCount) { this.ticketCount = ticketCount; }

    public double getTotalPrice() { return totalPrice; }
    public void setTotalPrice(double totalPrice) { this.totalPrice = totalPrice; }

    public long getTimestamp() { return timestamp; }
    public void setTimestamp(long timestamp) { this.timestamp = timestamp; }

    public String getSeats() { return seats; }
    public void setSeats(String seats) { this.seats = seats; }

    public String getSnacks() { return snacks; }
    public void setSnacks(String snacks) { this.snacks = snacks; }
}