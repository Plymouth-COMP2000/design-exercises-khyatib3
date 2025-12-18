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

        ImageButton menuOptToHomeBtn = findViewById(R.id.g_menuOptHomeBtn);
        menuOptToHomeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Guest_Menu_Options_Activity.this, GuestHomepage.class);
                startActivity(intent);
            }
        });

        ImageButton startersBtn = findViewById(R.id.g_startersBtn);
        startersBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Guest_Menu_Options_Activity.this, Guest_Starters_Activity.class);
                startActivity(intent);
            }
        });

        ImageButton mainsBtn = findViewById(R.id.g_mainsBtn);
        mainsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Guest_Menu_Options_Activity.this, GuestMainsActivity.class);
                startActivity(intent);
            }
        });

        ImageButton dessertsBtn = findViewById(R.id.g_dessertsBtn);
        dessertsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Guest_Menu_Options_Activity.this, Guest_Desserts_Activity.class);
                startActivity(intent);
            }
        });

        ImageButton drinksBtn = findViewById(R.id.g_drinksBtn);
        drinksBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Guest_Menu_Options_Activity.this, Guest_Drinks_Activity.class);
                startActivity(intent);
            }
        });

        ImageButton sidesBtn = findViewById(R.id.g_sidesBtn);
        sidesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Guest_Menu_Options_Activity.this, Guest_Sides_Activity.class);
                startActivity(intent);
            }
        });



    }
}