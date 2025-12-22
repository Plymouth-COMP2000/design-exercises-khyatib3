package com.example.comp2000assessment.users;


import com.google.gson.Gson;

import org.json.JSONObject;
import org.json.JSONException;

public class AppUser {
    private String firstname;
    private String lastname;
    private String contact;
    private String email;
    private String username;
    private String password;
    private String user_type;
    private boolean logged_in;

    //constructor
    public AppUser(String firstname, String lastname, String contact, String email, String username, String password){
        this.firstname = firstname;
        this.lastname = lastname;
        this.contact = contact;
        this.email = email;
        this.username = username;
        this.password = password;
    }

    public JSONObject returnUserJSON(){
        Gson gson = new Gson();

        try{
           String jsonString = gson.toJson(this);
           return new JSONObject(jsonString);

        }catch (JSONException e){
            e.printStackTrace();;
            return null;
        }

    }

    //this function will be used with the GET methods of the api endpoints: i.e. reading users, reading specific users
    public AppUser returnAppUserFromJson(JSONObject userJSON){
        Gson gson = new Gson();
        return gson.fromJson(userJSON.toString(), AppUser.class);
    }

    //getters and setters below
    public String getUser_type() {
        return user_type;
    }

    public void setUser_type(String user_type) {
        this.user_type = user_type;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public boolean isLogged_in() {
        return logged_in;
    }

    public void setLogged_in(boolean logged_in) {
        this.logged_in = logged_in;
    }

}
