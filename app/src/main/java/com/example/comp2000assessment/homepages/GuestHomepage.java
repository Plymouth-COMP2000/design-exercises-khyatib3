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
import com.example.comp2000assessment.users.AppUser;
import com.example.comp2000assessment.users.Login_Activity;
import com.example.comp2000assessment.users.ManageUser;

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

                startActivity(intent);
            }
        });

        Button requestTableBtn = findViewById(R.id.requestTblBtn);
        //setting on click functionality
        requestTableBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(GuestHomepage.this, Reservation_Enquiry.class);
                startActivity(intent);
            }
        });

        Button preferencesBtn = findViewById(R.id.preferencesBtn);
        //setting on click functionality
        preferencesBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(GuestHomepage.this, Settings.class);

                startActivity(intent);
            }
        });


    }
}