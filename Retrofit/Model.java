package com.example.myheroes;

import com.google.gson.annotations.SerializedName;

public class Model {

    @SerializedName("name")
    private String name;

    public Model(String _name){
        this.name = _name;
    }

    public String getName(){
        return name;
    }
}
