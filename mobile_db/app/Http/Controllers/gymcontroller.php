<?php

namespace App\Http\Controllers;
use Illuminate\Support\Facades\DB;
use Illuminate\Http\Request;
use App\gymModel;

class gymcontroller extends Controller
{
    //
    public function getGyms($latitude, $longitude){
        $gyms = DB::select('SELECT id,gym_name,latitude,longitude, (6372.797*acos(cos(radians('.$latitude.'))*cos(radians(gym_models.latitude))*cos(radians(gym_models.longitude)-radians('.$longitude.'))+sin(radians('.$latitude.'))*sin(radians(gym_models.latitude)))) AS distance FROM gym_models');
        $within_distance_gyms = [];
        foreach($gyms as $gym){
            if($gym->distance <= 2000){
                array_push($within_distance_gyms, $gym);
            }

        }
        return $within_distance_gyms;
        
    }
    public function getSingleGym($id){
        $gym = gymModel::all()->where('id', $id);
        return $gym;
        
    }
    public function getTrainers($id){
        
        $trainers = DB::table('gym_models')
        ->leftJoin('trainer_models', 'gym_models.id', '=', 'trainer_models.gym_id')
        ->where('gym_models.id', '=', $id)
        ->get();
        return $trainers;
    }
        
}
