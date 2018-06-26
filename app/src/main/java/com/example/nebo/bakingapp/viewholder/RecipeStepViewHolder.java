package com.example.nebo.bakingapp.viewholder;

import android.support.annotation.NonNull;
import android.view.View;

import com.example.nebo.bakingapp.AppAdapter.AdapterOnClickListener;
import com.example.nebo.bakingapp.data.RecipeStep;

import com.example.nebo.bakingapp.databinding.RecipeStepItemBinding;

public class RecipeStepViewHolder <D> extends AppViewHolder<D> {

    private RecipeStepItemBinding mBinding = null;

    public RecipeStepViewHolder(@NonNull RecipeStepItemBinding binding, AdapterOnClickListener listener) {
        super(binding.getRoot(), listener);
        mBinding = binding;
    }

    @Override
    public void bind(D data) {
        if (data != null && data.getClass().equals(RecipeStep.class)) {
            RecipeStep recipeStep = (RecipeStep) data;

            if (mBinding != null) {
                mBinding.tvRecipeStepId.setText(Integer.toString(recipeStep.getId()));
                mBinding.tvRecipeStepDescriptionBrief.setText(recipeStep.getShortDescription());
            }
        }
    }
}
