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

import com.bumptech.glide.Glide;

import java.util.List;

public class workoutAdapter extends RecyclerView.Adapter<workoutAdapter.MyViewHolder>{

public Context mContext;
public List<workout> workoutList;


public class MyViewHolder extends RecyclerView.ViewHolder{
    public TextView name, trainer_name, date, type, sets, gym_name;


    public MyViewHolder(View view){
        super(view);
        trainer_name = (TextView) view.findViewById(R.id.trainer_name);
        date = (TextView) view.findViewById(R.id.workout_date);
        type = (TextView) view.findViewById(R.id.workout_type);
        sets = (TextView) view.findViewById(R.id.workout_sets);
        gym_name = (TextView) view.findViewById(R.id.workout_gym_name);




    }
}
    public workoutAdapter(Context mContext, List<workout> workoutList){
        this.mContext = mContext;
        this.workoutList = workoutList;

    }
    @Override
    public workoutAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.workout_row, parent, false);
        Log.d("View Holder is running", "Holder running");
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        workout workout = workoutList.get(position);
        holder.date.setText(workout.getDate());
        holder.trainer_name.setText(workout.getTrainerName());
        holder.sets.setText(workout.getSets());
        holder.type.setText(workout.getType());
        holder.gym_name.setText(workout.getGymName());



    }


    @Override
    public int getItemCount() {
        return workoutList.size();
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
