package com.example.comp2000assessment.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.comp2000assessment.R;
import com.example.comp2000assessment.menu.Delete_Menu_Item_Activity;
import com.example.comp2000assessment.menu.EditMenu_Activity;
import com.example.comp2000assessment.menu.RestMenuItem;

import java.util.List;
public class StaffMenuAdapter extends RecyclerView.Adapter<StaffMenuAdapter.SMItemViewHolder>{
    public List<RestMenuItem> itemList;
    private Context context;
    private String staff_firstname, staff_lastname, staff_contact, staff_email, staff_username, staff_password, staff_usertype;
    private boolean staff_logged_in;


    public StaffMenuAdapter(Context context, List<RestMenuItem> itemList, String staff_firstname, String staff_lastname, String contact, String email,
                            String username, String password, String usertype, boolean staff_logged_in){
        this.context = context;
        this.itemList = itemList;
        this.staff_firstname = staff_firstname;
        this.staff_lastname = staff_lastname;
        this.staff_contact = staff_contact;
        this.staff_email = staff_email;
        this.staff_username = staff_username;
        this.staff_password = staff_password;
        this.staff_usertype = staff_usertype;
        this.staff_logged_in = staff_logged_in;
    }

    public static class SMItemViewHolder extends RecyclerView.ViewHolder{
        ImageView image;
        TextView name;
        TextView description;
        TextView price;
        Button editBtn;
        Button deleteBtn;

        public SMItemViewHolder(View menuItemView){
            super(menuItemView);
            image = menuItemView.findViewById(R.id.item_Img);
            description = menuItemView.findViewById(R.id.item_Desc);
            price = menuItemView.findViewById(R.id.item_Price);
            editBtn = menuItemView.findViewById(R.id.s_edit_button);
            deleteBtn = menuItemView.findViewById(R.id.s_delete_button);
        }

    }

    @NonNull
    @Override
    public StaffMenuAdapter.SMItemViewHolder onCreateViewHolder(ViewGroup parent, int ViewType){
        View view = LayoutInflater.from(context).inflate(R.layout.staff_menu_item, parent, false);
        return new StaffMenuAdapter.SMItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(StaffMenuAdapter.SMItemViewHolder holder, int pos){
        RestMenuItem item = itemList.get(pos);

        //converting the byte array to a Bitmap
        //adding bitmap validation to ensure I am displaying an image or a placeholder
        Bitmap bitmap = RestMenuItem.bytesToBitmap(item.getImageBlob());
        if (bitmap != null) {
            holder.image.setImageBitmap(bitmap);
        } else {
            holder.image.setImageResource(R.drawable.ic_placeholder);
        }

        holder.description.setText(item.getDescription());
        holder.price.setText(item.getPrice());

        holder.editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, EditMenu_Activity.class);
                //passing the itemID to the edit item activity
                //this is so i can update the correct item in the db
                //also passing other attributes so they are pre-filled in the edit screen
                intent.putExtra("itemID", item.getItemID());
                intent.putExtra("name", item.getName());
                intent.putExtra("description", item.getDescription());
                intent.putExtra("price", item.getPriceAsDouble());
                intent.putExtra("categoryID", item.getCategoryID());
                intent.putExtra("image", item.getImageBlob());

                //passing staff details
                intent.putExtra("staff_firstname", staff_firstname);
                intent.putExtra("staff_lastname", staff_lastname);
                intent.putExtra("staff_contact", staff_contact);
                intent.putExtra("staff_email", staff_email);
                intent.putExtra("staff_username", staff_username);
                intent.putExtra("staff_password", staff_password);
                intent.putExtra("staff_usertype", staff_usertype);
                intent.putExtra("staff_logged_in", staff_logged_in);

                context.startActivity(intent);
            }
        });

        //delete button
        holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Delete_Menu_Item_Activity.class);

                //passing id of the item to be deleted
                //so that the correct item gets deleted from the db
                intent.putExtra("itemID", item.getItemID());
                intent.putExtra("name", item.getName());

                //passing staff details
                intent.putExtra("staff_firstname", staff_firstname);
                intent.putExtra("staff_lastname", staff_lastname);
                intent.putExtra("staff_contact", staff_contact);
                intent.putExtra("staff_email", staff_email);
                intent.putExtra("staff_username", staff_username);
                intent.putExtra("staff_password", staff_password);
                intent.putExtra("staff_usertype", staff_usertype);
                intent.putExtra("staff_logged_in", staff_logged_in);

                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount(){
        return itemList.size();
    }

}
