package com.example.comp2000assessment.menu;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.comp2000assessment.homepages.GuestHomepage;
import com.example.comp2000assessment.R;

public class Guest_Menu_Options_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_guest_menu_options);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.menuOptionsScreen_guest), (v, insets) -> {
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


        ImageButton menuOptToHomeBtn = findViewById(R.id.g_menuOptHomeBtn);
        menuOptToHomeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Guest_Menu_Options_Activity.this, GuestHomepage.class);

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

        ImageButton startersBtn = findViewById(R.id.g_startersBtn);
        startersBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Guest_Menu_Options_Activity.this, Guest_Starters_Activity.class);

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

        ImageButton mainsBtn = findViewById(R.id.g_mainsBtn);
        mainsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Guest_Menu_Options_Activity.this, GuestMainsActivity.class);

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

        ImageButton dessertsBtn = findViewById(R.id.g_dessertsBtn);
        dessertsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Guest_Menu_Options_Activity.this, Guest_Desserts_Activity.class);

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

        ImageButton drinksBtn = findViewById(R.id.g_drinksBtn);
        drinksBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Guest_Menu_Options_Activity.this, Guest_Drinks_Activity.class);

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

        ImageButton sidesBtn = findViewById(R.id.g_sidesBtn);
        sidesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Guest_Menu_Options_Activity.this, Guest_Sides_Activity.class);

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