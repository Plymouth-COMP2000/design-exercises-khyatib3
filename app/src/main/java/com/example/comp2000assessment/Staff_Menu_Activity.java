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
    Context context;
    private static final int PICK_IMAGE_REQUEST = 1;

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

        menuItems = new ArrayList<>();
        RestMenuItem burrataCaprese = new RestMenuItem("£7.80", "Burrata Caprese","Authentic homemade Italian burrata \n in a fresh \n caprese salad", R.drawable.burrata_caprese);
        menuItems.add(burrataCaprese);
        RestMenuItem oliveBowl = new RestMenuItem("£5.60", "Olive Bowl", "Mediterranean-inspired bowl with \n marinated olives and \n fresh ingredients.", R.drawable.olive_bowl);
        menuItems.add(oliveBowl);
        RestMenuItem aubergineFritti = new RestMenuItem("£6.85", "Aubergine Fritti", "Crispy golden aubergine, \n lightly fried and \n perfectly seasoned.", R.drawable.aubergine_fritti);
        menuItems.add(aubergineFritti);
        RestMenuItem polloMilanese = new RestMenuItem("£9.50", "Pollo Milanese", "Classic Italian breaded chicken cutlet, perfectly fried.", R.drawable.pollo_milanese);
        menuItems.add(polloMilanese);
        RestMenuItem garlicBread = new RestMenuItem("£4.20", "Garlic Bread", "Warm, buttery bread with a rich garlic kick.", R.drawable.garlic_bread);
        menuItems.add(garlicBread);
        RestMenuItem mozzarellaArancini = new RestMenuItem("£6.40", "Mozzarella Arancini", "Classic Italian risotto balls with a cheesy heart.", R.drawable.mozzarella_arancini);
        menuItems.add(mozzarellaArancini);
        RestMenuItem primavera = new RestMenuItem("£8.00", "Primavera", "Fresh seasonal vegetables tossed in a light sauce.", R.drawable.primavera);
        menuItems.add(primavera);

        adapter = new StaffMenuAdapter(this,menuItems);
        staffRecyclerView.setAdapter(adapter);

        //find edit menu btn
        Button editMenuBtn = findViewById(R.id.edit_button);


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