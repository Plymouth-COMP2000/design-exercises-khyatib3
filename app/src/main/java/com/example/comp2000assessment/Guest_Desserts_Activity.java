package com.example.comp2000assessment;

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

import java.util.ArrayList;
import java.util.List;

public class Guest_Desserts_Activity extends AppCompatActivity {
    RecyclerView recyclerView;
    SearchView searchBar;
    MenuItemAdapter adapter;
    List<RestMenuItem> menuItems;
    List<RestMenuItem> filteredItems;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_guest_desserts);
        searchBar = findViewById(R.id.dessertsSearchBar);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.guest_desserts_page), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        recyclerView = findViewById(R.id.g_dessertsRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        MenuDatabaseHelper db = new MenuDatabaseHelper(Guest_Desserts_Activity.this);
        menuItems = new ArrayList<>();
        menuItems = db.showMenuItems("desserts");

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
        ImageButton homeBtn = findViewById(R.id.g_dessertsToHomeBtn);
        //setting on click functionality
        homeBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(Guest_Desserts_Activity.this, Guest_Menu_Options_Activity.class);
                startActivity(intent);
            }
        });
    }
}