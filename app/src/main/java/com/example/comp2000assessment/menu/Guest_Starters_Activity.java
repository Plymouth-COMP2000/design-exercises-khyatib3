package com.example.comp2000assessment.menu;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SearchView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.comp2000assessment.databases.MenuDatabaseHelper;
import com.example.comp2000assessment.R;
import com.example.comp2000assessment.adapters.MenuItemAdapter;

import java.util.ArrayList;
import java.util.List;

public class Guest_Starters_Activity extends AppCompatActivity {
    RecyclerView recyclerView;
    SearchView searchBar;
    MenuItemAdapter adapter;
    List<RestMenuItem> menuItems;
    List<RestMenuItem> filteredItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_guest_starters);
        searchBar = findViewById(R.id.startersSearchBar);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.guest_starters_page), (v, insets) -> {
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


        recyclerView = findViewById(R.id.g_startersRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        MenuDatabaseHelper db = new MenuDatabaseHelper(Guest_Starters_Activity.this);
        menuItems = new ArrayList<>();
        menuItems = db.showMenuItems("starters");

        filteredItems = new ArrayList<>(menuItems);

        adapter = new MenuItemAdapter(this, menuItems, filteredItems);
        recyclerView.setAdapter(adapter);

        //filtering logic
        searchBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                adapter.getFilter().filter(query);
                return true;
            }
        });

        //home button icon on click logic
        ImageButton homeBtn = findViewById(R.id.g_startersToHomeBtn);
        //setting on click functionality
        homeBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(Guest_Starters_Activity.this, Guest_Menu_Options_Activity.class);

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