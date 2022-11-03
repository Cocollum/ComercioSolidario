package com.o2.comerciosolidario.viewmodels;

import android.view.View;
import android.widget.CheckBox;

import androidx.databinding.Bindable;
import androidx.lifecycle.MutableLiveData;

import com.o2.comerciosolidario.BR;
import com.o2.comerciosolidario.R;
import com.o2.comerciosolidario.model.Bussiness;
import com.o2.comerciosolidario.viewmodels.BaseViewModel;

import java.util.Arrays;

public class RegisterViewModel extends BaseViewModel{
    private String name;
    private String lastname;
    private String email;
    private String vat_number;
    private String birthday;
    private String phone;
    private String genre;
    private int bussiness_genre;
    private String[] genre_list;
    private String sector;
    private String[] sector_list;
    private String contact_sector;
    private String[] contact_sector_list;
    private String contact_type;
    private String[] contact_type_list;
    private String interest;
    private Boolean lgtbi = false;
    private Boolean lgtbi_plus = false;
    private Bussiness bussiness = new Bussiness();

    public MutableLiveData<Boolean> didClickRegister = new MutableLiveData<>();
    public MutableLiveData<Boolean> didClickClose = new MutableLiveData<>();
    public MutableLiveData<Boolean> didClickBirthday = new MutableLiveData<>();
    public MutableLiveData<Boolean> didClickBack = new MutableLiveData<>();
    public MutableLiveData<Boolean> didClickPrivacyPolicy = new MutableLiveData<>();
    public MutableLiveData<Boolean> didChangeContactClub = new MutableLiveData<>();
    public MutableLiveData<Boolean> didChangeContactFreelance = new MutableLiveData<>();

    @Bindable
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        notifyPropertyChanged(BR.name);
    }

    @Bindable
    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
        notifyPropertyChanged(BR.lastname);
    }

    @Bindable
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
        notifyPropertyChanged(BR.email);
    }


    @Bindable
    public String getVat_number() {
        return vat_number;
    }

    public void setVat_number(String vat_number) {
        this.vat_number = vat_number;
        notifyPropertyChanged(BR.vat_number);
    }

    @Bindable
    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
        notifyPropertyChanged(BR.birthday);
    }

    @Bindable
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
        notifyPropertyChanged(BR.phone);
    }

    public String[] getGenre_list() {
        return genre_list;
    }

    public void setGenre_list(String[] genre_list) {
        this.genre_list = genre_list;
    }

    @Bindable
    public int getGenre() {
        int index = Arrays.asList(this.genre_list).indexOf(this.genre);
        return index < 0 ? 0 : index;
    }

    public void setGenre(int index) {
        this.genre = this.genre_list[index];
        notifyPropertyChanged(BR.genre);
    }

    @Bindable
    public int getSector() {
        int index = Arrays.asList(this.sector_list).indexOf(this.sector);
        return index < 0 ? 0 : index;
    }

    public void setSector(int index) {
        this.sector = this.sector_list[index];
        this.bussiness.setSector(this.sector_list[index]);
        notifyPropertyChanged(BR.sector);
    }

    public String[] getSector_list() {
        return sector_list;
    }

    public void setSector_list(String[] sector_list) {
        this.sector_list = sector_list;
    }

    @Bindable
    public int getContact_sector() {
        int index = Arrays.asList(this.contact_sector_list).indexOf(this.contact_sector);
        return index < 0 ? 0 : index;
    }

    public void setContact_sector(int index) {
        this.contact_sector = this.contact_sector_list[index];
        notifyPropertyChanged(BR.contact_sector);
    }

    public String[] getContact_sector_list() {
        return contact_sector_list;
    }

    public void setContact_sector_list(String[] contact_sector_list) {
        this.contact_sector_list = contact_sector_list;
    }

    @Bindable
    public int getContact_type() {
        int index = Arrays.asList(this.contact_type_list).indexOf(this.contact_type);
        return index < 0 ? 0 : index;
    }

    public void setContact_type(int index) {
        this.contact_type = this.contact_type_list[index];
        this.bussiness.setPartner_type(this.contact_type_list[index]);
        notifyPropertyChanged(BR.contact_type);
    }

    public String[] getContact_type_list() {
        return contact_type_list;
    }

    public void setContact_type_list(String[] contact_type_list) {
        this.contact_type_list = contact_type_list;
    }

    @Bindable
    public String getInterest() {
        return interest;
    }

    public void setInterest(String interest) {
        this.interest = interest;
        notifyPropertyChanged(BR.interest);
    }

    @Bindable
    public Boolean getLgtbi() {
        return lgtbi;
    }

    public void setLgtbi(Boolean lgtbi) {
        this.lgtbi = lgtbi;
        notifyPropertyChanged(BR.lgtbi);
    }

    @Bindable
    public Boolean getLgtbi_plus() {
        return lgtbi_plus;
    }

    public void setLgtbi_plus(Boolean lgtbi_plus) {
        this.lgtbi_plus = lgtbi_plus;
        notifyPropertyChanged(BR.lgtbi_plus);
    }

    @Bindable
    public int getBussiness_genre() {
        return bussiness_genre;
    }

    public void setBussiness_genre(int bussiness_genre) {
        this.bussiness_genre = bussiness_genre;
        this.bussiness.setPartner_genre(this.genre_list[bussiness_genre]);
        notifyPropertyChanged(BR.bussiness_genre);
    }

    @Bindable
    public Bussiness getBussiness() {
        return bussiness;
    }

    public void setBussiness(Bussiness bussiness) {
        this.bussiness = bussiness;
        notifyPropertyChanged(BR.bussiness);
    }

    public void setPartner_birthday(String date){
        this.bussiness.setPartner_birthday(date);
        notifyPropertyChanged(BR.bussiness);
    }

    public void onClickRegister(){
        this.didClickRegister.setValue(true);
    }
    public void onClickClose(){
        this.didClickClose.setValue(true);
    }
    public void onClickBirthday(){
        this.didClickBirthday.setValue(true);
    }
    public void onClickBack(){
        this.didClickBack.setValue(true);
    }
    public void onClickPrivacyPolicy(){
        this.didClickPrivacyPolicy.setValue(true);
    }
    public void onContactClubChange(View view, Boolean checked){
        this.didChangeContactClub.setValue(checked);
    }
    public void onContactFreelanceChange(View view, Boolean checked){
        this.didChangeContactFreelance.setValue(checked);
    }
}
