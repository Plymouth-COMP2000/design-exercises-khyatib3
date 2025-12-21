package com.example.comp2000assessment.bookings;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.comp2000assessment.R;

public class Enquiry_Failed_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_enquiry_failed);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.enquiry_failed_page), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //retrieving error message
        String errorMsg = getIntent().getStringExtra("errorMsg");

        //get textview
        TextView errorDisplay = findViewById(R.id.reservationSentFailText);
        errorDisplay.setText(errorMsg);

        Button retryEnquiryBtn = findViewById(R.id.enquiryRetryBtn);
        retryEnquiryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Enquiry_Failed_Activity.this, Reservation_Enquiry.class);
                startActivity(intent);
            }
        });
    }
}