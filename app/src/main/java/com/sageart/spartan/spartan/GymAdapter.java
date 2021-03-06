package com.sageart.spartan.spartan;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.maps.model.LatLng;

import org.w3c.dom.Text;

import java.util.List;

public class GymAdapter extends RecyclerView.Adapter<GymAdapter.MyViewHolder> {

    public Context mContext;
    public List<gym> gymList;


    public class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView name, rating, distance;
        public ImageView gymImage;
        public Button getDirections;
        public Button viewTrainers;
        LatLng location;

        public MyViewHolder(View view){
            super(view);
            name = (TextView) view.findViewById(R.id.gym_name);
//            rating = (TextView) view.findViewById(R.id.rating);
//            gymImage = (ImageView) view.findViewById(R.id.gym_image);
            distance = (TextView) view.findViewById(R.id.gym_distance);



        }
    }
    public GymAdapter(Context mContext, List<gym> gymList){
        this.mContext = mContext;
        this.gymList = gymList;

    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.gym_row, parent, false);
        Log.d("View Holder is running", "Holder running");
        return new MyViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        gym gym = gymList.get(position);
        holder.name.setText(gym.getName());
        double distance = gym.getDistance();
        holder.distance.setText(Double.toString(distance));


    }

    @Override
    public int getItemCount() {
        return gymList.size();
    }

    class MyMenuItemClickListener implements PopupMenu.OnMenuItemClickListener {

        public MyMenuItemClickListener() {
        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
//            switch (menuItem.getItemId()) {
//                case R.id.action_add_favourite:
//                    Toast.makeText(mContext, "Add to favourite", Toast.LENGTH_SHORT).show();
//                    return true;
//                case R.id.action_play_next:
//                    Toast.makeText(mContext, "Play next", Toast.LENGTH_SHORT).show();
//                    return true;
//                default:
//            }
            return false;
        }
    }

}
