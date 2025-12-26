package com.example.comp2000assessment.menu;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.comp2000assessment.databases.MenuDatabaseHelper;
import com.example.comp2000assessment.R;
import com.example.comp2000assessment.notifications.NotificationsHelper;
import com.example.comp2000assessment.users.AppUser;
import com.example.comp2000assessment.users.Login_Activity;
import com.example.comp2000assessment.users.ManageUser;

public class Delete_Menu_Item_Activity extends AppCompatActivity {
    private int THIS_ITEM_ID;
    private String CURRENT_NAME;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_delete_menu_item);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.delete_item_page), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //get the current user using ManageUser
        AppUser currentUser = ManageUser.getInstance().getCurrentUser();
        //check that current user isnt null
        if (currentUser == null) {
            //in case the app was killed in the background, send user back to the login screen
            //as a safety measure
            Intent intent = new Intent(this, Login_Activity.class);
            startActivity(intent);
            finish();
            return;
        }
        THIS_ITEM_ID = getIntent().getIntExtra("itemID", -1);
        CURRENT_NAME = getIntent().getStringExtra("name");

        //back button listener
        ImageButton backToMenuBtn = findViewById(R.id.deleteItemToMainMenuBtn);
        backToMenuBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Delete_Menu_Item_Activity.this, Staff_Menu_Activity.class);
                startActivity(intent);
            }
        });

        //delete item listener
        Button deleteItemBtn = findViewById(R.id.confirmDelItemBtn);
        deleteItemBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MenuDatabaseHelper db = new MenuDatabaseHelper(Delete_Menu_Item_Activity.this);

                boolean delete_result = db.deleteItem(THIS_ITEM_ID);
                if(delete_result){
                    //making toast to confirm it was deleted
                    Toast.makeText(Delete_Menu_Item_Activity.this, "Item deleted from database: " + CURRENT_NAME, Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(Delete_Menu_Item_Activity.this, Staff_Menu_Activity.class);

                    //push notification to tell it was deleted
                    NotificationsHelper.displayNotification(Delete_Menu_Item_Activity.this, "Menu Item Deleted", "You just deleted from the database: " + CURRENT_NAME, "menu");
                    startActivity(intent);
                }else{
                    Toast.makeText(Delete_Menu_Item_Activity.this, "Error! Item could not be deleted: " + CURRENT_NAME, Toast.LENGTH_SHORT).show();
                }
            }
        });

        Button cancelDeleteBtn = findViewById(R.id.cancelDelItemBtn);
        cancelDeleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Delete_Menu_Item_Activity.this, Staff_Menu_Activity.class);

                startActivity(intent);
            }
        });

    }
}