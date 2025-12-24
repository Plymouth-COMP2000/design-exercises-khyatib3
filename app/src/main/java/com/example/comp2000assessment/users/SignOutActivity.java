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
import com.example.comp2000assessment.settings.Settings;

public class SignOutActivity extends AppCompatActivity {
    private boolean isGuest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_out);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.signOutScreen), (v, insets) -> {
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

        //getting STAFF details (if staff details were passed)
        String staff_firstname = getIntent().getStringExtra("staff_firstname");
        String staff_lastname = getIntent().getStringExtra("staff_lastname");
        String staff_contact = getIntent().getStringExtra("staff_contact");
        String staff_email = getIntent().getStringExtra("staff_email");
        String staff_username = getIntent().getStringExtra("staff_username");
        String staff_password = getIntent().getStringExtra("staff_password");
        String staff_usertype = getIntent().getStringExtra("staff_usertype");
        boolean staff_logged_in = getIntent().getBooleanExtra("staff_logged_in", true);

        //determing type of user logged in
        if(user_usertype != null &&user_usertype.equals("guest")){
            isGuest = true;
        }else{
            isGuest = false;
        }


        Button backHomeBtn = findViewById(R.id.cancelSignOutBtn);
        //setting on click functionality
        backHomeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignOutActivity.this, Settings.class);

                if(isGuest){
                    //passing user details back to settings
                    intent.putExtra("user_firstname", user_firstname);
                    intent.putExtra("user_lastname", user_lastname);
                    intent.putExtra("user_contact", user_contact);
                    intent.putExtra("user_email", user_email);
                    intent.putExtra("user_username", user_username);
                    intent.putExtra("user_password", user_password);
                    intent.putExtra("user_usertype", user_usertype);
                    intent.putExtra("user_logged_in", user_logged_in);

                }else{
                    //passing the staff details
                    intent.putExtra("staff_firstname", staff_firstname);
                    intent.putExtra("staff_lastname", staff_lastname);
                    intent.putExtra("staff_contact", staff_contact);
                    intent.putExtra("staff_email", staff_email);
                    intent.putExtra("staff_username", staff_username);
                    intent.putExtra("staff_password", staff_password);
                    intent.putExtra("staff_usertype", staff_usertype);
                    intent.putExtra("staff_logged_in", staff_logged_in);

                }
                startActivity(intent);
            }
        });

        Button confirmSignOutBtn = findViewById(R.id.confirmSignOutBtn);
        //setting on click functionality
        confirmSignOutBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(SignOutActivity.this, StartUpActivity.class);

                //ensuring user can't just click back to an activity logged in after logging out
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                startActivity(intent);
                finish();
            }
        });
    }
}