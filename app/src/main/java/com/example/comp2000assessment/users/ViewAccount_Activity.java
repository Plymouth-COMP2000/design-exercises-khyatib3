package com.example.comp2000assessment.users;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.comp2000assessment.R;
import com.example.comp2000assessment.settings.Settings;

public class ViewAccount_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_view_account);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.view_account_page), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //get the current user using ManageUser
        AppUser currentUser = ManageUser.getInstance().getCurrentUser();
        String user_firstname, user_lastname, user_username, user_password, user_email, user_contact, user_usertype;
        boolean user_logged_in;

        //check that current user isnt null
        if (currentUser == null) {
            //in case the app was killed in the background, send user back to the login screen
            //as a safety measure
            Intent intent = new Intent(this, Login_Activity.class);
            startActivity(intent);
            finish();
            return;
        }else{
            user_firstname = currentUser.getFirstname();
            user_lastname = currentUser.getLastname();
            user_username = currentUser.getUsername();
            user_password = currentUser.getPassword();
            user_email = currentUser.getEmail();
            user_contact = currentUser.getContact();
            user_usertype = currentUser.getUserType();
            user_logged_in = currentUser.isLoggedIn();

        }

        //getting detail textviews
        TextView acc_firstName = findViewById(R.id.acc_firstName);
        TextView acc_lastName = findViewById(R.id.acc_lastName);
        TextView acc_email = findViewById(R.id.acc_email);
        TextView acc_phone = findViewById(R.id.acc_phone);
        TextView acc_username = findViewById(R.id.acc_username);
        TextView acc_password = findViewById(R.id.acc_password);

        //changing the default text of the textviews to display real details
        acc_firstName.setText(user_firstname);
        acc_lastName.setText(user_lastname);
        acc_email.setText(user_email);
        acc_phone.setText(user_contact);
        acc_username.setText(user_username);
        acc_password.setText(user_password);


        //getting toggle visibility button
        ImageButton toggleVisbilityBtn = findViewById(R.id.togglePassVisbilityBtn);
        toggleVisbilityBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (acc_password.getText().equals("********")){
                    acc_password.setText(user_password);
                    toggleVisbilityBtn.setImageResource(R.drawable.ic_visibility_off);
                }else{
                    acc_password.setText("********");
                    toggleVisbilityBtn.setImageResource(R.drawable.ic_visibility_on);
                }
            }
        });

        //go back to settings
        ImageButton viewAccountBackBtn = findViewById(R.id.viewAccountBackBtn);
        viewAccountBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewAccount_Activity.this, Settings.class);
                startActivity(intent);

            }
        });



    }
}