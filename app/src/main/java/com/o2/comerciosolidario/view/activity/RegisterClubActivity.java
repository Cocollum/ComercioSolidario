package com.o2.comerciosolidario.view.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatSpinner;
import androidx.databinding.DataBindingUtil;

import com.o2.comerciosolidario.app.AppController;
import com.o2.comerciosolidario.model.User;
import com.o2.comerciosolidario.utils.DatePickerFragment;
import com.o2.comerciosolidario.utils.MultiSpinner;
import com.o2.comerciosolidario.utils.Session;
import com.o2.comerciosolidario.utils.O2Api;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.o2.comerciosolidario.R;
import com.o2.comerciosolidario.databinding.ActivityRegisterClubBinding;
import com.o2.comerciosolidario.utils.WidgetDialogActivity;
import com.o2.comerciosolidario.viewmodels.RegisterViewModel;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import cz.msebera.android.httpclient.Header;

public class RegisterClubActivity extends AppController{
    Session session;
    RegisterViewModel viewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        session = new Session(getApplicationContext());

        viewModel = new RegisterViewModel();

        viewModel.setGenre_list(getResources().getStringArray(R.array.genre_list));

        ActivityRegisterClubBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_register_club);
        binding.setViewModel(viewModel);

        AppCompatSpinner genreSpinner = (AppCompatSpinner) findViewById(R.id.genre_spinner);
        genreSpinner.setSelection(0, false);
        TextView selectedView = (TextView) genreSpinner.getSelectedView();
        if(selectedView != null){
            selectedView.setTextColor(getResources().getColor(R.color.gray));
        }

        MultiSpinner mSpinner = (MultiSpinner) findViewById(R.id.interest);
        mSpinner.setSelection(0, false);
        TextView mselectedView = (TextView) mSpinner.getSelectedView();
        if(mselectedView != null){
            mselectedView.setTextColor(getResources().getColor(R.color.gray));
        }
        mSpinner.setMultiSpinnerListener(new MultiSpinner.MultiSpinnerListener() {
            @Override
            public void onItemsSelected(boolean[] selected) {
                String[] interest = getResources().getStringArray(R.array.interest_list);
                StringBuffer buffer = new StringBuffer();

                for(int i = 0; i < interest.length; i++){
                    if(selected[i]){
                        buffer.append(interest[i]);
                        buffer.append(", ");
                    }
                }

                viewModel.setInterest(buffer.toString());
            }
        });

        viewModel.didClickRegister.observe(this, (value) -> {
            if(value == true){
                RequestParams params = new RequestParams();
                params.add("name",viewModel.getName());
                params.add("lastname",viewModel.getLastname());
                params.add("email",viewModel.getEmail());
                params.add("vat_number",viewModel.getVat_number());
                params.add("phone",viewModel.getPhone());
                params.add("birthday",viewModel.getBirthday());
                params.add("genre",viewModel.getGenre_list()[viewModel.getGenre()]);
                params.add("interests",viewModel.getInterest());
                params.add("lgtbi",viewModel.getLgtbi() ? "SI" : "NO");
                params.add("lgtbi_plus",viewModel.getLgtbi_plus() ? "SI" : "NO");

                O2Api.post("collaborator/register_club", params, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                        String response = new String(responseBody);
                        Log.d("register_club",response);

                        try{
                            JSONObject obj = new JSONObject(response);

                            if(obj.getInt("status") == 200 && !obj.getString("data").contains("errors")){
                                User user = new User(obj.getString("data"));
                                session.setUser(user);

                                findViewById(R.id.ok_recover_password).setVisibility(View.VISIBLE);
                            }else{
                                findViewById(R.id.fail_recover_password).setVisibility(View.VISIBLE);
                            }

                        }catch (JSONException e){
                            findViewById(R.id.fail_recover_password).setVisibility(View.VISIBLE);
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody,
                                                   Throwable error) {
                        String response = new String(responseBody);
                        Log.d("register_club",response);
                        findViewById(R.id.fail_recover_password).setVisibility(View.VISIBLE);

                    }

                });
            }
        });

        viewModel.didClickClose.observe(this, (value) -> {
            if(value == true){
                finish();
            }
        });
        viewModel.didClickBack.observe(this, (value) -> {
            if(value == true){
                findViewById(R.id.fail_recover_password).setVisibility(View.GONE);
                viewModel.didClickBack.setValue(false);
            }
        });

        viewModel.didClickBirthday.observe(this, (value) -> {
            if(value == true){
                showDatePickerDialog();
            }
        });
        viewModel.didClickPrivacyPolicy.observe(this, (value) -> {
            if(value == true){
                Intent modalIntent = new Intent(this, WidgetDialogActivity.class);
                startActivity(modalIntent);
            }
        });
    }


    private void showDatePickerDialog() {
        DatePickerFragment newFragment = DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener(){
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day){
                final String selectedDate = day + "/" + (month < 9 ? "0" : "") + (month+1) + "/" + year;
                viewModel.setBirthday(selectedDate);
            }
        });

        newFragment.show(getSupportFragmentManager(), "datePicker");
    }
}
