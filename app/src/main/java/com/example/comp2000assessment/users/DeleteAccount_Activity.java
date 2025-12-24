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

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.comp2000assessment.R;
import com.example.comp2000assessment.settings.Settings;

import org.json.JSONObject;

public class DeleteAccount_Activity extends AppCompatActivity {

    private UserAPI_Helper api_helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_delete_account);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.delete_account_page), (v, insets) -> {
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

        Button cancelDeleteAccountBtn = findViewById(R.id.cancelDelAccountBtn);
        cancelDeleteAccountBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DeleteAccount_Activity.this, Settings.class);

                Toast.makeText(DeleteAccount_Activity.this, "Good choice :D", Toast.LENGTH_SHORT).show();

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

        //delete the users account functionality
        Button confirmDeleteAccountBtn = findViewById(R.id.confirmDeleteAccountBtn);
        confirmDeleteAccountBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //initialising the api helper
                api_helper = new UserAPI_Helper(DeleteAccount_Activity.this);

                //making an app user object so it can be passed to the api helper class method deletUser
                AppUser user = new AppUser(user_firstname, user_lastname, user_contact, user_email, user_username, user_password, user_usertype);

                //disabling both buttons whilst delete user process is happening
                confirmDeleteAccountBtn.setEnabled(false);
                confirmDeleteAccountBtn.setText("Deleting...");

                cancelDeleteAccountBtn.setEnabled(false);
                cancelDeleteAccountBtn.setText("Cannot cancel");

                //call the api
                UserAPI_Helper.deleteUser(user, api_helper, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String message = response.optString("message");
                            //check for the message
                            if (message.toLowerCase().contains("User deleted successfully") || response.has("message")) {

                                Toast.makeText(DeleteAccount_Activity.this, "Account Deleted!", Toast.LENGTH_SHORT).show();

                                //navigate to start up activity
                                Intent intent = new Intent(DeleteAccount_Activity.this, StartUpActivity.class);

                                //ensuring user can't just click back to an activity logged in after deleting account
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                                finish();

                            } else {
                                // in case api returned response that deleting failed
                                Toast.makeText(DeleteAccount_Activity.this, "Failed: " + message, Toast.LENGTH_SHORT).show();
                                confirmDeleteAccountBtn.setEnabled(true);
                                confirmDeleteAccountBtn.setText("Yes, delete my account :(");
                                cancelDeleteAccountBtn.setEnabled(true);
                                cancelDeleteAccountBtn.setText("No, I want to stay :)");
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            confirmDeleteAccountBtn.setEnabled(true);
                            confirmDeleteAccountBtn.setText("Yes, delete my account :(");
                            cancelDeleteAccountBtn.setEnabled(true);
                            cancelDeleteAccountBtn.setText("No, I want to stay :)");
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        String errorMsg = "Network Error";

                        //check error response is not null and get the error code, i.e. 400 or 404
                        if (error.networkResponse != null) {
                            int statusCode = error.networkResponse.statusCode;

                            try {
                                //get the 'detail' message from api
                                String responseBody = new String(error.networkResponse.data, "UTF-8");
                                JSONObject data = new JSONObject(responseBody);

                                //check it has 'detail' key
                                if (data.has("detail")) {
                                    errorMsg = data.getString("detail");
                                } else {
                                    errorMsg = "Error " + statusCode;
                                }

                                //if user not found
                                if (statusCode == 404) {
                                    errorMsg = "User not found";
                                }

                            } catch (Exception e) {
                                e.printStackTrace();
                                //print the error
                                errorMsg = "Error " + statusCode;
                            }
                        } else if (error.getMessage() != null) {
                            errorMsg = error.getMessage();
                        }

                        //send toast that deleting the user failed
                        Toast.makeText(DeleteAccount_Activity.this, "Delete Failed: " + errorMsg, Toast.LENGTH_LONG).show();

                        //bring back the buttons
                        confirmDeleteAccountBtn.setEnabled(true);
                        confirmDeleteAccountBtn.setText("Yes, delete my account :(");
                        cancelDeleteAccountBtn.setEnabled(true);
                        cancelDeleteAccountBtn.setText("No, I want to stay :)");
                    }
                });
            }
        });
    }
}