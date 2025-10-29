package com.example.comp2000assessment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.comp2000assessment.RestMenuItem;

import java.util.List;

public class MenuItemAdapter extends RecyclerView.Adapter<MenuItemAdapter.MenuItemViewHolder> {
    public List<RestMenuItem> itemList;
    private Context context;

    public MenuItemAdapter(Context context, List<RestMenuItem> itemList){
        this.context = context;
        this.itemList = itemList;
    }

    public static class MenuItemViewHolder extends RecyclerView.ViewHolder{
        ImageView image;
        TextView name;
        TextView description;
        TextView price;

        public MenuItemViewHolder(View menuItemView){
            super(menuItemView);
            image = menuItemView.findViewById(R.id.menu_image);
            name = menuItemView.findViewById(R.id.menu_item_name);
            description = menuItemView.findViewById(R.id.menu_description);
            price = menuItemView.findViewById(R.id.menu_price);
        }

    }

    @NonNull
    @Override
    public MenuItemViewHolder onCreateViewHolder(ViewGroup parent, int ViewType){
        View view = LayoutInflater.from(context).inflate(R.layout.menu_item, parent, false);
        return new MenuItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MenuItemViewHolder holder, int pos){
        RestMenuItem item = itemList.get(pos);
        holder.image.setImageResource(item.imageId);
        holder.name.setText(item.name);
        holder.description.setText(item.description);
        holder.price.setText(item.price);
    }

    @Override
    public int getItemCount(){
        return itemList.size();
    }
}


