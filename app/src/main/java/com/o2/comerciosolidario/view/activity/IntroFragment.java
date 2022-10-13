package com.o2.comerciosolidario.view.activity;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.o2.comerciosolidario.R;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class IntroFragment extends Fragment {


    public static IntroFragment newInstance(int layout_id) {
        IntroFragment frag = new IntroFragment();

        Bundle args = new Bundle();
        args.putString("layout_id", String.valueOf(layout_id));
        frag.setArguments(args);

        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Bundle args = getArguments();
        String string_layout_id = args.getString("layout_id");
        int layout_id;

        if(string_layout_id != null){
            layout_id = Integer.parseInt(string_layout_id);
        }else{
            layout_id = R.layout.intro_page_1_fragment;
        }
        View root = inflater.inflate(layout_id, container, false);

        return root;
    }
}
