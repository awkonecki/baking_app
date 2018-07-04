package com.example.nebo.bakingapp.task;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;

import com.example.nebo.bakingapp.BakingActivity;
import com.example.nebo.bakingapp.R;
import com.example.nebo.bakingapp.data.RecipeContract;

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
        ContentResolver resolver = getContext().getContentResolver();

        if (resolver != null && mArgs != null &&
                mArgs.containsKey(getContext().getResources().getString(
                        R.string.key_recipe_task_operation)))
        {
            // Inspect the bundle of arguments to determine what type of operation is going to be
            // performed.
            int operation = mArgs.getInt(getContext().getResources().
                    getString(R.string.key_recipe_task_operation));

            switch(operation) {
                case BakingActivity.DB_QUERY_ALL_RECIPES:
                    cursor = resolver.query(RecipeContract.RecipeIngredient.CONTENT_URI,
                            null,
                            null,
                            null,
                            null);
                    break;
                default:
                    throw new UnsupportedOperationException(
                            "Unsupported operation for RecipeTask."
                    );
            }

        }

        return cursor;
    }
}
