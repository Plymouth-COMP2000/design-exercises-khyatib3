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
    public String specialRequest;
    private int bookingID;



    public int getBookingID() {
        return bookingID;
    }

    public void setBookingID(int bookingID) {
        this.bookingID = bookingID;
    }
    public String getSpecialRequest() {
        return specialRequest;
    }

    public void setSpecialRequest(String specialRequest) {
        this.specialRequest = specialRequest;
    }

    //constructor for a new table booking request (used in reservation enquiry)
    public BookingRecord(String guestFirstName, String guestLastName, int numberOfGuests, String date, String time, int tableNo, String specialRequest, int peopleGroupIconId){
        this.guestFirstName = guestFirstName;
        this.guestLastName = guestLastName;
        this.numberOfGuests = numberOfGuests;
        this.date = date;
        this.time = time;
        this.tableNo = 0;
        this.specialRequest = specialRequest;
        this.confirmed = false;
        this.peopleGroupIconId = peopleGroupIconId;

    }

    //for guest confirmed bookings
    public BookingRecord(String date, String time, String specialRequest, int numberOfGuests, int tableNo, int peopleGroupIconId){
        this.date = date;
        this.time = time;
        this.numberOfGuests = numberOfGuests;
        this.tableNo = tableNo;
        this.peopleGroupIconId = peopleGroupIconId;
        this.specialRequest = specialRequest;
    }

    //for guest unconfirmed requests
    public BookingRecord(String date, String time, String specialRequest, int numberOfGuests, int peopleGroupIconId){
        this.date = date;
        this.time = time;
        this.numberOfGuests = numberOfGuests;
        this.peopleGroupIconId = peopleGroupIconId;
        this.specialRequest = specialRequest;
    }

    //for staff: booking requests
    public BookingRecord(String date, String time, int numberOfGuests, String guestFirstName, String guestLastName, String specialRequest, int peopleGroupIconId){
        this.date = date;
        this.time = time;
        this.numberOfGuests = numberOfGuests;
        this.guestFirstName = guestFirstName;
        this.guestLastName = guestLastName;
        this.specialRequest = specialRequest;
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
