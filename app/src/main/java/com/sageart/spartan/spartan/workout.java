package com.sageart.spartan.spartan;

public class workout {
    private String name,  description;
    private String gym_name, trainer_name;
    private String date, type;
    private String sets;

    public workout() {

    }
    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;

    }
    public String getDescription(){
        return description;
    }
    public void setDescription(String description){
        this.description = description;

    }
    public String getGymName(){
        return gym_name;
    }
    public void setGymName(String gym_name){
        this.gym_name = gym_name;

    }
    public String getTrainerName(){
        return trainer_name;
    }
    public void setTrainerName(String trainer_name){
        this.trainer_name = trainer_name;

    }
    public String getSets(){
        return sets;
    }
    public void setSets(String sets){
        this.sets = sets;

    }
    public String getDate(){
        return date;
    }
    public void setDate(String date){
        this.date = date;

    }
    public String getType(){
        return type;
    }
    public void setType(String type){
        this.type = type;

    }


}
