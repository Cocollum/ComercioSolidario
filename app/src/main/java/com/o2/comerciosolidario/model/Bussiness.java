package com.o2.comerciosolidario.model;

import org.json.JSONException;
import org.json.JSONObject;

public class Bussiness {
    private String bussiness_name = "";
    private String city = "";
    private String postal_code = "";
    private String province = "";
    private String bussiness_vat_number = "";
    private String address = "";
    private String bussiness_phone = "";
    private String bussiness_phone_alt = "";
    private String bussiness_email = "";
    private String activity = "";
    private String sector = "";
    private String sector_other = "";
    private Boolean bussiness_lgtbi = false;
    private Boolean bussiness_lgtbi_plus = false;
    private String facebook="";
    private String instagram="";
    private String linkedin="";
    private String tweeter="";
    private String [] social_media ={};
    private Boolean contact_freelance = false;
    private Boolean contact_club = false;
    private String contact_name = "";
    private String contact_lastname = "";
    private String contact_vat_number = "";
    private String contact_email = "";
    private String contact_phone = "";
    private String partner_name = "";
    private String partner_lastname = "";
    private String partner_email = "";
    private String partner_phone = "";
    private String partner_birthday = "";
    private String partner_genre = "";
    private String partner_interest = "";
    private Boolean partner_lgtbi = false;
    private Boolean partner_lgtbi_plus = false;
    private String partner_type = "";



    public String getBussiness_name() {
        return bussiness_name;
    }

    public void setBussiness_name(String bussiness_name) {
        this.bussiness_name = bussiness_name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostal_code() {
        return postal_code;
    }

    public void setPostal_code(String postal_code) {
        this.postal_code = postal_code;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getBussiness_vat_number() {
        return bussiness_vat_number;
    }

    public void setBussiness_vat_number(String bussiness_vat_number) {
        this.bussiness_vat_number = bussiness_vat_number;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBussiness_phone() {
        return bussiness_phone;
    }

    public void setBussiness_phone(String bussiness_phone) {
        this.bussiness_phone = bussiness_phone;
    }

    public String getBussiness_phone_alt() {
        return bussiness_phone_alt;
    }

    public void setBussiness_phone_alt(String bussiness_phone_alt) {
        this.bussiness_phone_alt = bussiness_phone_alt;
    }

    public String getBussiness_email() {
        return bussiness_email;
    }

    public void setBussiness_email(String bussiness_email) {
        this.bussiness_email = bussiness_email;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    public String getSector_other() {
        return sector_other;
    }

    public void setSector_other(String sector_other) {
        this.sector_other = sector_other;
    }

    public String getActivity() {
        return activity;
    }

    public String getContact_name() {
        return contact_name;
    }

    public void setContact_name(String contact_name) {
        this.contact_name = contact_name;
    }

    public String getContact_lastname() {
        return contact_lastname;
    }

    public void setContact_lastname(String contact_lastname) {
        this.contact_lastname = contact_lastname;
    }

    public String getContact_vat_number() {
        return contact_vat_number;
    }

    public void setContact_vat_number(String contact_vat_number) {
        this.contact_vat_number = contact_vat_number;
    }

    public String getContact_email() {
        return contact_email;
    }

    public void setContact_email(String contact_email) {
        this.contact_email = contact_email;
    }

    public String getContact_phone() {
        return contact_phone;
    }

    public void setContact_phone(String contact_phone) {
        this.contact_phone = contact_phone;
    }



    public String getPartner_type() {
        return partner_type;
    }

    public void setPartner_type(String partner_type) {
        this.partner_type = partner_type;
    }

    public Boolean getContact_club() {
        return contact_club;
    }

    public void setContact_club(Boolean contact_club) {
        this.contact_club = contact_club;
    }

    public Boolean getContact_freelance() {
        return contact_freelance;
    }

    public void setContact_freelance(Boolean contact_freelance) {
        this.contact_freelance = contact_freelance;
    }

    public Boolean getBussiness_lgtbi() {
        return bussiness_lgtbi;
    }

    public void setBussiness_lgtbi(Boolean bussiness_lgtbi) {
        this.bussiness_lgtbi = bussiness_lgtbi;
    }

    public Boolean getBussiness_lgtbi_plus() {
        return bussiness_lgtbi_plus;
    }

    public void setBussiness_lgtbi_plus(Boolean bussiness_lgtbi_plus) {
        this.bussiness_lgtbi_plus = bussiness_lgtbi_plus;
    }

    public String[] getSocial_media() {
        social_media= new String[] {getFacebook(), getInstagram(), getLinkedin(), getTweeter()};
        return social_media;
    }

    public void setSocial_media(String[] social_media) {
        this.social_media = social_media;
    }

    public String getFacebook() {
        return facebook;
    }

    public void setFacebook(String facebook) {
        this.facebook = facebook;
    }

    public String getInstagram() {
        return instagram;
    }
    public void setInstagram(String instagram) {
        this.instagram = instagram;
    }

    public String getLinkedin() {
        return linkedin;
    }
    public void setLinkedin(String linkedin) {
        this.linkedin = linkedin;
    }
    public String getTweeter() {
        return tweeter;
    }
    public void setTweeter(String tweeter) {
        this.tweeter = tweeter;
    }

    public String getPartner_name() {
        return partner_name;
    }

    public void setPartner_name(String partner_name) {
        this.partner_name = partner_name;
    }

    public String getPartner_lastname() {
        return partner_lastname;
    }

    public void setPartner_lastname(String partner_lastname) {
        this.partner_lastname = partner_lastname;
    }

    public String getPartner_email() {
        return partner_email;
    }

    public void setPartner_email(String partner_email) {
        this.partner_email = partner_email;
    }

    public String getPartner_phone() {
        return partner_phone;
    }

    public void setPartner_phone(String partner_phone) {
        this.partner_phone = partner_phone;
    }

    public String getPartner_birthday() {
        return partner_birthday;
    }

    public void setPartner_birthday(String partner_birthday) {
        this.partner_birthday = partner_birthday;
    }

    public String getPartner_genre() {
        return partner_genre;
    }

    public void setPartner_genre(String partner_genre) {
        this.partner_genre = partner_genre;
    }

    public String getPartner_interest() {
        return partner_interest;
    }

    public void setPartner_interest(String partner_interest) {
        this.partner_interest = partner_interest;
    }

    public Boolean getPartner_lgtbi() {
        return partner_lgtbi;
    }

    public void setPartner_lgtbi(Boolean partner_lgtbi) {
        this.partner_lgtbi = partner_lgtbi;
    }

    public Boolean getPartner_lgtbi_plus() {
        return partner_lgtbi_plus;
    }

    public void setPartner_lgtbi_plus(Boolean partner_lgtbi_plus) {
        this.partner_lgtbi_plus = partner_lgtbi_plus;
    }

    public String toJSON(){
        try {
            JSONObject obj = new JSONObject();
            obj.put("bussines_name", bussiness_name);
            obj.put("city", city);
            obj.put("postal_code", postal_code);
            obj.put("bussiness_vat_number", bussiness_vat_number);
            obj.put("address", address);
            obj.put("bussiness_phone", bussiness_phone);
            obj.put("bussiness_phone_alt", bussiness_phone_alt);
            obj.put("bussiness_email", bussiness_email);
            obj.put("bussiness_lgtbi", bussiness_lgtbi);
            obj.put("bussiness_lgbti_plus", bussiness_lgtbi_plus);
            obj.put("activity", activity);
            obj.put("sector", sector);
            obj.put("social_media", social_media);
            obj.put("sector_other", sector_other);
            obj.put("contact_freelance", contact_freelance);
            obj.put("contact_club", contact_club);
            obj.put("contact_name", contact_name);
            obj.put("contact_lastname", contact_lastname);
            obj.put("contact_var_number", contact_vat_number);
            obj.put("contact_phone", contact_phone);
            obj.put("partner_name", partner_name);
            obj.put("partner_lastname", partner_lastname);
            obj.put("partner_email", partner_email);
            obj.put("partner_phone", partner_phone);
            obj.put("partner_lgtbi", partner_lgtbi);
            obj.put("partner_genre", partner_genre);
            obj.put("partner_interest", partner_interest);
            obj.put("partner_lgtbi_plus", partner_lgtbi_plus);
            obj.put("partner_type", partner_type);

            return obj.toString();
        }catch(JSONException e){
            e.printStackTrace();
            return "";
        }
    }
}
