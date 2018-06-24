package com.example.nebo.bakingapp;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.example.nebo.bakingapp.databinding.ActivityBakingBinding;
import com.example.nebo.bakingapp.ui.RecipesFragment;

public class BakingActivity extends AppCompatActivity {
    private ActivityBakingBinding mBinding = null;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mBinding = DataBindingUtil.setContentView(
                this,
                R.layout.activity_baking);

        // the activity_baking.xml view is empty except for a frame-layout that is responsible for
        // being populated with the recycler view list of recipes.
        RecipesFragment recipesFragment = new RecipesFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();

        fragmentManager.beginTransaction().add(R.id.fl_recipes, recipesFragment).commit();

        // the target fragment fragment_recipes.xml is the fragment that contains the recycler view
        // that needs to be populated with an appropriate view holder.


    }
}
