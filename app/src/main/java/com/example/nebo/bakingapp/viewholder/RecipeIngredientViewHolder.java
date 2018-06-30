package com.example.nebo.bakingapp.viewholder;

import com.example.nebo.bakingapp.AppAdapter.AdapterOnClickListener;
import com.example.nebo.bakingapp.data.Ingredient;
import com.example.nebo.bakingapp.databinding.RecipeIngredientItemBinding;

public class RecipeIngredientViewHolder <D> extends AppViewHolder<D> {

    private RecipeIngredientItemBinding mBinding = null;

    public RecipeIngredientViewHolder(RecipeIngredientItemBinding binding,
                                      AdapterOnClickListener listener) {
        super(binding.getRoot(), listener);
        mBinding = binding;
    }

    @Override
    public void bind(D data) {
        if (data != null && data.getClass().equals(Ingredient.class)) {
            Ingredient ingredient = (Ingredient) data;

            mBinding.tvIngredientName.setText(ingredient.getIngredient());
            mBinding.tvIngredientMeasureType.setText(ingredient.getMeasure());
            mBinding.tvIngredientQuantity.setText(Float.toString(ingredient.getQuantity()));
        }
    }
}
