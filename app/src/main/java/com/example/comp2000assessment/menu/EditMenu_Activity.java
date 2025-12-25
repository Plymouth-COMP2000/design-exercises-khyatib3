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

public class EditMenu_Activity extends AppCompatActivity {
    private Bitmap selectedBitmap;
    private ImageView imageView;
    private byte[] currentImageBytes;
    private int THIS_ITEM_ID;
    private static String staff_usertype;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_edit_menu);
        imageView = findViewById(R.id.previewNewImg);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.edit_menu_item_page), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //getting attributes the menu activity intent passed
        THIS_ITEM_ID = getIntent().getIntExtra("itemID", -1);
        currentImageBytes = getIntent().getByteArrayExtra("image");
        String itemName = getIntent().getStringExtra("name");
        String itemDescription = getIntent().getStringExtra("description");
        double itemPrice = getIntent().getDoubleExtra("price", 0.00);
        int itemCategory = getIntent().getIntExtra("categoryID", -1);

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

        //finding input fields
        EditText editName = findViewById(R.id.editNameInput);
        EditText editDescription = findViewById(R.id.editDescriptionInput);
        EditText editPrice = findViewById(R.id.editPriceInput);
        Spinner editCategorySpinner = findViewById(R.id.editCategory);

        //filling the input fields with the attributes values
        editName.setText(itemName);
        editDescription.setText(itemDescription);
        editPrice.setText(String.valueOf(itemPrice));

        //handling image preview
        if (currentImageBytes != null && currentImageBytes.length > 0) {
            Bitmap existingBitmap = RestMenuItem.bytesToBitmap(currentImageBytes);
            if (existingBitmap != null) {
                imageView.setImageBitmap(existingBitmap);
            } else {
                imageView.setImageResource(R.drawable.ic_placeholder);
            }
        } else {
            imageView.setImageResource(R.drawable.ic_placeholder);
        }

        //setting spinner to current category
        String categoryString = Integer.toString(itemCategory);
        switch (categoryString) {
            case "1":
                editCategorySpinner.setSelection(1);
                break;
            case "2":
                editCategorySpinner.setSelection(2);
                break;
            case "3":
                editCategorySpinner.setSelection(3);
                break;
            case "4":
                editCategorySpinner.setSelection(4);
                break;
            case "5":
                editCategorySpinner.setSelection(5);
                break;
            default:
                editCategorySpinner.setSelection(0);
                break;
        }

        //go back to the menu arrow button
        ImageButton backBtn = findViewById(R.id.editMenuToMainMenuBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditMenu_Activity.this, Staff_Menu_Activity.class);

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

        //upload image button functionality
        Button browseBtn = findViewById(R.id.editImageBtn);
        browseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");

                //passing staff details
                intent.putExtra("staff_firstname", staff_firstname);
                intent.putExtra("staff_lastname", staff_lastname);
                intent.putExtra("staff_contact", staff_contact);
                intent.putExtra("staff_email", staff_email);
                intent.putExtra("staff_username", staff_username);
                intent.putExtra("staff_password", staff_password);
                intent.putExtra("staff_usertype", staff_usertype);
                intent.putExtra("staff_logged_in", staff_logged_in);

                startActivityForResult(intent, 100);
            }
        });

        Button cancelChangesBtn = findViewById(R.id.cancelMenuChangesBtn);
        cancelChangesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditMenu_Activity.this, Staff_Menu_Activity.class);

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

        Button saveChangesBtn = findViewById(R.id.saveMenuChangeBtn);
        saveChangesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                byte[] finalImageBytes;

                if (selectedBitmap != null) {
                    finalImageBytes = RestMenuItem.bitmapToBytes(selectedBitmap);
                } else {
                    finalImageBytes = currentImageBytes;
                }

                //locating elements via id
                EditText editName = findViewById(R.id.editNameInput);
                EditText editDescription = findViewById(R.id.editDescriptionInput);
                EditText editPrice = findViewById(R.id.editPriceInput);
                Spinner editCategorySpinner = findViewById(R.id.editCategory);

                //extracting values from the elements
                String newName = editName.getText().toString();
                String newDescription = editDescription.getText().toString();
                double newPrice = Double.parseDouble(String.valueOf(editPrice.getText()));
                String categoryString = editCategorySpinner.getSelectedItem().toString();
                int newItemCategory = Integer.parseInt(categoryString.substring(0, 1));

                //creating a menu item object to pass into updateItem()
                RestMenuItem item = new RestMenuItem(THIS_ITEM_ID, newName, newPrice, newItemCategory, newDescription, finalImageBytes);

                //getting database
                MenuDatabaseHelper db = new MenuDatabaseHelper(EditMenu_Activity.this);
                boolean update_result = db.updateItem(item);

                //switching display depending on success of item being edited
                if (update_result) {
                    Intent intent = new Intent(EditMenu_Activity.this, Staff_Menu_Activity.class);
                    Toast.makeText(EditMenu_Activity.this, "Item updated successfully: " + Integer.toString(THIS_ITEM_ID), Toast.LENGTH_SHORT).show();

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

                } else {
                    Intent intent = new Intent(EditMenu_Activity.this, Error_Add_MenuItem.class);

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
}