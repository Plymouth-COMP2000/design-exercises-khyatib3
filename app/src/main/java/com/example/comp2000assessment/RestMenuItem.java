package com.example.comp2000assessment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;

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

    private String price;
    private String name;
    private String description;
    private int categoryID;
    private int imageId; // for placeholder images

    public byte[] getImageBlob() {
        return imageBlob;
    }

    public void setImageBlob(byte[] imageBlob) {
        this.imageBlob = imageBlob;
    }

    private byte[] imageBlob; // for uploaded images

    //placeholder construct
    public RestMenuItem(String price, String name, String description, int categoryID, int imageId) {
        this.price = price;
        this.name = name;
        this.description = description;
        this.categoryID = categoryID;
        this.imageId = imageId;
    }

    //real items constructor
    public RestMenuItem(String name, double price, int categoryID, String description, byte[] imageBytes){
        this.name = name;
        this.price = String.format("£%.2f", price);
        this.categoryID = categoryID;
        this.description = description;
        this.imageBlob = imageBytes;
    }

}
