package com.sageart.spartan.spartan;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

public class custom_info_window_adapter implements GoogleMap.InfoWindowAdapter{

    private final View mWindow;
    private Context mContext;

    public custom_info_window_adapter(Context context) {
        mContext = context;
        mWindow = LayoutInflater.from(context).inflate(R.layout.custom_info_window, null);

    }

    private void renderWindow(Marker marker, View view){
        String title = marker.getTitle();
        TextView tvTitle = (TextView) view.findViewById(R.id.title);

        String snippet = marker.getSnippet();
        TextView tvSnippet = (TextView) view.findViewById(R.id.snippet);

        if(!snippet.equals("")){
            tvSnippet.setText(snippet);
        }
    }
    @Override
    public View getInfoWindow(Marker marker) {
        renderWindow(marker, mWindow);
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        renderWindow(marker, mWindow);
        return null;
    }
}
