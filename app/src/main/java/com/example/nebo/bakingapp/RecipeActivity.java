package com.example.nebo.bakingapp;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.nebo.bakingapp.data.Recipe;

import com.example.nebo.bakingapp.databinding.ActivityRecipeBinding;
import com.example.nebo.bakingapp.ui.RecipeIngredientFragment;
import com.example.nebo.bakingapp.ui.RecipeNavigationFragment;
import com.example.nebo.bakingapp.ui.RecipeStepsFragment;

public class RecipeActivity extends AppCompatActivity {
    private Recipe mRecipe = null;
    private ActivityRecipeBinding mBinding = null;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_recipe);

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

        if (this.mRecipe != null) {
            setTitle(this.mRecipe.getName());
        }

        RecipeStepsFragment stepsFragment = new RecipeStepsFragment();
        // RecipeNavigationFragment navigationFragment = new RecipeNavigationFragment();
        // RecipeIngredientFragment recipeIngredientFragment = new RecipeIngredientFragment();
        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();

        fragmentManager.beginTransaction().
                // add(R.id.fl_ingredients, recipeIngredientFragment).
                add(R.id.fl_recipe_steps, stepsFragment).
                // add(R.id.fl_navigation, navigationFragment).
                commit();

    }

    /*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_recipe, menu);


        return true;
    }
    */
}
