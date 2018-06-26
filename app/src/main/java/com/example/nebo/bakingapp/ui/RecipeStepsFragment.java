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

import java.util.List;

public class RecipeStepsFragment extends Fragment implements AppAdapter.AdapterOnClickListener {

    private FragmentRecipeStepsBinding mBinding = null;
    private AppAdapter<RecipeStep, RecipeStepViewHolder<RecipeStep>> mAdapter = null;
    private List<RecipeStep> mRecipeSteps = null;

    public RecipeStepsFragment() {}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_recipe_steps, container, false);

        // Creation of the recycler view constructs.
        mAdapter = new AppAdapter<RecipeStep, RecipeStepViewHolder<RecipeStep>>(R.layout.recipe_step_item,this);

        mBinding.rvRecipeSteps.setAdapter(mAdapter);
        mBinding.rvRecipeSteps.setLayoutManager(
                new LinearLayoutManager(
                        getContext(),
                        LinearLayoutManager.VERTICAL,
                        false));
        mBinding.rvRecipeSteps.setHasFixedSize(true);

        // Set the data of the adapter.
        Bundle bundleArgs = getArguments();

        if (bundleArgs != null && bundleArgs.containsKey(getString(R.string.key_recipe_steps))) {
            mRecipeSteps = bundleArgs.getParcelableArrayList(getString(R.string.key_recipe_steps));
        }

        mAdapter.setData(mRecipeSteps);

        return mBinding.getRoot();
    }

    @Override
    public void onClick(int position) {
        RecipeStep recipeStep = mAdapter.get(position);

        Log.d ("RecipeStepFragment", "Position Clicked " + Integer.toString(position));
    }
}
