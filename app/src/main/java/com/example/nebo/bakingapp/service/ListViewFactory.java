package com.example.nebo.bakingapp.service;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Binder;
import android.util.Log;
import android.widget.AdapterView;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.nebo.bakingapp.R;
import com.example.nebo.bakingapp.data.Ingredient;
import com.example.nebo.bakingapp.data.RecipeContract;

import java.util.ArrayList;

public class ListViewFactory implements RemoteViewsService.RemoteViewsFactory {

    // private ArrayList<Ingredient> mListIngredients = null;
    private Context mContext = null;
    // private int mAppWidgetId;
    private Cursor mCursor = null;

    public ListViewFactory(Context context, Intent intent) {
        Log.d("ListViewFactory", "Constructor called.");
        mContext = context;

        if (intent != null) {
            //mAppWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
            //        AppWidgetManager.INVALID_APPWIDGET_ID);
            // mListIngredients = intent.getParcelableArrayListExtra(
            //        context.getResources().getString(R.string.key_recipe_ingredients));
        }
    }

    @Override
    public void onCreate() {
        Log.d("ListViewFactory", "onCreate called.");
    }

    @Override
    public void onDataSetChanged() {
        if (mCursor != null) {
            mCursor.close();
        }

        final long identityToken = Binder.clearCallingIdentity();

        SharedPreferences sharedPreferences = mContext.getSharedPreferences(
                mContext.getResources().getString(R.string.shared_preferences_name),
                Context.MODE_PRIVATE);

        Uri uri = RecipeContract.RecipeIngredient.CONTENT_URI;
        String recipeName = null;

        if (sharedPreferences.contains(mContext.getResources().getString(R.string.key_recipe))) {
            recipeName = sharedPreferences.getString(
                    mContext.getResources().getString(R.string.key_recipe), null);

            String selection = RecipeContract.RecipeIngredient.COLUMN_RECIPE_NAME + "=?";
            String[] selectionArgs = new String[]{recipeName};

            mCursor = mContext.getContentResolver().query(
                    RecipeContract.RecipeIngredient.CONTENT_URI,
                    null,
                    selection,
                    selectionArgs,
                    null);
        }

        Binder.restoreCallingIdentity(identityToken);
    }

    @Override
    public void onDestroy() {
        Log.d("ListViewFactory", "onDestroy called.");
        // mListIngredients.clear();
        if (mCursor != null) {
            mCursor.close();
        }
    }

    @Override
    public int getCount() {
        Log.d("ListViewFactory", "getCount called.");
        return mCursor == null ? 0 : mCursor.getCount();

        /*
        if (mListIngredients != null) {
            return mListIngredients.size();
        }
        else {
            return 0;
        }
        */
    }

    @Override
    public RemoteViews getViewAt(int position) {
        Log.d("ListViewFactory", "getViewAt called with position " + Integer.toString(position));

        if (position == AdapterView.INVALID_POSITION ||
                mCursor == null ||
                !mCursor.moveToPosition(position)) {
            return null;
        }

        RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.ingredient_widget_item);
        rv.setTextViewText(R.id.tv_widget_ingredient,
                mCursor.getString(mCursor.getColumnIndex(RecipeContract.RecipeIngredient.COLUMN_INGREDIENT)));
        rv.setTextViewText(R.id.tv_widget_measuring,
                mCursor.getString(mCursor.getColumnIndex(RecipeContract.RecipeIngredient.COLUMN_MEASURING)));
        rv.setTextViewText(R.id.tv_widget_quantity,
                Float.toString(mCursor.getFloat(mCursor.getColumnIndex(RecipeContract.RecipeIngredient.COLUMN_QUANTITY))));

        // A fill intent i guess is needed.
        // Bundle extras = new Bundle();
        // extras.putInt(BakingWidgetProvider.EXTRA_ITEM, position);
        // Intent fillIntent = new Intent();
        // fillIntent.putExtras(extras);
        // rv.setOnClickFillInIntent(R.id.tv_widget_ingredient, fillIntent);
        // rv.setOnClickFillInIntent(R.id.tv_widget_measuring, fillIntent);
        // rv.setOnClickFillInIntent(R.id.tv_widget_quantity, fillIntent);

        return rv;
    }

    @Override
    public RemoteViews getLoadingView() {
        Log.d("ListViewFactory", "getLoadingView called.");
        return null;
    }

    @Override
    public int getViewTypeCount() {
        Log.d("ListViewFactory", "getViewTypeCount called.");
        return 1;
    }

    @Override
    public long getItemId(int position) {
        Log.d("ListViewFactory", "getItemId called  with position " + Integer.toString(position));
        return mCursor.moveToPosition(position) ? mCursor.getLong(0) : position;
    }

    @Override
    public boolean hasStableIds() {
        Log.d("ListViewFactory", "hasStableIds called.");
        return true;
    }
}
