package com.example.nebo.bakingapp.ui;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

// Playing with the databinding within the fragments to see if the two play well together.
import com.example.nebo.bakingapp.R;
import com.example.nebo.bakingapp.databinding.FragmentRecipeStepDetailsBinding;

public class RecipeStepDetailsFragment extends Fragment {

    // Basic idea for using data binding with the fragment is that the View hierarchy is not
    // traversed for manipulation, instead the class definition from the data binding provides this
    // accessors for us.
    private FragmentRecipeStepDetailsBinding mBinding = null;

    public RecipeStepDetailsFragment() {}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = null;

        if (this.mBinding == null) {
            this.mBinding = DataBindingUtil.getBinding(
                    inflater.inflate(
                            R.layout.fragment_recipe_step_details,
                            container,
                            false));
        }

        if (this.mBinding != null) {
            this.mBinding.tvStepDescription.setText("Some basic text.");
            view = this.mBinding.getRoot();
        }
        else {
            view = inflater.inflate(R.layout.fragment_recipe_step_details,
                    container,
                    false);
            TextView textView = view.findViewById(R.id.tv_step_description);
            textView.setText("Databinding failed, default text.");
        }

        return view;
    }
}
