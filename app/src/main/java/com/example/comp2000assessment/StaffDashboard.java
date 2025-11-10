package com.example.comp2000assessment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class StaffDashboard extends AppCompatActivity {

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
    }
}