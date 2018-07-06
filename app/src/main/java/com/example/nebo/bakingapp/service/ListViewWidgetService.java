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

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        Log.d("ListViewWidgetService", "in  onGetViewFactory");

        return new ListViewFactory(this.getApplicationContext(), intent);
    }
}
