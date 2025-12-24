package com.example.comp2000assessment.bookings;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.comp2000assessment.databases.BookingsDatabaseHelper;
import com.example.comp2000assessment.R;
import com.example.comp2000assessment.homepages.StaffDashboard;
import com.example.comp2000assessment.adapters.S_BookingRequestAdapter;

import java.util.ArrayList;
import java.util.List;

public class Open_Requests extends AppCompatActivity {
    RecyclerView requestsRecycler;
    List<BookingRecord> openRequestsList;
    S_BookingRequestAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_open_requests);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.openRequests_page), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //getting staff details
        String staff_firstname = getIntent().getStringExtra("staff_firstname");
        String staff_lastname = getIntent().getStringExtra("staff_lastname");
        String staff_contact = getIntent().getStringExtra("staff_contact");
        String staff_email = getIntent().getStringExtra("staff_email");
        String staff_username = getIntent().getStringExtra("staff_username");
        String staff_password = getIntent().getStringExtra("staff_password");
        String staff_usertype = getIntent().getStringExtra("staff_usertype");
        boolean staff_logged_in = getIntent().getBooleanExtra("staff_logged_in", true);

        requestsRecycler = findViewById(R.id.openReqsRecyclerView);
        requestsRecycler.setLayoutManager(new LinearLayoutManager(this));

        //getting instance of db
        BookingsDatabaseHelper db = new BookingsDatabaseHelper(Open_Requests.this);
        openRequestsList = new ArrayList<>();

        //retrieving bookings
        openRequestsList = db.showStaffUnconfirmedReqs();

        //setting list to adapter and adapter to recycler view
        adapter = new S_BookingRequestAdapter(this, openRequestsList);
        requestsRecycler.setAdapter(adapter);

        if (openRequestsList.isEmpty()) {
            Toast.makeText(this, "No requests currently", Toast.LENGTH_SHORT).show();
        }

        //home button navigation functionality
        ImageButton openReqsToHomeBtn = findViewById(R.id.openReqsToHomeBtn);
        openReqsToHomeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Open_Requests.this, All_Tables_Activity.class);

                //passing the staff details
                intent.putExtra("staff_firstname", staff_firstname);
                intent.putExtra("staff_lastname", staff_lastname);
                intent.putExtra("staff_contact", staff_contact);
                intent.putExtra("staff_email", staff_email);
                intent.putExtra("staff_username", staff_username);
                intent.putExtra("staff_password", staff_password);
                intent.putExtra("staff_usertype", staff_usertype);
                intent.putExtra("staff_logged_in", staff_logged_in);

                startActivity(intent);
            }
        });

    }
}