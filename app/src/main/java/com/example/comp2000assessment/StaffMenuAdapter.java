package com.example.comp2000assessment;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.comp2000assessment.RestMenuItem;

import java.util.List;
public class StaffMenuAdapter extends RecyclerView.Adapter<StaffMenuAdapter.SMItemViewHolder>{
    public List<RestMenuItem> itemList;
    private Context context;

    public StaffMenuAdapter(Context context, List<RestMenuItem> itemList){
        this.context = context;
        this.itemList = itemList;
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

    }

    @Override
    public int getItemCount(){
        return itemList.size();
    }

}
