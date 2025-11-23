package com.example.comp2000assessment;

public class RestMenuItem {
    public String getPrice() {
        return price;
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
    public RestMenuItem(String name, double price, int categoryID, String description, byte[] imageBlob){
        this.name = name;
        this.price = String.valueOf(price);
        this.categoryID = categoryID;
        this.description = description;
        this.imageBlob = imageBlob;
    }

}
