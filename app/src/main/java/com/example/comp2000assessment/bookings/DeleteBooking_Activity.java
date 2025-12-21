package com.example.comp2000assessment.bookings;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.comp2000assessment.R;
import com.example.comp2000assessment.databases.BookingsDatabaseHelper;

public class DeleteBooking_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_delete_booking);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.delete_booking_page), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //receiving values passed in intent
        int bookingID = getIntent().getIntExtra("bookingID", -1);
        String screenName = getIntent().getStringExtra("screenName");


        Button cancelDeleteBookingBtn = findViewById(R.id.cancelDelBookingBtn);
        cancelDeleteBookingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(screenName.equals("MyBookings")){
                    Intent intent = new Intent(DeleteBooking_Activity.this, MyBookingsActivity.class);
                    startActivity(intent);
                    return;
                }else if(screenName.equals("AllTables")) {
                    Intent intent = new Intent(DeleteBooking_Activity.this, All_Tables_Activity.class);
                    startActivity(intent);
                    return;
                }
            }
        });

        Button deleteBookingBtn = findViewById(R.id.confirmDeleteBookingBtn);
        deleteBookingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //getting instance of database
                BookingsDatabaseHelper db = new BookingsDatabaseHelper(DeleteBooking_Activity.this);

                boolean deleteResult = db.deleteBooking(bookingID);

                if(deleteResult){
                    if(screenName.equals("MyBookings")){
                        Intent intent = new Intent(DeleteBooking_Activity.this, MyBookingsActivity.class);
                        Toast.makeText(DeleteBooking_Activity.this, "This booking was deleted successfully", Toast.LENGTH_SHORT).show();
                        startActivity(intent);
                        return;
                    }else if(screenName.equals("AllTables")) {
                        Intent intent = new Intent(DeleteBooking_Activity.this, All_Tables_Activity.class);
                        Toast.makeText(DeleteBooking_Activity.this, "This booking was deleted successfully", Toast.LENGTH_SHORT).show();
                        startActivity(intent);
                        return;
                    }else{
                        Toast.makeText(DeleteBooking_Activity.this, "Error! Could not delete this booking", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }
}