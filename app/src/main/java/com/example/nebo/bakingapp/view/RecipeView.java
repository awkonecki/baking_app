package com.example.nebo.bakingapp.view;

import android.support.v7.widget.RecyclerView;

import com.example.nebo.bakingapp.data.Recipe;
import com.example.nebo.bakingapp.databinding.RecipeItemBinding;

public class RecipeView <D> extends RecyclerView.ViewHolder {
    private final RecipeItemBinding BINDING;

    public RecipeView(RecipeItemBinding binding) {
        super(binding.getRoot());
        BINDING = binding;
    }

    public void bind(D data) {
        if (data.getClass().equals(Recipe.class)) {
            Recipe recipe = (Recipe) data;
            BINDING.tvRecipeItemName.setText(recipe.getName());
        }
    }
}
