package com.example.nebo.bakingapp;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import android.widget.RemoteViews;

import com.example.nebo.bakingapp.service.ListViewWidgetService;

/**
 * Implementation of App Widget functionality.
 */
public class BakingWidgetProvider extends AppWidgetProvider
{
    public static final String EXTRA_ITEM = "com.example.nebo.bakingapp.EXTRA_ITEM";

    static void updateAppWidget(Context context,
                                AppWidgetManager appWidgetManager,
                                String recipeName,
                                int appWidgetId)
    {
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.baking_widget);
        views.setTextViewText(R.id.tv_widget_recipe_name, recipeName);

        Intent intent = new Intent(context, ListViewWidgetService.class);

        views.setRemoteAdapter(R.id.lv_widget_ingredient_list, intent);
        // views.setEmptyView(R.id.lv_widget_ingredient_list, R.id.empty_view);

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

    // By default is called when the time limit expires as specified within the
    // `baking_widget_info.xml` resource file.
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                context.getResources().getString(R.string.shared_preferences_name),
                Context.MODE_PRIVATE);

        String recipeName = sharedPreferences.getString(
                context.getResources().getString(R.string.key_recipe), "Recipe");;

        // Intent service will be responsible for updating all the UI elements.
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, recipeName, appWidgetId);
        }
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

