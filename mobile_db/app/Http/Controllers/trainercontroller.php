<?php

namespace App\Http\Controllers;
use App\trainerModel;
use Illuminate\Http\Request;

class trainercontroller extends Controller
{
    //
    
    public function getTrainers(){
        $trainers = trainerModel::all();

    }
    public function bookTrainer(){
        
    }

}
