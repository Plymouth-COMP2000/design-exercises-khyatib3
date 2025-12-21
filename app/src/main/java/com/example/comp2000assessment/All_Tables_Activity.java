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

public class All_Tables_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_all_tables);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.allTablesScreen), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ImageButton homeBtn = findViewById(R.id.allTablesHomeButton);
        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(All_Tables_Activity.this, StaffDashboard.class);
                startActivity(intent);
            }
        });

        //setting functionality for the table number buttons and open requests buttons
        Button openRequestsBtn = findViewById(R.id.openRequestsBtn);
        openRequestsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(All_Tables_Activity.this, Open_Requests.class);
                startActivity(intent);

            }
        });

        //table 1
        Button table1Btn = findViewById(R.id.table1Btn);
        table1Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(All_Tables_Activity.this, TableNo_ReservationActivity.class);
                intent.putExtra("tableNumber", 1);
                startActivity(intent);
            }
        });

        //table 2
        Button table2Btn = findViewById(R.id.table2Btn);
        table2Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(All_Tables_Activity.this, TableNo_ReservationActivity.class);
                intent.putExtra("tableNumber", 2);
                startActivity(intent);
            }
        });

        //table 3
        Button table3Btn = findViewById(R.id.table3Btn);
        table3Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(All_Tables_Activity.this, TableNo_ReservationActivity.class);
                intent.putExtra("tableNumber", 3);
                startActivity(intent);
            }
        });

        //table 1
        Button table4Btn = findViewById(R.id.table4Btn);
        table4Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(All_Tables_Activity.this, TableNo_ReservationActivity.class);
                intent.putExtra("tableNumber", 4);
                startActivity(intent);
            }
        });

        //table 5
        Button table5Btn = findViewById(R.id.table5Btn);
        table5Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(All_Tables_Activity.this, TableNo_ReservationActivity.class);
                intent.putExtra("tableNumber", 5);
                startActivity(intent);
            }
        });

        //table 6
        Button table6Btn = findViewById(R.id.table6Btn);
        table6Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(All_Tables_Activity.this, TableNo_ReservationActivity.class);
                intent.putExtra("tableNumber", 6);
                startActivity(intent);
            }
        });

        //table 7
        Button table7Btn = findViewById(R.id.table7Btn);
        table7Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(All_Tables_Activity.this, TableNo_ReservationActivity.class);
                intent.putExtra("tableNumber", 7);
                startActivity(intent);
            }
        });

        //table 8
        Button table8Btn = findViewById(R.id.table8Btn);
        table8Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(All_Tables_Activity.this, TableNo_ReservationActivity.class);
                intent.putExtra("tableNumber", 8);
                startActivity(intent);
            }
        });
    }
}