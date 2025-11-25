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
        searchBar = findViewById(R.id.menuSearchBar);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.guest_starters_page), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        recyclerView = findViewById(R.id.g_startersRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));




        filteredItems = new ArrayList<>(menuItems);
        adapter = new MenuItemAdapter(this,filteredItems);
        recyclerView.setAdapter(adapter);

        //filtering logic
        searchBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String newText) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                filteredItems.clear();
                //going through the list with each item
                for (RestMenuItem item : menuItems){
                    //checking to see if the keyword input is part of any item's description
                    //hence show if it is
                    if (item.getDescription().toLowerCase().contains(query.toLowerCase())){
                        filteredItems.add(item);
                    }
                }

                adapter.notifyDataSetChanged();
                return true;
            }
        });

        //home button icon on click logic
        ImageButton homeBtn = findViewById(R.id.homeBtn);
        //setting on click functionality
        homeBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(Guest_Starters_Activity.this, GuestHomepage.class);
                startActivity(intent);
            }
        });

    }

}