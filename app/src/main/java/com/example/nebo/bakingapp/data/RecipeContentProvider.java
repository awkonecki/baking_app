package com.example.nebo.bakingapp.data;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class RecipeContentProvider extends ContentProvider {
    private static RecipeDBHelper sDB = null;
    private static final UriMatcher sUriMatcher = RecipeContentProvider.buildUriMatcher();

    public static final int RECIPE_INGREDIENTS = 100;
    public static final int RECIPE_INGREDIENT_WITH_ID = 101;

    private static UriMatcher buildUriMatcher() {
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

        uriMatcher.addURI(RecipeContract.CONTENT_AUTHORITY,
                RecipeContract.PATH_RECIPE_INGREDIENTS,
                RECIPE_INGREDIENTS);
        uriMatcher.addURI(RecipeContract.CONTENT_AUTHORITY,
                RecipeContract.PATH_RECIPE_INGREDIENTS + "/#",
                RECIPE_INGREDIENT_WITH_ID);

        return uriMatcher;
    }

    @Override
    public boolean onCreate() {
        sDB = new RecipeDBHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri,
                        @Nullable String[] projection,
                        @Nullable String selection,
                        @Nullable String[] selectionArgs,
                        @Nullable String sortOrder)
    {
        return null;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri,
                      @Nullable String selection,
                      @Nullable String[] selectionArgs)
    {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri,
                      @Nullable ContentValues values,
                      @Nullable String selection,
                      @Nullable String[] selectionArgs)
    {
        return 0;
    }
}
