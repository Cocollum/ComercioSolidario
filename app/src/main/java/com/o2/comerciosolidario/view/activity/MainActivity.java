package com.o2.comerciosolidario.view.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.joanzapata.iconify.IconDrawable;
import com.joanzapata.iconify.Iconify;
import com.joanzapata.iconify.fonts.FontAwesomeIcons;
import com.joanzapata.iconify.fonts.FontAwesomeModule;
import com.o2.comerciosolidario.BuildConfig;
import com.o2.comerciosolidario.R;
import com.o2.comerciosolidario.app.AppController;
import com.o2.comerciosolidario.databinding.ActivityMainBinding;
import com.o2.comerciosolidario.model.User;
import com.o2.comerciosolidario.utils.Session;
import com.o2.comerciosolidario.viewmodels.HomeViewModel;

import org.json.JSONException;

public class MainActivity extends AppController implements NavigationView.OnNavigationItemSelectedListener, DrawerLayout.DrawerListener{
    Session session;
    private DrawerLayout drawerLayout;
    NavigationView navigationView;
    HomeViewModel viewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Iconify
                .with(new FontAwesomeModule());

        viewModel = new HomeViewModel();

        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        binding.setViewModel(viewModel);

        session = new Session(getApplicationContext());

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("");
        /*setSupportActionBar(toolbar);*/
        //toolbar.setTitleTextColor(getResources().getColor(R.color.gray));

        drawerLayout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);

        configureMenuIcons();

        MenuItem menuItem = navigationView.getMenu().getItem(0);
        onNavigationItemSelected(menuItem);
        menuItem.setChecked(true);

        drawerLayout.addDrawerListener(this);

        TextView version = (TextView) navigationView.findViewById(R.id.version_text);
        version.setText("Version: " + BuildConfig.VERSION_NAME);

        View header = navigationView.getHeaderView(0);
        header.findViewById(R.id.header_title).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Fragment fragment = new HomeContentFragment();
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.home_content, fragment).commit();

                drawerLayout.closeDrawer(GravityCompat.START);

            }
        });

        viewModel.didClickRRSS.observe(this, (value) -> {
            if(value != ""){
                Intent intent =new Intent(Intent.ACTION_VIEW, Uri.parse(value));
                startActivity(intent);
            }
        });

        viewModel.didClickIntro.observe(this, (value) -> {
            if(value == true){
                Intent intent = new Intent(getApplicationContext(), IntroActivity.class);
                startActivity(intent);
            }
        });
    }

    private void configureMenuIcons(){

        navigationView.getMenu().findItem(R.id.dashboard)
                .setIcon(new IconDrawable(this, FontAwesomeIcons.fa_home)
                .colorRes(R.color.white)
                .actionBarSize());
        navigationView.getMenu().findItem(R.id.login)
                .setIcon(new IconDrawable(this, FontAwesomeIcons.fa_star)
                .colorRes(R.color.white)
                .actionBarSize());
        navigationView.getMenu().findItem(R.id.club)
                .setIcon(new IconDrawable(this, FontAwesomeIcons.fa_user)
                .colorRes(R.color.white)
                .actionBarSize());
        /*navigationView.getMenu().findItem(R.id.register)
                .setIcon(new IconDrawable(this, FontAwesomeIcons.fa_map_marker)
                .colorRes(R.color.white)
                .actionBarSize());
        navigationView.getMenu().findItem(R.id.how_to)
                .setIcon(new IconDrawable(this, FontAwesomeIcons.fa_user)
                .colorRes(R.color.white)
                .actionBarSize());*/
        navigationView.getMenu().findItem(R.id.contact)
                .setIcon(new IconDrawable(this, FontAwesomeIcons.fa_envelope)
                .colorRes(R.color.white)
                .actionBarSize());
        navigationView.getMenu().findItem(R.id.about_us)
                .setIcon(new IconDrawable(this, FontAwesomeIcons.fa_info_circle)
                .colorRes(R.color.white)
                .actionBarSize());
        navigationView.getMenu().findItem(R.id.logout)
                .setIcon(new IconDrawable(this, FontAwesomeIcons.fa_sign_out)
                .colorRes(R.color.white)
                .actionBarSize());
    }

    public int checkNavigationMenuItem(){
        Menu menu = navigationView.getMenu();
        for(int i = 0; i < menu.size(); i++){
            if(menu.getItem(i).isChecked()){
                return i;
            }
        }
        return -1;
    }

    @Override
    public void onBackPressed(){
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else{
            if(checkNavigationMenuItem() != 0){
                navigationView.getMenu().getItem(0).setChecked(true);
                Fragment fragment = new HomeContentFragment();
                ((HomeContentFragment) fragment).viewModel = viewModel;
                getSupportFragmentManager().beginTransaction().replace(R.id.home_content,fragment).commit();
            }else {
                super.onBackPressed();
            }
        }
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem){
        int title;
        Intent browser = null;
        Fragment fragment = null;
        Uri uri = null;

        for(int i = 0; i < navigationView.getMenu().size(); i++){
            navigationView.getMenu().getItem(i).setChecked(false);
        }

        menuItem.setChecked(true);

        switch(menuItem.getItemId()){
            case R.id.dashboard:
                fragment = HomeContentFragment.newInstance("");
                ((HomeContentFragment) fragment).viewModel = viewModel;
                break;
            case R.id.login:
                browser = new Intent(getApplicationContext(), LoginActivity.class);
                try {
                    User user = session.getUser();
                    if(user != null){
                        browser = new Intent(getApplicationContext(), ProfileActivity.class);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.club:
                fragment = ClubFragment.newInstance("");
                ((ClubFragment) fragment).viewModel = viewModel;
                break;
            /*case R.id.register:
                uri = Uri.parse("https://generacion-o2.org");
                browser = new Intent(Intent.ACTION_VIEW, uri);
                break;
            case R.id.how_to:
                uri = Uri.parse("https://generacion-o2.org/sumate/");
                browser = new Intent(Intent.ACTION_VIEW, uri);
                break;*/
            case R.id.contact:
                uri = Uri.parse("https://generacion-o2.org#footer");
                browser = new Intent(Intent.ACTION_VIEW, uri);
                break;
            case R.id.about_us:
                uri = Uri.parse("https://generacion-o2.org/sobre-nosotros/");
                browser = new Intent(Intent.ACTION_VIEW, uri);
                break;
            case R.id.logout:
                session.removeUser();
                Toast logout_ok = Toast.makeText(getApplicationContext(), "Se ha desconectado correctamente", Toast.LENGTH_SHORT);
                logout_ok.show();
                break;
            default:
                throw new IllegalArgumentException("Menu option not implemented!");
        }

        if(fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.home_content, fragment).commit();

            drawerLayout.closeDrawer(GravityCompat.START);
        }

        if(browser != null){
            startActivity(browser);
        }

        return true;
    }

    @Override
    public void onDrawerSlide(@NonNull View view, float v){

    }

    @Override
    public void onDrawerOpened(@NonNull View view){
    }

    @Override
    public void onDrawerClosed(@NonNull View view){
    }

    @Override
    public void onDrawerStateChanged(int i) {

    }
}