package com.example.comp2000assessment.bookings;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.comp2000assessment.databases.BookingsDatabaseHelper;
import com.example.comp2000assessment.R;
import com.example.comp2000assessment.adapters.S_BookingRecordAdapter;

import java.util.ArrayList;
import java.util.List;

public class TableNo_ReservationActivity extends AppCompatActivity {
    List<BookingRecord> tableBookings;
    RecyclerView tableBookingsRecycler;

    S_BookingRecordAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_table_no_reservation);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.tableNo_reservation_page), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //getting staff details
        String staff_firstname = getIntent().getStringExtra("staff_firstname");
        String staff_lastname = getIntent().getStringExtra("staff_lastname");
        String staff_contact = getIntent().getStringExtra("staff_contact");
        String staff_email = getIntent().getStringExtra("staff_email");
        String staff_username = getIntent().getStringExtra("staff_username");
        String staff_password = getIntent().getStringExtra("staff_password");
        String staff_usertype = getIntent().getStringExtra("staff_usertype");
        boolean staff_logged_in = getIntent().getBooleanExtra("staff_logged_in", true);

        //getting table number passed from All Tables
        int tableNumber = getIntent().getIntExtra("tableNumber", 0);

        //getting textview of page title to change and display table no: x
        TextView tableNoTitle = findViewById(R.id.tableNoTitle);
        tableNoTitle.setText("Table " + tableNumber);


        //back button functionality
        ImageButton tableNoToAllTablesBtn = findViewById(R.id.tableNoToAllTablesBtn);
        tableNoToAllTablesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TableNo_ReservationActivity.this, All_Tables_Activity.class);

                //passing the user details
                intent.putExtra("staff_firstname", staff_firstname);
                intent.putExtra("staff_lastname", staff_lastname);
                intent.putExtra("staff_contact", staff_contact);
                intent.putExtra("staff_email", staff_email);
                intent.putExtra("staff_username", staff_username);
                intent.putExtra("staff_password", staff_password);
                intent.putExtra("staff_usertype", staff_usertype);
                intent.putExtra("staff_logged_in", staff_logged_in);

                startActivity(intent);
            }
        });

        //logic to show all bookings for that table

        //getting instance of db
        BookingsDatabaseHelper db = new BookingsDatabaseHelper(TableNo_ReservationActivity.this);
        tableBookings = new ArrayList<>();
        tableBookings = db.showStaffConfirmedBookings(tableNumber);

        tableBookingsRecycler = findViewById(R.id.tableNoRecyclerView);
        tableBookingsRecycler.setLayoutManager(new androidx.recyclerview.widget.LinearLayoutManager(this));

        adapter = new S_BookingRecordAdapter(this, tableBookings, staff_firstname, staff_lastname, staff_contact, staff_email, staff_username, staff_password, staff_usertype, staff_logged_in);
        tableBookingsRecycler = findViewById(R.id.tableNoRecyclerView);
        tableBookingsRecycler.setAdapter(adapter);



    }
}