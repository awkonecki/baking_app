package com.example.nebo.bakingapp.async;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import java.net.URL;

public class NetworkAsyncTask extends AsyncTaskLoader<String> {
    private static final String sEndPointAddress =
            "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";

    public NetworkAsyncTask(Context context) {
        super(context);
    }

    private static URL getURL(String address) {
        Uri uri = null;
        URL url = null;

        uri = Uri.parse(sEndPointAddress);

        try {
            url = new URL(uri.toString());
        }
        catch (java.net.MalformedURLException e) {
            Log.e ("Malformed URL",
                    "Error message " +
                            e.toString() +
                            " address " +
                            address +
                            " uri " +
                            uri.toString()
            );
        }

        return url;
    }

    @Nullable
    @Override
    public String loadInBackground() {

        return null;
    }
}
