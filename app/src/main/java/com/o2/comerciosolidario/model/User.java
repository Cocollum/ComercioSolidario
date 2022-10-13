package com.o2.comerciosolidario.model;

import org.json.JSONException;
import org.json.JSONObject;

public class User {
    private int id;
    private String username;
    private String email;
    private String displayname;
    private String avatar;

    public User(String json) throws JSONException {
        JSONObject obj = new JSONObject(json);
        this.id = obj.getInt("id");
        this.username = obj.getString("username");
        this.email = obj.getString("email");
        this.displayname = obj.getString("displayname");
        this.avatar = obj.getString("avatar");
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getDisplayname() {
        return displayname;
    }

    public void setDisplayname(String displayname) {
        this.displayname = displayname;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String toJSON() throws JSONException{
        JSONObject obj = new JSONObject();

        obj.put("id", this.id);
        obj.put("username", this.username);
        obj.put("email", this.email);
        obj.put("displayname", this.displayname);
        obj.put("avatar", this.avatar);

        return obj.toString();
    }
}
