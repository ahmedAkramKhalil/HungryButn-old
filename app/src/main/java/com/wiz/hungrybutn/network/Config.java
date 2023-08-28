package com.wiz.hungrybutn.network;

public class Config {
    public static final String Key_DATA = "data";
    public static final String Key_TOGO = "to_go";
    static  String url = "hungrybuten.ps" ;
    public static final String LOGIN_URL = "http://" + url +"/api/auth/login";
    public static final String REG_URL = "http://" + url +"/api/auth/register";
    public static final String GET_PRODUCT_URL = "http://" + url +"/api/auth/products";
    public static final String GET_COMPONENT_URL = "http://" + url +"/api/auth/ingredients";
    public static final String SEARCH_URL = "http://" + url +"/api/auth/search";
    public static final String SAVE_PRODUCT_URL = "http://" + url +"/api/auth/saveProduct";


    public static final String LANGUAGE_AR = "AR";
    public static final String LANGUAGE_EN = "EN";

    //Keys for email and password as defined in our $_POST['key'] in login.php
    public static final String KEY_EMAIL = "email";
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_AUTH = "Authorization";
    public static final String TOKEN_HEAD = "Bearer ";
    public static final String Key_TOKEN = "TOKEN";
    public static final String Key_LOGGEDIN = "LOGGED";
    public static final String Key_REGISTERED = "REGISTERED";
    public static final String Key_LANGUAGE = "LANGUAGE";

    // Regitration data
    public static final String NAME = "Screen1";
    public static final String EMAIL = "screen@hungrybutn.ps";
    public static final String PASSWORD = "password";



    //If server response is equal to this that means login is successful
    public static final String LOGIN_SUCCESS = "success";

    //Keys for Sharedpreferences
    //This would be the name of our shared preferences
    public static final String SHARED_PREF_NAME = "myloginapp";

    //This would be used to store the email of current logged in user
    public static final String EMAIL_SHARED_PREF = "email";

    //We will use this to store the boolean in sharedpreference to track user is loggedin or not
    public static final String LOGGEDIN_SHARED_PREF = "loggedin";
}
