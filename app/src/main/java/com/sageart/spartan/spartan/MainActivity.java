package com.sageart.spartan.spartan;

import android.Manifest;
import android.location.Location;
import android.location.LocationManager;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.android.gms.maps.CameraUpdateFactory;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private RecyclerView recyclerView;
    private GymAdapter adapter;
    private List<gym> gymList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        viewPager = (ViewPager) findViewById(R.id.viewPager_id);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_workouts);


        ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION}, MapFragment.REQUEST_LOCATION);



        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(),this);
        workout_fragment frag = new workout_fragment();
        adapter.AddFragment(new workout_fragment(), "Workouts");
        adapter.AddFragment(new MapFragment(), "Location");
        adapter.AddFragment(new workout_fragment(), "workouts");



        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).setIcon(R.drawable.ic_workout);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_place);
        tabLayout.getTabAt(2).setIcon(R.drawable.ic_timeline);




    }

    private void prepareGyms(){

    }
}
