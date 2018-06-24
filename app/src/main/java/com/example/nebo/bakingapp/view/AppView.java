package com.example.nebo.bakingapp.view;

import android.support.v7.widget.RecyclerView;
import android.view.View;

public abstract class AppView <D> extends RecyclerView.ViewHolder {
    public AppView(View view) {
        super(view);
    }

    public abstract void bind(D data);
}
