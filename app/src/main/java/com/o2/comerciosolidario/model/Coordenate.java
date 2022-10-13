package com.o2.comerciosolidario.model;

import org.json.JSONException;
import org.json.JSONObject;

public class Coordenate {
    private String address;
    private double lat;
    private double lng;
    private String place_id;
    private String name;
    private String street_number;
    private String street_name;
    private String city;
    private String state;
    private String post_code;
    private String country;
    private String country_short;

    public Coordenate(String json_string) throws JSONException {
        JSONObject obj = new JSONObject(json_string);

        this.address = obj.getString("address");
        this.lat = obj.getDouble("lat");
        this.lng = obj.getDouble("lng");
        this.place_id = obj.optString("place_id");
        this.name = obj.optString("name");
        this.street_name = obj.optString("street_name");
        this.street_number = obj.optString("street_number");
        this.city = obj.optString("city");
        this.state = obj.optString("state");
        this.post_code = obj.optString("post_code");
        this.country = obj.optString("country");
        this.country_short = obj.optString("country_short");
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public String getPlace_id() {
        return place_id;
    }

    public void setPlace_id(String place_id) {
        this.place_id = place_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStreet_number() {
        return street_number;
    }

    public void setStreet_number(String street_number) {
        this.street_number = street_number;
    }

    public String getStreet_name() {
        return street_name;
    }

    public void setStreet_name(String street_name) {
        this.street_name = street_name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPost_code() {
        return post_code;
    }

    public void setPost_code(String post_code) {
        this.post_code = post_code;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCountry_short() {
        return country_short;
    }

    public void setCountry_short(String country_short) {
        this.country_short = country_short;
    }

    public String getFormattedAddress(){
        if (!getStreet_name().equals("")) {
            return getStreet_name() + " " + getStreet_number() + ", " + getCity();
        }else{
            return getAddress();
        }
    }

    public String toJSON(){
        try{
            JSONObject obj = new JSONObject();

            obj.put("address", address);
            obj.put("lat", lat);
            obj.put("lng", lng);
            obj.put("place_id", place_id);
            obj.put("name", name);
            obj.put("street_number", street_number);
            obj.put("street_name", street_name);
            obj.put("city", city);
            obj.put("state", state);
            obj.put("post_code", post_code);
            obj.put("country", country);
            obj.put("country_short", country_short);

            return obj.toString();
        }catch (JSONException e){
            e.printStackTrace();
        }
        return "";
    }
}
