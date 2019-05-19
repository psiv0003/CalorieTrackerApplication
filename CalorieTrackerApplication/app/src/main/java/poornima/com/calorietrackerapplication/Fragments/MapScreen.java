package poornima.com.calorietrackerapplication.Fragments;

import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapquest.mapping.maps.MapView;
import com.mapquest.mapping.MapQuest;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;


import poornima.com.calorietrackerapplication.R;

import static com.mapbox.mapboxsdk.Mapbox.getApplicationContext;

public class MapScreen extends Fragment {
    View view;
    private MapboxMap mMapboxMap;
    private MapView mMapView;
    private final LatLng SAN_FRAN = new LatLng(37.7749, -122.4194);

    private final static String KEY_LOCATION = "location";

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        MapQuest.start(getContext());
        view = inflater.inflate(R.layout.map_fragment, container, false);

        mMapView = (MapView) view.findViewById(R.id.mapquestMapView);
        mMapView.onCreate(savedInstanceState);
        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(MapboxMap mapboxMap) {
                mMapboxMap = mapboxMap;
                mMapView.setStreetMode();
                mMapboxMap.moveCamera(CameraUpdateFactory.newLatLngZoom(SAN_FRAN, 11));
                addMarker(mapboxMap);
            }
        });


        return view;
    }

    private void addMarker(MapboxMap mapboxMap) {
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(SAN_FRAN);
        markerOptions.title("MapQuest");
        markerOptions.snippet("Welcome to San Francisco!");
        mapboxMap.addMarker(markerOptions);
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mMapView.onSaveInstanceState(outState);
    }
}