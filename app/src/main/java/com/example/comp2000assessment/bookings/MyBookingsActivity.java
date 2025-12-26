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
import com.example.comp2000assessment.users.AppUser;
import com.example.comp2000assessment.users.Login_Activity;
import com.example.comp2000assessment.users.ManageUser;

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

        //get the current user using ManageUser
        AppUser currentUser = ManageUser.getInstance().getCurrentUser();

        //check that current user isnt null
        if (currentUser == null) {
            //in case the app was killed in the background, send user back to the login screen
            //as a safety measure
            Intent intent = new Intent(this, Login_Activity.class);
            startActivity(intent);
            finish();
            return;
        }


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
                loadBookings(bookingType, currentUser);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //default should be unconfirmed requests
                loadBookings("Unconfirmed Requests",currentUser);
            }
        });

        BookingRecordAdapter adapter = new BookingRecordAdapter(this, bookingRecords);
        bookingRecycler.setAdapter(adapter);

        //home icon on click
        ImageButton homeIcon = findViewById(R.id.myBookingsHomeBtn);
        //setting on click functionality
        homeIcon.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(MyBookingsActivity.this, GuestHomepage.class);
                startActivity(intent);
            }
        });


    }

    public void loadBookings(String bookingType, AppUser currentUser){
        bookingRecords = new ArrayList<>();

        //getting current user's first and last name
        String fName = currentUser.getFirstname();
        String lName = currentUser.getLastname();

        //retrieving items of the current category from DB
        db = new BookingsDatabaseHelper(MyBookingsActivity.this);
        if (bookingType.equals("Confirmed Bookings")) {
            bookingRecords = db.showGuestConfirmedBookings(fName, lName);
        }else if (bookingType.equals("Unconfirmed Requests")){
            bookingRecords = db.showGuestUnconfirmedReqs(fName, lName);
        }

        //setting adapter to show list of bookings
        adapter = new BookingRecordAdapter(this, bookingRecords);
        bookingRecycler.setAdapter(adapter);


        if (bookingRecords.isEmpty()) {
            Toast.makeText(this, "No bookings requested", Toast.LENGTH_SHORT).show();
        }
    }
}