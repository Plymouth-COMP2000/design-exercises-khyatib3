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

public class Delete_Menu_Item_Activity extends AppCompatActivity {
    private int THIS_ITEM_ID;
    private String CURRENT_NAME;
    private static String staff_usertype;

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

        //getting staff details
        String staff_firstname = getIntent().getStringExtra("staff_firstname");
        String staff_lastname = getIntent().getStringExtra("staff_lastname");
        String staff_contact = getIntent().getStringExtra("staff_contact");
        String staff_email = getIntent().getStringExtra("staff_email");
        String staff_username = getIntent().getStringExtra("staff_username");
        String staff_password = getIntent().getStringExtra("staff_password");

        //in the event that staff_usertype is null, set it to staff
        staff_usertype = getIntent().getStringExtra("staff_usertype");
        //setting staff_usertype to staff in case it becomes null
        if (staff_usertype == null || staff_usertype.isEmpty()) {
            staff_usertype = "staff";
        }
        boolean staff_logged_in = getIntent().getBooleanExtra("staff_logged_in", true);

        THIS_ITEM_ID = getIntent().getIntExtra("itemID", -1);
        CURRENT_NAME = getIntent().getStringExtra("name");

        //back button listener
        ImageButton backToMenuBtn = findViewById(R.id.deleteItemToMainMenuBtn);
        backToMenuBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Delete_Menu_Item_Activity.this, Staff_Menu_Activity.class);

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
                    NotificationsHelper.displayNotification(Delete_Menu_Item_Activity.this, "Menu Item Deleted", "You just deleted from the database: " + CURRENT_NAME);

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
}