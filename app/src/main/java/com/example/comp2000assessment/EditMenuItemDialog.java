package com.example.comp2000assessment;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

public class EditMenuItemDialog extends DialogFragment {
    private Uri selectedImageURI;
    private static final int PICK_IMAGE_REQUEST = 1;

    @Nullable
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.custom_dialog, null);
        builder.setView(dialogView);

        ImageButton closeBtn = dialogView.findViewById(R.id.closeEditDialogBtn);
        Button saveBtn = dialogView.findViewById(R.id.saveMenuChangeBtn);
        Button cancelBtn = dialogView.findViewById(R.id.cancelMenuChangesBtn);
        Button imageBtn = dialogView.findViewById(R.id.editImageBtn);

        EditText descInput = dialogView.findViewById(R.id.editDescriptionInput);
        EditText priceInput = dialogView.findViewById(R.id.editPriceInput);

        closeBtn.setOnClickListener(v -> dismiss());
        cancelBtn.setOnClickListener(v -> dismiss());

        saveBtn.setOnClickListener(v -> {
            String newDesc = descInput.getText().toString();
            String newPrice = priceInput.getText().toString();
            // TODO: Handle save logic
            dismiss();
        });

        imageBtn.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            startActivityForResult(intent, PICK_IMAGE_REQUEST);
        });

        return builder.create();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null) {
            selectedImageURI = data.getData();
            Toast.makeText(getContext(), "Image selected!", Toast.LENGTH_SHORT).show();
        }
    }

}
