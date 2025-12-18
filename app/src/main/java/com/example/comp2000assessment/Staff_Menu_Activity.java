package com.example.comp2000assessment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

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
    Spinner categorySpinner;
    List<RestMenuItem> currentCategoryList;
    MenuDatabaseHelper db;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_staff_menu);
        categorySpinner = findViewById(R.id.s_categorySpinner);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.staffMenuScreen), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //finding the recycler view and setting it
        staffRecyclerView = findViewById(R.id.staffRecyclerView);
        staffRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        //getting the database
        MenuDatabaseHelper db = new MenuDatabaseHelper(Staff_Menu_Activity.this);
        menuItems = new ArrayList<>();

        //setting categories for the spinner
        String[] categories = {"Starters", "Mains", "Desserts", "Drinks", "Sides"};
        //setting the spinner to the categories
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                categories
        );
        categorySpinner.setAdapter(spinnerAdapter);

        //adding functionality to the spinner
        //should show items of that category when selected
        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //getting selected category
                String selectedCategory = parent.getItemAtPosition(position).toString();

                //loading data from db for given category
                loadMenu(selectedCategory);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //default should be starters
               loadMenu("Starters");
            }
        });

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

    public void loadMenu(String category) {
        //converting category to lower case
        String lowerCaseCategory = category.toLowerCase();

        //retrieving items of the current category from DB
        db = new MenuDatabaseHelper(Staff_Menu_Activity.this);
        currentCategoryList = db.showMenuItems(lowerCaseCategory);

        //setting the adapter to the list made
        adapter = new StaffMenuAdapter(this, currentCategoryList);
        staffRecyclerView.setAdapter(adapter);

        // Optional: Show a message if empty
        if (currentCategoryList.isEmpty()) {
            Toast.makeText(this, "Nothing found in " + category, Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (categorySpinner != null && categorySpinner.getSelectedItem() != null){
            loadMenu(categorySpinner.getSelectedItem().toString());
        }

    }


}