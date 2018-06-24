package com.example.nebo.bakingapp.view;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.example.nebo.bakingapp.AppAdapter;

public abstract class AppView <D> extends RecyclerView.ViewHolder implements View.OnClickListener{

    public AppView(View view) {
        super(view);
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int position = getAdapterPosition();
        Log.d("AppView", "OnClick Event");
    }

    public abstract void bind(D data);
}
