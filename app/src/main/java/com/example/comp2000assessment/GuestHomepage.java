package com.example.comp2000assessment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

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

        //menu options button-- on click logic
        Button viewMenuBtn = findViewById(R.id.viewMenuBtn);
        //setting on click functionality
        viewMenuBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(GuestHomepage.this, Guest_Starters_Activity.class);
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