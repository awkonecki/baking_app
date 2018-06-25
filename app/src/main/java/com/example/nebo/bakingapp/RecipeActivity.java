package com.example.nebo.bakingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;

import com.example.nebo.bakingapp.data.Recipe;

public class RecipeActivity extends AppCompatActivity {
    private Recipe mRecipe = null;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Supporting going back to the list of recipes.
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        // Determine if the activity is started from another, thus try to parse the activity desired
        // extras.
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra(getString(R.string.key_recipe))) {
            this.mRecipe = intent.getParcelableExtra(getString(R.string.key_recipe));
        }
    }

    /*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_recipe, menu);


        return true;
    }
    */
}
