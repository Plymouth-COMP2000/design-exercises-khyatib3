package com.example.comp2000assessment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

public class Guest_Menu_Activity extends AppCompatActivity {
    RecyclerView recyclerView;
    SearchView searchBar;
    MenuItemAdapter adapter;
    List<RestMenuItem> menuItems;
    List<RestMenuItem> filteredItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_guest_menu);
        searchBar = findViewById(R.id.menuSearchBar);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.guest_menu_page), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        menuItems = new ArrayList<>();
        RestMenuItem burrataCaprese = new RestMenuItem("£7.80", "Burrata Caprese","Authentic homemade Italian burrata \n in a fresh \n caprese salad",1, R.drawable.burrata_caprese);
        menuItems.add(burrataCaprese);
        RestMenuItem oliveBowl = new RestMenuItem("£5.60", "Olive Bowl", "Mediterranean-inspired bowl with \n marinated olives and \n fresh ingredients.", 5, R.drawable.olive_bowl);
        menuItems.add(oliveBowl);
        RestMenuItem aubergineFritti = new RestMenuItem("£6.85", "Aubergine Fritti", "Crispy golden aubergine, \n lightly fried and \n perfectly seasoned.",2, R.drawable.aubergine_fritti);
        menuItems.add(aubergineFritti);
        RestMenuItem polloMilanese = new RestMenuItem("£9.50", "Pollo Milanese", "Classic Italian breaded chicken cutlet, perfectly fried.", 2, R.drawable.pollo_milanese);
        menuItems.add(polloMilanese);
        RestMenuItem garlicBread = new RestMenuItem("£4.20", "Garlic Bread", "Warm, buttery bread with a rich garlic kick.", 5, R.drawable.garlic_bread);
        menuItems.add(garlicBread);
        RestMenuItem mozzarellaArancini = new RestMenuItem("£6.40", "Mozarella Arancini", "Classic Italian risotto balls with a cheesy heart.",2, R.drawable.mozzarella_arancini);
        menuItems.add(mozzarellaArancini);
        RestMenuItem primavera = new RestMenuItem("£8.00", "Primavera", "Fresh seasonal vegetables tossed in a light sauce.",2, R.drawable.primavera);
        menuItems.add(primavera);

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
                Intent intent = new Intent(Guest_Menu_Activity.this, GuestHomepage.class);
                startActivity(intent);
            }
        });

    }

}