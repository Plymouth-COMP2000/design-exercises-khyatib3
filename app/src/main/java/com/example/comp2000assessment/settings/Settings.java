package com.example.comp2000assessment.settings;

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

import com.example.comp2000assessment.R;
import com.example.comp2000assessment.homepages.StaffDashboard;
import com.example.comp2000assessment.users.AppUser;
import com.example.comp2000assessment.users.DeleteAccount_Activity;
import com.example.comp2000assessment.users.Login_Activity;
import com.example.comp2000assessment.users.ManageUser;
import com.example.comp2000assessment.users.UpdateAccount_Activity;
import com.example.comp2000assessment.users.ViewAccount_Activity;
import com.example.comp2000assessment.homepages.GuestHomepage;
import com.example.comp2000assessment.users.SignOutActivity;

public class Settings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_settings);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.settings_Page), (v, insets) -> {
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

        String currentUser_usertype = currentUser.getUserType();

        android.util.Log.d("SETTINGS_DEBUG", "UserType: " + currentUser_usertype);
        android.util.Log.d("SETTINGS_DEBUG", "StaffType: " + currentUser_usertype);
        boolean isGuest = (currentUser_usertype != null && currentUser_usertype.equals("guest"));
        boolean isStaff = (currentUser != null && currentUser_usertype.equals("staff"));

        ImageButton takeMeHomeBtn = findViewById(R.id.settingsGoHomeBtn);
        //setting on click functionality
        takeMeHomeBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

                if(isGuest){
                    Intent intent = new Intent(Settings.this, GuestHomepage.class);
                    startActivity(intent);
                    finish();
                }else if (isStaff){
                    Intent intent = new Intent(Settings.this, StaffDashboard.class);
                    startActivity(intent);
                    finish();
                }

            }
        });

        //view account button functionality, allows user to see all details stored regarding their account
        Button viewAccountBtn = findViewById(R.id.viewAccountBtn);
        viewAccountBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Settings.this, ViewAccount_Activity.class);
                startActivity(intent);
            }
        });

        //update account button functionality, allows user to update their account details
        Button updateAccountScreenBtn = findViewById(R.id.updateDetailsBtn);
        updateAccountScreenBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Settings.this, UpdateAccount_Activity.class);
                startActivity(intent);
            }
        });

        Button deleteAccountScreenBtn = findViewById(R.id.deleteAccountScreenBtn);
        deleteAccountScreenBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Settings.this, DeleteAccount_Activity.class);
                startActivity(intent);
            }
        });

        Button signOutBtn = findViewById(R.id.signOutBtn);
        //setting on click functionality
        signOutBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(Settings.this, SignOutActivity.class);
                startActivity(intent);
            }
        });
    }
}