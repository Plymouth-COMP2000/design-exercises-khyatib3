package com.example.comp2000assessment.users;

public class ManageUser {
    //instance of manage user
    private static ManageUser instance;

    //holding the current user using the app
    private AppUser currentUser;

    private ManageUser() {
        //private constructor to prevent external instantiation
        //early instantiation of singleton pattern
    }

    //public method used to get instance
    public static ManageUser getInstance() {
        if (instance == null) {
            instance = new ManageUser();
        }
        return instance;
    }

    //get current user
    public AppUser getCurrentUser() {
        return currentUser;
    }

    //set current user
    public void setCurrentUser(AppUser currentUser) {
        this.currentUser = currentUser;
    }

    //check if use logged in
    public boolean isUserLoggedIn() {
        return currentUser != null;
    }

    //logout method
    public void logout() {
        currentUser = null;
    }
}
