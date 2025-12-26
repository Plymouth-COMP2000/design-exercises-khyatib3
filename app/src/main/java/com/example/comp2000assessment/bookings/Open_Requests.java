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
import com.example.comp2000assessment.users.AppUser;
import com.example.comp2000assessment.users.Login_Activity;
import com.example.comp2000assessment.users.ManageUser;

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

                startActivity(intent);
            }
        });

    }
}