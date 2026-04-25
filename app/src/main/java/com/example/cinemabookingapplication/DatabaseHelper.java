package com.example.cinemabookingapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "cinema.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_SNACKS = "snacks";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_PRICE = "price";
    private static final String COLUMN_IMAGE_NAME = "image_name";

    private static final String CREATE_TABLE_SNACKS =
            "CREATE TABLE " + TABLE_SNACKS + "(" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_NAME + " TEXT NOT NULL, " +
                    COLUMN_PRICE + " REAL NOT NULL, " +
                    COLUMN_IMAGE_NAME + " TEXT NOT NULL" +
                    ")";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_SNACKS);
        insertInitialSnacks(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SNACKS);
        onCreate(db);
    }

    private void insertInitialSnacks(SQLiteDatabase db) {
        insertSnack(db, "Popcorn", 8.99, "popcorn");
        insertSnack(db, "Nachos", 7.99, "nachos");
        insertSnack(db, "Drink", 5.99, "drink");
        insertSnack(db, "Candy", 6.99, "candy");
    }

    private void insertSnack(SQLiteDatabase db, String name, double price, String imageName) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_PRICE, price);
        values.put(COLUMN_IMAGE_NAME, imageName);
        db.insert(TABLE_SNACKS, null, values);
    }

    public List<Snack> getAllSnacks(Context context) {
        List<Snack> snackList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_SNACKS, null, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID));
                String name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME));
                double price = cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_PRICE));
                String imageName = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_IMAGE_NAME));

                int imageResId = context.getResources().getIdentifier(imageName, "drawable", context.getPackageName());
                if (imageResId == 0) {
                    imageResId = R.drawable.poster;
                }

                Snack snack = new Snack(name, price, imageResId);
                snackList.add(snack);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return snackList;
    }
}