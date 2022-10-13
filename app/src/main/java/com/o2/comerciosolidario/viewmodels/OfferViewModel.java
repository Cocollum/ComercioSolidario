package com.o2.comerciosolidario.viewmodels;

import android.view.View;
import android.widget.ArrayAdapter;

import com.o2.comerciosolidario.BR;
import com.o2.comerciosolidario.model.Collaborator;
import com.o2.comerciosolidario.utils.PageViewerAdapter;

import java.util.ArrayList;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.BindingAdapter;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.MutableLiveData;
import androidx.viewpager.widget.ViewPager;

public class OfferViewModel extends BaseViewModel {
    private Collaborator offer;

    private ArrayList<Fragment> image_fragments = new ArrayList<>();
    public MutableLiveData<Boolean> didClickShowCredentials = new MutableLiveData<>();
    public MutableLiveData<Boolean> didClickClose = new MutableLiveData<>();

    public OfferViewModel(){
        didClickShowCredentials.setValue(false);
        didClickClose.setValue(false);
    }
    public OfferViewModel(FragmentManager fm){
        super.create(fm);
        didClickShowCredentials.setValue(false);
        didClickClose.setValue(false);
    }


    @Bindable
    public Collaborator getOffer() {
        return offer;
    }

    public void setOffer(Collaborator offer) {
        this.offer = offer;
        notifyPropertyChanged(BR.offer);
    }

    @Bindable
    public ArrayList<Fragment> getImage_fragments() {
        return image_fragments;
    }

    public void setImage_fragments(ArrayList<Fragment> fragments) {
        this.image_fragments = fragments;
        notifyPropertyChanged(BR.image_fragments);
    }

    public void onClickShowCredentials(){
        didClickShowCredentials.setValue(true);
    }

    public void onClickClose(){ didClickClose.setValue(true); }
}
