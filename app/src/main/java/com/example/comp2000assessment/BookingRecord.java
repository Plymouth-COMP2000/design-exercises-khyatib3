package com.example.comp2000assessment;

public class BookingRecord {
    public String date;
    public String time;
    public int numberOfGuests;
    public int peopleGroupIconId;
    public String guestFirstName;
    public String guestLastName;
    public boolean confirmed;
    public int tableNo;
    public String additionalRequest;
    private int bookingID;

    public int getBookingID() {
        return bookingID;
    }

    public void setBookingID(int bookingID) {
        this.bookingID = bookingID;
    }

    //for guest confirmed bookings
    public BookingRecord(String date, String time, int numberOfGuests, int tableNo, int peopleGroupIconId){
        this.date = date;
        this.time = time;
        this.numberOfGuests = numberOfGuests;
        this.tableNo = tableNo;
        this.peopleGroupIconId = peopleGroupIconId;
    }

    //for guest unconfirmed requests
    public BookingRecord(String date, String time, int numberOfGuests, int peopleGroupIconId){
        this.date = date;
        this.time = time;
        this.numberOfGuests = numberOfGuests;
        this.peopleGroupIconId = peopleGroupIconId;
    }

    //for staff: booking requests
    public BookingRecord(String date, String time, int numberOfGuests, String guestFirstName, String guestLastName, int peopleGroupIconId){
        this.date = date;
        this.time = time;
        this.numberOfGuests = numberOfGuests;
        this.guestFirstName = guestFirstName;
        this.guestLastName = guestLastName;
        this.peopleGroupIconId = peopleGroupIconId;
    }

    //for staff: confirmed bookings
    public BookingRecord(String date, String time, int numberOfGuests, String guestFirstName, String guestLastName, int tableNo, int peopleGroupIconId){
        this.date = date;
        this.time = time;
        this.numberOfGuests = numberOfGuests;
        this.guestFirstName = guestFirstName;
        this.guestLastName = guestLastName;
        this.tableNo = tableNo;
        this.peopleGroupIconId = peopleGroupIconId;
    }
}
