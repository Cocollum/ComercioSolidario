package com.o2.comerciosolidario.view.activity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
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
import androidx.databinding.DataBindingUtil;

import com.o2.comerciosolidario.app.AppController;

import com.o2.comerciosolidario.databinding.ActivityRegisterClubBinding;
import com.o2.comerciosolidario.model.User;
import com.o2.comerciosolidario.utils.DatePickerFragment;
import com.o2.comerciosolidario.utils.MultiSpinner;
import com.o2.comerciosolidario.utils.Session;
import com.o2.comerciosolidario.utils.O2Api;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.o2.comerciosolidario.R;

import com.o2.comerciosolidario.utils.WidgetDialogActivity;
import com.o2.comerciosolidario.viewmodels.RegisterViewModel;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Collections;

import cz.msebera.android.httpclient.Header;

public class RegisterClubActivity extends AppController {
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
        this.configureAppCompatSpinner(genreSpinner, getResources().getStringArray(R.array.genre_list));
        genreSpinner.setSelection(0,false);
        TextView selected_View = (TextView) genreSpinner.getSelectedView();
        if (selected_View != null) {
            selected_View.setTextColor(getResources().getColor(R.color.gray));
        }


        TextView interests = (TextView) findViewById(R.id.interest);
        this.configureInterestsList(interests, getResources().getStringArray(R.array.interest_list));

        viewModel.didClickRegister.observe(this, (value) -> {
            if (value == true) {
                RequestParams params = new RequestParams();
                params.add("name", viewModel.getName());
                params.add("lastname", viewModel.getLastname());
                params.add("email", viewModel.getEmail());
                params.add("vat_number", viewModel.getVat_number());
                params.add("phone", viewModel.getPhone());
                params.add("birthday", viewModel.getBirthday());
                params.add("genre", viewModel.getGenre_list()[viewModel.getGenre()]);
                params.add("interests", viewModel.getInterest());
                params.add("lgtbi", viewModel.getLgtbi() ? "SI" : "NO");
                params.add("lgtbi_plus", viewModel.getLgtbi_plus() ? "SI" : "NO");

                O2Api.post("collaborator/register_club", params, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                        String response = new String(responseBody);
                        Log.d("register_club", response);

                        try {
                            JSONObject obj = new JSONObject(response);

                            if (obj.getInt("status") == 200 && !obj.getString("data").contains("errors")) {
                                User user = new User(obj.getString("data"));
                                session.setUser(user);

                                findViewById(R.id.ok_recover_password).setVisibility(View.VISIBLE);
                            } else {
                                findViewById(R.id.fail_recover_password).setVisibility(View.VISIBLE);
                            }

                        } catch (JSONException e) {
                            findViewById(R.id.fail_recover_password).setVisibility(View.VISIBLE);
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody,
                                          Throwable error) {
                        String response = new String(responseBody);
                        Log.d("register_club", response);
                        findViewById(R.id.fail_recover_password).setVisibility(View.VISIBLE);

                    }
                });
            }
        });

        viewModel.didClickClose.observe(this, (value) -> {
            if (value == true) {
                finish();
            }
        });
        viewModel.didClickBack.observe(this, (value) -> {
            if (value == true) {
                findViewById(R.id.fail_recover_password).setVisibility(View.GONE);
                viewModel.didClickBack.setValue(false);
            }
        });

        viewModel.didClickBirthday.observe(this, (value) -> {
            if (value == true) {
                showDatePickerDialog();
            }
        });
        viewModel.didClickPrivacyPolicy.observe(this, (value) -> {
            if (value == true) {
                Intent modalIntent = new Intent(this, WidgetDialogActivity.class);
                startActivity(modalIntent);
            }
        });
    }

    private void configureInterestsList(TextView interests, String[] list) {

        boolean[] selectedInteres;
        ArrayList<Integer> selected = new ArrayList<>();

        selectedInteres = new boolean[list.length];

            interests.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterClubActivity.this);
                    builder.setTitle("Intereses");
                    builder.setCancelable(false);

                    builder.setMultiChoiceItems(list, selectedInteres, new DialogInterface.OnMultiChoiceClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i, boolean b) {
                            if (b) {
                                selected.add(i);
                                Collections.sort(selected);
                            } else {
                                selected.remove(Integer.valueOf(i));
                            }
                        }
                    });
                    
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            StringBuilder stringBuilder = new StringBuilder();
                            for (int j = 0; j < selected.size(); j++) {
                                stringBuilder.append(list[selected.get(j)]);
                                if (j != selected.size() - 1) {
                                    stringBuilder.append(", ");
                                }
                            }
                            viewModel.setInterest(stringBuilder.toString());

                        }
                    });

                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });
                    builder.setNeutralButton("Clear All", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            for (int j = 0; j < selectedInteres.length; j++) {
                                selected.clear();
                                interests.setText("");
                            }
                        }
                    });
                    builder.show();
                }
            });
        }

    private void configureAppCompatSpinner(AppCompatSpinner spinner, String[] list) {

        spinner.setSelection(0, false);
        TextView selectedView = (TextView) spinner.getSelectedView();
        if (selectedView != null) {
            selectedView.setTextAppearance(getBaseContext(),R.style.spinner_style);
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
                     adapterView.setSelection(0);
                    ((TextView) adapterView.getChildAt(0)).setTextColor(Color.GRAY);
                }
            });
            spinner.setAdapter(adapter);
            spinner.setSelection(0);
        }
    }

    private void showDatePickerDialog() {
        DatePickerFragment newFragment = DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                final String selectedDate = day + "/" + (month < 9 ? "0" : "") + (month + 1) + "/" + year;
                viewModel.setBirthday(selectedDate);
            }
        });

        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

}
