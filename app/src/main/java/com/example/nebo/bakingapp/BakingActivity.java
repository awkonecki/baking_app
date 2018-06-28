package com.example.nebo.bakingapp;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.example.nebo.bakingapp.data.Recipe;
import com.example.nebo.bakingapp.databinding.ActivityBakingBinding;
import com.example.nebo.bakingapp.ui.RecipesFragment;
import com.example.nebo.bakingapp.util.NetworkUtils;

public class BakingActivity extends AppCompatActivity implements RecipesFragment.OnClickRecipeListener {
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

        fragmentManager.beginTransaction().add(R.id.fl_recipes, recipesFragment).addToBackStack(null).commit();

        // now need to provide some logic to actually get the recipes.
        NetworkUtils.getRecipesFromNetwork(recipesFragment);
    }

    @Override
    public void onClickRecipe(Recipe recipe) {
        // now can launch the actual intent from the activity instead of from the fragment.
    }
}
