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

        adapter = new S_BookingRecordAdapter(this, tableBookings);
        tableBookingsRecycler = findViewById(R.id.tableNoRecyclerView);
        tableBookingsRecycler.setAdapter(adapter);



    }
}