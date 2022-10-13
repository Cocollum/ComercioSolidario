package com.o2.comerciosolidario.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.o2.comerciosolidario.R;
import com.o2.comerciosolidario.app.AppController;
import com.o2.comerciosolidario.databinding.ActivityViewOfferBinding;
import com.o2.comerciosolidario.model.Collaborator;
import com.o2.comerciosolidario.utils.O2Api;
import com.o2.comerciosolidario.utils.Session;
import com.o2.comerciosolidario.viewmodels.OfferViewModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import cz.msebera.android.httpclient.Header;

public class ViewOfferActivity  extends AppController {

    OfferViewModel viewModel;
    Session session;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new OfferViewModel(getSupportFragmentManager());
        session = new Session(this);

        try {
            Collaborator offer = new Collaborator(getIntent().getStringExtra("offer"));

            viewModel.setOffer(offer);

            ArrayList<Fragment> fragments = new ArrayList<>();
            for(int i = 0; i < offer.getImages().size(); i++){
                fragments.add(ImageFragment.newInstance(offer.getImages().get(i)));
            }
            viewModel.setImage_fragments(fragments);

            viewModel.didClickClose.observe(this, (value) -> {
                if(value == true){
                    finish();
                }
            });

            viewModel.didClickShowCredentials.observe(this,value -> {
                if(value == true) {
                    try {
                        Intent intent;
                        if (session.getUser() != null) {
                            intent = new Intent(getApplicationContext(), ProfileActivity.class);
                        } else {
                            intent = new Intent(getApplicationContext(), LoginActivity.class);
                        }
                        startActivity(intent);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ActivityViewOfferBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_view_offer);

        binding.setViewModel(viewModel);
    }
}
