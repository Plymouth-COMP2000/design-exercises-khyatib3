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

        //getting user details from account created
        String user_firstname = getIntent().getStringExtra("user_firstname");
        String user_lastname = getIntent().getStringExtra("user_lastname");
        String user_contact = getIntent().getStringExtra("user_contact");
        String user_email = getIntent().getStringExtra("user_email");
        String user_username = getIntent().getStringExtra("user_username");
        String user_password = getIntent().getStringExtra("user_password");
        String user_usertype = getIntent().getStringExtra("user_usertype");
        boolean user_logged_in = getIntent().getBooleanExtra("user_logged_in", true);

        //receiving values passed in intent
        int bookingID = getIntent().getIntExtra("bookingID", -1);
        String screenName = getIntent().getStringExtra("screenName");


        Button cancelDeleteBookingBtn = findViewById(R.id.cancelDelBookingBtn);
        cancelDeleteBookingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(screenName.equals("MyBookings")){
                    Intent intent = new Intent(DeleteBooking_Activity.this, MyBookingsActivity.class);

                    //passing the user details
                    intent.putExtra("user_firstname", user_firstname);
                    intent.putExtra("user_lastname", user_lastname);
                    intent.putExtra("user_contact", user_contact);
                    intent.putExtra("user_email", user_email);
                    intent.putExtra("user_username", user_username);
                    intent.putExtra("user_password", user_password);
                    intent.putExtra("user_usertype", user_usertype);
                    intent.putExtra("user_logged_in", user_logged_in);

                    startActivity(intent);
                    return;
                }else if(screenName.equals("AllTables")) {
                    Intent intent = new Intent(DeleteBooking_Activity.this, All_Tables_Activity.class);

                    //passing the user details
                    intent.putExtra("user_firstname", user_firstname);
                    intent.putExtra("user_lastname", user_lastname);
                    intent.putExtra("user_contact", user_contact);
                    intent.putExtra("user_email", user_email);
                    intent.putExtra("user_username", user_username);
                    intent.putExtra("user_password", user_password);
                    intent.putExtra("user_usertype", user_usertype);
                    intent.putExtra("user_logged_in", user_logged_in);

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

                        //passing the user details
                        intent.putExtra("user_firstname", user_firstname);
                        intent.putExtra("user_lastname", user_lastname);
                        intent.putExtra("user_contact", user_contact);
                        intent.putExtra("user_email", user_email);
                        intent.putExtra("user_username", user_username);
                        intent.putExtra("user_password", user_password);
                        intent.putExtra("user_usertype", user_usertype);
                        intent.putExtra("user_logged_in", user_logged_in);

                        startActivity(intent);
                        return;
                    }else if(screenName.equals("AllTables")) {
                        Intent intent = new Intent(DeleteBooking_Activity.this, All_Tables_Activity.class);
                        Toast.makeText(DeleteBooking_Activity.this, "This booking was deleted successfully", Toast.LENGTH_SHORT).show();

                        //passing the user details
                        intent.putExtra("user_firstname", user_firstname);
                        intent.putExtra("user_lastname", user_lastname);
                        intent.putExtra("user_contact", user_contact);
                        intent.putExtra("user_email", user_email);
                        intent.putExtra("user_username", user_username);
                        intent.putExtra("user_password", user_password);
                        intent.putExtra("user_usertype", user_usertype);
                        intent.putExtra("user_logged_in", user_logged_in);

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