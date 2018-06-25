package com.example.nebo.bakingapp;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.nebo.bakingapp.databinding.RecipeItemBinding;
import com.example.nebo.bakingapp.view.AppView;
import com.example.nebo.bakingapp.view.RecipeView;

import java.util.List;

public class AppAdapter <D, VH extends AppView<D>> extends RecyclerView.Adapter<VH> {
    private List<D> mData = null;
    private final AdapterOnClickListener LISTENER;

    public interface AdapterOnClickListener {
        void onClick(int position);
    }

    public AppAdapter(AdapterOnClickListener listener) {
        this.LISTENER = listener;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        RecipeItemBinding binding = DataBindingUtil.inflate(inflater, R.layout.recipe_item, parent, false);

        // hard code the Recipe view for now.
        RecipeView<D> recipeView = new RecipeView<D>(binding, LISTENER);

        return (VH) recipeView;
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        if (mData != null && position < mData.size()) {
            holder.bind(mData.get(position));
        }
    }

    @Override
    public int getItemCount() {
        if (this.mData == null) {
            return 0;
        }
        else {
            return this.mData.size();
        }
    }

    public void setData(List<D> data) {
        if (data != null) {
            Log.d ("AppAdapter", "Updating the Adapter Data Set with " + Integer.toString(data.size()));
            this.mData = data;
            notifyDataSetChanged();
        }
    }

    public D get(int index) {
        if (index < 0 || index >= this.mData.size()) {
            return null;
        }
        else {
            return this.mData.get(index);
        }
    }
}
