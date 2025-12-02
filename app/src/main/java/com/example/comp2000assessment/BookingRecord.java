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



    public BookingRecord(String date, String time, int numberOfGuests, int peopleGroupIconId){
        this.date = date;
        this.time = time;
        this.numberOfGuests = numberOfGuests;
        this.peopleGroupIconId = peopleGroupIconId;
    }

    public BookingRecord(String date, String time, int numberOfGuests, String guestFirstName, String guestLastName){
        this.date = date;
        this.time = time;
        this.numberOfGuests = numberOfGuests;
        this.guestFirstName = guestFirstName;
        this.guestLastName = guestLastName;
    }
}
