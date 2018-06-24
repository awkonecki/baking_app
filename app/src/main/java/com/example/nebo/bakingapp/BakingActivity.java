package com.example.nebo.bakingapp;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.nebo.bakingapp.databinding.ActivityBakingBinding;

public class BakingActivity extends AppCompatActivity {
    private static ActivityBakingBinding mBinding = null;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BakingActivity.mBinding = DataBindingUtil.setContentView(
                this,
                R.layout.activity_baking);


    }
}
