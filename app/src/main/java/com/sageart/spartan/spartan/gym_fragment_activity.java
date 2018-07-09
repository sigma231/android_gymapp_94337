package com.sageart.spartan.spartan;

import android.os.Bundle;

public class gym_fragment_activity extends MainActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null){
            getSupportFragmentManager().beginTransaction()
                    .add(android.R.id.content, new gym_fragment ()).commit();}
    }


}

