package com.o2.comerciosolidario.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

public class Collaborator {
    private String id;
    private String name;
    private String description;
    private Coordenate coords;
    private ArrayList<String> offers = new ArrayList<>();
    private ArrayList<String> tags = new ArrayList<>();
    private ArrayList<String> images = new ArrayList<>();


    public Collaborator(String json_string) throws JSONException {
        JSONObject obj = new JSONObject(json_string);

        this.id = obj.getString("id");
        this.name = obj.getString("name");
        this.description = obj.getString("description");
        this.coords = new Coordenate(obj.getString("coords"));

        if(obj.optString("offers") != null && !obj.optString("offers").equals("null") && !obj.optString("offers").equals("")) {
            JSONArray array_offers = new JSONArray(obj.optString("offers"));
            for (int i = 0; i < array_offers.length(); i++) {
                JSONObject offer_obj = new JSONObject(array_offers.getString(i));
                this.offers.add(offer_obj.getString("offer_text"));
            }
        }
        if(obj.optString("tags") != null && !obj.optString("tags").equals("null") && !obj.optString("tags").equals("")) {
            JSONArray array_tags = new JSONArray(obj.optString("tags"));
            for (int i = 0; i < array_tags.length(); i++) {
                this.tags.add(array_tags.getString(i));
            }
        }
        if(obj.optString("images") != null && !obj.optString("images").equals("null") && !obj.optString("images").equals("")) {
            JSONArray array_images = new JSONArray(obj.optString("images"));
            for (int i = 0; i < array_images.length(); i++) {
                this.images.add(array_images.getString(i));
            }
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Coordenate getCoords() {
        return coords;
    }

    public void setCoords(Coordenate coords) {
        this.coords = coords;
    }

    public ArrayList<String> getOffers() {
        return offers;
    }

    public ArrayList<String> getOffers_home() {
        ArrayList<String> offers_home;
        if(this.offers.size() <= 2){
            offers_home = this.offers;
        }else{
            offers_home = new ArrayList<String>();

            for(int i = 0; i < 2; i++){
                offers_home.add(i, offers.get(i));
            }

            offers_home.add(2, "+ " + (offers.size() - 2) + " ofertas");
        }

        return offers_home;
    }

    public void setOffers(ArrayList<String> offers) {
        this.offers = offers;
    }

    public ArrayList<String> getTags() {
        return tags;
    }

    public void setTags(ArrayList<String> tags) {
        this.tags = tags;
    }

    public ArrayList<String> getImages() {
        return images;
    }

    public void setImages(ArrayList<String> images) {
        this.images = images;
    }

    public String toJSON(){
        try{
            JSONObject obj = new JSONObject();

            obj.put("id", id);
            obj.put("name", name);
            obj.put("description", description);
            obj.put("coords", coords.toJSON());

            JSONArray array = new JSONArray();
            Iterator<String> offers_array = offers.iterator();
            while(offers_array.hasNext()){
                String offer = offers_array.next();
                JSONObject offer_obj = new JSONObject();
                offer_obj.put("offer_text", offer);
                array.put(offer_obj.toString());
            }
            obj.put("offers", array.toString());

            JSONArray array_tags = new JSONArray();
            Iterator<String> tags_array = tags.iterator();
            while(tags_array.hasNext()){
                String tag = tags_array.next();
                array_tags.put(tag);
            }
            obj.put("tags", array_tags.toString());

            JSONArray array_images = new JSONArray();
            Iterator<String> images_array = images.iterator();
            while(images_array.hasNext()){
                String image = images_array.next();
                array_images.put(image);
            }
            obj.put("images", array_images.toString());

            return obj.toString();
        }catch(JSONException e){
           e.printStackTrace();
        }

        return "";
    }
}
