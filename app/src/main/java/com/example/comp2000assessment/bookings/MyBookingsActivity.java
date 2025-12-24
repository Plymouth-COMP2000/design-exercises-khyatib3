package com.example.comp2000assessment.bookings;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.comp2000assessment.homepages.GuestHomepage;
import com.example.comp2000assessment.R;
import com.example.comp2000assessment.adapters.BookingRecordAdapter;
import com.example.comp2000assessment.databases.BookingsDatabaseHelper;

import java.util.ArrayList;
import java.util.List;

public class MyBookingsActivity extends AppCompatActivity {
    List<BookingRecord> bookingRecords;
    Spinner bookingTypeSpinner;
    BookingsDatabaseHelper db;
    BookingRecordAdapter adapter;

    RecyclerView bookingRecycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_my_bookings);
        bookingTypeSpinner = findViewById(R.id.bookingTypeDropdown);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.my_bookings_page), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        bookingRecycler = findViewById(R.id.bookingsRecyclerView);
        bookingRecycler.setLayoutManager(new LinearLayoutManager(this));

        //getting details of user passed in the intent
        String user_firstname = getIntent().getStringExtra("user_firstname");
        String user_lastname = getIntent().getStringExtra("user_lastname");
        String user_contact = getIntent().getStringExtra("user_contact");
        String user_email = getIntent().getStringExtra("user_email");
        String user_username = getIntent().getStringExtra("user_username");
        String user_password = getIntent().getStringExtra("user_password");
        String user_usertype = getIntent().getStringExtra("user_usertype");
        boolean user_logged_in = getIntent().getBooleanExtra("user_logged_in", true);

        //setting options for spinner
        String[] categories = {"Confirmed Bookings", "Unconfirmed Requests"};
        //setting the spinner to the categories
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                categories
        );
        bookingTypeSpinner.setAdapter(spinnerAdapter);

        //adding functionality to the spinner
        //should show items of that category when selected
        bookingTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //getting selected category
                String bookingType = parent.getItemAtPosition(position).toString();

                //loading bookings as per bookingType
                //TODO CHANGE PASSING NAME SANDRA SMITH TO USER.FIRSTNAME AND USER.LASTNAME ONCE API IS IMPLEMENTED
                loadBookings(bookingType, user_firstname, user_lastname, user_contact, user_email, user_username, user_password, user_usertype, user_logged_in);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //default should be unconfirmed requests
                loadBookings("Unconfirmed Requests", user_firstname, user_lastname, user_contact, user_email, user_username, user_password, user_usertype, user_logged_in);
            }
        });

        BookingRecordAdapter adapter = new BookingRecordAdapter(this, bookingRecords, user_firstname, user_lastname, user_contact, user_email,
                user_username, user_password, user_usertype, user_logged_in);
        bookingRecycler.setAdapter(adapter);

        //home icon on click
        ImageButton homeIcon = findViewById(R.id.myBookingsHomeBtn);
        //setting on click functionality
        homeIcon.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(MyBookingsActivity.this, GuestHomepage.class);

                //passing details
                intent.putExtra("user_firstname", user_firstname);
                intent.putExtra("user_lastname", user_lastname);
                intent.putExtra("user_contact", user_contact);
                intent.putExtra("user_email", user_email);
                intent.putExtra("user_username", user_username);
                intent.putExtra("user_password", user_password);
                intent.putExtra("user_usertype", user_usertype);
                intent.putExtra("user_logged_in", user_logged_in);

                startActivity(intent);
            }
        });


    }

    public void loadBookings(String bookingType, String fName, String lName, String user_contact, String user_email, String user_username, String user_password, String user_usertype, boolean user_logged_in){
        bookingRecords = new ArrayList<>();
        //retrieving items of the current category from DB
        db = new BookingsDatabaseHelper(MyBookingsActivity.this);
        if (bookingType.equals("Confirmed Bookings")) {
            bookingRecords = db.showGuestConfirmedBookings(fName, lName);
        }else if (bookingType.equals("Unconfirmed Requests")){
            bookingRecords = db.showGuestUnconfirmedReqs(fName, lName);
        }

        //setting adapter to show list of bookings
        adapter = new BookingRecordAdapter(this, bookingRecords, fName, lName, user_contact, user_email,
                user_username, user_password, user_usertype, user_logged_in);
        bookingRecycler.setAdapter(adapter);


        if (bookingRecords.isEmpty()) {
            Toast.makeText(this, "No bookings requested", Toast.LENGTH_SHORT).show();
        }
    }
}