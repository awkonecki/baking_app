package com.example.nebo.bakingapp.service;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.nebo.bakingapp.BakingWidgetProvider;
import com.example.nebo.bakingapp.R;
import com.example.nebo.bakingapp.data.Ingredient;

import java.util.ArrayList;

public class ListViewWidgetService extends RemoteViewsService {
    private ListViewFactory mFactory = null;

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        Log.d("ListViewWidgetService", "in  onGetViewFactory");

        if (mFactory == null) {
            mFactory = new ListViewFactory(this.getApplicationContext(), intent);
        }

        return mFactory;
    }

    private class ListViewFactory implements RemoteViewsService.RemoteViewsFactory {

        private ArrayList<Ingredient> mListIngredients = null;
        private Context mContext = null;
        private int mAppWidgetId;

        public ListViewFactory(Context context, Intent intent) {
            Log.d("ListViewFactory", "Constructor called.");
            mContext = context;

            if (intent != null) {
                mAppWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                        AppWidgetManager.INVALID_APPWIDGET_ID);
                mListIngredients = intent.getParcelableArrayListExtra(
                        context.getResources().getString(R.string.key_recipe_ingredients));
            }
        }

        @Override
        public void onCreate() {
            Log.d("ListViewFactory", "onCreate called.");
        }

        @Override
        public void onDataSetChanged() {
            Log.d("ListViewFactory", "onDataSetChanged called.");
        }

        @Override
        public void onDestroy() {
            Log.d("ListViewFactory", "onDestroy called.");
            mListIngredients.clear();
        }

        @Override
        public int getCount() {
            Log.d("ListViewFactory", "getCount called.");
            if (mListIngredients != null) {
                return mListIngredients.size();
            }
            else {
                return 0;
            }
        }

        @Override
        public RemoteViews getViewAt(int position) {
            Log.d("ListViewFactory", "getViewAt called with position " + Integer.toString(position));

            RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.ingredient_widget_item);
            rv.setTextViewText(R.id.tv_widget_ingredient,
                    mListIngredients.get(position).getIngredient());
            rv.setTextViewText(R.id.tv_widget_measuring,
                    mListIngredients.get(position).getMeasure());
            rv.setTextViewText(R.id.tv_widget_quantity,
                    Float.toString(mListIngredients.get(position).getQuantity()));

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
            return position;
        }

        @Override
        public boolean hasStableIds() {
            Log.d("ListViewFactory", "hasStableIds called.");
            return true;
        }
    }
}
