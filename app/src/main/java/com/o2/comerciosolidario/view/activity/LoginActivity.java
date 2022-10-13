package com.o2.comerciosolidario.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.o2.comerciosolidario.R;
import com.o2.comerciosolidario.app.AppController;
import com.o2.comerciosolidario.databinding.ActivityLoginBinding;
import com.o2.comerciosolidario.model.User;
import com.o2.comerciosolidario.utils.O2Api;
import com.o2.comerciosolidario.utils.Session;
import com.o2.comerciosolidario.viewmodels.LoginViewModel;

import org.json.JSONException;
import org.json.JSONObject;

import androidx.databinding.DataBindingUtil;
import cz.msebera.android.httpclient.Header;

public class LoginActivity extends AppController {
    Session session;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        session = new Session(getApplicationContext());

        LoginViewModel viewModel = new LoginViewModel();

        ActivityLoginBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        binding.setViewModel(viewModel);



        viewModel.didClickBack.observe(this, (value) -> {
            if(value == true){
                LinearLayout fail_layout = findViewById(R.id.fail_recover_password);
                fail_layout.setVisibility(View.GONE);
                viewModel.didClickBack.setValue(false);
            }
        });

        viewModel.didClickLogin.observe(this, (value) -> {
            if (value == true) {
                RequestParams params = new RequestParams();
                params.add("username", viewModel.username);
                params.add("password", viewModel.password);

                O2Api.get("auth/generate_auth_cookie", params, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                        String response = new String(responseBody);
                        Log.d("login",response);
                        try {
                            JSONObject response_obj = new JSONObject(response);
                            if (response_obj.getString("status").equals("ok")) {
                                session.setUser(new User(response_obj.getString("user")));
                                Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
                                startActivity(intent);
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
                        if(responseBody != null) {
                            String response = new String(responseBody);
                            Log.d("Login", response);
                        }else{
                            error.printStackTrace();
                        }
                    }
                });
            }
        });

        viewModel.didClickRecoverPassword.observe(this, (value) -> {
            if (value == true) {
                Intent intent = new Intent(getApplicationContext(), RecoverPasswordActivity.class);
                startActivity(intent);
            }
        });

        viewModel.didClickClose.observe(this, (value) -> {
            if(value == true){
                finish();
            }
        });
    }
}
