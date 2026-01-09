package com.example.comp2000assessment.menu;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;

public class RestMenuItem {
    public String getPrice() {
        return price;
    }
    public double getPriceAsDouble() {
        try {
            return Double.parseDouble(price.replace("£",""));
        } catch (Exception e) {
            return 0.0;
        }
    }

    public static byte[] bitmapToBytes(Bitmap bitmap){
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }

    public static Bitmap bytesToBitmap(byte[] imageBytes){
        if (imageBytes == null || imageBytes.length == 0) {
            return null;
        }
        return BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);

    }


    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getCategoryID() {
        return categoryID;
    }

    public int getImageId() {
        return imageId;
    }
    private int itemID;
    private String price;
    private String name;
    private String description;
    private int categoryID;
    private int imageId; // for placeholder images

    public byte[] getImageBlob() {
        return imageBlob;
    }
    public int getItemID() {
        return itemID;
    }

    public void setImageBlob(byte[] imageBlob) {
        this.imageBlob = imageBlob;
    }

    private byte[] imageBlob; // for uploaded images


    //actual items constructor -- used when passing item to DB
    public RestMenuItem(String name, double price, int categoryID, String description, byte[] imageBytes){
        this.name = name;
        this.price = String.format("£%.2f", price);
        this.categoryID = categoryID;
        this.description = description;
        this.imageBlob = imageBytes;
    }

    //constructor for showing menu items
    //uses itemID from DB
    public RestMenuItem(int itemID, String name, double price, int categoryID, String description, byte[] imageBytes){
        this.itemID = itemID;
        this.name = name;
        this.price = String.format("£%.2f", price);
        this.categoryID = categoryID;
        this.description = description;
        this.imageBlob = imageBytes;
    }

}
