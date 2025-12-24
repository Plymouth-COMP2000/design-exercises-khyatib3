package com.example.comp2000assessment.users;

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

import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.comp2000assessment.R;
import com.example.comp2000assessment.homepages.StaffDashboard;

public class New_Staff_Admin extends AppCompatActivity {

    private UserAPI_Helper api_helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_new_staff_admin);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.addNewStaffScreen), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ImageButton register_HomeBtn = findViewById(R.id.staff_RegisterHomeBtn);
        register_HomeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(New_Staff_Admin.this, StaffDashboard.class);
                startActivity(intent);
            }
        });

        EditText firstNameInput = findViewById(R.id.staff_enterFName);
        EditText lastNameInput = findViewById(R.id.staff_enterLName);
        EditText workEmailInput = findViewById(R.id.staff_enterWorkEmail);
        EditText phoneInput = findViewById(R.id.staff_enterPhoneNo);
        EditText usernameInput = findViewById(R.id.staff_CreateUsername);
        EditText passwordInput = findViewById(R.id.staff_enterPwd);

        api_helper = new UserAPI_Helper(New_Staff_Admin.this);


        Button addNewStaffBtn = findViewById(R.id.staff_registerBtn);
        addNewStaffBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //retrieve values from inout fields
                String staff_firstname = firstNameInput.getText().toString().trim();
                String staff_lastname = lastNameInput.getText().toString().trim();
                String staff_email = workEmailInput.getText().toString().trim();
                String staff_contact = phoneInput.getText().toString().trim();
                String staff_username = usernameInput.getText().toString().trim();
                String staff_password = passwordInput.getText().toString().trim();

                //check all fields were entered
                if(staff_firstname.isEmpty() || staff_lastname.isEmpty() || staff_email.isEmpty() ||
                        staff_contact.isEmpty() || staff_username.isEmpty() || staff_password.isEmpty()){
                    Toast.makeText(New_Staff_Admin.this, "You must fill in ALL the fields!", Toast.LENGTH_SHORT).show();
                    return;
                }

                //disabling the button from being clicked so staff account doesn't get made accidentally whilst checks are being performed
                addNewStaffBtn.setEnabled(false);
                addNewStaffBtn.setText("Validating account...");

                //performing a check on username to see if it is unique
                checkUsernameRegister(staff_username, staff_password, staff_firstname, staff_lastname, staff_email, staff_contact, addNewStaffBtn);
            }
        });

    }

    private void checkUsernameRegister(String staff_username, String staff_password, String staff_firstname, String staff_lastname, String email, String staff_contact, Button submitBtn) {
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

                            if (existingUsername.equalsIgnoreCase(staff_username)) {
                                usernameTaken = true;
                                break;
                            }
                        }
                    }

                    if (usernameTaken) {
                        // sending toast to user that the username they're signing up with is already taken
                        Toast.makeText(New_Staff_Admin.this, "Username already taken! Please choose another.", Toast.LENGTH_LONG).show();
                        submitBtn.setEnabled(true);
                        submitBtn.setText("Submit");
                    } else {
                        //else if the username is unique, then go ahead and register the new user
                        registerUser(staff_firstname, staff_lastname, staff_contact, email, staff_username, staff_password, submitBtn);
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
                    registerUser(staff_firstname, staff_lastname, staff_contact, email, staff_username, staff_password, submitBtn);
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

                    Toast.makeText(New_Staff_Admin.this, "Network Error: " + errorMessage, Toast.LENGTH_LONG).show();
                    submitBtn.setEnabled(true);
                    submitBtn.setText("Submit");
                }
            }
        });
    }

    private void registerUser(String staff_firstname, String staff_lastname, String staff_contact, String staff_email, String staff_username, String staff_password, Button submitBtn) {
        //making user object
        //setting user type to staff, as this is the add new staff screen
        AppUser newUser = new AppUser(staff_firstname, staff_lastname, staff_contact, staff_email, staff_username, staff_password, "staff");
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
                            //if so, show the account created screen
                            Toast.makeText(New_Staff_Admin.this, "New staff member successfully added!", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(New_Staff_Admin.this, StaffDashboard.class);

                            //passing the user details to the next activity
                            intent.putExtra("staff_firstname", staff_firstname);
                            intent.putExtra("staff_lastname", staff_lastname);
                            intent.putExtra("staff_contact", staff_contact);
                            intent.putExtra("staff_email", staff_email);
                            intent.putExtra("staff_username", staff_username);
                            intent.putExtra("staff_password", staff_password);
                            intent.putExtra("staff_usertype", newUser.getUserType());
                            intent.putExtra("staff_logged_in", newUser.isLogged_in());

                            startActivity(intent);
                            finish();
                        } else {
                            //return api error message (so when testing we know what the error was in registering)
                            Toast.makeText(New_Staff_Admin.this, "API Error: " + message, Toast.LENGTH_LONG).show();
                            submitBtn.setEnabled(true);
                            submitBtn.setText("Submit");
                        }
                    } else {
                        //in the event the api didnt respond in the way we expected to (again, for testing)
                        Toast.makeText(New_Staff_Admin.this, "Invalid response from server.", Toast.LENGTH_LONG).show();
                        submitBtn.setEnabled(true);
                        submitBtn.setText("Submit");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(New_Staff_Admin.this, "Error parsing server response.", Toast.LENGTH_LONG).show();
                    //so we can retry, enable button
                    submitBtn.setEnabled(true);
                    submitBtn.setText("Submit");
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

                //failed to add staff member, return toast
                Toast.makeText(New_Staff_Admin.this, "Failed to add staff: " + error.getMessage(), Toast.LENGTH_LONG).show();
                submitBtn.setEnabled(true);
                submitBtn.setText("Submit");
            }
        }, api_helper);

    }


}