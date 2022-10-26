package com.o2.comerciosolidario.view.activity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatSpinner;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.databinding.DataBindingUtil;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.o2.comerciosolidario.R;
import com.o2.comerciosolidario.app.AppController;

import com.o2.comerciosolidario.databinding.ActivityRegisterBussinessBinding;
import com.o2.comerciosolidario.databinding.ActivityRegisterClubBinding;
import com.o2.comerciosolidario.model.Bussiness;
import com.o2.comerciosolidario.model.User;
import com.o2.comerciosolidario.utils.DatePickerFragment;
import com.o2.comerciosolidario.utils.MultiSpinner;
import com.o2.comerciosolidario.utils.O2Api;
import com.o2.comerciosolidario.utils.Session;
import com.o2.comerciosolidario.utils.WidgetDialogActivity;
import com.o2.comerciosolidario.viewmodels.RegisterViewModel;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class RegisterBussinessActivity extends AppController {
    Session session;
    RegisterViewModel viewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        session = new Session(getApplicationContext());

        viewModel = new RegisterViewModel();

        viewModel.setGenre_list(getResources().getStringArray(R.array.genre_list));
        viewModel.setSector_list(getResources().getStringArray(R.array.interest_list));
        viewModel.setContact_sector_list(getResources().getStringArray(R.array.department_list));
        viewModel.setContact_type_list(getResources().getStringArray(R.array.type_list));

        ActivityRegisterBussinessBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_register_bussiness);
        binding.setViewModel(viewModel);

        AppCompatSpinner genre_spinner = ((AppCompatSpinner) findViewById(R.id.genre_spinner));
        this.configureSpinner(genre_spinner, getResources().getStringArray(R.array.genre_list));
        genre_spinner.setSelection(0, false);
        TextView selectedView = (TextView) genre_spinner.getSelectedView();
        if (selectedView != null) {
            selectedView.setTextColor(getResources().getColor(R.color.gray));
        }

        AppCompatSpinner sector_spinner = ((AppCompatSpinner) findViewById(R.id.sector_spinner));
        this.configureSpinner(sector_spinner, getResources().getStringArray(R.array.interest_list));
        sector_spinner.setSelection(0, false);
        TextView selected_View = (TextView) sector_spinner.getSelectedView();
        if (selected_View != null) {
            selected_View.setTextColor(getResources().getColor(R.color.gray));
        }


        viewModel.didChangeContactFreelance.observe(this, (value) -> {
            Bussiness bussiness = viewModel.getBussiness();
            if(value == true){
                bussiness.setContact_name(bussiness.getBussiness_name());
                bussiness.setContact_vat_number(bussiness.getBussiness_vat_number());
                bussiness.setContact_email(bussiness.getBussiness_email());
                bussiness.setContact_phone(bussiness.getBussiness_phone());
            }else{
                bussiness.setContact_name("");
                bussiness.setContact_vat_number("");
                bussiness.setContact_email("");
                bussiness.setContact_phone("");
            }

            viewModel.setBussiness(bussiness);
        });
        viewModel.didChangeContactFreelance.observe(this, (value) -> {
            Bussiness bussiness = viewModel.getBussiness();
            if(value == true){
                bussiness.setContact_name(bussiness.getBussiness_name());
                bussiness.setContact_vat_number(bussiness.getBussiness_vat_number());
                bussiness.setContact_email(bussiness.getBussiness_email());
                bussiness.setContact_phone(bussiness.getBussiness_phone());
            }else{
                bussiness.setContact_name("");
                bussiness.setContact_vat_number("");
                bussiness.setContact_email("");
                bussiness.setContact_phone("");
            }

            viewModel.setBussiness(bussiness);
        });

        viewModel.didClickRegister.observe(this, (value) -> {
            if(value == true){
                RequestParams params = new RequestParams();
                params.add("bussiness",viewModel.getBussiness().toJSON());

                O2Api.post("collaborator/register_bussiness", params, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                        String response = new String(responseBody);
                        Log.d("register_bussiness", response);

                        try{
                            JSONObject obj = new JSONObject(response);

                            if(obj.getInt("status") == 200 && !obj.getString("data").contains("errors")){
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
                        Log.d("register_bussiness", response);
                        findViewById(R.id.fail_recover_password).setVisibility(View.VISIBLE);

                    }

                });
            }
        });

        viewModel.didClickBack.observe(this, (value) -> {
            if(value == true){
                findViewById(R.id.fail_recover_password).setVisibility(View.GONE);
                viewModel.didClickBack.setValue(false);
            }
        });

        viewModel.didClickClose.observe(this, (value) -> {
            if(value == true){
                finish();
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

    private void configureSpinner(AppCompatSpinner spinner, String[] list) {

        spinner.setSelection(0, false);
        TextView selectedView = (TextView) spinner.getSelectedView();
        if (selectedView != null) {
            selectedView.setTextAppearance(getBaseContext(),R.style.spinner_style );
        }

        SpinnerAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, list) {

            @Override
            public boolean isEnabled(int position) {
                if (position == 0) {
                    // Disable the first item from Spinner
                    // First item will be used for hint
                    return false;
                } else {
                    return true;
                }
            }


            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);

                return view;
            }
        };

        if(spinner != null) {
            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    if(i == 0){
                        ((TextView) view).setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.gray));
                    }else{
                        ((TextView) adapterView.getChildAt(0)).setTextColor(Color.GREEN);
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {
                    ((TextView) adapterView.getChildAt(0)).setTextColor(Color.GRAY);
                }
            });
            spinner.setAdapter(adapter);

            spinner.setSelection(0);
        }
    }

    private void showDatePickerDialog() {
        DatePickerFragment newFragment = DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener(){
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day){
                final String selectedDate = day + "/" + (month < 9 ? "0" : "") + (month+1) + "/" + year;
                viewModel.setPartner_birthday(selectedDate);
            }
        });

        newFragment.show(getSupportFragmentManager(), "datePicker");
    }
}
