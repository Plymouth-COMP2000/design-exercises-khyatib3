package com.example.comp2000assessment.users;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.comp2000assessment.R;

import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SignUpActivity extends AppCompatActivity {
    private UserAPI_Helper api_helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_up);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.signUpScreen), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //initialising api helper
        api_helper = new UserAPI_Helper(this);

        ImageButton goBackBtn = findViewById(R.id.backButton);
        //setting on click functionality
        goBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUpActivity.this, StartUpActivity.class);
                startActivity(intent);
            }
        });

        Button submitBtn = findViewById(R.id.submitRegButton);
        //setting on click functionality
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View view) {
                EditText firstNameInput = findViewById(R.id.firstNameInput);
                EditText lastNameInput = findViewById(R.id.lastNameInput);
                EditText emailInput = findViewById(R.id.emailInput);
                EditText phoneInput = findViewById(R.id.phoneInput);
                EditText usernameInput = findViewById(R.id.usernameInput);
                EditText passwordInput = findViewById(R.id.passwordInput);

                //getting user entered values
                String firstname = firstNameInput.getText().toString().trim();
                String lastname = lastNameInput.getText().toString().trim();
                String email = emailInput.getText().toString().trim();
                String contact = phoneInput.getText().toString().trim();
                String username = usernameInput.getText().toString().trim();
                String password = passwordInput.getText().toString().trim();

                //performing validation checks on the entered values
                if (firstname.isEmpty() || lastname.isEmpty() || email.isEmpty() ||
                        contact.isEmpty() || username.isEmpty() || password.isEmpty()) {
                    Toast.makeText(SignUpActivity.this, "All fields are required!", Toast.LENGTH_SHORT).show();
                    return;
                }

                //feedback change below:
                if(firstname.matches(".*[0-9].*") || firstname.matches(".*[0-9].*")){
                    Toast.makeText(SignUpActivity.this, "A name cannot contain numbers!", Toast.LENGTH_LONG).show();
                    return;
                }

                if(!email.contains("@") || !email.contains(".")){
                    Toast.makeText(SignUpActivity.this, "Email must contain @ and .", Toast.LENGTH_LONG).show();
                    return;
                }

                if(!contact.startsWith("0") || contact.length() != 11){
                    Toast.makeText(SignUpActivity.this, "Please enter a valid phone number!", Toast.LENGTH_LONG).show();
                    return;
                }

                if(password.length() < 8){
                    Toast.makeText(SignUpActivity.this, "Password must be at least 8 characters!", Toast.LENGTH_LONG).show();
                    return;
                } else if (!password.matches(".*[!£$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>/?@#~].*")) {
                    Toast.makeText(SignUpActivity.this, "Password must contain at least 1 special character!", Toast.LENGTH_LONG).show();
                    return;
                }else if(!password.matches(".*[A-Z].*")){
                    Toast.makeText(SignUpActivity.this, "Password must contain at least 1 uppercase character!", Toast.LENGTH_LONG).show();
                    return;
                } else if (!password.matches(".*[0-9]*")) {
                    Toast.makeText(SignUpActivity.this, "Password must contain at least 1 number!", Toast.LENGTH_LONG).show();
                    return;
                }
                //end of feedback change

                //disabling the button from being clicked so user account doesn't get made accidentally whilst checks are being performed
                submitBtn.setEnabled(false);
                submitBtn.setText("Validating account...");

                //performing a check on username to see if it is unique
                checkUsernameRegister(username, password, firstname, lastname, email, contact, submitBtn);
            }
        });

    }

    private void checkUsernameRegister(String username, String password, String firstname, String lastname, String email, String contact, Button submitBtn) {
        api_helper.getAllUsers(api_helper, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    boolean usernameTaken = false;

                    //checking the api responds with array of users
                    if (response.has("users")) {

                        //getting users array
                        JSONArray usersArray = response.getJSONArray("users");

                        //iterating through the users array to check for a username that matches the new user's username
                        for (int i = 0; i < usersArray.length(); i++) {
                            JSONObject userObj = usersArray.getJSONObject(i);
                            // Use optString to avoid crashes if key is missing
                            String existingUsername = userObj.optString("username");

                            if (existingUsername.equalsIgnoreCase(username)) {
                                usernameTaken = true;
                                break;
                            }
                        }
                    }

                    if (usernameTaken) {
                        // sending toast to user that the username they're signing up with is already taken
                        Toast.makeText(SignUpActivity.this, "Username already taken! Please choose another.", Toast.LENGTH_LONG).show();
                        submitBtn.setEnabled(true);
                        submitBtn.setText("Sign Up");
                    } else {
                        //else if the username is unique, then go ahead and register the new user
                        registerUser(firstname, lastname, contact, email, username, password, submitBtn);
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //if user array is empty, no users added yet, so still safe to add a user
                if (error.networkResponse != null && error.networkResponse.statusCode == 404) {
                    registerUser(firstname, lastname, contact, email, username, password, submitBtn);
                } else {
                    //debugging the error
                    String errorMessage = "Unknown error";
                    if (error.networkResponse != null) {
                        errorMessage = "Status Code: " + error.networkResponse.statusCode;
                    } else if (error.getMessage() != null) {
                        errorMessage = error.getMessage();
                    } else {
                        errorMessage = error.getClass().getSimpleName();
                    }

                    error.printStackTrace();

                    Toast.makeText(SignUpActivity.this, "Network Error: " + errorMessage, Toast.LENGTH_LONG).show();
                    submitBtn.setEnabled(true);
                    submitBtn.setText("Sign Up");
                }
            }
        });
    }

    private void registerUser(String firstname, String lastname, String contact, String email, String username, String password, Button submitBtn) {
        //making user object
        //setting user type to (guest) -- anyone who signs up on the sign up activity screen is guest
        //anyone who signs up using the add new staff screen will be a staff member so will assign user type (staff) there
        AppUser newUser = new AppUser(firstname, lastname, contact, email, username, password, "guest");
        newUser.setLogged_in(true);

        //creating new user via api
        UserAPI_Helper.createUser(newUser, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    //first check that the api response actually has the key 'message'
                    if (response.has("message")) {

                        //get the message
                        String message = response.getString("message");

                        //check message says 'user created successfully'
                        if (message.equals("User created successfully")) {
                            //if so, toast and save user globally
                            ManageUser.getInstance().setCurrentUser(newUser);
                            Toast.makeText(SignUpActivity.this, "Account Created Successfully!", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(SignUpActivity.this, AccountCreated.class);

                            startActivity(intent);
                            finish();
                        } else {
                            //return api error message (so when testing we know what the error was in registering)
                            Toast.makeText(SignUpActivity.this, "API Error: " + message, Toast.LENGTH_LONG).show();
                            submitBtn.setEnabled(true);
                            submitBtn.setText("Sign Up");
                        }
                    } else {
                        //in the event the api didnt respond in the way we expected to (again, for testing)
                        Toast.makeText(SignUpActivity.this, "Invalid response from server.", Toast.LENGTH_LONG).show();
                        submitBtn.setEnabled(true);
                        submitBtn.setText("Sign Up");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(SignUpActivity.this, "Error parsing server response.", Toast.LENGTH_LONG).show();
                    //so we can retry, enable button
                    submitBtn.setEnabled(true);
                    submitBtn.setText("Sign Up");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                String errorMessage = "Unknown Registration Error";
                if (error.networkResponse != null) {
                    errorMessage = "Status Code: " + error.networkResponse.statusCode;
                    try {

                        String responseBody = new String(error.networkResponse.data, "UTF-8");
                        errorMessage += "\nBody: " + responseBody;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else if (error.getMessage() != null) {
                    errorMessage = error.getMessage();
                } else {
                    errorMessage = error.getClass().getSimpleName();
                }

                error.printStackTrace();

                //registration failed, return toast
                Toast.makeText(SignUpActivity.this, "Registration Failed: " + error.getMessage(), Toast.LENGTH_LONG).show();
                submitBtn.setEnabled(true);
                submitBtn.setText("Sign Up");
            }
        }, api_helper);

    }
}