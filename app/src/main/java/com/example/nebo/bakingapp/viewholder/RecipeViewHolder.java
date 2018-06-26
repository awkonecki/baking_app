package com.example.nebo.bakingapp.viewholder;

import com.example.nebo.bakingapp.AppAdapter;
import com.example.nebo.bakingapp.data.Recipe;
import com.example.nebo.bakingapp.databinding.RecipeItemBinding;

public class RecipeViewHolder<D> extends AppViewHolder<D> {
    private final RecipeItemBinding BINDING;

    public RecipeViewHolder(RecipeItemBinding binding, AppAdapter.AdapterOnClickListener listener) {
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
