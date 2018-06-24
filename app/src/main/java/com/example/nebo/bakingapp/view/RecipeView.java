package com.example.nebo.bakingapp.view;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.nebo.bakingapp.databinding.RecipeItemBinding;

public class RecipeView <D> extends RecyclerView.ViewHolder {
    private final RecipeItemBinding BINDING;

    public RecipeView(RecipeItemBinding binding) {
        super(binding.getRoot());
        BINDING = binding;
    }
}
