package com.o2.comerciosolidario.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.o2.comerciosolidario.model.User;

import org.json.JSONException;

public class Session {
    private SharedPreferences prefs;

    public Session(Context ctx) {
        prefs = PreferenceManager.getDefaultSharedPreferences(ctx);
    }


    public void setUser(User user) throws JSONException {
        prefs.edit().putString("user", user.toJSON()).commit();
    }
    public void removeUser(){
        prefs.edit().remove("user").commit();
    }

    public User getUser() throws JSONException{
        String user_json = prefs.getString("user","");
        if(!user_json.equals("")) {
                User object = new User(user_json);
                return object;
        }
        return null;
    }

    public Boolean getJumpIntro(){
        Boolean jump_intro = prefs.getBoolean("jump_intro",false);
        return jump_intro;
    }
    public void setJumpIntro(Boolean jump_intro){
        prefs.edit().putBoolean("jump_intro", jump_intro).commit();
    }
}
