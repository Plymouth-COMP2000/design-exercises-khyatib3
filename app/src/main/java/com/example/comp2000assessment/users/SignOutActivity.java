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

        //get the current user using ManageUser
        AppUser currentUser = ManageUser.getInstance().getCurrentUser();
        //check that current user isnt null
        if (currentUser == null) {
            //in case the app was killed in the background, send user back to the login screen
            //as a safety measure
            Intent intent = new Intent(this, Login_Activity.class);
            startActivity(intent);
            finish();
            return;
        }

        Button backHomeBtn = findViewById(R.id.cancelSignOutBtn);
        //setting on click functionality
        backHomeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignOutActivity.this, Settings.class);
                startActivity(intent);
            }
        });

        Button confirmSignOutBtn = findViewById(R.id.confirmSignOutBtn);
        //setting on click functionality
        confirmSignOutBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                //clearing singleton so user cant open app logged in already
                ManageUser.getInstance().setCurrentUser(null);

                Intent intent = new Intent(SignOutActivity.this, StartUpActivity.class);

                //ensuring user can't just click back to an activity logged in after logging out
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                startActivity(intent);
                finish();
            }
        });
    }
}