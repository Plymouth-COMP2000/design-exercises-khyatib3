package com.example.comp2000assessment.users;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.android.volley.Response;

import com.android.volley.VolleyError;
import com.example.comp2000assessment.R;
import com.example.comp2000assessment.databases.BookingsDatabaseHelper;
import com.example.comp2000assessment.settings.Settings;

import org.json.JSONObject;

public class UpdateAccount_Activity extends AppCompatActivity {

    private UserAPI_Helper api_helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_update_account);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.update_account_page), (v, insets) -> {
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


        EditText fNameInput = findViewById(R.id.accUpdate_firstName);
        EditText lNameInput = findViewById(R.id.accUpdate_lastName);
        EditText emailInput = findViewById(R.id.accUpdate_email);
        EditText phoneInput = findViewById(R.id.accUpdate_phone);
        EditText usernameInput = findViewById(R.id.accUpdate_username);
        EditText passwordInput = findViewById(R.id.accUpdate_password);

        //so users can see their current info and make a change depending on that
        fNameInput.setText(user_firstname);
        lNameInput.setText(user_lastname);
        emailInput.setText(user_email);
        phoneInput.setText(user_contact);
        usernameInput.setText(user_username);
        passwordInput.setText(user_password);

        //back to settings
        ImageButton backToSettingsBtn = findViewById(R.id.updateAccountBackBtn);
        backToSettingsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UpdateAccount_Activity.this, Settings.class);

                //passing the user details
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

        //update the user's account
        Button updateAccountBtn = findViewById(R.id.updateAccountBtn);
        updateAccountBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //getting input values
                String new_firstname = fNameInput.getText().toString();
                String new_lastname = lNameInput.getText().toString();
                String new_email = emailInput.getText().toString();
                String new_contact = phoneInput.getText().toString();
                String new_username = usernameInput.getText().toString();
                String new_password = passwordInput.getText().toString();

                //if any of the fields are left empty tell user, since api expects updated info for each
                if (new_firstname.isEmpty() || new_lastname.isEmpty() || new_email.isEmpty() ||
                        new_contact.isEmpty() || new_password.isEmpty()) {
                    Toast.makeText(UpdateAccount_Activity.this, "All fields must be filled out!", Toast.LENGTH_SHORT).show();
                    return;
                }
                updateAccountBtn.setText("Checking username...");

                if (!new_username.equalsIgnoreCase(user_username)) {
                    //username changed, so check inf unique
                    checkUsernameAndUpdate(new_username, user_firstname, user_lastname, new_firstname, new_lastname, new_contact, new_email, new_password, user_usertype, user_logged_in, updateAccountBtn);
                } else {
                    //username hasn't changed dont perform check
                    performUpdate(user_username, new_username, user_firstname, user_lastname, new_firstname, new_lastname, new_contact, new_email, new_password, user_usertype, user_logged_in, updateAccountBtn);
                }
            }
        });
    }
    private void checkUsernameAndUpdate(String new_username, String oldFName, String oldLName, String fName, String lName, String contact, String email, String password, String usertype, boolean loggedIn, Button btn) {
        btn.setText("Checking username...");

        api_helper.getAllUsers(api_helper, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                boolean usernameTaken = false;
                try {
                    if (response.has("users")) {
                        org.json.JSONArray usersArray = response.getJSONArray("users");

                        //loop through users to see if taken
                        for (int i = 0; i < usersArray.length(); i++) {
                            JSONObject userObj = usersArray.getJSONObject(i);
                            if (userObj.optString("username").equalsIgnoreCase(new_username)) {
                                usernameTaken = true;
                                break;
                            }
                        }
                    }

                    //if not unique, tell user
                    if (usernameTaken) {
                        Toast.makeText(UpdateAccount_Activity.this, "That username is already taken!", Toast.LENGTH_SHORT).show();
                        resetButton(btn);
                    } else {
                        //new username is unique, update user, pass original username
                        String originalUsername = getIntent().getStringExtra("user_username");
                        performUpdate(originalUsername, new_username, oldFName, oldLName, fName, lName, contact, email, password, usertype, loggedIn, btn);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    resetButton(btn);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //if error 404 occurs, where there are no users
                if (error.networkResponse != null && error.networkResponse.statusCode == 404) {
                    String originalUsername = getIntent().getStringExtra("user_username");
                    performUpdate(originalUsername, new_username, oldFName, oldLName, fName, lName, contact, email, password, usertype, loggedIn, btn);
                } else {
                    Toast.makeText(UpdateAccount_Activity.this, "Network check failed", Toast.LENGTH_SHORT).show();
                    resetButton(btn);
                }
            }
        });
    }

    // Method 2: The actual Update Call
    private void performUpdate(String originalUsername, String newUsername, String oldFName, String oldLName, String fName, String lName, String contact, String email, String password, String usertype, boolean loggedIn, Button btn) {
        btn.setText("Updating...");
        btn.setEnabled(false);

        //making an app user object so it can be converted to json in api helper class and sent to api
        AppUser updatedUser = new AppUser(fName, lName, contact, email, newUsername, password, usertype);
        updatedUser.setLogged_in(loggedIn);

        //all api update user
        UserAPI_Helper.updateUser(originalUsername, updatedUser, api_helper, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String message = response.optString("message");
                    if (message.toLowerCase().contains("success") || response.has("message")) {
                        Toast.makeText(UpdateAccount_Activity.this, "Account Updated!", Toast.LENGTH_SHORT).show();

                        //call updateBookingHolderName immediately after updating user
                        BookingsDatabaseHelper db = new BookingsDatabaseHelper(UpdateAccount_Activity.this);
                        boolean result = db.updateBookingHolderName(oldFName, fName, oldLName, lName);

                        //go back to settings with updated info
                        Intent intent = new Intent(UpdateAccount_Activity.this, Settings.class);
                        intent.putExtra("user_firstname", fName);
                        intent.putExtra("user_lastname", lName);
                        intent.putExtra("user_contact", contact);
                        intent.putExtra("user_email", email);
                        intent.putExtra("user_username", newUsername);
                        intent.putExtra("user_password", password);
                        intent.putExtra("user_usertype", usertype);
                        intent.putExtra("user_logged_in", loggedIn);

                        startActivity(intent);
                        finish();


                    } else {
                        Toast.makeText(UpdateAccount_Activity.this, "Failed: " + message, Toast.LENGTH_LONG).show();
                        resetButton(btn);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    resetButton(btn);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(UpdateAccount_Activity.this, "Update Failed (Network)", Toast.LENGTH_SHORT).show();
                resetButton(btn);
            }
        });
    }
    private void resetButton(Button btn) {
        btn.setEnabled(true);
        btn.setText("Update Account");
    }
}