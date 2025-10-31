package com.example.comp2000assessment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Reservation_Enquiry extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_reservation_enquiry);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.reservation_EnquiryScreen), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ImageButton resEnq_HomeIcon = findViewById(R.id.reservationHomeIcon);
        //setting on click functionality
        resEnq_HomeIcon.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(Reservation_Enquiry.this, GuestHomepage.class);
                startActivity(intent);
            }
        });

        Button sendRequestBtn = findViewById(R.id.submitRequestButton);
        //setting on click functionality
        sendRequestBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(Reservation_Enquiry.this, Enquiry_Sent_Activity.class);
                startActivity(intent);
            }
        });
    }
}