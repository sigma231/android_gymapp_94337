package com.sageart.spartan.spartan;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;
import static com.bumptech.glide.gifdecoder.GifHeaderParser.TAG;

public class gym_fragment extends Fragment {
    View view;
    String url = "http://gentle-garden-55289.herokuapp.com/api/workouts/1";
    private List<gym> gymList;
    private RecyclerView.Adapter adapter;
    private DividerItemDecoration dividerItemDecoration;
    private LinearLayoutManager linearLayoutManager;
    private double latitude;
    private double longitude;

    public gym_fragment() {

    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        view = inflater.inflate(R.layout.gyms_activity,container,false);
        RecyclerView rv = (RecyclerView) view.findViewById(R.id.recycler_view_gyms);
        gymList = new ArrayList<>();
        latitude = ((MainActivity)getActivity()).getLocationLatitude();
        longitude = ((MainActivity)getActivity()).getLocationLongitude();
        adapter = new GymAdapter(getContext(), gymList);
        linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        dividerItemDecoration = new DividerItemDecoration(rv.getContext(),linearLayoutManager.getOrientation());
        rv.setHasFixedSize(true);
        rv.setLayoutManager(linearLayoutManager);
        rv.addItemDecoration(dividerItemDecoration);
        rv.setAdapter(adapter);
        getGymsNearby();

        return view;
    }

    public void getGymsNearby() {
        RequestQueue queue = Volley.newRequestQueue(getContext());
        SharedPreferences preferences = getActivity().getSharedPreferences("preferences",
                MODE_PRIVATE);
        String latitude = preferences.getString("latitude", "0");
        String longitude = preferences.getString("longitude", "0");
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

        };
        queue.add(request);
        request.setRetryPolicy(new DefaultRetryPolicy( 5000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

    }
    private void parseLocationResult(JSONArray result) {

        String id, place_id, placeName = null, reference, icon;
        Double distance;
        double latitude, longitude;
        String snippet;

        try {


            for (int i = 0; i < result.length(); i++) {
                JSONObject row = result.getJSONObject(i);
                gym gym_ = new gym();

                gym_.setName(row.getString("gym_name"));
                gym_.setLocation(new LatLng(row.getDouble("latitude"), row.getDouble("longitude")));
                gym_.setImage_source("http://placehold.it/100x100");
                gym_.setDistance(row.getDouble("distance"));

                longitude = row.getDouble("longitude");
                distance = row.getDouble("distance");

                gymList.add(gym_);




            }

//                Toast.makeText(getContext(), result.length() + " Gyms Found", Toast.LENGTH_LONG).show();




        } catch (JSONException e) {

            e.printStackTrace();
//            Log.e(TAG, "parseLocationResult: Error=" + e.getMessage());
        }
        adapter.notifyDataSetChanged();
    }


}
