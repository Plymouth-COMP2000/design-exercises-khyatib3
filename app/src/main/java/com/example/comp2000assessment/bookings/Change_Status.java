package com.example.comp2000assessment.bookings;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.comp2000assessment.databases.BookingsDatabaseHelper;
import com.example.comp2000assessment.R;

public class Change_Status extends AppCompatActivity {

    Spinner tableNoSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_change_status);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //receiving passed values from open requests intent
        String guest_fullName = getIntent().getStringExtra("fullName");
        String firstName = getIntent().getStringExtra("firstName");
        String lastName = getIntent().getStringExtra("lastName");
        String date = getIntent().getStringExtra("date");
        String time = getIntent().getStringExtra("time");
        String specialRequest = getIntent().getStringExtra("specialRequest");
        int noOfGuests = getIntent().getIntExtra("noGuests", 0);
        int bookingID = getIntent().getIntExtra("bookingID", -1);

        TextView guestFullName = findViewById(R.id.guestFullName);
        guestFullName.setText(guest_fullName);

        TextView noGuestsDisplay = findViewById(R.id.cs_guestNo);
        noGuestsDisplay.setText(String.format("%d", noOfGuests));

        TextView bookingDate = findViewById(R.id.cs_date);
        bookingDate.setText(date);

        TextView bookingTime = findViewById(R.id.cs_time);
        bookingTime.setText(time);

        tableNoSpinner = findViewById(R.id.assignTableNoSpinner);

        Button confirmResBtn = findViewById(R.id.confirmResBtn);
        confirmResBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //get selected table no from spinner
                int assignedTableNo = Integer.parseInt(tableNoSpinner.getSelectedItem().toString());

                //update booking in database
                //use constructor for staff confirmed bookings as here we are confirming booking
                BookingRecord booking = new BookingRecord(date, time, noOfGuests, firstName, lastName, specialRequest, assignedTableNo, R.drawable.ic_people_group);

                //setting bookingID to it
                booking.setBookingID(bookingID);

                //setting booking to confirmed
                booking.confirmed = true;

                BookingsDatabaseHelper db = new BookingsDatabaseHelper(Change_Status.this);
                boolean updateResult = db.updateBooking(booking);

                if (updateResult) {
                    Intent intent = new Intent(Change_Status.this, Open_Requests.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(Change_Status.this, "Error updating booking", Toast.LENGTH_SHORT).show();
                }

            }
        });


        ImageButton changeStatusGoBackBtn = findViewById(R.id.changeStatusGoBackBtn);
        changeStatusGoBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Change_Status.this, Open_Requests.class);
                startActivity(intent);
            }
        });

        Button denyRequestBtn = findViewById(R.id.denyRequestBtn);
        denyRequestBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BookingRecord booking = new BookingRecord(date, time, noOfGuests, firstName, lastName, specialRequest, 0, R.drawable.ic_people_group);

                //setting bookingID to it
                booking.setBookingID(bookingID);

                //setting booking to confirmed
                booking.confirmed = false;

                BookingsDatabaseHelper db = new BookingsDatabaseHelper(Change_Status.this);
                boolean updateResult = db.updateBooking(booking);

                if(updateResult) {
                    Intent intent = new Intent(Change_Status.this, Open_Requests.class);

                    Toast.makeText(Change_Status.this, "Booking denied: " + guest_fullName, Toast.LENGTH_SHORT).show();

                    startActivity(intent);
                }
            }
        });
    }
}