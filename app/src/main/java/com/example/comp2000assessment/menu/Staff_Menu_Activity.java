package com.example.comp2000assessment.menu;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.comp2000assessment.databases.MenuDatabaseHelper;
import com.example.comp2000assessment.R;
import com.example.comp2000assessment.homepages.StaffDashboard;
import com.example.comp2000assessment.adapters.StaffMenuAdapter;

import java.util.ArrayList;
import java.util.List;

public class Staff_Menu_Activity extends AppCompatActivity {

    RecyclerView staffRecyclerView;
    StaffMenuAdapter adapter;
    List<RestMenuItem> menuItems;
    Spinner categorySpinner;
    List<RestMenuItem> currentCategoryList;
    MenuDatabaseHelper db;
    String staff_firstname;
    String staff_lastname;
    String staff_contact;
    String staff_email;
    String staff_username;
    String staff_password;
    boolean staff_logged_in;
    String staff_usertype;

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

        //getting staff details
        staff_firstname = getIntent().getStringExtra("staff_firstname");
        staff_lastname = getIntent().getStringExtra("staff_lastname");
        staff_contact = getIntent().getStringExtra("staff_contact");
        staff_email = getIntent().getStringExtra("staff_email");
        staff_username = getIntent().getStringExtra("staff_username");
        staff_password = getIntent().getStringExtra("staff_password");
        staff_logged_in = getIntent().getBooleanExtra("staff_logged_in", true);

        // Your existing logic for usertype safety check
        staff_usertype = getIntent().getStringExtra("staff_usertype");
        if (staff_usertype == null || staff_usertype.isEmpty()) {
            staff_usertype = "staff";
        }

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
                loadMenu(selectedCategory, staff_firstname, staff_lastname, staff_contact, staff_email, staff_username, staff_password, staff_usertype, staff_logged_in);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //default should be starters
               loadMenu("Starters", staff_firstname, staff_lastname, staff_contact, staff_email, staff_username, staff_password, staff_usertype, staff_logged_in);
            }
        });

        adapter = new StaffMenuAdapter(this,menuItems, staff_firstname, staff_lastname, staff_contact, staff_email, staff_username, staff_password, staff_usertype, staff_logged_in);
        staffRecyclerView.setAdapter(adapter);

        //find home button
        ImageButton homeBtn = findViewById(R.id.staffMenuHomeBtn);
        homeBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(Staff_Menu_Activity.this, StaffDashboard.class);

                //passing staff details
                intent.putExtra("staff_firstname", staff_firstname);
                intent.putExtra("staff_lastname", staff_lastname);
                intent.putExtra("staff_contact", staff_contact);
                intent.putExtra("staff_email", staff_email);
                intent.putExtra("staff_username", staff_username);
                intent.putExtra("staff_password", staff_password);
                intent.putExtra("staff_usertype", staff_usertype);
                intent.putExtra("staff_logged_in", staff_logged_in);

                startActivity(intent);
            }
        });

        //find menu add item
        ImageButton menuAddBtn = findViewById(R.id.addToMenuBtn);
        menuAddBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(Staff_Menu_Activity.this, AddMenuItemActivity.class);

                //passing staff details
                intent.putExtra("staff_firstname", staff_firstname);
                intent.putExtra("staff_lastname", staff_lastname);
                intent.putExtra("staff_contact", staff_contact);
                intent.putExtra("staff_email", staff_email);
                intent.putExtra("staff_username", staff_username);
                intent.putExtra("staff_password", staff_password);
                intent.putExtra("staff_usertype", staff_usertype);
                intent.putExtra("staff_logged_in", staff_logged_in);

                startActivity(intent);
            }
        });

    }

    public void loadMenu(String category, String staff_firstname, String staff_lastname, String contact, String email,
                         String username, String password, String usertype, boolean staff_logged_in) {
        //converting category to lower case
        String lowerCaseCategory = category.toLowerCase();

        //retrieving items of the current category from DB
        db = new MenuDatabaseHelper(Staff_Menu_Activity.this);
        currentCategoryList = db.showMenuItems(lowerCaseCategory);

        //setting the adapter to the list made
        adapter = new StaffMenuAdapter(this, currentCategoryList, staff_firstname, staff_lastname, contact, email, username, password, usertype, staff_logged_in);
        staffRecyclerView.setAdapter(adapter);


        if (currentCategoryList.isEmpty()) {
            Toast.makeText(this, "Nothing found in " + category, Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (categorySpinner != null && categorySpinner.getSelectedItem() != null){
            loadMenu(categorySpinner.getSelectedItem().toString(), staff_firstname, staff_lastname, staff_contact, staff_email, staff_username, staff_password, staff_usertype, staff_logged_in);
        }

    }


}