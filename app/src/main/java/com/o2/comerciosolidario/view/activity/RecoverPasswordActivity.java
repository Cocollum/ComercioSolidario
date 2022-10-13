package com.o2.comerciosolidario.view.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.o2.comerciosolidario.R;
import com.o2.comerciosolidario.app.AppController;
import com.o2.comerciosolidario.databinding.ActivityRecoverPasswordBinding;
import com.o2.comerciosolidario.utils.O2Api;
import com.o2.comerciosolidario.viewmodels.LoginViewModel;

import org.json.JSONException;
import org.json.JSONObject;

import androidx.databinding.DataBindingUtil;
import cz.msebera.android.httpclient.Header;

public class RecoverPasswordActivity extends AppController {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LoginViewModel viewModel = new LoginViewModel();
        ActivityRecoverPasswordBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_recover_password);

        binding.setViewModel(viewModel);

        viewModel.didClickClose.observe(this, (value) -> {
            if(value == true){
                finish();
            }
        });

        viewModel.didClickBack.observe(this, (value) -> {
            if(value == true){
                LinearLayout fail_layout = findViewById(R.id.fail_recover_password);
                fail_layout.setVisibility(View.GONE);
                viewModel.didClickBack.setValue(false);
            }
        });

        viewModel.didRecoverPassword.observe(this, value -> {
            if(!value.equals("")){
                RequestParams params = new RequestParams();
                params.add("username",value);
                O2Api.post("collaborator/retrieve_password", params, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                        String response = new String(responseBody);
                        Log.d("retrieve_passowrd", response);
                        try{
                            JSONObject obj = new JSONObject(response);
                            if(obj.getString("data").equals("ok")){
                                LinearLayout ok_layout = findViewById(R.id.ok_recover_password);
                                ok_layout.setVisibility(View.VISIBLE);
                            }else{
                                LinearLayout fail_layout = findViewById(R.id.fail_recover_password);
                                fail_layout.setVisibility(View.VISIBLE);
                            }
                        }catch(JSONException e){
                            e.printStackTrace();
                            LinearLayout fail_layout = findViewById(R.id.fail_recover_password);
                            fail_layout.setVisibility(View.VISIBLE);
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody,
                                          Throwable error) {

                    }
                });
            }
        });
    }
}
