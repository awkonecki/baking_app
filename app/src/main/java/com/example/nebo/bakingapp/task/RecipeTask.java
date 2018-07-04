package com.example.nebo.bakingapp.task;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;

public class RecipeTask extends AsyncTaskLoader<Cursor> {

    private final Bundle mArgs;

    public RecipeTask(Context context, @Nullable Bundle args) {
        super(context);
        this.mArgs = args;
    }

    @Nullable
    @Override
    public Cursor loadInBackground() {
        Cursor cursor = null;


        return cursor;
    }
}
