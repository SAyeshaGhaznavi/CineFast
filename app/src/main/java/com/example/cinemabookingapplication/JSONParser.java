package com.example.cinemabookingapplication;

import android.content.Context;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class JSONParser {

    public static String loadJSONFromAsset(Context context, String fileName) {
        String json = null;
        try {
            InputStream is = context.getAssets().open(fileName);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return json;
    }

    public static ArrayList<Movie> parseNowShowingMovies(Context context, String date) {
        ArrayList<Movie> movieList = new ArrayList<>();
        try {
            String jsonString = loadJSONFromAsset(context, "movies.json");
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONObject nowShowing = jsonObject.getJSONObject("now_showing");

            String dateKey = date.equals("Today") ? "today" : "tomorrow";
            JSONArray moviesArray = nowShowing.getJSONArray(dateKey);

            for (int i = 0; i < moviesArray.length(); i++) {
                JSONObject movieJson = moviesArray.getJSONObject(i);

                String name = movieJson.getString("name");
                String details = movieJson.getString("details");
                String trailer = movieJson.getString("trailer");
                String posterName = movieJson.getString("posterResId");
                boolean isComingSoon = movieJson.getBoolean("isComingSoon");

                // Get drawable resource ID by name
                int posterResId = context.getResources().getIdentifier(posterName, "drawable", context.getPackageName());
                if (posterResId == 0) {
                    posterResId = R.drawable.poster; // fallback
                }

                Movie movie = new Movie(name, details, trailer, posterResId, isComingSoon);

                // Add booked seats from JSON
                JSONArray bookedSeatsArray = movieJson.getJSONArray("bookedSeats");
                for (int j = 0; j < bookedSeatsArray.length(); j++) {
                    movie.bookedSeats.add(bookedSeatsArray.getString(j));
                }

                movieList.add(movie);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return movieList;
    }

    public static ArrayList<Movie> parseComingSoonMovies(Context context) {
        ArrayList<Movie> movieList = new ArrayList<>();
        try {
            String jsonString = loadJSONFromAsset(context, "movies.json");
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONArray comingSoonArray = jsonObject.getJSONArray("coming_soon");

            for (int i = 0; i < comingSoonArray.length(); i++) {
                JSONObject movieJson = comingSoonArray.getJSONObject(i);

                String name = movieJson.getString("name");
                String details = movieJson.getString("details");
                String trailer = movieJson.getString("trailer");
                String posterName = movieJson.getString("posterResId");
                boolean isComingSoon = movieJson.getBoolean("isComingSoon");

                int posterResId = context.getResources().getIdentifier(posterName, "drawable", context.getPackageName());
                if (posterResId == 0) {
                    posterResId = R.drawable.poster;
                }

                Movie movie = new Movie(name, details, trailer, posterResId, isComingSoon);
                movieList.add(movie);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return movieList;
    }
}