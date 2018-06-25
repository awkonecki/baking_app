package com.example.nebo.bakingapp.view;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.example.nebo.bakingapp.AppAdapter;

public abstract class AppView <D> extends RecyclerView.ViewHolder implements View.OnClickListener{

    private final AppAdapter.AdapterOnClickListener LISTENER;

    public AppView(View view, AppAdapter.AdapterOnClickListener listener) {
        super(view);
        itemView.setOnClickListener(this);
        LISTENER = listener;
    }

    @Override
    public void onClick(View v) {
        int position = getAdapterPosition();

        if (LISTENER != null) {
            LISTENER.onClick(position);
        }
    }

    public abstract void bind(D data);
}
