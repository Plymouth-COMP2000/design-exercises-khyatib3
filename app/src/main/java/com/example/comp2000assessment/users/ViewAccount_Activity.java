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

        //receiving user details passed
        String user_firstname = getIntent().getStringExtra("user_firstname");
        String user_lastname = getIntent().getStringExtra("user_lastname");
        String user_contact = getIntent().getStringExtra("user_contact");
        String user_email = getIntent().getStringExtra("user_email");
        String user_username = getIntent().getStringExtra("user_username");
        String user_password = getIntent().getStringExtra("user_password");
        String user_usertype = getIntent().getStringExtra("user_usertype");
        boolean user_logged_in = getIntent().getBooleanExtra("user_logged_in", true);

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

                //passing user details back to settings
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