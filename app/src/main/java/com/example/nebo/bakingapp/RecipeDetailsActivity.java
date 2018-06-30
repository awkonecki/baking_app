package com.example.nebo.bakingapp;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.nebo.bakingapp.data.Recipe;
import com.example.nebo.bakingapp.ui.RecipeIngredientsFragment;
import com.example.nebo.bakingapp.ui.RecipeNavigationFragment;
import com.example.nebo.bakingapp.ui.RecipeStepDetailFragment;

import com.example.nebo.bakingapp.databinding.ActivityRecipeDetailsBinding;

public class RecipeDetailsActivity extends AppCompatActivity
        implements RecipeNavigationFragment.NavigationOnClickListener {
    private Recipe mRecipe = null;
    private int mRecipeStep = -1;
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
            Log.d ("SavedInstanceState", "SavedInstance state is not null for recipeDetailsActivity");
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
                mRecipeStep = activityData.getInt(getString(R.string.key_recipe_step_id));
            }
        }

        if (mRecipeStep != -1 && mRecipe != null) {
            setTitle(mRecipe.getStep(mRecipeStep).getDescription());
        }
        else {
            setTitle(getString(R.string.ingredients_label));
        }

        // Now will need to manage the fragment that will be associated with the fragment host
        // activity.
        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();

        // There are two type of of possible details for this activity.
        // One for the ingredients
        // One for a recipe step

        if (mRecipeStep > -1 &&
                mRecipe != null &&
                mRecipe.getSteps() != null &&
                mRecipeStep < mRecipe.getSteps().size())
        {
            // For now only working with recipe step.
            RecipeStepDetailFragment recipeStepDetailFragment = new RecipeStepDetailFragment();
            Bundle stepData = new Bundle();
            stepData.putParcelable(getString(R.string.key_recipe_step), mRecipe.getStep(mRecipeStep));
            recipeStepDetailFragment.setArguments(stepData);

            // A common piece that will exist no matter what will be the navigation fragment.
            RecipeNavigationFragment recipeNavigationFragment = new RecipeNavigationFragment();

            fragmentManager.beginTransaction().
                    add(mBinding.flRecipeDetail.getId(), recipeStepDetailFragment).
                    add(mBinding.flRecipeDetailNavigation.getId(), recipeNavigationFragment).
                    commit();
        }
        else if (mRecipeStep == -1 && mRecipe != null) {
            RecipeIngredientsFragment recipeIngredientsFragment = new RecipeIngredientsFragment();
            Bundle ingredientData = new Bundle();
            ingredientData.putParcelableArrayList(
                    getString(R.string.key_recipe_ingredients),
                    mRecipe.getIngredients());
            recipeIngredientsFragment.setArguments(ingredientData);

            // A common piece that will exist no matter what will be the navigation fragment.
            RecipeNavigationFragment recipeNavigationFragment = new RecipeNavigationFragment();

            fragmentManager.beginTransaction().
                    add(mBinding.flRecipeDetail.getId(), recipeIngredientsFragment).
                    add(mBinding.flRecipeDetailNavigation.getId(), recipeNavigationFragment).
                    commit();
        }
        else {
            // Index out of supported range.
            finish();
        }
    }

    @Override
    public void onNavigationClick(int direction) {
        mRecipeStep = mRecipeStep + direction;

        if (mRecipeStep == -1) {
            // need to display the ingrediants for the recipe.
            RecipeIngredientsFragment recipeIngredientsFragment = new RecipeIngredientsFragment();
            Bundle ingredientData = new Bundle();
            ingredientData.putParcelableArrayList(
                    getString(R.string.key_recipe_ingredients),
                    mRecipe.getIngredients());
            recipeIngredientsFragment.setArguments(ingredientData);

            getSupportFragmentManager().beginTransaction().
                    replace(mBinding.flRecipeDetail.getId(), recipeIngredientsFragment).
                    commit();
        }
        else if (mRecipeStep < -1 || mRecipeStep >= mRecipe.getSteps().size()) {
            // the activity is done.  Go back to the RecipeActivity.
            finish();
        }
        else {
            RecipeStepDetailFragment recipeStepDetailFragment = new RecipeStepDetailFragment();
            Bundle stepData = new Bundle();
            stepData.putParcelable(getString(R.string.key_recipe_step), mRecipe.getStep(mRecipeStep));
            recipeStepDetailFragment.setArguments(stepData);

            getSupportFragmentManager().beginTransaction().
                    replace(mBinding.flRecipeDetail.getId(), recipeStepDetailFragment).
                    commit();
        }
    }
}
