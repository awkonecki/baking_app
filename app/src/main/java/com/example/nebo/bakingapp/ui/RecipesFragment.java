package com.example.nebo.bakingapp.ui;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.nebo.bakingapp.AppAdapter;
import com.example.nebo.bakingapp.R;
import com.example.nebo.bakingapp.RecipeActivity;
import com.example.nebo.bakingapp.data.Recipe;
import com.example.nebo.bakingapp.databinding.FragmentRecipesBinding;
import com.example.nebo.bakingapp.view.RecipeView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecipesFragment extends Fragment
        implements Callback<List<Recipe>>,
        AppAdapter.AdapterOnClickListener {

    private FragmentRecipesBinding mBinding = null;
    private AppAdapter<Recipe, RecipeView<Recipe>> mAdapter = null;
    private Context mContext;

    public RecipesFragment() {}

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState)
    {
        mBinding = DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_recipes,
                container,
                false);

        LinearLayoutManager layoutManager =
                new LinearLayoutManager(getContext(),
                        LinearLayoutManager.VERTICAL,
                        false);

        mAdapter = new AppAdapter<>(this);

        mBinding.rvRecipes.setAdapter(mAdapter);
        mBinding.rvRecipes.setLayoutManager(layoutManager);
        mBinding.rvRecipes.setHasFixedSize(true);

        return mBinding.getRoot();
    }

    @Override
    public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
        if (response != null && response.body() != null) {
            mAdapter.setData(response.body());
        }
    }

    @Override
    public void onFailure(Call<List<Recipe>> call, Throwable t) {

    }

    @Override
    public void onClick(int position) {
        // On click event has occurred, so obtain the desire recipe from the adapter.
        Recipe recipe = mAdapter.get(position);

        if (recipe != null) {
            // now will perform an intent to switch to the activity that is responsible for
            // rendering the details associated with the recipe.
            Intent intent = new Intent(getContext(), RecipeActivity.class);

            // Before starting the activity need to setup the bundle for it.
            intent.putExtra(getString(R.string.key_recipe), recipe);

            startActivity(intent);
        }
    }
}
