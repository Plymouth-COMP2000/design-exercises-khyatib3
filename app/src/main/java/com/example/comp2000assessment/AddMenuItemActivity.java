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

public class AddMenuItemActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_menu_item);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.addNewItemScreen), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ImageButton addItemBackBtn = findViewById(R.id.addItemBackBtn);
        addItemBackBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(AddMenuItemActivity.this, Staff_Menu_Activity.class);
                startActivity(intent);
            }
        });

        Button addItemBtn = findViewById(R.id.addItemBtn);
        addItemBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(AddMenuItemActivity.this, Item_Add_Confirm_Activity.class);
                startActivity(intent);
            }
        });
    }
}