package com.example.comp2000assessment.users;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.comp2000assessment.R;
import com.example.comp2000assessment.homepages.GuestHomepage;

public class AccountCreated extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_account_created);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.sidebarBtn), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //receiving user details passed
        String user_firstname = getIntent().getStringExtra("user_firstname");
        String user_lastname = getIntent().getStringExtra("user_lastname");
        String user_contact = getIntent().getStringExtra("user_contact");
        String user_email = getIntent().getStringExtra("user_email");
        String user_username = getIntent().getStringExtra("user_username");
        String user_password = getIntent().getStringExtra("user_password");
        String user_usertype = getIntent().getStringExtra("user_usertype");
        boolean user_logged_in = getIntent().getBooleanExtra("user_logged_in", true);


        Button goHomeBtn = findViewById(R.id.goHomeBtn1);
        //setting on click functionality
        goHomeBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(AccountCreated.this, GuestHomepage.class);

                //passing values to homepage
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