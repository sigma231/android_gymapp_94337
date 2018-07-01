package com.sageart.spartan.spartan;

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

public class workout_fragment extends Fragment
{
    View view;
    String url = "http://10.0.2.2:8000/api/workouts/1";
    private GymAdapter gymAdapter;
    private List<gym> gymList;
    private RecyclerView.Adapter adapter;
    private DividerItemDecoration dividerItemDecoration;
    private LinearLayoutManager linearLayoutManager;
    public workout_fragment(){

    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        view = inflater.inflate(R.layout.workout_activity,container,false);
        RecyclerView rv = (RecyclerView) view.findViewById(R.id.recycler_view_workouts);
        gymList = new ArrayList<>();

        adapter = new GymAdapter(getContext(), gymList);
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
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for(int a = 0; a < response.length(); a++){
                    try{
                        Log.d("Response", response.toString());
                        JSONObject jsonObject = response.getJSONObject(a);
                        gym gym_ = new gym();
                        gym_.setName(jsonObject.getString("gym_id"));
//                        gym_.getImage_source();
                        double latitude = jsonObject.getDouble("trainer_id");
                        double longitude = jsonObject.getDouble("user_id");
                        LatLng location = new LatLng(latitude, longitude);
                        gym_.setLocation(location);
                        gymList.add(gym_);
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
        jsonArrayRequest.setRetryPolicy(new DefaultRetryPolicy( 10000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }






}
