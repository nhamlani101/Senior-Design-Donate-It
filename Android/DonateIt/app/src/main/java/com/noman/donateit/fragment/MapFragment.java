package com.noman.donateit.fragment;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.noman.donateit.R;
import com.noman.donateit.data.SiteDataHolder;
import com.noman.donateit.data.SiteModel;

import static com.noman.donateit.data.UtilityFuncs.switchCatInt;


/**
 * Created by noman on 3/30/17.
 */

public class MapFragment extends android.support.v4.app.Fragment {

    MapView mapView;
    private GoogleMap googleMap;
    private Location mLocation;
    private int filter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_map, container, false);

        mapView = (MapView) rootView.findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mLocation = getArguments().getParcelable("Location");
        filter = getArguments().getInt("Filter");
        //Log.d("THE FILTER", ""+filter);


        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap mMap) {
                googleMap = mMap;
                TypedValue tv = new TypedValue();
                getContext().getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true);
                int actionBarHeight = getResources().getDimensionPixelSize(tv.resourceId);

                // For showing a move to my location button
                googleMap.setMyLocationEnabled(true);

                // For dropping a marker at a point on the Map
                googleMap.setPadding(0, 0, 0, actionBarHeight);
                for (SiteModel site : SiteDataHolder.getInstance().getData()) {
                    if (site.getCategories().contains(new Integer(filter))) {

                        String snippet = "";
                        if(site.getCategories().size() > 1) {
                            snippet = "This site accepts multiple items";
                        } else {
                            snippet = "This site accepts " + switchCatInt(filter);
                        }
                        googleMap.addMarker(new MarkerOptions().position(site.getLocation())
                                .title(site.getOrganization()).snippet(snippet));
                    }
                }

                if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                        && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    googleMap.setMyLocationEnabled(true);

                }
                LatLng noLoc = new LatLng(40.6942, -73.9866);
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(noLoc, 14));



                googleMap.setOnMyLocationButtonClickListener(new GoogleMap.OnMyLocationButtonClickListener() {
                    @Override
                    public boolean onMyLocationButtonClick() {
                        //TODO: Any custom actions
                        if (mLocation != null) {
                            LatLng cur = new LatLng(mLocation.getLatitude(), mLocation.getLongitude());
                            //drawMarker(cur, "user's location", "");
                            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(cur, 14));
                        } else {
                            LatLng noLoc = new LatLng(37.4220, 122.0841);
                            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(noLoc, 14));
                        }
                        return true;
                    }

                });



            }
        });

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }
}
