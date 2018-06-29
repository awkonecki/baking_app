package com.example.nebo.bakingapp.ui;

import android.content.Context;
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
import com.example.nebo.bakingapp.databinding.FragmentRecipeStepDetailBinding;

public class RecipeStepDetailFragment extends Fragment {
    private FragmentRecipeStepDetailBinding mBinding = null;
    private RecipeStep mRecipeStep = null;

    public RecipeStepDetailFragment() {}

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_recipe_step_detail,
                container,
                false);

        Bundle fragmentArgs = getArguments();

        if (fragmentArgs != null && fragmentArgs.containsKey(getString(R.string.key_recipe_step))) {
            mRecipeStep = fragmentArgs.getParcelable(getString(R.string.key_recipe_step));
        }

        if (mRecipeStep != null) {
            mBinding.description.tvRecipeDescription.setText(mRecipeStep.getDescription());
        }

        if (mBinding != null) {
            return mBinding.getRoot();
        }
        else {
            return null;
        }
    }
}
