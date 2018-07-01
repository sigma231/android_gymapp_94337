package com.sageart.spartan.spartan;

import android.Manifest;
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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

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




    public MapFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_maps, container, false);
        mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        getGymsNearby();

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
        LocationListener mLocationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                double latitude = location.getLatitude();
                double longitude = location.getLongitude();
                LatLng latLng = new LatLng(latitude,longitude);
                myGPSposition = new LatLng(latitude,longitude);
                Log.d("Position", latitude+ " Longitude =" + longitude);
                mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                mMap.animateCamera(CameraUpdateFactory.zoomTo(20));



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
        };


        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            if(myGPSposition != null) {
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myGPSposition, 15));
                googleMap.addMarker(new MarkerOptions()
                        .title("Current Location")
                        .position(myGPSposition)
                );

            }
//            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myGPSposition, 15));
            mMap.setMyLocationEnabled(true);

        }
        else{
            Toast.makeText(getContext(), "You have to accept to enjoy all app's services!", Toast.LENGTH_LONG).show();
            if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                return ;
            }else {

            }
            mMap.setMyLocationEnabled(true);
        }


    }
    @Override
    public void onLocationChanged(Location location) {
        double latitude = location.getLatitude();
        double longitude = location.getLongitude();
        LatLng latLng = new LatLng(latitude, longitude);

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
    public void getGymsNearby() {
        RequestQueue queue = Volley.newRequestQueue(getContext());
        StringBuilder googlePlacesUrl = new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");

        googlePlacesUrl.append("location=-33.8670522,151.1957362");
        googlePlacesUrl.append("&radius=50000");
//        googlePlacesUrl.append("&rankby=distance");
        googlePlacesUrl.append("&type=gym");
//        googlePlacesUrl.append("&sensor=true");
        googlePlacesUrl.append("&key=AIzaSyCV01W4FqtRj7OAC_UFjxOdCkZbT5M98kM");
        Toast.makeText(getContext(), googlePlacesUrl.toString(), Toast.LENGTH_SHORT).show();
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, googlePlacesUrl.toString(),null,  new Response.Listener<JSONObject>() {


            @Override
            public void onResponse(JSONObject response) {
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
    }
    private void parseLocationResult(JSONObject result) {

        String id, place_id, placeName = null, reference, icon, vicinity = null;
        double latitude, longitude;

        try {

            JSONArray jsonArray = result.getJSONArray("results");

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject place = jsonArray.getJSONObject(i);


                    latitude = place.getJSONObject("geometry").getJSONObject("location")
                            .getDouble("lat");
                    longitude = place.getJSONObject("geometry").getJSONObject("location")
                            .getDouble("lng");


                    MarkerOptions markerOptions = new MarkerOptions();
                    LatLng latLng = new LatLng(latitude, longitude);
                    markerOptions.position(latLng);


                    mMap.addMarker(markerOptions);
                }

                Toast.makeText(getContext(), jsonArray.length() + " Gyms Found",
                        Toast.LENGTH_LONG).show();




        } catch (JSONException e) {

            e.printStackTrace();
//            Log.e(TAG, "parseLocationResult: Error=" + e.getMessage());
        }
    }


}