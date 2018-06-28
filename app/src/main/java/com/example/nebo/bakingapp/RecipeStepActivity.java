package com.example.nebo.bakingapp;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.example.nebo.bakingapp.data.RecipeStep;
import com.example.nebo.bakingapp.databinding.ActivityRecipeStepBinding;
import com.example.nebo.bakingapp.ui.RecipeStepDetailDescriptionFragment;

public class RecipeStepActivity extends AppCompatActivity {

    private ActivityRecipeStepBinding mBinding = null;
    private RecipeStep mRecipeStep = null;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_recipe_step);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        // this activity will be launch via an intent thus will need to pull intent bundle.
        Intent intent = getIntent();
        Bundle args = null;

        if (intent != null) {
            args = intent.getExtras();
        }

        // This view has several fragments associated with it.  For the time being only operating
        // with the fragment that is responsible for the recipe step description.
        RecipeStepDetailDescriptionFragment recipeStepDetailDescriptionFragment =
                new RecipeStepDetailDescriptionFragment();
        recipeStepDetailDescriptionFragment.setArguments(args);

        FragmentManager fragmentManager = getSupportFragmentManager();

        fragmentManager.beginTransaction().
                add(R.id.fl_recipe_step_detail_description, recipeStepDetailDescriptionFragment).
                commit();
    }
}
