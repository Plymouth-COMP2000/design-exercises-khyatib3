package com.example.comp2000assessment.users;


import android.content.Context;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONObject;
public class UserAPI_Helper {
    private static String BASE_URL = "http://10.240.72.69/comp2000/coursework/";
    private static String CREATE_USER_ENDPOINT = "create_user/10920850";
    private static String CREATE_STUDENT_DATABASE_ENDPOINT = "create_student/10920850";
    private static String READ_ALL_USERS_ENDPOINT = "read_all_users/10920850";
    private static String READ_SPECIFIC_USER_ENDPOINT = "read_user/10920850";
    private static String UPDATE_USER_ENDPOINT = "update_user/10920850";
    private static String DELETE_USER_ENDPOINT = "delete_user/10920850";
    private static Context context;
    private static RequestQueue requestQueue;

    public UserAPI_Helper(Context context) {
        this.requestQueue = Volley.newRequestQueue(context.getApplicationContext());
    }

    //interface below for defining the callbacks
    public interface APIResponseCallback<T> {
        void onSuccess(T response);
        void onError(String message);
    }
    public static RequestQueue getRequestQueue() {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        }
        return requestQueue;
    }

    //using endpoint /create_student/
    public void createStudentDatabase(APIResponseCallback<JSONObject> callback){
        String url = BASE_URL + CREATE_STUDENT_DATABASE_ENDPOINT;

        //creating a request
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        callback.onSuccess(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(com.android.volley.VolleyError error) {
                        callback.onError(error.toString());
                    }
                }
        );

        requestQueue.add(request);

    }

    //create user endpoint
    public static void createUser(AppUser user, Response.Listener<org.json.JSONObject> callback, Response.ErrorListener errorListener, UserAPI_Helper apiHelper){
        String url = BASE_URL + CREATE_USER_ENDPOINT;

        //converting the user object to json object
        JSONObject userJSON = user.returnUserJSON();

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, userJSON, callback, errorListener);
        apiHelper.getRequestQueue().add(request);
    }

    //update user endpoint
    public static void updateUser(AppUser user, UserAPI_Helper apiHelper, Response.Listener<org.json.JSONObject> callback, Response.ErrorListener errorListener){
        String username = user.getUsername();

        String url = BASE_URL + UPDATE_USER_ENDPOINT + "/" + username;

        JSONObject userJSON = user.returnUserJSON();

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.PUT, url, userJSON, callback, errorListener);
        apiHelper.getRequestQueue().add(request);

    }

    //delete user endpoint
    public static void deleteUser(AppUser user, UserAPI_Helper apiHelper, Response.Listener<org.json.JSONObject> callback, Response.ErrorListener errorListener){
        String username = user.getUsername();

        String url = BASE_URL + DELETE_USER_ENDPOINT + "/" + username;

        JSONObject userJSON = user.returnUserJSON();

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.DELETE, url, userJSON, callback, errorListener);
        apiHelper.getRequestQueue().add(request);

    }

    //get specific user endpoint
    public static void getSpecificUser(String username, UserAPI_Helper apiHelper, Response.Listener<org.json.JSONObject> callback, Response.ErrorListener errorCallback){
        String url = BASE_URL + READ_SPECIFIC_USER_ENDPOINT + "/" + username;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, callback, errorCallback);
        apiHelper.getRequestQueue().add(request);

    }

    //get all users endpoint -- used for checking duplicate users existence in SignUp_Activity.java
    public void getAllUsers(UserAPI_Helper apiHelper, Response.Listener<org.json.JSONObject> callback, Response.ErrorListener errorCallback){
        String url = BASE_URL + READ_ALL_USERS_ENDPOINT;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, callback, errorCallback);
        apiHelper.getRequestQueue().add(request);
    }





}
