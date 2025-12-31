package com.example.comp2000assessment.databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.comp2000assessment.R;
import com.example.comp2000assessment.bookings.BookingRecord;

import java.util.ArrayList;

public class BookingsDatabaseHelper extends SQLiteOpenHelper {
    private static final String TABLE_NAME = "Bookings";
    private static final int DATABASE_VER = 8;
    private static final String BOOKING_LOG_TABLE = "BookingsLog";

    public BookingsDatabaseHelper(Context context) {
        super(context, TABLE_NAME, null, DATABASE_VER);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        //SQL for Bookings Table, with Primary Key bookingID
        String createAllBookings = "CREATE TABLE IF NOT EXISTS Bookings(" +
                "bookingID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "confirmed INTEGER NOT NULL, " +
                "guest_first_name TEXT NOT NULL, " +
                "guest_last_name TEXT NOT NULL, " +
                "date TEXT NOT NULL, " +
                "time TEXT NOT NULL, " +
                "table_no INTEGER NOT NULL, " +
                "no_guests INTEGER NOT NULL CHECK (no_guests > 0 AND no_guests <=10), " +
                "special_request TEXT " +
                ")";
        db.execSQL(createAllBookings);

        //SQL for confirmed bookings view (guest)
        String createGuestConfirmedView = "CREATE VIEW IF NOT EXISTS GConBookings " +
                "AS " +
                "SELECT bookingID, date, time, no_guests, table_no, guest_first_name, guest_last_name, special_request " +
                "FROM Bookings " +
                "WHERE confirmed = 1";
        db.execSQL(createGuestConfirmedView);

        //SQL for unconfirmed requests view (guest)
        String createGuestUnconfirmedView = "CREATE VIEW IF NOT EXISTS GUnconfirmedReqs " +
                "AS " +
                "SELECT bookingID, date, time, no_guests, table_no, guest_first_name, guest_last_name, special_request " +
                "FROM Bookings " +
                "WHERE confirmed = 0";
        db.execSQL(createGuestUnconfirmedView);

        //SQL for staff viewing confirmed bookings
        String createStaffConfirmedView = "CREATE VIEW IF NOT EXISTS StaffConBookings " +
                "AS " +
                "SELECT bookingID, date, time, guest_first_name, guest_last_name, no_guests, table_no, special_request " +
                "FROM Bookings " +
                "WHERE confirmed = 1";
        db.execSQL(createStaffConfirmedView);

        //SQL for staff viewing booking requests
        String createStaffBookingsReqsView = "CREATE VIEW IF NOT EXISTS StaffBookingsReqs " +
                "AS " +
                "SELECT bookingID, date, time, guest_first_name, guest_last_name, no_guests, special_request " +
                "FROM Bookings " +
                "WHERE confirmed = 0";
        db.execSQL(createStaffBookingsReqsView);

        String createBookingsLogTable = "CREATE TABLE IF NOT EXISTS BookingsLog(" +
                "bookingID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "confirmed INTEGER NOT NULL," +
                "guest_first_name TEXT NOT NULL," +
                "guest_last_name TEXT NOT NULL," +
                "date TEXT NOT NULL," +
                "time TEXT NOT NULL," +
                "table_no INTEGER NOT NULL," +
                "no_guests INTEGER NOT NULL CHECK (no_guests > 0 AND no_guests <=10), " +
                "special_request TEXT "+
                ")";
        db.execSQL(createBookingsLogTable);

        String createTrigger = "CREATE TRIGGER IF NOT EXISTS trg_AddNewBooking " +
                "AFTER INSERT ON Bookings " +
                "BEGIN " +
                "INSERT INTO BookingsLog(confirmed, guest_first_name, guest_last_name, date, time, table_no, no_guests, special_request) " +
                "VALUES (NEW.confirmed, NEW.guest_first_name, NEW.guest_last_name, NEW.date, NEW.time, NEW.table_no, NEW.no_guests, NEW.special_request);" +
                "END;";
        db.execSQL(createTrigger);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        db.execSQL("DROP VIEW IF EXISTS GConBookings");
        db.execSQL("DROP VIEW IF EXISTS GUnconfirmedReqs");
        db.execSQL("DROP VIEW IF EXISTS StaffConBookings");
        db.execSQL("DROP VIEW IF EXISTS StaffBookingsReqs");
        db.execSQL("DROP TABLE IF EXISTS " + BOOKING_LOG_TABLE);
        db.execSQL("DROP TRIGGER IF EXISTS trg_AddNewBooking");

        onCreate(db);
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
        bookingValues.put("no_guests", booking.numberOfGuests);
        bookingValues.put("special_request", booking.specialRequest);


        //retrieving the new booking id so it can be set to the BookingRecord object booking
        long newBookingID = db.insert(TABLE_NAME, null, bookingValues);

        //checking whether the insert into the database was successful
        //only applying new booking id if it worked

        if (newBookingID > 0) {
            booking.setBookingID((int) newBookingID);
            Log.e("New booking added, bookingID: ", String.valueOf(newBookingID));
        }else{
            Log.e("New booking not added", String.valueOf(newBookingID));
        }

        //returning if booking being added to the Bookings Table was successful
        return newBookingID > 0;
    }

    //READ
    //multiple required here, as I need to show:
    // guests confirmed bookings and unconfirmed requests
    // all unconfirmed requests and confirmed bookings for staff
    public ArrayList<BookingRecord> showGuestConfirmedBookings(String guest_first_name, String guest_last_name) {
        //getting readable database
        SQLiteDatabase db = this.getReadableDatabase();

        //declaring arraylist that will store bookings and be returned
        ArrayList<BookingRecord> confirmedBookings = new ArrayList<>();
        //using appropriate view to show confirmed bookings
        String viewName = "GConBookings";

        //creating query to get bookings for given user
        String[] columns = {"bookingID", "date", "time", "no_guests", "table_no, special_request"};
        String selection = "guest_first_name = ? AND guest_last_name = ?";
        String[] selectionArgs = {guest_first_name, guest_last_name};

        Cursor cursor;
        cursor = db.query(viewName, columns, selection, selectionArgs, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                //getting the values of the attributes of the booking
                String date = cursor.getString(cursor.getColumnIndexOrThrow("date"));
                String time = cursor.getString(cursor.getColumnIndexOrThrow("time"));
                int noGuests = cursor.getInt(cursor.getColumnIndexOrThrow("no_guests"));
                int tableNo = cursor.getInt(cursor.getColumnIndexOrThrow("table_no"));
                String special_request = cursor.getString(cursor.getColumnIndexOrThrow("special_request"));
                int bookingID = cursor.getInt(cursor.getColumnIndexOrThrow("bookingID"));

                //creating a BookingRecord object with the values
                BookingRecord booking = new BookingRecord(date, time, special_request, noGuests, tableNo, R.drawable.ic_people_group);
                booking.confirmed = true;
                booking.tableNo = tableNo;
                booking.setBookingID(bookingID);

                //adding the booking to the arraylist
                confirmedBookings.add(booking);

            } while (cursor.moveToNext());
        }

        //closing cursor and database and returning the list of confirmed bookings
        cursor.close();
        return confirmedBookings;

    }

    //to show guests their unconfirmed bookings
    public ArrayList<BookingRecord> showGuestUnconfirmedReqs(String guest_first_name, String guest_last_name) {
        //getting readable database
        SQLiteDatabase db = this.getReadableDatabase();

        //declaring arraylist that will store bookings and be returned
        ArrayList<BookingRecord> guestBookingReqs = new ArrayList<>();
        //using appropriate view to show confirmed bookings
        String viewName = "GUnconfirmedReqs";

        //creating query to get bookings for given user
        String[] columns = {"bookingID", "date", "time", "no_guests, special_request"};
        String selection = "guest_first_name = ? AND guest_last_name = ?";
        String[] selectionArgs = {guest_first_name, guest_last_name};

        Cursor cursor;
        cursor = db.query(viewName, columns, selection, selectionArgs, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                //getting the values of the attributes of the booking
                String date = cursor.getString(cursor.getColumnIndexOrThrow("date"));
                String time = cursor.getString(cursor.getColumnIndexOrThrow("time"));
                String special_request = cursor.getString(cursor.getColumnIndexOrThrow("special_request"));
                int noGuests = cursor.getInt(cursor.getColumnIndexOrThrow("no_guests"));
                int bookingID = cursor.getInt(cursor.getColumnIndexOrThrow("bookingID"));

                //creating a BookingRecord object with the values
                BookingRecord booking = new BookingRecord(date, time, special_request, noGuests, R.drawable.ic_people_group);
                booking.confirmed = false;
                booking.setBookingID(bookingID);

                //adding the booking to the arraylist
                guestBookingReqs.add(booking);

            } while (cursor.moveToNext());
        }

        //closing cursor and database and returning the list of confirmed bookings
        cursor.close();
        return guestBookingReqs;

    }

    //for staff to see unconfirmed requests
    public ArrayList<BookingRecord> showStaffUnconfirmedReqs() {
        //getting readable database
        SQLiteDatabase db = this.getReadableDatabase();

        ArrayList<BookingRecord> allRequests = new ArrayList<>();
        String viewName = "StaffBookingsReqs";

        String[] columns = {"bookingID", "date", "time", "guest_first_name", "guest_last_name", "no_guests", "special_request"};
        Cursor cursor;
        cursor = db.query(viewName, columns, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                String date = cursor.getString(cursor.getColumnIndexOrThrow("date"));
                String time = cursor.getString(cursor.getColumnIndexOrThrow("time"));
                String firstName = cursor.getString(cursor.getColumnIndexOrThrow("guest_first_name"));
                String lastName = cursor.getString(cursor.getColumnIndexOrThrow("guest_last_name"));
                String specialRequest = cursor.getString(cursor.getColumnIndexOrThrow("special_request"));
                int noGuests = cursor.getInt(cursor.getColumnIndexOrThrow("no_guests"));
                int bookingID = cursor.getInt(cursor.getColumnIndexOrThrow("bookingID"));

                BookingRecord booking = new BookingRecord(date, time, noGuests, firstName, lastName, specialRequest, R.drawable.ic_people_group);
                booking.confirmed = false;
                booking.setBookingID(bookingID);

                allRequests.add(booking);

            } while (cursor.moveToNext());

        }
        cursor.close();

        return allRequests;

    }

    //to see bookings, which are confirmed and for a specific table
    public ArrayList<BookingRecord> showStaffConfirmedBookings(int tableNo) {
        SQLiteDatabase db = this.getReadableDatabase();

        ArrayList<BookingRecord> allConfirmedBookings = new ArrayList<>();
        String viewName = "StaffConBookings";

        String[] columns = {"bookingID", "date", "time", "guest_first_name", "guest_last_name", "no_guests", "table_no", "special_request"};
        String selection = "table_no = ?";
        String[] selectionArgs = {String.valueOf(tableNo)};

        Cursor cursor;
        cursor = db.query(viewName, columns, selection, selectionArgs, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                String date = cursor.getString(cursor.getColumnIndexOrThrow("date"));
                String time = cursor.getString(cursor.getColumnIndexOrThrow("time"));
                String firstName = cursor.getString(cursor.getColumnIndexOrThrow("guest_first_name"));
                String lastName = cursor.getString(cursor.getColumnIndexOrThrow("guest_last_name"));
                String special_request = cursor.getString(cursor.getColumnIndexOrThrow("special_request"));
                int table_No = cursor.getInt(cursor.getColumnIndexOrThrow("table_no"));
                int noGuests = cursor.getInt(cursor.getColumnIndexOrThrow("no_guests"));
                int bookingID = cursor.getInt(cursor.getColumnIndexOrThrow("bookingID"));

                //using staff confirmed bookings constructor
                BookingRecord booking = new BookingRecord(date, time, noGuests, firstName, lastName, special_request, table_No, R.drawable.ic_people_group);
                booking.confirmed = true;
                booking.setBookingID(bookingID);

                allConfirmedBookings.add(booking);
            } while (cursor.moveToNext());


        }

        cursor.close();
        return allConfirmedBookings;
    }

    //UPDATE
    public boolean updateBooking(BookingRecord booking) {
        //first check booking passed has an id
        if (booking.getBookingID() > 0) {
            SQLiteDatabase db = this.getWritableDatabase();

            //retrieving booking attributes to be updated and setting them in content values
            ContentValues bookingVals = new ContentValues();
            bookingVals.put("confirmed", booking.confirmed);
            bookingVals.put("guest_first_name", booking.guestFirstName);
            bookingVals.put("guest_last_name", booking.guestLastName);
            bookingVals.put("no_guests", booking.numberOfGuests);
            bookingVals.put("date", booking.date);
            bookingVals.put("time", booking.time);
            bookingVals.put("table_no", booking.tableNo);

            //creating the where clause that identifies the booking to be updated
            String whereClause = "bookingID = ?";
            String[] whereArgs = {String.valueOf(booking.getBookingID())};

            if(booking.confirmed == true){
                //updating the db with the updated booking and closing db
                long updateResult = db.update(TABLE_NAME, bookingVals, whereClause, whereArgs);
                //return result
                return updateResult > 0;
            }else if (booking.confirmed == false){
                boolean result = this.deleteBooking(booking.getBookingID());
                return result;
            }

        } else {
            return false;
        }

        return false;
    }

    //DELETE
    public boolean deleteBooking(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int rows = db.delete(TABLE_NAME, "bookingID=?", new String[]{String.valueOf(id)});
        return rows > 0;
    }

    //in the event a user updates their first or last name in account settings, change the booking name so they can still see their bookings
    public boolean updateBookingHolderName(String oldFirstName, String newFirstName, String oldLastName, String newLastName){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("guest_first_name", newFirstName);
        values.put("guest_last_name", newLastName);

        //where clause: where names equal old names
        String whereClause = "guest_first_name = ? AND guest_last_name = ?";
        String[] whereArgs = {oldFirstName, oldLastName};

        //update booking
        int rowsAffected = db.update(TABLE_NAME, values, whereClause, whereArgs);

        //update booking log table just for consistency
        db.update(BOOKING_LOG_TABLE, values, whereClause, whereArgs);

        return rowsAffected > 0;
    }


}
