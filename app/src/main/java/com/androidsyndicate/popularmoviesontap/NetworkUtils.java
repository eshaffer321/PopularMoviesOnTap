package com.androidsyndicate.popularmoviesontap;

import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkUtils {

    public final static String QUERY_PARAM_API = "api_key";

    public final static String QUERY_PARAM_LANG = "language";
    public final static String LANGUAGE = "en-US";

    public final static String QUERY_PARAM_PAGE = "page";
    public final static String PAGE = "1";

    public final static String BASE_IMAGE_URL = "http://image.tmdb.org/t/p/";
    public final static String POSTER_IMAGE_SIZE = "w185/";

    public final static String BACKDROP_IMAGE_SIZE= "w780/";

    public final static String BASE_URL = "https://api.themoviedb.org";

    public final static String MY_TAG = "NetworkUtils";


    public final static String POPULAR_PATH = "popular";
    public final static String TOP_RATED_PATH = "top_rated";


    public static boolean sendNetworkRequest(String category){
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();
        MovieApi client = retrofit.create(MovieApi.class);
        Call<List<Movie>> call = client.listOfMovies(category);
        call.enqueue(new Callback<List<Movie>>() {
            @Override
            public void onResponse(Call<List<Movie>> call, Response<List<Movie>> response) {
                List<Movie> movies = response.body();
            }

            @Override
            public void onFailure(Call<List<Movie>> call, Throwable t) {

            }
        });

        return true;

    }

}//End NetworkUtils Class


