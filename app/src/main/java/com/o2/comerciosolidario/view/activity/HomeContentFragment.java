package com.o2.comerciosolidario.view.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Camera;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TableLayout;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.navigation.NavigationView;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.o2.comerciosolidario.R;
import com.o2.comerciosolidario.databinding.HomeContentFragmentBinding;
import com.o2.comerciosolidario.model.Collaborator;
import com.o2.comerciosolidario.utils.O2Api;
import com.o2.comerciosolidario.utils.Session;
import com.o2.comerciosolidario.viewmodels.HomeViewModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import cz.msebera.android.httpclient.Header;

public class HomeContentFragment extends Fragment {
    private static final String TEXT = "text";
    NavigationView navigationView;
    Context context = getContext();
    private GoogleMap mMap;
    private Session session;

    private LocationManager locManager;
    private LocationListener locListener;
    private Location loc;

    public static final int INTRO_REQUEST_CODE = 1;
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    public HomeViewModel viewModel;

    public static HomeContentFragment newInstance(String text) {
        HomeContentFragment frag = new HomeContentFragment();

        Bundle args = new Bundle();
        args.putString(TEXT, text);
        frag.setArguments(args);

        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setRetainInstance(true);

        session = new Session(getContext());

        HomeContentFragmentBinding binding = DataBindingUtil.inflate(inflater, R.layout.home_content_fragment, container, false);
        View layout = binding.getRoot();

        context = getContext();

        binding.setViewModel(viewModel);

        navigationView = getActivity().findViewById(R.id.navigation_view);

        if(session.getJumpIntro() == false) {
            Intent intro_intent = new Intent(getContext(), IntroActivity.class);
            startActivityForResult(intro_intent, INTRO_REQUEST_CODE);
        }

        requestLocation();

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.mapView);
        if(mapFragment != null) {
            mapFragment.getMapAsync(new OnMapReadyCallback() {
                @Override
                public void onMapReady(GoogleMap googleMap) {
                    loadMap(googleMap);
                    mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                        @Override
                        public boolean onMarkerClick(Marker marker) {
                            Collaborator collaborator = (Collaborator) marker.getTag();
                            viewModel.onClickMarker(collaborator);
                            return false;
                        }
                    });
                    mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                        @Override
                        public void onMapClick(LatLng latLng) {
                            viewModel.onClickMap();
                        }
                    });
                }
            });
        }

        viewModel.didChangeCheckAllCheckbox.observe(this, (value) -> {
            TableLayout table = getActivity().findViewById(R.id.filtered_table);
            ArrayList<String> items = new ArrayList<>();

            if(value == true){
                for(int i = 0; i < table.getChildCount(); i++){
                    CheckBox checkbox = (CheckBox) table.getChildAt(i);

                    items.add(checkbox.getText().toString());
                }
            }
            viewModel.filteredItems.postValue(items);
        });

        viewModel.filteredItems.observe(getViewLifecycleOwner(), (value) -> {
            if(value != null){
                TableLayout table = getActivity().findViewById(R.id.filtered_table);

                if(value.size() < table.getChildCount()){
                    ((CheckBox) getActivity().findViewById(R.id.check_all)).setChecked(false);
                }

                for(int i = 0; i < table.getChildCount(); i++){
                    CheckBox checkbox = (CheckBox) table.getChildAt(i);

                    if(value.stream().anyMatch(checkbox.getText().toString()::equals)){
                        checkbox.setChecked(true);
                    }else{
                        checkbox.setChecked(false);
                    }
                }
            }
        });

        viewModel.didClickViewOffer.observe(getViewLifecycleOwner(), (value) -> {
            if(value != null){

                viewModel.didClickViewOffer.setValue(null);
                Intent intent = new Intent(getContext(), ViewOfferActivity.class);
                intent.putExtra("offer",value.toJSON());
                startActivity(intent);
                viewModel.onClickMap();
            }
        });

        return layout;
    }

    private void requestLocation(){
        locManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSIONS_REQUEST_LOCATION);
        }else {
            loc = locManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        }
    }

    private void getNearBussiness(LatLng coords){
        RequestParams params = new RequestParams();
        params.add("lat",String.valueOf(coords.latitude));
        params.add("long",String.valueOf(coords.longitude));

        params.add("filters", viewModel.filteredItems.getValue().stream().collect(Collectors.joining(",")));
        params.add("lgtbi_friendly", viewModel.getLgtbi_friendly_checked() ? "1" : "0");
        params.add("lgtbi_plus", viewModel.getLgtbi_plus_checked() ? "1" : "0");

        mMap.clear();

        O2Api.post("collaborator/get_near_collaborators", params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String response = new String(responseBody);
                Log.d("get_near_collaborators", response);
                try{
                    JSONObject obj = new JSONObject(response);
                    if(obj.getString("status").equals("200")){
                        JSONArray array = obj.getJSONArray("data");
                        for(int i = 0; i < array.length(); i++){
                            Collaborator collaborator = new Collaborator(array.getString(i));
                            createMarker(collaborator);
                        }
                    }
                }catch (JSONException e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody,
                                  Throwable error) {
                error.printStackTrace();
                if(responseBody != null) {
                    String response = new String(responseBody);
                    Log.e("get_near_collaborators", response);
                }
            }
        });
    }

    private void createMarker(Collaborator collaborator){
        LatLng marker_position = new LatLng(collaborator.getCoords().getLat(), collaborator.getCoords().getLng());
        MarkerOptions marker = new MarkerOptions().position(marker_position);

        BitmapDrawable bitmapdraw = (BitmapDrawable)getResources().getDrawable(R.drawable.round_pushpin_1f4cd);
        Bitmap b = bitmapdraw.getBitmap();
        Bitmap smallMarker = Bitmap.createScaledBitmap(b, 120, 120, false);

        marker.icon(BitmapDescriptorFactory.fromBitmap(smallMarker));

        Marker mark = mMap.addMarker(marker);

        mark.setTag(collaborator);
    }

    private void loadMap(GoogleMap googleMap){
        mMap = googleMap;
        LatLng coords;
        if(loc != null) {
            coords = new LatLng(loc.getLatitude(), loc.getLongitude());
        }else{
            coords = new LatLng(41.38641, 2.1621677);
        }

        CameraPosition cameraPosition = CameraPosition.builder().target(coords).zoom(14.0f).build();

        mMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

        viewModel.filteredItems.observe(getViewLifecycleOwner(), (value) -> {
            if(value != null) {
                getNearBussiness(coords);
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // location-related task you need to do.
                    if (ContextCompat.checkSelfPermission(context,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {
                        requestLocation();
                    }
                }
                return;
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == INTRO_REQUEST_CODE && resultCode == getActivity().RESULT_OK){
            session.setJumpIntro(true);
        }
    }
}