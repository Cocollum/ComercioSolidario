package com.o2.comerciosolidario.view.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.o2.comerciosolidario.R;
import com.o2.comerciosolidario.databinding.ClubFragmentBinding;
import com.o2.comerciosolidario.viewmodels.HomeViewModel;

public class ClubFragment extends Fragment {
    private static final String TEXT = "text";
    public HomeViewModel viewModel;

    public static ClubFragment newInstance(String text) {
        ClubFragment frag = new ClubFragment();

        Bundle args = new Bundle();
        args.putString(TEXT, text);
        frag.setArguments(args);

        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = new HomeViewModel();

        ClubFragmentBinding binding = DataBindingUtil.inflate(inflater, R.layout.club_fragment, container, false);
        binding.setViewModel(viewModel);
        View layout = binding.getRoot();

        viewModel.didClickRegister.observe(getActivity(), (value) -> {
            if(value == true){
                viewModel.didClickRegister.postValue(false);

                //Uri uri = Uri.parse("https://generacion-o2.org/unete-al-club/sumate/");
                Intent activity = new Intent(getContext(), RegisterClubActivity.class);


                startActivity(activity);
            }
        });

        viewModel.didClickRegisterBussiness.observe(getActivity(), (value) -> {
            if(value == true){
                viewModel.didClickRegisterBussiness.postValue(false);
                Intent activity = new Intent(getContext(), RegisterBussinessActivity.class);


                startActivity(activity);
            }
        });

        return layout;
    }
}
