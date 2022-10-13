package com.o2.comerciosolidario.viewmodels;

import android.view.View;
import android.widget.ImageView;

import com.o2.comerciosolidario.utils.DownloadImageTask;
import com.o2.comerciosolidario.utils.MultiSpinner;
import com.o2.comerciosolidario.utils.PageViewerAdapter;

import java.util.ArrayList;

import androidx.databinding.BaseObservable;
import androidx.databinding.BindingAdapter;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

public class BaseViewModel extends BaseObservable {
    private static FragmentManager fm;

    public BaseViewModel(){}

    public void create(FragmentManager fm){ this.fm = fm; }

    @BindingAdapter("fragment_adapter")
    public static void setAdapter(ViewPager view, ArrayList<Fragment> fragments){
        PageViewerAdapter adapter = new PageViewerAdapter(fm);
        adapter.setItems(fragments);
        view.setId(View.generateViewId());
        view.setAdapter(adapter);
    }

    @BindingAdapter("pager")
    public static void bindViewPagerTabs(final com.google.android.material.tabs.TabLayout view, androidx.viewpager.widget.ViewPager pagerView){
        view.setupWithViewPager(pagerView, true);
    }

    @BindingAdapter("url")
    public static void setImageToImageView(ImageView view, String url){
        new DownloadImageTask(view).execute(url);
    }


    @BindingAdapter("selectedItems")
    public String getSelectedItems(MultiSpinner spinner, int position){
        return "Hola";
    }
}
