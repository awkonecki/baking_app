package com.example.nebo.bakingapp.viewholder;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.nebo.bakingapp.AppAdapter.AdapterOnClickListener;
import com.example.nebo.bakingapp.R;
import com.example.nebo.bakingapp.data.Ingredient;
import com.example.nebo.bakingapp.data.Recipe;
import com.example.nebo.bakingapp.data.RecipeStep;
import com.example.nebo.bakingapp.databinding.RecipeItemBinding;
import com.example.nebo.bakingapp.databinding.RecipeStepItemBinding;
import com.example.nebo.bakingapp.databinding.RecipeIngredientItemBinding;

public class ViewHolderFactory {
    public static AppViewHolder createViewHolder(@NonNull LayoutInflater inflater,
                                                 @NonNull ViewGroup viewGroup,
                                                 int layoutID,
                                                 @NonNull AdapterOnClickListener listener) {

        AppViewHolder appViewHolder = null;

        if (layoutID == R.layout.recipe_item) {
            RecipeItemBinding binding = DataBindingUtil.inflate(
                    inflater,
                    R.layout.recipe_item,
                    viewGroup,
                    false);

            appViewHolder = new RecipeViewHolder<Recipe>(binding, listener);
        }
        else if (layoutID == R.layout.recipe_step_item) {
            RecipeStepItemBinding binding = DataBindingUtil.inflate(
                    inflater,
                    R.layout.recipe_step_item,
                    viewGroup,
                    false);

            appViewHolder = new RecipeStepViewHolder<RecipeStep>(binding, listener);
        }
        else if (layoutID == R.layout.recipe_ingredient_item) {
            RecipeIngredientItemBinding binding = DataBindingUtil.inflate(
                    inflater,
                    R.layout.recipe_ingredient_item,
                    viewGroup,
                    false);

            appViewHolder = new RecipeIngredientViewHolder<Ingredient>(binding, listener);
        }
        else {
            throw new java.lang.UnsupportedOperationException(
                    "Un-supported view holder class exception"
            );
        }

        return appViewHolder;
    }
}
