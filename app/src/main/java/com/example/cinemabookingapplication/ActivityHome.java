//package com.example.cinemabookingapplication;
//
//import android.content.Intent;
//import android.net.Uri;
//import android.os.Bundle;
//import android.widget.Button;
//import android.widget.Toast;
//
//import androidx.activity.EdgeToEdge;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.core.graphics.Insets;
//import androidx.core.view.ViewCompat;
//import androidx.core.view.WindowInsetsCompat;
//
//public class ActivityHome extends AppCompatActivity {
//
//    private final String[][] movies = {
//            {"The Dark Knight", "Action / 152 min", "https://www.youtube.com/watch?v=EXeTwQWrcwY"},
//            {"Inception", "Sci-Fi / 148 min", "https://www.youtube.com/watch?v=YoHD9XEInc0"},
//            {"Interstellar", "Sci-Fi / 169 min", "https://www.youtube.com/watch?v=zSWdZVtXT7E"},
//            {"The Shawshank Redemption", "Drama / 142 min", "https://www.youtube.com/watch?v=6hB3S9bIaco"}
//    };
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
//        setContentView(R.layout.activity_home);
//
//        Button btnToday = findViewById(R.id.btnToday);
//        Button btnTomorrow = findViewById(R.id.btnTomorrow);
//
//        btnToday.setOnClickListener(v -> {
//            btnToday.setBackgroundTintList(getColorStateList(android.R.color.holo_red_dark));
//            btnTomorrow.setBackgroundTintList(getColorStateList(android.R.color.darker_gray));
//            Toast.makeText(this, "Showing movies for Today", Toast.LENGTH_SHORT).show();
//        });
//
//        btnTomorrow.setOnClickListener(v -> {
//            btnTomorrow.setBackgroundTintList(getColorStateList(android.R.color.holo_red_dark));
//            btnToday.setBackgroundTintList(getColorStateList(android.R.color.darker_gray));
//            Toast.makeText(this, "Showing movies for Tomorrow", Toast.LENGTH_SHORT).show();
//        });
//
//        setupMovieCardClickListeners();
//
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });
//    }
//
//    private void setupMovieCardClickListeners() {
//        setupMovieButtons(1, 0);
//        setupMovieButtons(2, 1);
//        setupMovieButtons(3, 2);
//        setupMovieButtons(4, 3);
//    }
//
//    private void setupMovieButtons(int movieNumber, int movieIndex) {
//
//        // Booking button
//        Button btnBook = findViewById(
//                getResources().getIdentifier(
//                        "btnBookSeats" + movieNumber,
//                        "id",
//                        getPackageName()
//                )
//        );
//
//        if (btnBook != null) {
//            btnBook.setOnClickListener(v -> {
//                Intent intent = new Intent(ActivityHome.this, SeatBookingActivity.class);
//                intent.putExtra("movie_name", movies[movieIndex][0]);      // ✅ com.example.cinemabookingapplication.Movie Name
//                intent.putExtra("movie_details", movies[movieIndex][1]);  // Optional
//                intent.putExtra("movie_trailer", movies[movieIndex][2]);  // Optional
//                startActivity(intent);
//            });
//        }
//
//        // Trailer button
//        Button btnTrailer = findViewById(
//                getResources().getIdentifier(
//                        "btnTrailer" + movieNumber,
//                        "id",
//                        getPackageName()
//                )
//        );
//
//        if (btnTrailer != null) {
//            btnTrailer.setOnClickListener(v -> {
//                Toast.makeText(this,
//                        "Opening trailer for " + movies[movieIndex][0],
//                        Toast.LENGTH_SHORT).show();
//
//                openYouTubeTrailer(movies[movieIndex][2]);
//            });
//        }
//    }
//
//    private void openYouTubeTrailer(String url) {
//        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
//        startActivity(intent);
//    }
//}