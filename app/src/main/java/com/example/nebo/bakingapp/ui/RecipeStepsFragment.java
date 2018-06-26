package com.example.nebo.bakingapp.ui;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.nebo.bakingapp.AppAdapter;
import com.example.nebo.bakingapp.R;
import com.example.nebo.bakingapp.data.RecipeStep;
import com.example.nebo.bakingapp.databinding.FragmentRecipeStepsBinding;
import com.example.nebo.bakingapp.viewholder.RecipeStepViewHolder;

public class RecipeStepsFragment extends Fragment implements AppAdapter.AdapterOnClickListener {

    private FragmentRecipeStepsBinding mBinding = null;
    private AppAdapter<RecipeStep, RecipeStepViewHolder<RecipeStep>> mAdapter = null

    public RecipeStepsFragment() {}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_recipe_steps, container, false);

        // Creation of the recycler view constructs.
        mAdapter = new AppAdapter<RecipeStep, RecipeStepViewHolder<RecipeStep>>(this);

        mBinding.rvRecipeSteps.setAdapter(mAdapter);
        mBinding.rvRecipeSteps.setLayoutManager(
                new LinearLayoutManager(
                        getContext(),
                        LinearLayoutManager.VERTICAL,
                        false));
        mBinding.rvRecipeSteps.setHasFixedSize(true);

        return mBinding.getRoot();
    }

    @Override
    public void onClick(int position) {
        RecipeStep recipeStep = mAdapter.get(position);

        Log.d ("RecipeStepFragment", "Position Clicked " + Integer.toString(position));
    }
}
