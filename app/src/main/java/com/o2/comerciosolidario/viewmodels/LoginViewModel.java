package com.o2.comerciosolidario.viewmodels;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.lifecycle.MutableLiveData;

public class LoginViewModel extends BaseObservable {

    public MutableLiveData<Boolean> didClickRecoverPassword = new MutableLiveData<>();
    public MutableLiveData<Boolean> didClickLogin = new MutableLiveData<>();
    public MutableLiveData<Boolean> didClickClose = new MutableLiveData<>();
    public MutableLiveData<Boolean> didClickBack = new MutableLiveData<>();
    public MutableLiveData<Boolean> didClickProfile = new MutableLiveData<>();
    public MutableLiveData<Boolean> didClickSaveChanges = new MutableLiveData<>();
    public MutableLiveData<String> didRecoverPassword = new MutableLiveData<>();

    public String userid;
    public String profile_image;
    public String username;
    public String password;

    public LoginViewModel(){
        didClickRecoverPassword.setValue(false);
        didClickLogin.setValue(false);
        didClickClose.setValue(false);
        didClickBack.setValue(false);
        didRecoverPassword.setValue("");
    }

    @Bindable
    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getProfile_image() {
        return profile_image;
    }

    public void setProfile_image(String profile_image) {
        this.profile_image = profile_image;
    }

    @Bindable
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Bindable
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void onClickRecoverPassword() {
        didClickRecoverPassword.setValue(true);
    }
    public void onClickLogin(){
        didClickLogin.setValue(true);
    }
    public void onClickClose(){ didClickClose.setValue(true); }
    public void onRecoverPassword(){ didRecoverPassword.setValue(this.username);}
    public void onClickBack(){ didClickBack.setValue(true);}
    public void onClickProfile(){ didClickProfile.setValue(true); }
    public void onClickSaveChanges(){ didClickSaveChanges.setValue(true); }
}
