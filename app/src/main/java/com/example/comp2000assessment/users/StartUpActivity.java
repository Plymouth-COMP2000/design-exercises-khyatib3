package com.example.comp2000assessment.users;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.comp2000assessment.R;
import com.example.comp2000assessment.terms_conditions.T_C_Activity;

import org.json.JSONObject;

public class StartUpActivity extends AppCompatActivity {
    private UserAPI_Helper api_helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_start_up);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.appStartUpScreen), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //initialise api helper
        api_helper = new UserAPI_Helper(this);

        //create student database (one time)
        api_helper.createStudentDatabase(new UserAPI_Helper.APIResponseCallback<JSONObject>() {
            @Override
            public void onSuccess(JSONObject response) {
                android.util.Log.d("DB_INIT", "Database Initialized Successfully");
            }

            @Override
            public void onError(String message) {
                if (message.contains("ClientError") || message.contains("400")) {
                    android.util.Log.d("DB_INIT", "Database already exists (Error 400). This is fine.");
                } else {
                    // Actual network error
                    android.util.Log.e("DB_INIT", "Real Connection Error: " + message);
                }
            }
        });


        //Button linking logic below
        //finding signUp button
        Button signUpBtn = findViewById(R.id.signUpButton);
        //setting on click functionality
        signUpBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(StartUpActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });

        //finding login button
        Button loginBtn = findViewById(R.id.loginButton);

        //login button on click functionality
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StartUpActivity.this, Login_Activity.class);
                startActivity(intent);
            }
        });

        //finding t&c button
        Button tCBtn = findViewById(R.id.viewTCBtn);

        //tc button on click functionality
        tCBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StartUpActivity.this, T_C_Activity.class);
                startActivity(intent);
            }
        });


    }
}