package com.sageart.spartan.spartan;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.List;

public class workout_fragment extends Fragment
{
    View view;

    private GymAdapter gymAdapter;
    private List<gym> gymList;
    String[] strings = {"1", "2", "3", "4", "5", "6", "7"};
    public workout_fragment(){

    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        view = inflater.inflate(R.layout.workout_activity,container,false);
        RecyclerView rv = (RecyclerView) view.findViewById(R.id.recycler_view_workouts);
        rv.setLayoutManager(new LinearLayoutManager(getActivity().getBaseContext()));
        rv.setAdapter(new GymAdapter(getActivity(), gymList));

        return view;
    }






}
