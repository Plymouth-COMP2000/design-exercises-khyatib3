package com.example.comp2000assessment.menu;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.comp2000assessment.databases.MenuDatabaseHelper;
import com.example.comp2000assessment.R;
import com.example.comp2000assessment.notifications.NotificationsHelper;

public class AddMenuItemActivity extends AppCompatActivity {
    private Bitmap selectedBitmap;
    private ImageView imageView;
    private static String staff_usertype;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_menu_item);
        imageView = findViewById(R.id.previewImage);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.addNewItemScreen), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //getting staff details
        String staff_firstname = getIntent().getStringExtra("staff_firstname");
        String staff_lastname = getIntent().getStringExtra("staff_lastname");
        String staff_contact = getIntent().getStringExtra("staff_contact");
        String staff_email = getIntent().getStringExtra("staff_email");
        String staff_username = getIntent().getStringExtra("staff_username");
        String staff_password = getIntent().getStringExtra("staff_password");

        //in the event that staff_usertype is null, set it to staff
        staff_usertype = getIntent().getStringExtra("staff_usertype");
        //setting staff_usertype to staff in case it becomes null
        if (staff_usertype == null || staff_usertype.isEmpty()) {
            staff_usertype = "staff";
        }
        boolean staff_logged_in = getIntent().getBooleanExtra("staff_logged_in", true);

        ImageButton addItemBackBtn = findViewById(R.id.addItemBackBtn);
        addItemBackBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(AddMenuItemActivity.this, Staff_Menu_Activity.class);

                //passing staff details
                intent.putExtra("staff_firstname", staff_firstname);
                intent.putExtra("staff_lastname", staff_lastname);
                intent.putExtra("staff_contact", staff_contact);
                intent.putExtra("staff_email", staff_email);
                intent.putExtra("staff_username", staff_username);
                intent.putExtra("staff_password", staff_password);
                intent.putExtra("staff_usertype", staff_usertype);
                intent.putExtra("staff_logged_in", staff_logged_in);

                startActivity(intent);
            }
        });

        Button browseBtn =  findViewById(R.id.uploadImgBtn);
        browseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent,100);

                //passing staff details
                intent.putExtra("staff_firstname", staff_firstname);
                intent.putExtra("staff_lastname", staff_lastname);
                intent.putExtra("staff_contact", staff_contact);
                intent.putExtra("staff_email", staff_email);
                intent.putExtra("staff_username", staff_username);
                intent.putExtra("staff_password", staff_password);
                intent.putExtra("staff_usertype", staff_usertype);
                intent.putExtra("staff_logged_in", staff_logged_in);

            }
        });

        Button addItemBtn = findViewById(R.id.addItemBtn);
        addItemBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                //convert bitmap image to bytes
                if (selectedBitmap == null) {
                    Toast.makeText(AddMenuItemActivity.this, "Please select an image first", Toast.LENGTH_SHORT).show();
                    return;
                }
                //resize the bitmap before saving into menu db
                Bitmap smallBitmap = resizeBitmap(selectedBitmap, 500);

                //compress
                java.io.ByteArrayOutputStream stream = new java.io.ByteArrayOutputStream();
                smallBitmap.compress(Bitmap.CompressFormat.JPEG, 50, stream);
                byte[] imageBytes = stream.toByteArray();


                //locating elements via their id
                EditText addName = findViewById(R.id.inputItemName);
                EditText addPrice = findViewById(R.id.inputItemPrice);
                EditText addDescription = findViewById(R.id.inputDescription);
                Spinner categorySpinner = findViewById(R.id.categorySelection);

                String itemName = addName.getText().toString();
                double itemPrice = Double.parseDouble(String.valueOf(addPrice.getText()));
                String itemDesc = addDescription.getText().toString();
                String categoryString = categorySpinner.getSelectedItem().toString();
                int itemCategory = Integer.parseInt(categoryString.substring(0, 1));

                RestMenuItem menuItem = new RestMenuItem(itemName, itemPrice, itemCategory, itemDesc, imageBytes);
                MenuDatabaseHelper db = new MenuDatabaseHelper(AddMenuItemActivity.this);
                boolean insert_result = db.addItem(menuItem);

                if(insert_result){
                    Intent intent = new Intent(AddMenuItemActivity.this, Item_Add_Confirm_Activity.class);
                    Toast.makeText(AddMenuItemActivity.this, "Menu Updated Successfully!", Toast.LENGTH_SHORT).show();

                    //push notification that item was added
                    NotificationsHelper.displayNotification(AddMenuItemActivity.this, "New Menu Item Added", "A new item has been added to the menu: " + itemName);

                    //passing staff details
                    intent.putExtra("staff_firstname", staff_firstname);
                    intent.putExtra("staff_lastname", staff_lastname);
                    intent.putExtra("staff_contact", staff_contact);
                    intent.putExtra("staff_email", staff_email);
                    intent.putExtra("staff_username", staff_username);
                    intent.putExtra("staff_password", staff_password);
                    intent.putExtra("staff_usertype", staff_usertype);
                    intent.putExtra("staff_logged_in", staff_logged_in);

                    startActivity(intent);
                }else{
                    Intent intent = new Intent(AddMenuItemActivity.this, Error_Add_MenuItem.class);

                    //passing staff details
                    intent.putExtra("staff_firstname", staff_firstname);
                    intent.putExtra("staff_lastname", staff_lastname);
                    intent.putExtra("staff_contact", staff_contact);
                    intent.putExtra("staff_email", staff_email);
                    intent.putExtra("staff_username", staff_username);
                    intent.putExtra("staff_password", staff_password);
                    intent.putExtra("staff_usertype", staff_usertype);
                    intent.putExtra("staff_logged_in", staff_logged_in);

                    startActivity(intent);
                }
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 100 && resultCode == RESULT_OK) {
            Uri imageUri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                selectedBitmap = bitmap;   // store in a field
                imageView.setImageBitmap(bitmap); // preview
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private Bitmap resizeBitmap(Bitmap original, int maxWidth) {
        int width = original.getWidth();
        int height = original.getHeight();
        float ratio = (float) width / height;
        int newHeight = (int) (maxWidth / ratio);

        return Bitmap.createScaledBitmap(original, maxWidth, newHeight, true);
    }

}