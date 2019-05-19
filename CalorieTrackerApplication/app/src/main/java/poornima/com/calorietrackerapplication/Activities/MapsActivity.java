package poornima.com.calorietrackerapplication.Activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.annotation.DrawableRes;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;


import com.mapbox.mapboxsdk.annotations.Icon;
import com.mapbox.mapboxsdk.annotations.IconFactory;
import com.mapbox.mapboxsdk.annotations.Marker;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapquest.mapping.maps.MapView;
import com.mapquest.mapping.MapQuest;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import poornima.com.calorietrackerapplication.R;
import poornima.com.calorietrackerapplication.WebServices.PublicAPIs;
import poornima.com.calorietrackerapplication.WebServices.RestWS;

public class MapsActivity extends AppCompatActivity {

    private MapboxMap mMapboxMap;
    private MapView mMapView;
    private LatLng USER_LATLONG ;
    String userAddress;
    Icon icon;
    LatLng center;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MapQuest.start(getApplicationContext());
        setContentView(R.layout.activity_maps);
        setTitle("Maps");
       // IconFactory iconFactory = IconFactory.getInstance(this.getApplicationContext());

//        icon = iconFactory.fromResource(R.drawable.ic_menu_camera);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //get address
       SharedPreferences preferences = this.getSharedPreferences("userDetails", MODE_PRIVATE);
        String address =  preferences.getString("address", "");
        String postCode = preferences.getString("postcode", "");

         userAddress = address + " " + postCode;


        getGeoCode getGeoCode = new getGeoCode();
        getGeoCode.execute(userAddress);
       // getGeoCode(userAddress);

        mMapView = (MapView) findViewById(R.id.mapquestMapView);
        mMapView.onCreate(savedInstanceState);



    }


    private class getGeoCode extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog;
        String results;
        @Override
        public void onPreExecute(){
           // progressDialog = ProgressDialog.show(this,
                  //  "ProgressDialog", "Waiting...");
            //progressDialog.show();

        }

        @Override
        protected String doInBackground(String... params) {
            results =  PublicAPIs.getGeoCode(params[0]);

            String locationParks = PublicAPIs.getParks(params[0]);


            return results;

        }

        @Override
        protected void onPostExecute(String result) {
          //  progressDialog.dismiss();
            try {
                locationResponse(result);
                getParkCodes getParkCodes = new getParkCodes();
                getParkCodes.execute(userAddress);

            } catch (JSONException e) {
                e.printStackTrace();
            }


        }
    }


    private class getParkCodes extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog;
        String results;
        @Override
        public void onPreExecute(){
            // progressDialog = ProgressDialog.show(this,
            //  "ProgressDialog", "Waiting...");
            //progressDialog.show();

        }

        @Override
        protected String doInBackground(String... params) {
            results =  PublicAPIs.getParks(params[0]);


            Log.d("RES", results);
            return results;

        }

        @Override
        protected void onPostExecute(String result) {
            //  progressDialog.dismiss();
            try {

                getParks(result);
            } catch (JSONException e) {
                e.printStackTrace();
            }


        }
    }
    //get lat long from the api

    public void locationResponse(String locationResponse) throws JSONException {

        JSONObject geoCoderObj = new JSONObject(locationResponse);
        JSONArray jsonArray=  geoCoderObj.getJSONArray("results");

        JSONObject jsonObject = jsonArray.getJSONObject(0);
        JSONArray jsonArray1=  jsonObject.getJSONArray("locations");

        JSONObject finalObj = jsonArray1.getJSONObject(0);
        JSONObject finalArray = finalObj.getJSONObject("latLng");

        String lat = finalArray.getString("lat");
        String lng = finalArray.getString("lng");
        USER_LATLONG= new LatLng(Double.parseDouble(lat), Double.parseDouble(lng));
         //Log.d("GEOO", lat);



    }



    //get parks

    public void getParks(String locationResponse) throws JSONException {

        JSONObject geoCoderObj = new JSONObject(locationResponse);
        JSONArray jsonArray=  geoCoderObj.getJSONArray("searchResults");

        int length = jsonArray.length();
        for(int i =0; i<length; i++){
            JSONObject jsonObject = jsonArray.getJSONObject(i);

            JSONObject jObjFields=  jsonObject.getJSONObject("fields");
            JSONObject mqap_geography = jObjFields.getJSONObject("mqap_geography");
            String parkName = jObjFields.getString("name");
            JSONObject latLng = mqap_geography.getJSONObject("latLng");
           // Log.d("PLEASE WORK", latLng.toString());

            String lat = latLng.getString("lat");
            String lng = latLng.getString("lng");

            LatLng parkLat = new LatLng(Double.parseDouble(lat), Double.parseDouble(lng));
            mMapView.getMapAsync(new OnMapReadyCallback() {
                @Override
                public void onMapReady(MapboxMap mapboxMap) {
                    mMapboxMap = mapboxMap;
                    mMapView.setStreetMode();
                    mMapboxMap.moveCamera(CameraUpdateFactory.newLatLngZoom(parkLat, 11));
                    //addMarker(mapboxMap);
                    MarkerOptions markerOptions = new MarkerOptions();
                    markerOptions.position(parkLat);
                    markerOptions.title(parkName);
                  //  markerOptions.snippet(userAddress);
                 //   markerOptions.setIcon(icon);
                    mapboxMap.addMarker(markerOptions);
                }
            });
        }




        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(MapboxMap mapboxMap) {
                mMapboxMap = mapboxMap;
                mMapView.setStreetMode();
                mMapboxMap.moveCamera(CameraUpdateFactory.newLatLngZoom(USER_LATLONG, 11));
                addMarker(mapboxMap);
            }
        });



        //Log.d("GEOO", lat);



    }

    private void addMarker(MapboxMap mapboxMap) {
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(USER_LATLONG);
        markerOptions.title("Home Sweet Home");
        markerOptions.snippet(userAddress);
        //markerOptions.setIcon(R.drawable.mapbox_logo_icon);
        mapboxMap.addMarker(markerOptions);
      //  markerOptions.setIcon(icon);
    }


    @Override
    public void onResume()
    { super.onResume(); mMapView.onResume(); }
    @Override
    public void onPause()
    { super.onPause(); mMapView.onPause(); }
    @Override
    protected void onDestroy()
    { super.onDestroy(); mMapView.onDestroy(); }
    @Override
    protected void onSaveInstanceState(Bundle outState)
    { super.onSaveInstanceState(outState);
        mMapView.onSaveInstanceState(outState);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {
            // finish the activity
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
