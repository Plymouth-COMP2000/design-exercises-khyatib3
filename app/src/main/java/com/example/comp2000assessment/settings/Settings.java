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
import com.example.comp2000assessment.users.DeleteAccount_Activity;
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

        //receiving GUEST details passed (if guest details were passed)
        String user_firstname = getIntent().getStringExtra("user_firstname");
        String user_lastname = getIntent().getStringExtra("user_lastname");
        String user_contact = getIntent().getStringExtra("user_contact");
        String user_email = getIntent().getStringExtra("user_email");
        String user_username = getIntent().getStringExtra("user_username");
        String user_password = getIntent().getStringExtra("user_password");
        String user_usertype = getIntent().getStringExtra("user_usertype");
        boolean user_logged_in = getIntent().getBooleanExtra("user_logged_in", true);

        //getting STAFF details (if staff details were passed)
        String staff_firstname = getIntent().getStringExtra("staff_firstname");
        String staff_lastname = getIntent().getStringExtra("staff_lastname");
        String staff_contact = getIntent().getStringExtra("staff_contact");
        String staff_email = getIntent().getStringExtra("staff_email");
        String staff_username = getIntent().getStringExtra("staff_username");
        String staff_password = getIntent().getStringExtra("staff_password");
        String staff_usertype = getIntent().getStringExtra("staff_usertype");
        boolean staff_logged_in = getIntent().getBooleanExtra("staff_logged_in", true);

        android.util.Log.d("SETTINGS_DEBUG", "UserType: " + user_usertype);
        android.util.Log.d("SETTINGS_DEBUG", "StaffType: " + staff_usertype);
        boolean isGuest = (user_usertype != null && user_usertype.equals("guest"));
        boolean isStaff = (staff_usertype != null && staff_usertype.equals("staff"));



        ImageButton takeMeHomeBtn = findViewById(R.id.settingsGoHomeBtn);
        //setting on click functionality
        takeMeHomeBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

                if(isGuest){
                    Intent intent = new Intent(Settings.this, GuestHomepage.class);

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
                    finish();
                }else if (isStaff){
                    Intent intent = new Intent(Settings.this, StaffDashboard.class);

                    //passing the user details
                    intent.putExtra("staff_firstname", staff_firstname);
                    intent.putExtra("staff_lastname", staff_lastname);
                    intent.putExtra("staff_contact", staff_contact);
                    intent.putExtra("staff_email", staff_email);
                    intent.putExtra("staff_username", staff_username);
                    intent.putExtra("staff_password", staff_password);
                    intent.putExtra("staff_usertype", staff_usertype);
                    intent.putExtra("staff_logged_in", staff_logged_in);

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
                if(isGuest){
                    Intent intent = new Intent(Settings.this, ViewAccount_Activity.class);

                    //passing values to view account activity
                    intent.putExtra("user_firstname", user_firstname);
                    intent.putExtra("user_lastname", user_lastname);
                    intent.putExtra("user_contact", user_contact);
                    intent.putExtra("user_email", user_email);
                    intent.putExtra("user_username", user_username);
                    intent.putExtra("user_password", user_password);
                    intent.putExtra("user_usertype", user_usertype);
                    intent.putExtra("user_logged_in", user_logged_in);

                    startActivity(intent);
                } else if (isStaff) {
                    Intent intent = new Intent(Settings.this, ViewAccount_Activity.class);

                    //passing the user details
                    intent.putExtra("staff_firstname", staff_firstname);
                    intent.putExtra("staff_lastname", staff_lastname);
                    intent.putExtra("staff_contact", staff_contact);
                    intent.putExtra("staff_email", staff_email);
                    intent.putExtra("staff_username", staff_username);
                    intent.putExtra("staff_password", staff_password);
                    intent.putExtra("staff_usertype", staff_usertype);
                    intent.putExtra("staff_logged_in", staff_logged_in);

                    startActivity(intent);
                    finish();
                }

            }
        });

        //update account button functionality, allows user to update their account details
        Button updateAccountScreenBtn = findViewById(R.id.updateDetailsBtn);
        updateAccountScreenBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isGuest){
                    Intent intent = new Intent(Settings.this, UpdateAccount_Activity.class);

                    //passing values to view account activity
                    intent.putExtra("user_firstname", user_firstname);
                    intent.putExtra("user_lastname", user_lastname);
                    intent.putExtra("user_contact", user_contact);
                    intent.putExtra("user_email", user_email);
                    intent.putExtra("user_username", user_username);
                    intent.putExtra("user_password", user_password);
                    intent.putExtra("user_usertype", user_usertype);
                    intent.putExtra("user_logged_in", user_logged_in);

                    startActivity(intent);
                } else if (isStaff) {
                    Intent intent = new Intent(Settings.this, UpdateAccount_Activity.class);

                    //passing the user details
                    intent.putExtra("staff_firstname", staff_firstname);
                    intent.putExtra("staff_lastname", staff_lastname);
                    intent.putExtra("staff_contact", staff_contact);
                    intent.putExtra("staff_email", staff_email);
                    intent.putExtra("staff_username", staff_username);
                    intent.putExtra("staff_password", staff_password);
                    intent.putExtra("staff_usertype", staff_usertype);
                    intent.putExtra("staff_logged_in", staff_logged_in);

                    startActivity(intent);
                    finish();
                }
            }
        });

        Button deleteAccountScreenBtn = findViewById(R.id.deleteAccountScreenBtn);
        deleteAccountScreenBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isGuest){
                    Intent intent = new Intent(Settings.this, DeleteAccount_Activity.class);

                    //passing values to view account activity
                    intent.putExtra("user_firstname", user_firstname);
                    intent.putExtra("user_lastname", user_lastname);
                    intent.putExtra("user_contact", user_contact);
                    intent.putExtra("user_email", user_email);
                    intent.putExtra("user_username", user_username);
                    intent.putExtra("user_password", user_password);
                    intent.putExtra("user_usertype", user_usertype);
                    intent.putExtra("user_logged_in", user_logged_in);

                    startActivity(intent);
                    finish();
                } else if (isStaff) {
                    Intent intent = new Intent(Settings.this, DeleteAccount_Activity.class);

                    //passing the user details
                    intent.putExtra("staff_firstname", staff_firstname);
                    intent.putExtra("staff_lastname", staff_lastname);
                    intent.putExtra("staff_contact", staff_contact);
                    intent.putExtra("staff_email", staff_email);
                    intent.putExtra("staff_username", staff_username);
                    intent.putExtra("staff_password", staff_password);
                    intent.putExtra("staff_usertype", staff_usertype);
                    intent.putExtra("staff_logged_in", staff_logged_in);

                    startActivity(intent);
                    finish();
                }
            }
        });

        Button signOutBtn = findViewById(R.id.signOutBtn);
        //setting on click functionality
        signOutBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if(isGuest){
                    Intent intent = new Intent(Settings.this, SignOutActivity.class);

                    //passing values to view account activity
                    intent.putExtra("user_firstname", user_firstname);
                    intent.putExtra("user_lastname", user_lastname);
                    intent.putExtra("user_contact", user_contact);
                    intent.putExtra("user_email", user_email);
                    intent.putExtra("user_username", user_username);
                    intent.putExtra("user_password", user_password);
                    intent.putExtra("user_usertype", user_usertype);
                    intent.putExtra("user_logged_in", user_logged_in);

                    startActivity(intent);
                } else if (isStaff) {
                    Intent intent = new Intent(Settings.this, SignOutActivity.class);

                    //passing the user details
                    intent.putExtra("staff_firstname", staff_firstname);
                    intent.putExtra("staff_lastname", staff_lastname);
                    intent.putExtra("staff_contact", staff_contact);
                    intent.putExtra("staff_email", staff_email);
                    intent.putExtra("staff_username", staff_username);
                    intent.putExtra("staff_password", staff_password);
                    intent.putExtra("staff_usertype", staff_usertype);
                    intent.putExtra("staff_logged_in", staff_logged_in);

                    startActivity(intent);
                    finish();
                }
            }
        });
    }
}