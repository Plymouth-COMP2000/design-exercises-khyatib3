package com.example.comp2000assessment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class Staff_Menu_Activity extends AppCompatActivity {

    RecyclerView staffRecyclerView;
    StaffMenuAdapter adapter;
    List<RestMenuItem> menuItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_staff_menu);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.staffMenuScreen), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        staffRecyclerView = findViewById(R.id.staffRecyclerView);
        staffRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        MenuDatabaseHelper db = new MenuDatabaseHelper(Staff_Menu_Activity.this);
        menuItems = new ArrayList<>();

        adapter = new StaffMenuAdapter(this,menuItems);
        staffRecyclerView.setAdapter(adapter);

        //find home button
        ImageButton homeBtn = findViewById(R.id.staffMenuHomeBtn);
        homeBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(Staff_Menu_Activity.this, StaffDashboard.class);
                startActivity(intent);
            }
        });

        //find menu add item
        ImageButton menuAddBtn = findViewById(R.id.addToMenuBtn);
        menuAddBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(Staff_Menu_Activity.this, AddMenuItemActivity.class);
                startActivity(intent);
            }
        });

    }


}