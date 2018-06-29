package com.example.nebo.bakingapp;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.nebo.bakingapp.data.Recipe;
import com.example.nebo.bakingapp.ui.RecipeNavigationFragment;
import com.example.nebo.bakingapp.ui.RecipeStepDetailFragment;

import com.example.nebo.bakingapp.databinding.ActivityRecipeDetailsBinding;

public class RecipeDetailsActivity extends AppCompatActivity {
    private Recipe mRecipe = null;
    private int mRecipeSetp = -1;
    private ActivityRecipeDetailsBinding mBinding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_recipe_details);

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

        // Now will need to manage the fragment that will be associated with the fragment host
        // activity.
        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();

        // There are two type of of possible details for this activity.
        // One for the ingredients
        // One for a recipe step

        // For now only working with recipe step.
        RecipeStepDetailFragment recipeStepDetailFragment = new RecipeStepDetailFragment();
        Bundle stepData = new Bundle();
        stepData.putParcelable(getString(R.string.key_recipe_step), mRecipe.getStep(mRecipeSetp));
        recipeStepDetailFragment.setArguments(stepData);

        // A common piece that will exist no matter what will be the navigation fragment.
        RecipeNavigationFragment recipeNavigationFragment = new RecipeNavigationFragment();

        fragmentManager.beginTransaction().
                add(mBinding.flRecipeDetail.getId(), recipeStepDetailFragment).
                add(mBinding.flRecipeDetailNavigation.getId(), recipeNavigationFragment).
                commit();
    }
}
