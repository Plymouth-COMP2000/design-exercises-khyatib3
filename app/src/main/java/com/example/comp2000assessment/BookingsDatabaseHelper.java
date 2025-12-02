package com.example.comp2000assessment;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BookingsDatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "Bookings";
    private static final int DATABASE_VER = 1;

    public BookingsDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VER);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        String createAllBookings = "CREATE TABLE IF NOT EXISTS Bookings(" +
                                        "bookingID INTEGER PRIMARY KEY AUTOINCREMENT,"+
                                        "confirmed INTEGER NOT NULL,"+
                                        "guest_first_name TEXT NOT NULL,"+
                                        "guest_last_name TEXT NOT NULL," +
                                        "date TEXT NOT NULL," +
                                        "time TEXT NOT NULL," +
                                        "table_no INTEGER NOT NULL,"+
                                        "no_guests INTEGER NOT NULL CHECK (no_guests > 0 AND no_guests <=10)"+
                                    ")";
        db.execSQL(createAllBookings);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ DATABASE_NAME);
    }
}
