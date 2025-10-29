package com.example.comp2000assessment;

public class BookingRecord {
    public String date;
    public String time;
    public int numberOfGuests;
    public int peopleGroupIconId;

    public BookingRecord(String date, String time, int numberOfGuests, int peopleGroupIconId){
        this.date = date;
        this.time = time;
        this.numberOfGuests = numberOfGuests;
        this.peopleGroupIconId = peopleGroupIconId;
    }
}
