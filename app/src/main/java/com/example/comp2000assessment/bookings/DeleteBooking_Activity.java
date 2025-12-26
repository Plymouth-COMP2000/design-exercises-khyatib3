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
import com.example.comp2000assessment.notifications.NotificationsHelper;
import com.example.comp2000assessment.users.AppUser;
import com.example.comp2000assessment.users.Login_Activity;
import com.example.comp2000assessment.users.ManageUser;

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

        //get the current user using ManageUser
        AppUser currentUser = ManageUser.getInstance().getCurrentUser();
        String user_firstname, user_lastname, user_username, user_password, user_email, user_contact, user_usertype;
        boolean user_logged_in;

        //check that current user isnt null
        if (currentUser == null) {
            //in case the app was killed in the background, send user back to the login screen
            //as a safety measure
            Intent intent = new Intent(this, Login_Activity.class);
            startActivity(intent);
            finish();
            return;
        }

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
                    finish();
                }else if(screenName.equals("AllTables")) {
                    Intent intent = new Intent(DeleteBooking_Activity.this, All_Tables_Activity.class);

                    startActivity(intent);
                    finish();
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

                        //push notification
                        NotificationsHelper.displayNotification(DeleteBooking_Activity.this, "Booking Deleted!", "You successfully deleted your booking!", "booking");


                        startActivity(intent);
                        return;
                    }else if(screenName.equals("AllTables")) {
                        Intent intent = new Intent(DeleteBooking_Activity.this, All_Tables_Activity.class);
                        Toast.makeText(DeleteBooking_Activity.this, "This booking was deleted successfully", Toast.LENGTH_SHORT).show();

                        //push notification
                        NotificationsHelper.displayNotification(DeleteBooking_Activity.this, "Booking Deleted!", "This booking was cancelled.", "booking");


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