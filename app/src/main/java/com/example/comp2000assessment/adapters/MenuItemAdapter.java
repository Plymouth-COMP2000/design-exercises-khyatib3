package com.example.comp2000assessment.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.comp2000assessment.R;
import com.example.comp2000assessment.menu.RestMenuItem;

import java.util.ArrayList;
import java.util.List;

public class MenuItemAdapter extends RecyclerView.Adapter<MenuItemAdapter.MenuItemViewHolder> implements Filterable {
    public List<RestMenuItem> itemList;
    public List<RestMenuItem> itemsListFull;
    private Context context;

    public MenuItemAdapter(Context context, List<RestMenuItem> itemList, List<RestMenuItem> itemsListFull){
        this.context = context;
        this.itemList = itemList;
        this.itemsListFull = new ArrayList<>(itemList);
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

        //converting the byte array to a Bitmap
        //adding bitmap validation to ensure I am displaying an image or a placeholder
        Bitmap bitmap = RestMenuItem.bytesToBitmap(item.getImageBlob());
        if (bitmap != null) {
            holder.image.setImageBitmap(bitmap);
        } else {
            holder.image.setImageResource(R.drawable.ic_placeholder);
        }

        holder.name.setText(item.getName());
        holder.description.setText(item.getDescription());
        holder.price.setText(item.getPrice());
    }


    public Filter getFilter() {
        return menuItemFilter;
    }

    private Filter menuItemFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<RestMenuItem> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                // If the search text is empty, show all items
                filteredList.addAll(itemsListFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (RestMenuItem item : itemsListFull) {
                    // Your filtering logic
                    if (item.getDescription().toLowerCase().contains(filterPattern) || item.getName().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            itemList.clear();
            itemList.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };

    @Override
    public int getItemCount(){
        return itemList.size();
    }
}


