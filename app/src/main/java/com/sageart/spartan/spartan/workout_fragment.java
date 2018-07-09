package com.sageart.spartan.spartan;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class workout_fragment extends Fragment
{
    View view;
    String url = "http://gentle-garden-55289.herokuapp.com/api/workouts/";
//    private GymAdapter gymAdapter;
    private List<workout> workoutList;
    private RecyclerView.Adapter adapter;
    private DividerItemDecoration dividerItemDecoration;
    private LinearLayoutManager linearLayoutManager;
    private double latitude;
    sessions session;
    private double longitude;
    public workout_fragment(){


    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        view = inflater.inflate(R.layout.workout_activity,container,false);
        RecyclerView rv = (RecyclerView) view.findViewById(R.id.recycler_view_workouts);
        workoutList = new ArrayList<>();
        latitude = ((MainActivity)getActivity()).getLocationLatitude();
        longitude = ((MainActivity)getActivity()).getLocationLongitude();

        adapter = new workoutAdapter(getContext(), workoutList);
        linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        dividerItemDecoration = new DividerItemDecoration(rv.getContext(),linearLayoutManager.getOrientation());
        rv.setHasFixedSize(true);
            rv.setLayoutManager(linearLayoutManager);
        rv.addItemDecoration(dividerItemDecoration);
        rv.setAdapter(adapter);
        getData();


        return view;
    }

    private void getData(){
        SharedPreferences preferences = this.getActivity().getSharedPreferences("preferences",
                MODE_PRIVATE);
        String user_id = preferences.getString("user_id", "null");
        Toast.makeText(getContext(), user_id, Toast.LENGTH_LONG).show();
        url = this.url + user_id;
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for(int a = 0; a < response.length(); a++){
                    try{
                        Log.d("Response", response.toString());
                        JSONObject jsonObject = response.getJSONObject(a);
                        workout workout_ = new workout();
                        workout_.setTrainerName(jsonObject.getString("trainer_name"));
//                        gym_.getImage_source();
                        workout_.setDate(jsonObject.getString("date"));
                        workout_.setGymName(jsonObject.getString("gym_name"));
                        workout_.setSets(jsonObject.getString("sets"));
                        workout_.setType(jsonObject.getString("type"));

                        workoutList.add(workout_);
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }
                }
                adapter.notifyDataSetChanged();
            }


        },new Response.ErrorListener(){


            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Volley", error.toString());
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(jsonArrayRequest);
        jsonArrayRequest.setRetryPolicy(new DefaultRetryPolicy( 5000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }






}
