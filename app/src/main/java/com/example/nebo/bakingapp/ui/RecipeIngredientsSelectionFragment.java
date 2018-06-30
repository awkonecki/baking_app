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
import com.example.nebo.bakingapp.databinding.FragmentRecipeIngredientsSelectionBinding;

public class RecipeIngredientsSelectionFragment extends Fragment {

    private FragmentRecipeIngredientsSelectionBinding mBinding = null;
    private OnClickIngredientsListener mCallback = null;

    public interface OnClickIngredientsListener {
        void onIngredientsClick();
    }

    public RecipeIngredientsSelectionFragment() {}

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            mCallback = (OnClickIngredientsListener) context;
        }
        catch (ClassCastException e) {
            throw new ClassCastException(
                    context.toString() + " must implement the OnClickIngredientsListener."
            );
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState)
    {
        mBinding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_recipe_ingredients_selection,
                container,
                false);

        if (mBinding != null) {
            mBinding.llIngredientSelectionLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mCallback != null) {
                        mCallback.onIngredientsClick();
                    }
                }
            });
            return mBinding.getRoot();
        }
        else {
            return null;
        }
    }
}
