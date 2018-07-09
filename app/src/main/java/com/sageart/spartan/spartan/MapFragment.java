package com.sageart.spartan.spartan;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.LocationSource;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Array;
import java.util.HashMap;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;
import static com.bumptech.glide.gifdecoder.GifHeaderParser.TAG;

public class MapFragment extends Fragment implements OnMapReadyCallback,LocationListener {

    View view;

    private GoogleMap mMap;
    private Marker currentPositionMarker = null;
    static public int REQUEST_LOCATION = 1;
    LatLng myGPSposition;
    private LocationManager locationManager;
    SupportMapFragment mapFragment;
    private Map<String, String> params;
    BitmapDescriptor gymIcon;
    onMessageSendListener messageSendListener;



    public MapFragment() {

    }

    public interface onMessageSendListener{
        public void onMessageSend(JSONArray message);
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_maps, container, false);
        gymIcon = BitmapDescriptorFactory.fromResource(R.drawable.ic_workout);
        mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        myGPSposition = new LatLng(((MainActivity)getActivity()).getLocationLatitude(), ((MainActivity)getActivity()).getLocationLongitude());
        try {
            if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {

                getGymsNearby();
            }else{

            }
            }catch(Exception e) {
            e.printStackTrace();
        }
        mapFragment.getMapAsync(this);


        return view;
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == REQUEST_LOCATION) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                onMapReady(mMap);
            } else {

            }
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
         mMap = googleMap;
        BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory.fromResource(R.drawable.ic_person);
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            if(myGPSposition != null) {

                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myGPSposition, 9));
                googleMap.addMarker(new MarkerOptions()
                        .title("Current Location")
                        .position(myGPSposition)
                        .snippet(myGPSposition.toString())
                        .icon(bitmapDescriptor)
                );
                mMap.setMyLocationEnabled(true);

            }

            getGymsNearby();
//            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myGPSposition, 15));


        }
        else{
            Toast.makeText(getContext(), "You have to accept to enjoy all app's services!", Toast.LENGTH_LONG).show();
            if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                return ;
            }else {
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        1);
            }
            mMap.setMyLocationEnabled(true);
        }


    }

    public void getGymsNearby() {
        RequestQueue queue = Volley.newRequestQueue(getContext());
        double latitude = myGPSposition.latitude;
        double longitude = myGPSposition.longitude;
        SharedPreferences preferences = getActivity().getSharedPreferences("preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        String latitude_value = Double.toString(latitude);
        String longitude_value = Double.toString(longitude);
        editor.putString("latitude", latitude_value);
        editor.putString("longitude", longitude_value);
        editor.commit();

        Toast.makeText(getContext(), "Latitude is" + latitude + "Longitude is " + longitude, Toast.LENGTH_SHORT).show();

        StringBuilder placesUrl = new StringBuilder("https://gentle-garden-55289.herokuapp.com/api/gyms/"+latitude+"/"+longitude);
//        placesUrl.append(latitude);
//        placesUrl.append("/");
//        placesUrl.append(longitude);
        Toast.makeText(getContext(), placesUrl, Toast.LENGTH_SHORT).show();
        JsonArrayRequest request = new JsonArrayRequest(placesUrl.toString() , new Response.Listener<JSONArray>() {


            @Override
            public void onResponse(JSONArray response) {
                Log.i(TAG, "onResponse: Result= " + response.toString());
                parseLocationResult(response);
                }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "onErrorResponse: Error= " + error);
                Log.e(TAG, "onErrorResponse: Error= " + error.getMessage());
            }

        }){
            @Override
            protected Map<String, String> getParams(){
                return params;
            };
        };
        queue.add(request);
        request.setRetryPolicy(new DefaultRetryPolicy( 5000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

    }
    private void parseLocationResult(JSONArray result) {

        String id, place_id, placeName = null, reference, icon;
        Double distance;
        double latitude, longitude;
        String snippet;

        mMap.setInfoWindowAdapter(new custom_info_window_adapter(getContext()));

        try {


                for (int i = 0; i < result.length(); i++) {
                    JSONObject row = result.getJSONObject(i);

                    placeName = row.getString("gym_name");
                    latitude = row.getDouble("latitude");
                    longitude = row.getDouble("longitude");
                    distance = row.getDouble("distance");
                    snippet = "Longitude "+ longitude + " Latitude " + latitude+ "distance = "+ distance;



                    MarkerOptions markerOptions = new MarkerOptions();
                    LatLng latLng = new LatLng(latitude, longitude);
                    markerOptions.position(latLng).title(placeName)
                            .snippet(snippet)
                            .icon(gymIcon);


                    mMap.addMarker(markerOptions);
                }

//                Toast.makeText(getContext(), result.length() + " Gyms Found", Toast.LENGTH_LONG).show();




        } catch (JSONException e) {

            e.printStackTrace();
//            Log.e(TAG, "parseLocationResult: Error=" + e.getMessage());
        }
    }


    @Override
    public void onLocationChanged(Location location) {

        double latitude = location.getLatitude();
        double longitude = location.getLongitude();
        LatLng newgpsposition = new LatLng(latitude, longitude);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(newgpsposition, 10));
        mMap.addMarker(new MarkerOptions()
                .title("Current Location")
                .position(myGPSposition)
                .snippet(myGPSposition.toString())

        );
        getGymsNearby();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}