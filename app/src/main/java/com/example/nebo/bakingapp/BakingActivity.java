package com.example.nebo.bakingapp;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.nebo.bakingapp.data.Recipe;
import com.example.nebo.bakingapp.databinding.ActivityBakingBinding;
import com.example.nebo.bakingapp.ui.RecipesFragment;
import com.example.nebo.bakingapp.util.NetworkUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BakingActivity extends AppCompatActivity
        implements
        RecipesFragment.OnClickRecipeListener,
        Callback<ArrayList<Recipe>>
{

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

        fragmentManager.beginTransaction().add(mBinding.flRecipes.getId(), recipesFragment).addToBackStack(null).commit();

        // now need to provide some logic to actually get the recipes.
        NetworkUtils.getRecipesFromNetwork(this);
    }

    @Override
    public void onResponse(Call<ArrayList<Recipe>> call, Response<ArrayList<Recipe>> response) {
        if (response != null && response.body() != null) {
            Bundle fragmentArgs = new Bundle();

            fragmentArgs.putParcelableArrayList(getString(R.string.key_recipes), response.body());

            FragmentManager fragmentManager = getSupportFragmentManager();
            RecipesFragment recipesFragment = new RecipesFragment();
            recipesFragment.setArguments(fragmentArgs);

            fragmentManager.beginTransaction().replace(mBinding.flRecipes.getId(), recipesFragment).commit();
        }
    }

    @Override
    public void onFailure(Call<ArrayList<Recipe>> call, Throwable t) {

    }

    @Override
    public void onClickRecipe(Recipe recipe) {
        // now can launch the actual intent from the activity instead of from the fragment.
        Log.d ("BakingActivity onClickRecipe", recipe.getName() + " has been clicked.");
    }
}
