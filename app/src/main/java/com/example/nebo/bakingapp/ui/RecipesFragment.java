package com.example.nebo.bakingapp.ui;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.nebo.bakingapp.AppAdapter;
import com.example.nebo.bakingapp.R;
import com.example.nebo.bakingapp.data.Recipe;
import com.example.nebo.bakingapp.databinding.FragmentRecipesBinding;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecipesFragment extends Fragment implements Callback<List<Recipe>> {

    private FragmentRecipesBinding mBinding = null;

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

        AppAdapter<Recipe, RecyclerView.ViewHolder> adapter = new AppAdapter<>();

        mBinding.rvRecipes.setAdapter(adapter);
        mBinding.rvRecipes.setLayoutManager(layoutManager);
        mBinding.rvRecipes.setHasFixedSize(true);

        return mBinding.getRoot();
    }

    @Override
    public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
        if (response != null && response.body() != null) {
            AppAdapter<Recipe, RecyclerView.ViewHolder> adapter = null;

            // Warning of unchecked cast.
            if (mBinding.rvRecipes.getAdapter() instanceof AppAdapter) {
                adapter = (AppAdapter) mBinding.rvRecipes.getAdapter();

                adapter.setData(response.body());
            }
        }
    }

    @Override
    public void onFailure(Call<List<Recipe>> call, Throwable t) {

    }
}
