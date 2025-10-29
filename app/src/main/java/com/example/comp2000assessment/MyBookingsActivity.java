package com.example.comp2000assessment;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MyBookingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_my_bookings);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.my_bookings_page), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        RecyclerView bookingRecycler = findViewById(R.id.bookingsRecyclerView);
        bookingRecycler.setLayoutManager(new LinearLayoutManager(this));

        DividerItemDecoration divider = new DividerItemDecoration(
                bookingRecycler.getContext(),
                DividerItemDecoration.VERTICAL
        );
        divider.setDrawable(ContextCompat.getDrawable(this, R.drawable.divider));
        bookingRecycler.addItemDecoration(divider);


        List<BookingRecord> bookingRecords = new ArrayList<>();
        BookingRecord booking1 = new BookingRecord("Sat. 4th Oct", "18:30", 4, R.drawable.ic_people_group);
        bookingRecords.add(booking1);

        BookingRecord booking2 = new BookingRecord("Thur. 11th Nov", "14:30", 3, R.drawable.ic_people_group);
        bookingRecords.add(booking2);

        BookingRecord booking3 = new BookingRecord("Mon. 15th Nov", "20:30", 6, R.drawable.ic_people_group);
        bookingRecords.add(booking3);

        BookingRecordAdapter adapter = new BookingRecordAdapter(this, bookingRecords);
        bookingRecycler.setAdapter(adapter);



    }
}