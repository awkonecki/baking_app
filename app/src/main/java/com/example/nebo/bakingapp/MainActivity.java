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

import java.util.List;

import static com.example.nebo.bakingapp.async.NetworkAsyncTaskLoader.QUERY_TASK_ID;

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
            Loader<List<Recipe>> loader = loaderManager.getLoader(QUERY_TASK_ID);

            if (loader == null) {
                loaderManager.initLoader(QUERY_TASK_ID,
                        null,
                        new NetworkLoader()).forceLoad();
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

    private class NetworkLoader implements LoaderManager.LoaderCallbacks<List<Recipe>> {
        @NonNull
        @Override
        public Loader<List<Recipe>> onCreateLoader(int id, @Nullable Bundle args) {
            Loader<List<Recipe>> loader = null;

            switch (id) {
                case QUERY_TASK_ID:
                    loader = new NetworkAsyncTask(MainActivity.this);
                default:
                    break;
            }

            return loader;
        }

        @Override
        public void onLoadFinished(@NonNull Loader<List<Recipe>> loader, List<Recipe> data) {
            if (data != null) {
                Log.d("Data Size", Integer.toString(data.size()));
            }
        }

        @Override
        public void onLoaderReset(@NonNull Loader<List<Recipe>> loader) {
        }
    }
}
