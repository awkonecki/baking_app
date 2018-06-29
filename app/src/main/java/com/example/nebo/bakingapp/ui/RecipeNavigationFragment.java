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
import com.example.nebo.bakingapp.databinding.FragmentRecipeNavigationBinding;

public class RecipeNavigationFragment extends Fragment {

    private FragmentRecipeNavigationBinding mBinding = null;

    public RecipeNavigationFragment() {}

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_recipe_navigation,
                container,
                false);

        if (mBinding == null) {
            return null;
        }
        else {
            return mBinding.getRoot();
        }
    }
}
