package com.o2.comerciosolidario.view.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.o2.comerciosolidario.R;
import com.o2.comerciosolidario.app.AppController;
import com.o2.comerciosolidario.databinding.ActivityProfileBinding;
import com.o2.comerciosolidario.model.User;
import com.o2.comerciosolidario.utils.AsyncResponse;
import com.o2.comerciosolidario.utils.Session;
import com.o2.comerciosolidario.utils.uploadToServer;
import com.o2.comerciosolidario.viewmodels.LoginViewModel;

import org.json.JSONException;
import org.json.JSONObject;

import androidx.databinding.DataBindingUtil;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class ProfileActivity extends AppController implements AsyncResponse {
    Session session;
    User user;
    final private int SELECT_IMAGE = 74;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        session = new Session(getApplicationContext());

        try {
            user = session.getUser();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        LoginViewModel viewModel = new LoginViewModel();

        ActivityProfileBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_profile);
        binding.setViewModel(viewModel);

        viewModel.username = user.getDisplayname();
        viewModel.profile_image = user.getAvatar();
        viewModel.userid = String.valueOf(user.getId());

        viewModel.didClickClose.observe(this, value -> {
            if(value == true){
                finish();
            }
        });

        viewModel.didClickProfile.observe(this, value -> {
            if(value == true){
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_PICK);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"),SELECT_IMAGE);
                viewModel.didClickProfile.setValue(false);
            }
        });

        viewModel.didClickSaveChanges.observe(this, value -> {
            upload();
        });
    }

    private void upload() {

        // Image
        Bitmap bm = ((BitmapDrawable) ((ImageView) findViewById(R.id.profile_image)).getDrawable()).getBitmap();
        ByteArrayOutputStream bao = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 90, bao);
        byte[] ba = bao.toByteArray();
        String ba1 = new String(Base64.encode(ba, Base64.DEFAULT));

        // Upload image to server
        uploadToServer uts = new uploadToServer(ba1);
        uts.delegate = this;
        uts.execute();

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SELECT_IMAGE) {
            if (resultCode == RESULT_OK) {
                if (data != null) {
                    try {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), data.getData());
                        ((ImageView) findViewById(R.id.profile_image)).setImageBitmap(bitmap);
                        findViewById(R.id.save_changes).setVisibility(View.VISIBLE);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } else if (resultCode == RESULT_CANCELED)  {
                Toast.makeText(this, "Canceled", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void processFinish(String output) {
        try{
            JSONObject obj = new JSONObject(output);
            if(obj.getInt("status") == 200){
                user.setAvatar((new JSONObject(obj.getString("data"))).getString("image_url"));
                session.setUser(user);
                findViewById(R.id.save_changes).setVisibility(View.GONE);
            }
        }catch(JSONException e){
            e.printStackTrace();
        }
    }
}
