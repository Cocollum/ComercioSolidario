package com.o2.comerciosolidario.viewmodels;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.o2.comerciosolidario.BR;
import com.o2.comerciosolidario.model.Collaborator;
import com.o2.comerciosolidario.utils.O2Api;
import com.o2.comerciosolidario.utils.PageViewerAdapter;
import com.o2.comerciosolidario.view.activity.IntroFragment;

import java.util.ArrayList;

import androidx.core.view.GravityCompat;
import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.BindingAdapter;
import androidx.databinding.ObservableField;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.MutableLiveData;
import androidx.viewpager.widget.ViewPager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class HomeViewModel extends BaseViewModel {
    public Boolean view_offer =  false;
    public Collaborator offer_tag = null;
    public String offer_name = "";
    public String offer_address = "";
    public String offer_description = "";
    public Boolean filtersVisibility = false;

    public MutableLiveData<Collaborator> didClickViewOffer = new MutableLiveData<>();
    public MutableLiveData<Boolean> didClickJumpIntro = new MutableLiveData<>();
    public MutableLiveData<Boolean> didChangeCheckAllCheckbox = new MutableLiveData<>();
    public Boolean lgtbi_friendly_checked = false;
    public Boolean lgtbi_plus_checked = false;
    public MutableLiveData<String> didClickRRSS = new MutableLiveData<>();
    public MutableLiveData<Boolean> didClickIntro = new MutableLiveData<>();
    public MutableLiveData<Boolean> didClickRegister = new MutableLiveData<>();
    public MutableLiveData<Boolean> didClickRegisterBussiness = new MutableLiveData<>();
    public MutableLiveData<ArrayList<String>> filteredItems = new MutableLiveData<>();


    ArrayList<Fragment> fragments = new ArrayList<>();
    ArrayList<String> filters = new ArrayList<>();

    private void get_filters(){
        O2Api.get("collaborator/get_categories", new RequestParams(), new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if(responseBody != null) {
                    String response = new String(responseBody);
                    Log.d("filters", response);
                    try {
                        JSONObject obj = new JSONObject(response);
                        if (obj.getString("status").equals("200")) {
                            JSONArray arr = new JSONArray(obj.getString("data"));
                            ArrayList<String> filters = new ArrayList<>();

                            for (int i = 0; i < arr.length(); i++) {
                                filters.add(arr.getString(i));
                            }
                            setFilters(filters);
                            filteredItems.setValue(filters);
                        }
                    } catch (JSONException exc) {
                        exc.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                if(responseBody != null) {
                    String response = new String(responseBody);
                    Log.d("Get Categories", response);
                }
            }
        });
    }

    public HomeViewModel(){
        didClickViewOffer.setValue(null);
        didClickJumpIntro.setValue(false);
        didChangeCheckAllCheckbox.setValue(true);
        didClickRRSS.setValue("");
        get_filters();

    }
    public HomeViewModel(FragmentManager fm){
        super.create(fm);
        didClickViewOffer.setValue(null);
        didClickJumpIntro.setValue(false);
        didChangeCheckAllCheckbox.setValue(true);
        didClickRRSS.setValue("");
        get_filters();
    }

    @Bindable
    public ArrayList<Fragment> getFragments() {
        return fragments;
    }

    public void setFragments(ArrayList<Fragment> fragments) {
        this.fragments = fragments;
        notifyPropertyChanged(BR.fragments);
    }

    public void onClickViewOffer(Collaborator offer){
        didClickViewOffer.setValue(offer);
    }

    @Bindable
    public Boolean getView_offer() {
        return view_offer;
    }

    public void setView_offer(Boolean view_offer) {
        this.view_offer = view_offer;
        notifyPropertyChanged(BR.view_offer);
    }

    @Bindable
    public Collaborator getOffer_tag() {
        return offer_tag;
    }

    public void setOffer_tag(Collaborator offer_tag) {
        this.offer_tag = offer_tag;
        notifyPropertyChanged(BR.offer_tag);
    }

    @Bindable
    public String getOffer_name() {
        return offer_name;
    }

    public void setOffer_name(String offer_name) {
        this.offer_name = offer_name;
        notifyPropertyChanged(BR.offer_name);
    }

    @Bindable
    public String getOffer_address() {
        return offer_address;
    }

    public void setOffer_address(String offer_address) {
        this.offer_address = offer_address;
        notifyPropertyChanged(BR.offer_address);
    }

    @Bindable
    public String getOffer_description() {
        return offer_description;
    }

    public void setOffer_description(String offer_description) {
        this.offer_description = offer_description;
        notifyPropertyChanged(BR.offer_description);
    }

    @Bindable
    public ArrayList<String> getFilters() {
        return filters;
    }

    public void setFilters(ArrayList<String> filters) {
        this.filters = filters;
        notifyPropertyChanged(BR.filters);
    }

   @Bindable
   public Boolean getFiltersVisibility() {
       return filtersVisibility;
   }

   public void setFiltersVisibility(Boolean filtersVisibility) {
       this.filtersVisibility = filtersVisibility;
       notifyPropertyChanged(BR.filtersVisibility);
   }

    @Bindable
    public Boolean getLgtbi_friendly_checked() {
        return lgtbi_friendly_checked;
    }

    public void setLgtbi_friendly_checked(Boolean lgtbi_friendly_checked) {
        this.lgtbi_friendly_checked = lgtbi_friendly_checked;
        notifyPropertyChanged(BR.lgtbi_friendly_checked);
        filteredItems.postValue(filteredItems.getValue());
    }

    @Bindable
    public Boolean getLgtbi_plus_checked() {
        return lgtbi_plus_checked;
    }

    public void setLgtbi_plus_checked(Boolean lgtbi_plus_checked) {
        this.lgtbi_plus_checked = lgtbi_plus_checked;
        notifyPropertyChanged(BR.lgtbi_plus_checked);
        filteredItems.postValue(filteredItems.getValue());
    }

    public void onClickMarker(Collaborator collaborator){
        setView_offer(true);
        setOffer_tag(collaborator);
        setOffer_name(collaborator.getName());
        setOffer_description(collaborator.getDescription());
        setOffer_address(collaborator.getCoords().getFormattedAddress());
    }
    public void onClickMap(){
        setView_offer(false);
        setOffer_tag(null);
        setOffer_name("");
        setOffer_description("");
        setOffer_address("");
    }

    public void onClickJumpIntro(){
        didClickJumpIntro.setValue(true);
    }
    public void onClickOpenFilters(){ setFiltersVisibility(true); }
    public void onClickCloseFilters(){ setFiltersVisibility(false);}
    public void onClickRRSS(View v){ didClickRRSS.postValue((String) v.getTag()); }
    public void onClickIntro(){ didClickIntro.postValue(true); }
    public void onClickRegister(){ didClickRegister.postValue(true); }
    public void onClickRegisterBusssiness(){ didClickRegisterBussiness.postValue(true); }

    public void onChangeFilterCheckbox(View v){
        String text = (String) ((CheckBox) v).getText();
        Boolean isChecked = ((CheckBox) v).isChecked();
        ArrayList items = filteredItems.getValue();
        if(isChecked){
            if(!items.contains(text)){
                items.add(text);
            }
        }else{
            items.remove(text);
        }

        filteredItems.postValue(items);
    }

    public void onChangeCheckAllCheckbox(View v){
        Boolean isChecked = ((CheckBox) v).isChecked();
        didChangeCheckAllCheckbox.setValue(isChecked);
    }

}


