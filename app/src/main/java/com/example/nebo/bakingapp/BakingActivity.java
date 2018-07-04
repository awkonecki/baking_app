package com.example.nebo.bakingapp;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.nebo.bakingapp.data.Ingredient;
import com.example.nebo.bakingapp.data.Recipe;
import com.example.nebo.bakingapp.data.RecipeContract;
import com.example.nebo.bakingapp.databinding.ActivityBakingBinding;
import com.example.nebo.bakingapp.task.RecipeTask;
import com.example.nebo.bakingapp.ui.RecipesFragment;
import com.example.nebo.bakingapp.util.NetworkUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BakingActivity extends AppCompatActivity
        implements
        RecipesFragment.OnClickRecipeListener,
        Callback<ArrayList<Recipe>>,
        LoaderManager.LoaderCallbacks<Cursor>
{
    private HashSet<String> mSupportedRecipes = new HashSet<String>();
    private ActivityBakingBinding mBinding = null;
    public static final int DB_QUERY_ALL_RECIPES = 100;
    public static final int DB_INSERT_RECIPE_INGREDIENTS = 200;

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

        // Perform a query & network fetch of data.
        Bundle recipeTaskArgs = new Bundle();
        recipeTaskArgs.putInt(getString(R.string.key_recipe_task_operation), DB_QUERY_ALL_RECIPES);

        LoaderManager loaderManager = getSupportLoaderManager();
        Loader<Cursor> loader = loaderManager.getLoader(DB_QUERY_ALL_RECIPES);

        if (loader == null) {
            loaderManager.initLoader(DB_QUERY_ALL_RECIPES, recipeTaskArgs, this).
                    forceLoad();
        }
        else {
            loaderManager.restartLoader(DB_QUERY_ALL_RECIPES, recipeTaskArgs, this).
                    forceLoad();
        }
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

            // Time to now insert the data into the database if the recipe does not already exist.
            ContentResolver resolver = getContentResolver();

            for (Recipe recipe : response.body()) {
                if (!mSupportedRecipes.contains(recipe.getName())) {
                    for (Ingredient ingredient : recipe.getIngredients()) {
                        // Construct the content values
                        ContentValues values = new ContentValues();
                        values.put(RecipeContract.RecipeIngredient.COLUMN_RECIPE_NAME,
                                recipe.getName());
                        values.put(RecipeContract.RecipeIngredient.COLUMN_INGREDIENT,
                                ingredient.getIngredient());
                        values.put(RecipeContract.RecipeIngredient.COLUMN_MEASURING,
                                ingredient.getMeasure());
                        values.put(RecipeContract.RecipeIngredient.COLUMN_QUANTITY,
                                ingredient.getQuantity());

                        resolver.insert(RecipeContract.RecipeIngredient.CONTENT_URI, values);
                    }
                }
            }
        }
    }

    @Override
    public void onFailure(Call<ArrayList<Recipe>> call, Throwable t) { }

    @Override
    public void onClickRecipe(Recipe recipe) {
        // now can launch the actual intent from the activity instead of from the fragment.
        Intent intent = new Intent(this, RecipeActivity.class);
        intent.putExtra(getString(R.string.key_recipe), recipe);

        startActivity(intent);
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        Loader<Cursor> loader = null;

        switch (id) {
            case DB_QUERY_ALL_RECIPES:
                loader = new RecipeTask(this, args);
                break;
            case DB_INSERT_RECIPE_INGREDIENTS:
                break;
            default:
                throw new UnsupportedOperationException(
                        "The provided id of " + Integer.toString(id) + " is not currently supported."
                );
        }

        return loader;
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        if (data != null) {
            if (data.getCount() > 0) {
                data.moveToFirst();

                do {
                    mSupportedRecipes.add(
                            data.getString(
                                    data.getColumnIndex(
                                            RecipeContract.RecipeIngredient.COLUMN_RECIPE_NAME)));
                } while (data.moveToNext());
            }

            // Clean-up the resource
            data.close();

            // Once closed the data can be fetched
            NetworkUtils.getRecipesFromNetwork(this);
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) { }
}
