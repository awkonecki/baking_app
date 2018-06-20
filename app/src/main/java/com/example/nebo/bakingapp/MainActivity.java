package com.example.nebo.bakingapp;

import android.database.Cursor;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.nebo.bakingapp.data.Data;
import com.example.nebo.bakingapp.ui.RecipeStepDetailsFragment;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecipeStepDetailsFragment fragment = new RecipeStepDetailsFragment();
        FragmentManager manager = getSupportFragmentManager();

        manager.beginTransaction().add(R.id.fl_recipe_step_details, fragment).commit();

        Log.d("Count of Recipes", Integer.toString(Data.getRecipes().length));
    }
}
