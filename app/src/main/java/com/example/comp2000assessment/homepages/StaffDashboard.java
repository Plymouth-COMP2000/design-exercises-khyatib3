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
import com.example.comp2000assessment.bookings.All_Tables_Activity;
import com.example.comp2000assessment.menu.Staff_Menu_Activity;
import com.example.comp2000assessment.settings.Settings;
import com.example.comp2000assessment.users.AppUser;
import com.example.comp2000assessment.users.Login_Activity;
import com.example.comp2000assessment.users.ManageUser;
import com.example.comp2000assessment.users.New_Staff_Admin;

public class StaffDashboard extends AppCompatActivity {
    private static String staff_usertype;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_staff_dashboard);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.staffDashboardScreen), (v, insets) -> {
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
        }else{
            user_firstname = currentUser.getFirstname();
            user_lastname = currentUser.getLastname();
            user_username = currentUser.getUsername();
            user_password = currentUser.getPassword();
            user_email = currentUser.getEmail();
            user_contact = currentUser.getContact();
            user_usertype = currentUser.getUserType();
            user_logged_in = currentUser.isLoggedIn();
        }

        if(user_usertype != null || !user_usertype.equals("staff")){
            user_usertype = "staff";
        }
        //navigate to menu
        Button toMenuBtn = findViewById(R.id.viewChangeMenuBtn);
        toMenuBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(StaffDashboard.this, Staff_Menu_Activity.class);
                startActivity(intent);
            }
        });

        //view tables
        Button viewTables = findViewById(R.id.viewTblResBtn);
        viewTables.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StaffDashboard.this, All_Tables_Activity.class);
                startActivity(intent);
            }
        });

        Button adminScreenBtn = findViewById(R.id.addStaffScreenBtn);
        adminScreenBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StaffDashboard.this, New_Staff_Admin.class);
                startActivity(intent);

            }
        });

        //navigate to settings
        Button toSettingsBtn = findViewById(R.id.staffPreferencesBtn);
        toSettingsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StaffDashboard.this, Settings.class);
                startActivity(intent);
            }
        });

    }
}