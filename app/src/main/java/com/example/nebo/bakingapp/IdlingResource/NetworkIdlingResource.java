package com.example.nebo.bakingapp.IdlingResource;

import android.support.annotation.Nullable;
import android.support.test.espresso.IdlingResource;
import java.util.concurrent.atomic.AtomicBoolean;

public class NetworkIdlingResource implements IdlingResource {

    @Nullable private volatile ResourceCallback mCallback;
    private AtomicBoolean mIsIdle = new AtomicBoolean(true);

    @Override
    public String getName() {
        return this.getClass().getName();
    }

    @Override
    public boolean isIdleNow() {
        return mIsIdle.get();
    }

    @Override
    public void registerIdleTransitionCallback(ResourceCallback callback) {
        mCallback = callback;
    }

    public void setmIsIdle(boolean isIdle) {
        mIsIdle.set(isIdle);
        if (isIdle && mCallback != null) {
            mCallback.onTransitionToIdle();
        }
    }
}
