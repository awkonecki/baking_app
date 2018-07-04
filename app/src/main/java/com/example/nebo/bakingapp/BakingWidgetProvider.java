package com.example.nebo.bakingapp;

import android.app.IntentService;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.util.Log;
import android.widget.RemoteViews;

import com.example.nebo.bakingapp.data.Ingredient;
import com.example.nebo.bakingapp.service.IngredientService;
import com.example.nebo.bakingapp.service.ListViewWidgetService;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

/**
 * Implementation of App Widget functionality.
 */
public class BakingWidgetProvider extends AppWidgetProvider
{
    public static final String EXTRA_ITEM = "com.example.nebo.bakingapp.EXTRA_ITEM";

    static void updateAppWidget(Context context,
                                AppWidgetManager appWidgetManager,
                                ArrayList<Ingredient> ingredientList,
                                String recipeName,
                                int appWidgetId)
    {
        String widgetRecipeText = null;

        if (recipeName == null || recipeName.isEmpty()) {
            widgetRecipeText =
                    context.getResources().getString(R.string.default_recipe_widget_text);
        }
        else {
            widgetRecipeText = recipeName;
        }

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.baking_widget);
        views.setTextViewText(R.id.tv_widget_recipe_name, widgetRecipeText);

        if (ingredientList != null) {
            Log.d("BakingWidgetProvider", Integer.toString(ingredientList.size()));
        }
        else {
            Log.d("BakingWidgetProvider", "ingredient list is null.");
        }

        Intent intent = new Intent(context, ListViewWidgetService.class);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        intent.putExtra(context.getResources().getString(R.string.key_recipe_ingredients),
                ingredientList);

        views.setRemoteAdapter(R.id.lv_widget_ingredient_list, intent);
        views.setEmptyView(R.id.lv_widget_ingredient_list, R.id.empty_view);

        // CharSequence widgetText = context.getString(R.string.appwidget_text);
        // Construct the RemoteViews object
        // RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.baking_widget);

        // Intent intent = new Intent(context, BakingActivity.class);
        // PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        // setup support for click anywhere on the widget.
        // views.setOnClickPendingIntent(R.id.ll_widget, pendingIntent);

        // views.setTextViewText(R.id.appwidget_text, widgetText);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    public static void updateRecipeIngredientListWidgets(Context context,
                                                         AppWidgetManager appWidgetManager,
                                                         ArrayList<Ingredient> ingredientList,
                                                         String recipeName,
                                                         int [] appWidgetIds)
    {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, ingredientList, recipeName, appWidgetId);
        }
    }

    // By default is called when the time limit expires as specified within the
    // `baking_widget_info.xml` resource file.
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // Intent service will be responsible for updating all the UI elements.
        IngredientService.startActionListIngredients(context);
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

