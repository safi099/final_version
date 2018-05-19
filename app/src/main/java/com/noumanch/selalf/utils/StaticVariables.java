package com.noumanch.selalf.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.noumanch.selalf.model.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;



/**
 * Created by NoumanCh on 3/30/2017.
 */

public class StaticVariables {

    static String currentUserId;

    static boolean INDICATOR_OF_LOGIN=false;
    public static String KEY_FOR_USER_ID ="user_id" ;
    public static String KEY_FOR_USER_TOKEN = "user_token";
    public static String KEY_FOR_LOGIN_INDICATOR = "indicator_of_login";
    public static String TOKEN = "";
    public static String USER_ID = "";
    public static String className;
    public static boolean appPurchased=false;

    public static boolean appGalleryModulePurcahsed=false;
    public static boolean appBackgroundModulePurcahsed=false;
     public static boolean appStartForFisttime;
    public static String  TAG = "MillionareSayings";
    public static boolean language=true;
    private static String KEY_FOR_lastname  = "lastname";
    private static String KEY_FOR_firstname = "firstname";
    private static String KEY_FOR_email     = "email";
    private static String KEY_FOR_phone     = "phone";

    static SharedPreferences preferences;

    public static void setCurrentUserId(Context context, String id, String lastname, String firstname, String phone, String email) {

        SharedPreferences sharedPreferences = context.getSharedPreferences("SavedData",Context.MODE_PRIVATE);

        sharedPreferences.edit().putString(StaticVariables.KEY_FOR_USER_ID,id).commit();
        sharedPreferences.edit().putString(StaticVariables.KEY_FOR_lastname,lastname).commit();
        sharedPreferences.edit().putString(StaticVariables.KEY_FOR_firstname,firstname).commit();
        sharedPreferences.edit().putString(StaticVariables.KEY_FOR_phone,phone).commit();
        sharedPreferences.edit().putString(StaticVariables.KEY_FOR_email,email).commit();


        StaticVariables.currentUserId = id;
    }


    public static boolean isIndicatorOfLogin(Context context) {
        preferences = context.getSharedPreferences("SavedData",Context.MODE_PRIVATE);
        return preferences.getBoolean("Login",false);
    }

    public static void setIndicatorOfLogin(Context context,boolean indicatorOfLogin) {
        preferences = context.getSharedPreferences("SavedData",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("Login", indicatorOfLogin);
        editor.commit();
    }

    public static void removeData(Context context ){
        SharedPreferences sharedPreferences = context.getSharedPreferences("SavedData", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.commit();
    }




    //mehtod that make the image favourute


    public  static  boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public static  boolean firstTime(Context context){

        SharedPreferences sharedPreferences = context.getSharedPreferences("preferences",Context.MODE_PRIVATE);
        if (sharedPreferences.contains("flagLog")){

            Log.i("FlagLog","contain flag");
            appStartForFisttime=false;
            return false;
        }else{
            sharedPreferences.edit().putString("flagLog","ok").commit();
            appStartForFisttime = true;
            Log.i("FlagLog","contain dont flag");
            return true;
        }
    }


    public static void deleteCache(Context context) {
        try {
            SharedPreferences preferences = context.getSharedPreferences("SavedData",Context.MODE_PRIVATE);
            preferences.edit().clear().commit();
            File dir = context.getCacheDir();
            deleteDir(dir);
        } catch (Exception e) {}
    }

    public static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
            return dir.delete();
        } else if(dir!= null && dir.isFile()) {
            return dir.delete();
        } else {
            return false;
        }
    }





    public static void saveDaily(Context context,String s) {

        SharedPreferences preferences = context.getSharedPreferences("daily",Context.MODE_PRIVATE);
        preferences.edit().putString("d",s).apply();
    }
    public static String getDaily(Context context)
    {
        SharedPreferences preferences = context.getSharedPreferences("daily",Context.MODE_PRIVATE);
        return  preferences.getString("d","");
    }

    public static void savePopular(Context context,String s) {

        SharedPreferences preferences = context.getSharedPreferences("daily",Context.MODE_PRIVATE);
        preferences.edit().putString("popular",s).apply();
        Log.i(TAG,"popular data saved to preferences");

    }

    public static String getPopular(Context context)
    {
        SharedPreferences preferences = context.getSharedPreferences("daily",Context.MODE_PRIVATE);
        String s =preferences.getString("popular","");
        Log.i(TAG,"popular data retrieved from  preferences = "+s);
        return  s;
    }


    public static void saveLatest(Context context,String s) {

        SharedPreferences preferences = context.getSharedPreferences("daily",Context.MODE_PRIVATE);
        preferences.edit().putString("latest",s).apply();
        Log.i(TAG,"latest data saved to preferences");

    }
    public static void saveStories(Context context,String s) {

        SharedPreferences preferences = context.getSharedPreferences("stories",Context.MODE_PRIVATE);
        preferences.edit().putString("story",s).apply();
        Log.i(TAG,"latest data saved to preferences");

    }


    public static String getLatest(Context cont) {
        SharedPreferences preferences = cont.getSharedPreferences("daily",Context.MODE_PRIVATE);
        String s =preferences.getString("latest","");
        Log.i(TAG,"latest data retrieved from  preferences = "+s);
        return  s;
    }
    public static String getStories(Context cont) {
        SharedPreferences preferences = cont.getSharedPreferences("stories",Context.MODE_PRIVATE);
        String s =preferences.getString("story","");
        Log.i(TAG,"latest data retrieved from  preferences = "+s);
        return  s;
    }

    public static void saveFavourite(Context context,String s) {

        SharedPreferences preferences = context.getSharedPreferences("daily",Context.MODE_PRIVATE);
        preferences.edit().putString("fav",s).apply();
        Log.i(TAG,"favourite data saved to preferences");

    }


    public static String getFavourite(Context cont) {
        SharedPreferences preferences = cont.getSharedPreferences("daily",Context.MODE_PRIVATE);
        String s =preferences.getString("fav","");
        Log.i(TAG,"favourite data retrieved from  preferences = "+s);
        return  s;
    }

    public static void saveUserUploaded(Context cont, String jsonStr) {
        SharedPreferences preferences = cont.getSharedPreferences("daily",Context.MODE_PRIVATE);
        preferences.edit().putString("useruploaded",jsonStr).apply();
        Log.i(TAG,"useruploaded data saved to preferences");

    }
    public static String getUserUploaded(Context cont) {
        SharedPreferences preferences = cont.getSharedPreferences("daily",Context.MODE_PRIVATE);
        String s =preferences.getString("useruploaded","");
        Log.i(TAG,"useruploaded data retrieved from  preferences = "+s);
        return  s;
    }


    public static  String getUserId(Context context){

        SharedPreferences sharedPreferences = context.getSharedPreferences("SavedData",Context.MODE_PRIVATE);

        String s =sharedPreferences.getString(StaticVariables.KEY_FOR_USER_ID,"");

        return s;

    }

    public static  User getUser(Context context){

        SharedPreferences sharedPreferences = context.getSharedPreferences("SavedData",Context.MODE_PRIVATE);

        String id =sharedPreferences.getString(StaticVariables.KEY_FOR_USER_ID,"");
        String lastname =sharedPreferences.getString(StaticVariables.KEY_FOR_lastname,"");
        String firstname =sharedPreferences.getString(StaticVariables.KEY_FOR_firstname,"");
        String email =sharedPreferences.getString(StaticVariables.KEY_FOR_email,"");
        String phone =sharedPreferences.getString(StaticVariables.KEY_FOR_phone,"");


        User u = new User(lastname,firstname,email,phone,id);

        if (u.getLastname().equals("") && u.getEmail().equals("")){
            return null;
        }


        return u;

    }


    public static  void setFirstLoginData(Context context){

        SharedPreferences sharedPreferences = context.getSharedPreferences("SavedData",Context.MODE_PRIVATE);

        sharedPreferences.edit().putString("first_login_data","").commit();



    }
    public static void setFirstLogin(Context context,boolean isLogin){

        preferences = context.getSharedPreferences("SavedData",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("isLogin", isLogin);
        editor.commit();
       /* if (sharedPreferences.contains("first_login_data")){

            return false;
        }return true;*/

    }

    public static boolean getFirstLogin(Context context){
        preferences = context.getSharedPreferences("SavedData",Context.MODE_PRIVATE);
        return preferences.getBoolean("isLogin",false);
    }

    public static  void setLanguage(Context context,String lang){

        SharedPreferences sharedPreferences = context.getSharedPreferences("SavedData",Context.MODE_PRIVATE);

        sharedPreferences.edit().putString("lang",""+lang);

        if (lang.equals("eng"))
            StaticVariables.language =true;
        else
            StaticVariables.language = false;


    }
    public static boolean getLang(Context context){

        SharedPreferences sharedPreferences = context.getSharedPreferences("SavedData",Context.MODE_PRIVATE);

        if (sharedPreferences.contains("lang")){

            if (sharedPreferences.getString("lang","").equals("eng")){
                StaticVariables.language = true;
            }else {

                StaticVariables.language = false;
            }

            return false;
        }return true;
    }

}
