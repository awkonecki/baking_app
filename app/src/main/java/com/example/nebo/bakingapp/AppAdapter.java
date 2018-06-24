package com.example.nebo.bakingapp;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.List;

public class AppAdapter <D, VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {
    private List<D> mData = null;

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {

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
}
