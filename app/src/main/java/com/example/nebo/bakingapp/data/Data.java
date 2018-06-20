package com.example.nebo.bakingapp.data;

import com.google.gson.Gson;

import org.json.JSONObject;

public class Data {
    public static Recipe [] getRecipes(JSONObject jsonObject) {
        Gson gson = new Gson();

        return gson.fromJson(jsonObject.toString(), Recipe[].class);
    }
}