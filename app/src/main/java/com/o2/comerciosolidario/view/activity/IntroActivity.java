package com.o2.comerciosolidario.view.activity;

import android.os.Bundle;

import com.o2.comerciosolidario.R;
import com.o2.comerciosolidario.app.AppController;
import com.o2.comerciosolidario.databinding.ActivityIntroBinding;
import com.o2.comerciosolidario.viewmodels.HomeViewModel;

import java.util.ArrayList;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

public class IntroActivity extends AppController {

    HomeViewModel viewModel;

    ArrayList<Fragment> fragments = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        fragments.add(IntroFragment.newInstance(R.layout.intro_page_1_fragment));
        fragments.add(IntroFragment.newInstance(R.layout.intro_page_2_fragment));
        fragments.add(IntroFragment.newInstance(R.layout.intro_page_3_fragment));

        viewModel = new HomeViewModel(getSupportFragmentManager());

        ActivityIntroBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_intro);

        binding.setViewModel(viewModel);

        viewModel.setFragments(fragments);

        viewModel.didClickJumpIntro.observe(this,value -> {
            if(value == true){
                setResult(RESULT_OK);
                finish();
            }
        });
    }
}
