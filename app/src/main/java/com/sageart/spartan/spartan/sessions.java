package com.sageart.spartan.spartan;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class sessions {
    public SharedPreferences prefs;
    public sessions(Context cntxt){
        prefs = PreferenceManager.getDefaultSharedPreferences(cntxt);

    }
    public void setUserId(String user_id){
        prefs.edit().putString("user_id", user_id).commit();
    }
    public String getUserId(){
        String user_id = prefs.getString("user_id", "");
        return user_id;

    }

}
