package com.o2.comerciosolidario.utils;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;

import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.NameValuePair;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.entity.UrlEncodedFormEntity;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;
import cz.msebera.android.httpclient.message.BasicNameValuePair;
import cz.msebera.android.httpclient.util.EntityUtils;

public class uploadToServer extends AsyncTask<Void, Void, String> {
    final private String URL = "https://generacion-o2.org/api/collaborator/change_profile";
    private String ba1;
    public AsyncResponse delegate = null;

    public uploadToServer(String ba1) {
        this.ba1 = ba1;
    }

    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(Void... params) {

        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("base64", ba1));
        nameValuePairs.add(new BasicNameValuePair("ImageName", System.currentTimeMillis() + ".jpg"));
        nameValuePairs.add(new BasicNameValuePair("user_id", "50"));
        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(URL);
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            HttpResponse response = httpclient.execute(httppost);
            String st = EntityUtils.toString(response.getEntity());
            //Log.v("log_tag", "In the try Loop" + st);
            return st;
        } catch (Exception e) {
            Log.v("log_tag", "Error in http connection " + e.toString());
            return "error";
        }
    }

    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        delegate.processFinish(result);
    }
}
