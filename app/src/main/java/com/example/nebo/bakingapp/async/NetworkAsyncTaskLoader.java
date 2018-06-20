package com.example.nebo.bakingapp.async;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;

public class NetworkAsyncTaskLoader implements LoaderManager.LoaderCallbacks<String> {
    public static final int QUERY_TASK_ID = 1;

    @NonNull
    @Override
    public Loader<String> onCreateLoader(int id, @Nullable Bundle args) {
        Loader<String> loader = null;

        switch(id) {

            default:
                break;
        }

        return null;
    }

    @Override
    public void onLoadFinished(@NonNull Loader<String> loader, String data) {

    }

    @Override
    public void onLoaderReset(@NonNull Loader<String> loader) {}
}
