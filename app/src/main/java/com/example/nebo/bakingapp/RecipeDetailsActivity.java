package com.example.nebo.bakingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.nebo.bakingapp.data.Recipe;

public class RecipeDetailsActivity extends AppCompatActivity {
    private Recipe mRecipe = null;
    private int mRecipeSetp = -1;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Allow going back to the recipe activity.
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        Bundle activityData = null;

        // Get the data from the saved instance state or the intent.
        if (savedInstanceState != null) {
            activityData = savedInstanceState.deepCopy();
        }
        else {
            Intent intent = getIntent();

            if (intent != null) {
                activityData = intent.getExtras();
            }
        }

        // Parsing of the data to context class member variables.
        if (activityData != null) {
            if (activityData.containsKey(getString(R.string.key_recipe))) {
                mRecipe = activityData.getParcelable(getString(R.string.key_recipe));
            }

            if (activityData.containsKey(getString(R.string.key_recipe_step_id))) {
                mRecipeSetp = activityData.getInt(getString(R.string.key_recipe_step_id));
            }
        }
    }
}
