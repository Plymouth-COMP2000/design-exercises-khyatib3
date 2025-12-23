package com.example.comp2000assessment.homepages;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.comp2000assessment.R;
import com.example.comp2000assessment.settings.Settings;
import com.example.comp2000assessment.bookings.MyBookingsActivity;
import com.example.comp2000assessment.bookings.Reservation_Enquiry;
import com.example.comp2000assessment.menu.Guest_Menu_Options_Activity;

public class GuestHomepage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_guest_homepage);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.guest_homepage_screen), (v, insets) -> {
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


        //menu options button-- on click logic
        Button viewMenuBtn = findViewById(R.id.viewMenuBtn);
        //setting on click functionality
        viewMenuBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(GuestHomepage.this, Guest_Menu_Options_Activity.class);
                startActivity(intent);
            }
        });

        Button myBookingsBtn = findViewById(R.id.myBookingsBtn);
        //setting on click functionality
        myBookingsBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(GuestHomepage.this, MyBookingsActivity.class);

                //passing the user details
                intent.putExtra("user_firstname", user_firstname);
                intent.putExtra("user_lastname", user_lastname);
                intent.putExtra("user_usertype", user_usertype);
                intent.putExtra("user_logged_in", user_logged_in);

                startActivity(intent);
            }
        });

        Button requestTableBtn = findViewById(R.id.requestTblBtn);
        //setting on click functionality
        requestTableBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(GuestHomepage.this, Reservation_Enquiry.class);

                //passing the user details
                intent.putExtra("user_firstname", user_firstname);
                intent.putExtra("user_lastname", user_lastname);
                startActivity(intent);
            }
        });

        Button preferencesBtn = findViewById(R.id.preferencesBtn);
        //setting on click functionality
        preferencesBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(GuestHomepage.this, Settings.class);

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
            }
        });


    }
}