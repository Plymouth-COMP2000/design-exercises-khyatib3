package com.example.comp2000assessment;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class BookingsDatabaseHelper extends SQLiteOpenHelper {
    private static final String TABLE_NAME = "Bookings";
    private static final int DATABASE_VER = 1;

    public BookingsDatabaseHelper(Context context) {
        super(context, TABLE_NAME, null, DATABASE_VER);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        //SQL for Bookings Table, with Primary Key bookingID
        String createAllBookings = "CREATE TABLE IF NOT EXISTS Bookings(" +
                "bookingID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "confirmed INTEGER NOT NULL," +
                "guest_first_name TEXT NOT NULL," +
                "guest_last_name TEXT NOT NULL," +
                "date TEXT NOT NULL," +
                "time TEXT NOT NULL," +
                "table_no INTEGER NOT NULL," +
                "no_guests INTEGER NOT NULL CHECK (no_guests > 0 AND no_guests <=10)" +
                ")";
        db.execSQL(createAllBookings);

        //SQL for confirmed bookings view (guest)
        String createGuestConfirmedView = "CREATE VIEW IF NOT EXISTS GConBookings " +
                "AS " +
                "SELECT date, time, no_guests, table_no " +
                "FROM Bookings " +
                "WHERE confirmed = 1";
        db.execSQL(createGuestConfirmedView);

        //SQL for unconfirmed requests view (guest)
        String createGuestUnconfirmedView = "CREATE VIEW IF NOT EXISTS GUnconfirmedReqs " +
                "AS " +
                "SELECT date, time, no_guests, table_no " +
                "FROM Bookings " +
                "WHERE confirmed = 0";
        db.execSQL(createGuestUnconfirmedView);

        //SQL for staff viewing confirmed bookings
        String createStaffConfirmedView = "CREATE VIEW IF NOT EXISTS StaffConBookings "+
                    "AS " +
                    "SELECT date, time, guest_first_name, guest_last_name, no_guests, table_no " +
                    "FROM Bookings " +
                    "WHERE confirmed = 1";
        db.execSQL(createStaffConfirmedView);

        //SQL for staff viewing booking requests
        String createStaffBookingsReqsView = "CREATE VIEW IF NOT EXISTS StaffBookingsReqs " +
                "AS " +
                "SELECT date, time, guest_first_name, guest_last_name, no_guests " +
                "FROM Bookings " +
                "WHERE confirmed = 0";
        db.execSQL(createStaffBookingsReqsView);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
    }

    //CREATE
    public boolean addBooking(BookingRecord booking) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues bookingValues = new ContentValues();
        bookingValues.put("confirmed", booking.confirmed);
        bookingValues.put("guest_first_name", booking.guestFirstName);
        bookingValues.put("guest_last_name", booking.guestLastName);
        bookingValues.put("date", booking.date);
        bookingValues.put("time", booking.time);
        bookingValues.put("table_no", booking.tableNo);

        //retrieving the new booking id so it can be set to the BookingRecord object booking
        long newBookingID = db.insert(TABLE_NAME, null, bookingValues);

        //checking whether the insert into the database was successful
        //only applying new booking id if it worked

        if (newBookingID > 0) {
            booking.setBookingID((int) newBookingID);
        }
        db.close();

        //returning if booking being added to the Bookings Table was successful
        return newBookingID > 0;
    }

    //READ
    //multiple required here, as I need to show:
    // guests confirmed bookings and unconfirmed requests
    // all unconfirmed requests and confirmed bookings for staff
    public ArrayList<BookingRecord> showGuestConfirmedBookings(String guest_first_name, String guest_last_name){
        //getting readable database
        SQLiteDatabase db = this.getReadableDatabase();

        //declaring arraylist that will store bookings and be returned
        ArrayList<BookingRecord> confirmedBookings = new ArrayList<>();


    }


    //UPDATE
    public boolean updateBooking(BookingRecord booking) {
        //first check booking passed has an id
        if (booking.getBookingID() > 0) {
            SQLiteDatabase db = getWritableDatabase();

            //retrieving booking attributes to be updated and setting them in content values
            ContentValues bookingVals = new ContentValues();
            bookingVals.put("confirmed", booking.confirmed);
            bookingVals.put("guest_first_name", booking.guestFirstName);
            bookingVals.put("guest_last_name", booking.guestLastName);
            bookingVals.put("date", booking.date);
            bookingVals.put("time", booking.time);
            bookingVals.put("table_no", booking.tableNo);

            //creating the where clause that identifies the booking to be updated
            String whereClause = "bookingID = ?";
            String[] whereArgs = {String.valueOf(booking.getBookingID())};

            //updating the db with the updated booking and closing db
            long updateResult = db.update(TABLE_NAME, bookingVals, whereClause, whereArgs);
            db.close();

            //return result
            return updateResult > 0;
        } else {
            return false;
        }

    }

    //DELETE
    public boolean deleteBooking(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int rows = db.delete(TABLE_NAME, "bookingID=?", new String[]{String.valueOf(id)});
        return rows > 0;
    }


}
