package com.example.nebo.bakingapp.async;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;

public class NetworkAsyncTask extends AsyncTaskLoader {
    public NetworkAsyncTask(Context context) {
        super(context);
    }

    @Nullable
    @Override
    public Object loadInBackground() {
        return null;
    }
}
