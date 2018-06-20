package com.example.nebo.bakingapp;

import android.database.Cursor;
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

import java.util.List;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecipeStepDetailsFragment fragment = new RecipeStepDetailsFragment();
        FragmentManager manager = getSupportFragmentManager();

        manager.beginTransaction().add(R.id.fl_recipe_step_details, fragment).commit();

        // NetworkAsyncTaskLoader taskLoader = new NetworkAsyncTaskLoader(this);

        LoaderManager loaderManager = getSupportLoaderManager();

        if (loaderManager != null) {
            Loader<List<Recipe>> loader = loaderManager.getLoader(NetworkAsyncTaskLoader.QUERY_TASK_ID);

            if (loader == null) {
                loaderManager.initLoader(NetworkAsyncTaskLoader.QUERY_TASK_ID,
                        null,
                        new NetworkAsyncTaskLoader(this)).forceLoad();
            } else {
                //loaderManager.restartLoader(NetworkAsyncTaskLoader.QUERY_TASK_ID,
                //        null,
                //        new NetworkAsyncTaskLoader(this)).forceLoad();
            }
        }
        else {
            Log.e("MainActivity", "Loader Manager is null.");
        }
    }
}
