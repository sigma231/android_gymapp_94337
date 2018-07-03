<?php

namespace App\Http\Controllers;
use App\workoutModel;
use Illuminate\Support\Facades\DB;

use Illuminate\Http\Request;

class workoutcontroller extends Controller
{
    //
    public function createWorkouts(){

    }
    public function getMultipleWorkouts(Request $request){
        $user_id = $request->input('id');
        echo $user_id;
        $workout = DB::table('workout_models')
        ->leftJoin('gym_models', 'workout_models.gym_id', '=', 'gym_models.id')
        ->leftJoin('users', 'workout_models.user_id', '=', 'users.id')
        ->leftJoin('trainer_models', 'workout_models.trainer_id', '=', 'trainer_models.id')
        ->get();
        
        return $workout;

    }
    public function getSingleWorkout(){
        
    }
}
