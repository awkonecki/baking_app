package com.example.nebo.bakingapp.service;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.Nullable;

import com.example.nebo.bakingapp.BakingWidgetProvider;
import com.example.nebo.bakingapp.R;
import com.example.nebo.bakingapp.data.Ingredient;
import com.example.nebo.bakingapp.data.RecipeContract;

import java.util.ArrayList;
import java.util.List;

public class IngredientService extends IntentService {
    public static final String ACTION_LIST_INGREDIENTS = "com.example.nebo.bakingapp.action.list_ingredients";

    public IngredientService() {
        super("IngredientService");
    }

    public static void startActionListIngredients(Context context) {
        Intent intent = new Intent(context, IngredientService.class);
        intent.setAction(IngredientService.ACTION_LIST_INGREDIENTS);
        context.startService(intent);
    }

    private void handleActionListIngredients() {
        SharedPreferences sharedPreferences = this.getSharedPreferences(
                this.getResources().getString(R.string.shared_preferences_name), MODE_PRIVATE);
        Uri uri = RecipeContract.RecipeIngredient.CONTENT_URI;
        Cursor cursor = null;
        ArrayList<Ingredient> ingredients = null;
        String recipeName = null;

        if (sharedPreferences.contains(getString(R.string.key_recipe))) {
            recipeName = sharedPreferences.getString(getString(R.string.key_recipe), null);
            String selection = RecipeContract.RecipeIngredient.COLUMN_RECIPE_NAME + "=?";
            String[] selectionArgs = new String[]{recipeName};

            cursor = getContentResolver().query(uri,
                    null,
                    selection,
                    selectionArgs,
                    null);

            if (cursor != null) {

                if (cursor.getCount() > 0) {
                    ingredients = new ArrayList<>(cursor.getCount());
                    cursor.moveToFirst();

                    do {
                        Ingredient ingredient = new Ingredient(0,
                                cursor.getFloat(cursor.getColumnIndex(
                                        RecipeContract.RecipeIngredient.COLUMN_QUANTITY)),
                                cursor.getString(cursor.getColumnIndex(
                                        RecipeContract.RecipeIngredient.COLUMN_MEASURING)),
                                cursor.getString(cursor.getColumnIndex(
                                        RecipeContract.RecipeIngredient.COLUMN_INGREDIENT)));
                        ingredients.add(ingredient);
                    } while (cursor.moveToNext());
                }

                cursor.close();
            }

            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
            // Update the recipe ingredient list
            BakingWidgetProvider.updateRecipeIngredientListWidgets(this,
                    appWidgetManager,
                    ingredients,
                    recipeName,
                    appWidgetManager.getAppWidgetIds(new ComponentName(
                            this,
                            BakingWidgetProvider.class)));
        }
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (action.equals(ACTION_LIST_INGREDIENTS)) {
                handleActionListIngredients();
            }
        }
    }
}
