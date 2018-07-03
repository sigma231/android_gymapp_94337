<?php

use Illuminate\Http\Request;

/*
|--------------------------------------------------------------------------
| API Routes
|--------------------------------------------------------------------------
|
| Here is where you can register API routes for your application. These
| routes are loaded by the RouteServiceProvider within a group which
| is assigned the "api" middleware group. Enjoy building your API!
|
*/
Route::post('login', 'UserController@login');
Route::post('register', 'UserController@register');

Route::middleware('auth:api')->get('/user', function () {
    Route::post('details', 'API\UserController@details');
});
Route::get('/workouts/1', 'workoutcontroller@getMultipleWorkouts');
Route::get('/trainers/{gym_id}', 'gymcontroller@getTrainers');
Route::get('/gyms/{latitude}/{longitude}', 'gymcontroller@getGyms');


?>