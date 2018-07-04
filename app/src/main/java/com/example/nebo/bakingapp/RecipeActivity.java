package com.example.nebo.bakingapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.example.nebo.bakingapp.data.Recipe;

import com.example.nebo.bakingapp.data.RecipeStep;
import com.example.nebo.bakingapp.databinding.ActivityRecipeBinding;
import com.example.nebo.bakingapp.ui.RecipeIngredientsSelectionFragment;
import com.example.nebo.bakingapp.ui.RecipeStepsFragment;

public class RecipeActivity extends AppCompatActivity
        implements RecipeStepsFragment.OnClickRecipeStepListener,
        RecipeIngredientsSelectionFragment.OnClickIngredientsListener,
        View.OnClickListener
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

        // Set the onclick listener to the RecipeActivity instance.
        mBinding.btnStartContinue.setOnClickListener(this);

        // Populate the text of the button to be either `Start Recipe` or `Continue Recipe` based
        // on if shared preferences is set.
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(
                getApplicationContext());

        if (sharedPreferences.contains(getString(R.string.key_recipe)) && mRecipe != null) {
            if (sharedPreferences.getString(getString(R.string.key_recipe), "").
                    equals(mRecipe.getName()))
            {
                mBinding.btnStartContinue.setText(getString(R.string.recipe_selection_continue_label));
            }
        }

        RecipeStepsFragment stepsFragment = new RecipeStepsFragment();
        stepsFragment.setArguments(bundle);

        // RecipeNavigationFragment navigationFragment = new RecipeNavigationFragment();
        RecipeIngredientsSelectionFragment recipeIngredientFragment = new RecipeIngredientsSelectionFragment();
        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();

        fragmentManager.beginTransaction().
                add(R.id.fl_ingredients, recipeIngredientFragment).
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
        Intent intent = new Intent(this, RecipeDetailsActivity.class);
        intent.putExtra(getString(R.string.key_recipe), mRecipe);
        intent.putExtra(getString(R.string.key_recipe_step_id), recipeStep.getId());
        startActivity(intent);
    }

    @Override
    public void onIngredientsClick() {
        Intent intent = new Intent(this, RecipeDetailsActivity.class);
        intent.putExtra(getString(R.string.key_recipe), mRecipe);
        intent.putExtra(getString(R.string.key_recipe_step_id), -1);
        startActivity(intent);
    }

    // Launch the actual process of wanting to bake to the recipe.
    private void startContinueBakingRecipe(int step) {
        Intent intent = new Intent(this, RecipeDetailsActivity.class);
        intent.putExtra(getString(R.string.key_recipe), mRecipe);
        intent.putExtra(getString(R.string.key_recipe_step_id), step);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        // Method for starting or resuming a recipe.  Application will only support one at a time.
        // No matter what the view will check the shared preferences to determine the last recipe
        // and page navigated to.
        int recipeStep = -1;

        // if the current recipe name is the same as the one stored, use the page to go straight to
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(
                getApplicationContext());

        if (mRecipe == null) {
            Log.d ("RecipeActivity", "Null Recipe State Detected in onClick");
            return;
        }


        if (sharedPreferences.getString(getString(R.string.key_recipe), "").
                equals(mRecipe.getName()))
        {
            recipeStep = sharedPreferences.getInt(
                    getString(R.string.key_recipe_step_id), -1);
        }
        else {
            // Store the current recipe as the current recipe for the user.
            sharedPreferences.edit().
                    putString(getString(R.string.key_recipe), mRecipe.getName()).
                    putInt(getString(R.string.key_recipe_step_id), recipeStep).
                    apply();
        }

        startContinueBakingRecipe(recipeStep);
    }

    /*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_recipe, menu);


        return true;
    }
    */
}
