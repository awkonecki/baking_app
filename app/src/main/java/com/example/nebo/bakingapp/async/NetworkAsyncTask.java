package com.example.nebo.bakingapp.async;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import com.example.nebo.bakingapp.data.Recipe;

import java.net.URL;
import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public class NetworkAsyncTask extends AsyncTaskLoader<List<Recipe>> {
    private static final String sEndPointAddress =
            "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";

    private List<Recipe> mRecipes;

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
    public List<Recipe> loadInBackground() {

        Retrofit retrofit = new Retrofit.Builder().
                baseUrl(sEndPointAddress).
                addConverterFactory(GsonConverterFactory.create()).
                client(new OkHttpClient()).
                build();
        RecipeService client = retrofit.create(RecipeService.class);

        Call<List<Recipe>> call = client.listRecipes();

        call.enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                mRecipes = response.body();
            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {
                mRecipes = null;
            }
        });

        return mRecipes;
    }

    public interface RecipeService {
        @GET("")
        Call<List<Recipe>> listRecipes();
    }
}
