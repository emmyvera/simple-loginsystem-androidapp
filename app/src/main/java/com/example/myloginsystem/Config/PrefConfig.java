package com.example.myloginsystem.Config;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.example.myloginsystem.R;

public class PrefConfig {

    private SharedPreferences sharedPreferences;
    private Context context;

    public PrefConfig(Context context){
        this.context = context;
        sharedPreferences = context.getSharedPreferences(context.getString(R.string.pref_file), Context.MODE_PRIVATE);
    }

    public void writeLoginStatus(boolean status){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(context.getString(R.string.pref_login), status);
        editor.commit();
    }

    public void writeToken(String token){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(context.getString(R.string.pref_userToken), token);
        editor.commit();
    }

    public void writeUserId(String id){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(context.getString(R.string.pref_userID), id);
        editor.commit();
    }

    public boolean readLoginStatus(){
        return sharedPreferences.getBoolean(context.getString(R.string.pref_login), false);
    }

    public String readId(){
        return sharedPreferences.getString(context.getString(R.string.pref_userID), "User");
    }

    public String readToken(){
        return sharedPreferences.getString(context.getString(R.string.pref_userToken),"Token");
    }


    public void  displayToast(String message){
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }



}
