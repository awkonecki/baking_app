package com.example.nebo.bakingapp.view;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.nebo.bakingapp.AppAdapter;
import com.example.nebo.bakingapp.data.Recipe;
import com.example.nebo.bakingapp.databinding.RecipeItemBinding;

public class RecipeView <D> extends AppView<D> {
    private final RecipeItemBinding BINDING;

    public RecipeView(RecipeItemBinding binding, AppAdapter.AdapterOnClickListener listener) {
        super(binding.getRoot(), listener);
        BINDING = binding;
    }

    @Override
    public void bind(D data) {
        if (data.getClass().equals(Recipe.class)) {
            Recipe recipe = (Recipe) data;
            BINDING.tvRecipeItemName.setText(recipe.getName());
        }
    }
}
