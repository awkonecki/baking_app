package com.example.nebo.bakingapp;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.nebo.bakingapp.data.Recipe;

import com.example.nebo.bakingapp.data.RecipeStep;
import com.example.nebo.bakingapp.databinding.ActivityRecipeBinding;
import com.example.nebo.bakingapp.ui.RecipeIngredientFragment;
import com.example.nebo.bakingapp.ui.RecipeNavigationFragment;
import com.example.nebo.bakingapp.ui.RecipeStepsFragment;
import com.example.nebo.bakingapp.ui.RecipesFragment;

public class RecipeActivity extends AppCompatActivity
        implements RecipeStepsFragment.OnClickRecipeStepListener
{
    private Recipe mRecipe = null;
    private ActivityRecipeBinding mBinding = null;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_recipe);

        if (savedInstanceState != null && savedInstanceState.containsKey(getString(R.string.key_recipe))) {
            mRecipe = savedInstanceState.getParcelable(getString(R.string.key_recipe));
        }
        else if (savedInstanceState == null) {
            Log.d("RecipeActivity", "savedInstanceState is null.");
        }

        Log.d("RecipeActivity", "OnCreate is called " + (mRecipe == null ? "recipe is null" : mRecipe.getName()));


        // Supporting going back to the list of recipes.
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        Bundle bundle = new Bundle();

        // Determine if the activity is started from another, thus try to parse the activity desired
        // extras.
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra(getString(R.string.key_recipe))) {
            this.mRecipe = intent.getParcelableExtra(getString(R.string.key_recipe));
        }

        if (this.mRecipe != null) {
            setTitle(this.mRecipe.getName());
            bundle.putParcelableArrayList(
                    getString(R.string.key_recipe_steps),
                    this.mRecipe.getSteps());
        }

        RecipeStepsFragment stepsFragment = new RecipeStepsFragment();
        stepsFragment.setArguments(bundle);

        // RecipeNavigationFragment navigationFragment = new RecipeNavigationFragment();
        // RecipeIngredientFragment recipeIngredientFragment = new RecipeIngredientFragment();
        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();

        fragmentManager.beginTransaction().
                // add(R.id.fl_ingredients, recipeIngredientFragment).
                add(R.id.fl_recipe_steps, stepsFragment).
                // add(R.id.fl_navigation, navigationFragment).
                commit();

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        if (mRecipe != null) {
            outState.putParcelable(getString(R.string.key_recipe), mRecipe);
            Log.d("onSavedInstanceState", "saved " + mRecipe.getName());
        }
    }

    @Override
    public void onRecipeStepClick(RecipeStep recipeStep) {
        Intent intent = new Intent(this, RecipeStepActivity.class);
        intent.putExtra(getString(R.string.key_recipe_step), recipeStep);
        startActivity(intent);
    }

    /*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_recipe, menu);


        return true;
    }
    */
}
