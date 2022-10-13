package com.o2.comerciosolidario.view.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.o2.comerciosolidario.R;
import com.o2.comerciosolidario.utils.DownloadImageTask;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class ImageFragment extends Fragment {
    ImageView imageView;
    public static ImageFragment newInstance(String url) {
        ImageFragment frag = new ImageFragment();

        Bundle args = new Bundle();
        args.putString("url", url);
        frag.setArguments(args);

        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.image_fragment, container, false);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        imageView = (ImageView) root.findViewById(R.id.image_fragment_view);

        Bundle args = getArguments();
        String url_string = args.getString("url");
        if(url_string != null){
            try {
                new DownloadImageTask(imageView).execute(url_string);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return root;
    }
}
