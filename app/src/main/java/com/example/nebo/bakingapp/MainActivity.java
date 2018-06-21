package com.example.nebo.bakingapp;

import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.nebo.bakingapp.async.NetworkAsyncTask;
import com.example.nebo.bakingapp.async.NetworkAsyncTaskLoader;
import com.example.nebo.bakingapp.data.Data;
import com.example.nebo.bakingapp.data.Recipe;
import com.example.nebo.bakingapp.ui.RecipeStepDetailsFragment;
import com.example.nebo.bakingapp.util.NetworkUtils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.nebo.bakingapp.async.NetworkAsyncTaskLoader.QUERY_TASK_ID;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MAIN ACTIVITY";
    private List<Recipe> mRecipes = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecipeStepDetailsFragment fragment = new RecipeStepDetailsFragment();
        FragmentManager manager = getSupportFragmentManager();

        manager.beginTransaction().add(R.id.fl_recipe_step_details, fragment).commit();
        NetworkUtils.getRecipesFromNetwork(new RecipeNetworkHandler());
    }

    private class RecipeNetworkHandler implements Callback<List<Recipe>> {

        @Override
        public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
            if (response != null && response.body() != null) {
                MainActivity.this.mRecipes = response.body();
                Log.d (TAG, "Number of recipes is " + Integer.toString(mRecipes.size()));
            }
        }

        @Override
        public void onFailure(Call<List<Recipe>> call, Throwable t) {
        }
    }
}
