package com.example.nebo.bakingapp;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.nebo.bakingapp.databinding.RecipeItemBinding;
import com.example.nebo.bakingapp.viewholder.AppViewHolder;
import com.example.nebo.bakingapp.viewholder.RecipeViewHolder;
import com.example.nebo.bakingapp.viewholder.ViewHolderFactory;

import java.util.List;

public class AppAdapter <D, VH extends AppViewHolder<D>> extends RecyclerView.Adapter<VH> {
    private List<D> mData = null;
    private final AdapterOnClickListener LISTENER;
    private final int LAYOUT_ID;

    public interface AdapterOnClickListener {
        void onClick(int position);
    }

    public AppAdapter(int layout, AdapterOnClickListener listener) {
        this.LAYOUT_ID = layout;
        this.LISTENER = listener;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        // Ugly type casting.
        return (VH) ViewHolderFactory.createViewHolder(inflater, parent, this.LAYOUT_ID, LISTENER);
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
