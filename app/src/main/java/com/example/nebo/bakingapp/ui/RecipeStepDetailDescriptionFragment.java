package com.example.nebo.bakingapp.ui;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.nebo.bakingapp.R;
import com.example.nebo.bakingapp.data.RecipeStep;
import com.example.nebo.bakingapp.databinding.FragmentRecipeStepDetailDescriptionBinding;

public class RecipeStepDetailDescriptionFragment extends Fragment {

    private FragmentRecipeStepDetailDescriptionBinding mBinding = null;

    public RecipeStepDetailDescriptionFragment() {}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_recipe_step_detail_description,
                container,
                false);

        if (mBinding != null) {
            // Attach the data.
            Bundle args = getArguments();

            if (args != null && args.containsKey(getString(R.string.key_recipe_step))) {
                RecipeStep recipeStep = args.getParcelable(getString(R.string.key_recipe_step));

                if (recipeStep != null) {
                    mBinding.tvRecipeDescription.setText(recipeStep.getDescription());
                }
            }

            return mBinding.getRoot();
        }
        else {
            return null;
        }
    }
}
