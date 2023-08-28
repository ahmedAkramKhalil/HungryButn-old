package com.wiz.hungrybutn.network;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.text.AllCapsTransformationMethod;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.wiz.hungrybutn.DBHandler;
import com.wiz.hungrybutn.Order;
import com.wiz.hungrybutn.chef.Component;
import com.wiz.hungrybutn.main.Dashboard;
import com.wiz.hungrybutn.menu.Category;
import com.wiz.hungrybutn.menu.ComponentCategory;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.InetAddress;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.realm.RealmObject;

public class NetworkHandler {
    static NetworkHandler handler;
    Context context;

    private NetworkHandler(Context context) {
        this.context = context;
    }

    public static NetworkHandler getInstance(Context context) {
        if (handler == null) {
            handler = new NetworkHandler(context);
        }
        return handler;
    }


    public void register(String requestUrl, final Activity activity) {
        if (!SharedPreferanceHandler.getInstance(activity).getBoolean(Config.Key_REGISTERED)) {


            StringRequest stringRequest = new StringRequest(Request.Method.POST, requestUrl, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.e("Volley Result", "" + response); //the response contains the result from the server, a json string or any other object returned by your server
                    JsonParser jsonParser = new JsonParser();
                    JsonElement jsonElement = jsonParser.parse(response);
//                 JsonArray array = jsonElement.getAsJsonArray();
//                 JsonObject jsonObject = (JsonObject) array.get(0)

                    JsonObject jsonObject = jsonElement.getAsJsonObject();
                    JsonObject success = jsonObject.get("success").getAsJsonObject();
                    String token = success.get("token").getAsString();
                    SharedPreferanceHandler.getInstance(activity).putString(Config.Key_TOKEN, token);
                    SharedPreferanceHandler.getInstance(activity).putBoolean(Config.Key_REGISTERED, true);
                    Log.e("Volley Result", "" + token);

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    error.printStackTrace(); //log the error resulting from the request for diagnosis/debugging

                }
            }) {

                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> postMap = new HashMap<>();
                    postMap.put("name", Config.NAME);
                    postMap.put("email", Config.EMAIL);
                    postMap.put("password", Config.PASSWORD);
                    postMap.put("password_confirmation", Config.PASSWORD);
                    //..... Add as many key value pairs in the map as necessary for your request
                    return postMap;
                }
            };

            Volley.newRequestQueue(context).add(stringRequest);

        }
        login(Config.EMAIL, Config.PASSWORD, Config.LOGIN_URL, activity);

    }

    public void login(final String email, final String password, String url, final Activity activity) {
        //Getting values from edit texts
        Log.d("Sttttttt", "rrrrrrrrrrrrrrr");

        //Creating a string request
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //If we are getting success from server
                        Log.e("Volley Result", "" + response);

                        JsonParser jsonParser = new JsonParser();
                        JsonElement jsonElement = jsonParser.parse(response);

                        JsonObject jsonObject = jsonElement.getAsJsonObject();
                        JsonObject user = jsonObject.get("user").getAsJsonObject();
                        String token = jsonObject.get("access_token").getAsString();
                        SharedPreferanceHandler.getInstance(activity).putString(Config.Key_TOKEN, token);
                        SharedPreferanceHandler.getInstance(activity).putBoolean(Config.Key_LOGGEDIN, true);
//                        if (success.isJsonObject()) {
//                            String token = success.get("access_token").getAsString();
//                        }
//                        if (success.isJsonArray()) {
//                            String token = success.get("access_token").getAsString();
//                        }
                        Log.d("Volley Result", "" + token);


//                        sharedPreferences.edit().putString("TOKEN",token).apply();

                        if (response.equalsIgnoreCase(Config.LOGIN_SUCCESS)) {


//                            //Creating a shared preference
//                            SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
//
//                            //Creating editor to store values to shared preferences
//                            SharedPreferences.Editor editor = sharedPreferences.edit();
//
//                            //Adding values to editor
//                            editor.putBoolean(Config.LOGGEDIN_SHARED_PREF, true);
//                            editor.putString(Config.EMAIL_SHARED_PREF, email);
//
//                            //Saving values to editor
//                            editor.commit();
//
//                            //Starting profile activity
//


                        } else {
                            //If the server response is not success
                            //Displaying an error message on toast
                            // Toast.makeText(LoginActivity.this, "Invalid username or password", Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //You can handle error here if you want
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                //Adding parameters to request
                params.put(Config.KEY_EMAIL, email);
                params.put(Config.KEY_PASSWORD, password);

                //returning parameter
                return params;
            }
        };

        //Adding the string request to the queue
        RequestQueue requestQueue = Volley.newRequestQueue(activity);
        requestQueue.add(stringRequest);
    }

    public void getComponents(String url, final Activity activity) {

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Gson gson = new Gson();
                Log.d("Volley Result", "" + response);
                JsonParser jsonParser = new JsonParser();
//                JsonElement jsonElement = jsonParser.parse(response);

//                JsonObject jsonObject = jsonElement.getAsJsonObject();
                JSONArray dataArray = null;
                boolean success = false;
                try {
                    success = (boolean) response.get("success");

//                        JsonObject data = jsonObject.get("user").getAsJsonObject();
                    if (!success){
                        Toast.makeText(activity.getApplicationContext(),"Could not Connect to Server" , Toast.LENGTH_SHORT).show();
                    }

                    dataArray = (JSONArray) response.get("data");

                } catch (JSONException e) {
                    e.printStackTrace();
                }

//                List<Component> components = Arrays.asList(gson.fromJson(dataArray.toString(), Component[].class));
//                DBHandler.getInstance(context).addObjectsToDB((RealmObject) components);
                List<ComponentCategory> componentCategories = Arrays.asList(gson.fromJson(dataArray.toString(), ComponentCategory[].class));
//                DBHandler.getInstance(context).addObjectsToDB((RealmObject) ComponentCategory);

                for (ComponentCategory componentCategory : componentCategories){
                    DBHandler.getInstance(context).addObjectsToDB(componentCategory);
                }
//                for (ComponentCategory component : componentCategories) {
//                    Log.d("com", " " + component.getId() + "  -  " + component.getComponents().get(0).getPrice());
//                    ;
//                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Volley Result", "" + error.toString());

            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                //Adding parameters to request
                String s = Config.TOKEN_HEAD + SharedPreferanceHandler.getInstance(activity).getString(Config.Key_TOKEN);
                params.put(Config.KEY_AUTH, s);

                //returning parameter
                return params;
            }

        };

        //Adding the string request to the queue
        RequestQueue requestQueue = Volley.newRequestQueue(activity);
        requestQueue.add(jsonObjectRequest);


    }

    public void getProducts(String url, final Activity activity) {

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Gson gson = new Gson();
                Log.d("Volley Result", "" + response);
                JsonParser jsonParser = new JsonParser();
//                JsonElement jsonElement = jsonParser.parse(response);

//                JsonObject jsonObject = jsonElement.getAsJsonObject();
                JSONArray dataArray = null;
                boolean success;
                try {
                    success = (boolean) response.get("success");

//                        JsonObject data = jsonObject.get("user").getAsJsonObject();
                    if (!success){
                        Toast.makeText(activity.getApplicationContext(),"Could not Connect to Server" , Toast.LENGTH_SHORT).show();
                    }
                    dataArray = (JSONArray) response.get("data");

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                List<Category> categories = Arrays.asList(gson.fromJson(dataArray.toString(), Category[].class));
//                DBHandler.getInstance(context).addObjectsToDB((RealmObject) categories);

                for (Category category : categories){
                    DBHandler.getInstance(context).addObjectsToDB(category);
                }

                for (Category category : categories) {
                     if (category.getProducts() != null)
                    Log.d("com", " " + category.getId() + "  " + category.getProducts().size());
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Volley Result", "" + error.toString());

            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                //Adding parameters to request
                String str = Config.TOKEN_HEAD + SharedPreferanceHandler.getInstance(activity).getString(Config.Key_TOKEN);
                params.put(Config.KEY_AUTH, str);

                //returning parameter
                return params;
            }

        };

        //Adding the string request to the queue
        RequestQueue requestQueue = Volley.newRequestQueue(activity);
        requestQueue.add(jsonObjectRequest);
    }


    public void saveOrder(final Order order, String url, final Activity activity) {
        //Getting values from edit texts
        Log.d("Sttttttt", "------------------");

        //Creating a string request
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //If we are getting success from server
                        Log.e("Volley Result", "" + response);
//
//                        JsonParser jsonParser = new JsonParser();
//                        JsonElement jsonElement = jsonParser.parse(response);
//
//                        JsonObject jsonObject = jsonElement.getAsJsonObject();
//                        JsonObject user = jsonObject.get("user").getAsJsonObject();
//                        String token = jsonObject.get("access_token").getAsString();
//                        SharedPreferanceHandler.getInstance(activity).putString(Config.Key_TOKEN , token);
//                        SharedPreferanceHandler.getInstance(activity).putBoolean(Config.Key_LOGGEDIN ,true);
////                        if (success.isJsonObject()) {
////                            String token = success.get("access_token").getAsString();
////                        }
////                        if (success.isJsonArray()) {
////                            String token = success.get("access_token").getAsString();
////                        }
//                        Log.d("Volley Result", ""+token);


//                        sharedPreferences.edit().putString("TOKEN",token).apply();

                        if (response.equalsIgnoreCase(Config.LOGIN_SUCCESS)) {


//                            //Creating a shared preference
//                            SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
//
//                            //Creating editor to store values to shared preferences
//                            SharedPreferences.Editor editor = sharedPreferences.edit();
//
//                            //Adding values to editor
//                            editor.putBoolean(Config.LOGGEDIN_SHARED_PREF, true);
//                            editor.putString(Config.EMAIL_SHARED_PREF, email);
//
//                            //Saving values to editor
//                            editor.commit();
//
//                            //Starting profile activity
//


                        } else {
                            //If the server response is not success
                            //Displaying an error message on toast
                            // Toast.makeText(LoginActivity.this, "Invalid username or password", Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("errror ", error.toString());
                    }
                }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                String str = Config.TOKEN_HEAD + SharedPreferanceHandler.getInstance(activity).getString(Config.Key_TOKEN);
                params.put(Config.KEY_AUTH, str);
                return params;


            }


            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();
                params.put("name",order.name);
                params.put("name_en",order.name_en);
                params.put("option",order.option);
                String s =  order.ingredients.toString() ;
                params.put("ingredients",s.substring(1,s.length()-1));
                params.put("products",s.substring(1,s.length()-1));
//              params.put("ingredients","[1,2,3]");
                params.put("price",order.price+ "");
                params.put("user_id",order.user_id+ "");
                params.put("to_go",order.to_go + "");
                params.put("option_type",order.option_type + "");
                params.put("category_id",1 + "");
                params.put("is_active",1+ "");
                params.put("type",1 + "");
                params.put("description", "gggggggg");
                params.put("client_id", "" +1);
//               Gson gson = new Gson();
                //Adding parameters to request
//                String s = new Gson().toJson(order);
//                params.put("ingredients", s);
//                Log.d("data ", s);
                //returning parameter
               return params;
            }
        };

        //Adding the string request to the queue
        RequestQueue requestQueue = Volley.newRequestQueue(activity);
        requestQueue.add(stringRequest);
    }

    public void saveOrders(final Order order, String url, final Activity activity) {
        //Getting values from edit texts
        Log.d("Sttttttt", "rrrrrrrrrrrrrrr");
        Map<String, String> params = new HashMap<>();
//        Gson gson = new GsonBuilder()
//                .excludeFieldsWithoutExposeAnnotation()
//                .create();
//               Gson gson = new Gson();
        //Adding parameters to request
        String s = new Gson().toJson(order);
        params.put("ingredientsr", s);

        Log.d("data ", s);

        //Creating a string request
        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(params), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";            }
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                String str = Config.TOKEN_HEAD + SharedPreferanceHandler.getInstance(activity).getString(Config.Key_TOKEN);
                params.put(Config.KEY_AUTH, str);
                //returning parameter
                return params;
            }
        };
        //Adding the string request to the queue
        RequestQueue requestQueue = Volley.newRequestQueue(activity);
        requestQueue.add(stringRequest);
    }

    public static boolean isInternetAvailable() {
        try {
            InetAddress ipAddr = InetAddress.getByName("216.58.210.238");
            //You can replace it with your name
            return !ipAddr.equals("");
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }




}
