package com.example.nebo.bakingapp.data;

import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

public abstract class IngredientDataBase extends RoomDatabase {
    private static final Object LOCK = new Object();
    private static final String DATABASE_NAME = "recipe_ingredients";
    private static IngredientDataBase sInstance;

    public static IngredientDataBase getInstance(Context context) {
        if (sInstance == null) {
            synchronized (LOCK) {
                sInstance = Room.databaseBuilder(context.getApplicationContext(),
                        IngredientDataBase.class, IngredientDataBase.DATABASE_NAME).build();
            }
        }
        return sInstance;
    }
}
