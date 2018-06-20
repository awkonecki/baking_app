package com.example.nebo.bakingapp.async;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.util.Log;

import com.example.nebo.bakingapp.data.Recipe;

import java.util.List;

public class NetworkAsyncTaskLoader implements LoaderManager.LoaderCallbacks<List<Recipe>> {
    public static final int QUERY_TASK_ID = 1;
    private final Context mContext;

    public NetworkAsyncTaskLoader(Context context) {
        this.mContext = context;
    }

    @NonNull
    @Override
    public Loader<List<Recipe>> onCreateLoader(int id, @Nullable Bundle args) {
        Loader<List<Recipe>> loader = null;

        switch(id) {
            case QUERY_TASK_ID:
                loader = new NetworkAsyncTask(this.mContext);
            default:
                break;
        }

        return null;
    }

    @Override
    public void onLoadFinished(@NonNull Loader<List<Recipe>> loader, List<Recipe> data) {
        Log.d("Data Size", Integer.toString(data.size()));
    }

    @Override
    public void onLoaderReset(@NonNull Loader<List<Recipe>> loader) {}
}
