package com.example.comp2000assessment.users;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.comp2000assessment.R;
import com.example.comp2000assessment.homepages.GuestHomepage;
import com.example.comp2000assessment.homepages.StaffDashboard;

import org.json.JSONException;
import org.json.JSONObject;

public class Login_Activity extends AppCompatActivity {
    private UserAPI_Helper api_helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.loginScreen), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //initialise api helper
        api_helper = new UserAPI_Helper(this);

        //retrieving user input
        EditText usernameInput = findViewById(R.id.usernameInput);
        EditText passwordInput = findViewById(R.id.passwordInput);

        String enteredUsername = usernameInput.getText().toString().trim();
        String enteredPassword = passwordInput.getText().toString().trim();


        //find submit button
        Button submitBtn = findViewById(R.id.submitButton);
        //setting on click functionality
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String enteredUsername = usernameInput.getText().toString().trim();
                String enteredPassword = passwordInput.getText().toString().trim();

                //checking if username and password are empty
                if (usernameInput.getText().toString().isEmpty() || passwordInput.getText().toString().isEmpty()) {
                    Toast.makeText(Login_Activity.this, "You must fill BOTH fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                //like with sign up, whilst performing checks, disable the login button
                submitBtn.setEnabled(false);
                submitBtn.setText("Checking credentials...");

                UserAPI_Helper.getSpecificUser(enteredUsername, api_helper, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            //check user key exists
                            if (response.has("user")) {
                                JSONObject userObj = response.getJSONObject("user");

                                //convert json object to app user object-- for passing data
                                AppUser user = AppUser.returnAppUserFromJson(userObj);

                                //check both creds match
                                if (user.getPassword().equalsIgnoreCase(enteredPassword) && user.getUsername().equalsIgnoreCase(enteredUsername)) {
                                    //if match, login successful
                                    //so save the user globally and toast
                                    ManageUser.getInstance().setCurrentUser(user);

                                    Toast.makeText(Login_Activity.this, "Login Successful!", Toast.LENGTH_SHORT).show();

                                    Intent intent;

                                    //find out where to navigate to depending on user type
                                    if(user.getUserType().equalsIgnoreCase("guest")){
                                        intent = new Intent(Login_Activity.this, GuestHomepage.class);

                                        startActivity(intent);
                                        finish();

                                    }else if (user.getUserType().equalsIgnoreCase("staff")){
                                        intent = new Intent(Login_Activity.this, StaffDashboard.class);

                                        startActivity(intent);
                                        finish();
                                    }
                                } else {
                                    //if password entered was incorrect tell user
                                    Toast.makeText(Login_Activity.this, "Incorrect Password", Toast.LENGTH_SHORT).show();
                                    resetButton(submitBtn);
                                }
                            } else {
                                //if api sent back invalid response (for testing)
                                Toast.makeText(Login_Activity.this, "Invalid server response", Toast.LENGTH_SHORT).show();
                                resetButton(submitBtn);
                            }
                        } catch (JSONException e) {
                            //something was wrong with parsing user data
                            e.printStackTrace();
                            Toast.makeText(Login_Activity.this, "Error parsing user data", Toast.LENGTH_SHORT).show();
                            resetButton(submitBtn);
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //i user not found error happens
                        if (error.networkResponse != null && error.networkResponse.statusCode == 404) {
                            Toast.makeText(Login_Activity.this, "User not found", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(Login_Activity.this, "Network Error. Check VPN/Connection", Toast.LENGTH_SHORT).show();
                        }
                        error.printStackTrace();
                        resetButton(submitBtn);
                    }
                });
            }
        });

        //back to start up page logic
        Button backStartUpBtn = findViewById(R.id.loginBackBtn);
        backStartUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login_Activity.this, StartUpActivity.class);
                startActivity(intent);
            }
        });

    }


    private void resetButton(Button btn) {
        btn.setEnabled(true);
        btn.setText("Submit"); // Or whatever your original text was
    }




}
