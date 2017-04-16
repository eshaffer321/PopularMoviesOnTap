package com.androidsyndicate.popularmoviesontap.api;

import com.androidsyndicate.popularmoviesontap.BuildConfig;
import com.androidsyndicate.popularmoviesontap.model.MovieDetailsById;
import com.androidsyndicate.popularmoviesontap.model.MovieResults;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ApiClient {

    private final static String BASE_URL = "https://api.themoviedb.org";
    private final static String LANGUAGE = "en-US";
    private final static int PAGE = 1;

    public static Call<MovieResults> getResultsClientCall(String category){
        Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl(BASE_URL)
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();
        ApiInterface client = retrofit.create(ApiInterface.class);
        return client.listOfMovies( category,
                                    BuildConfig.TMDB_API_KEY,
                                    LANGUAGE,PAGE);

    }

    public static Call<MovieDetailsById> getMovieIdClientCall(int id){
        Retrofit retrofit = new Retrofit.Builder()
                                .baseUrl(BASE_URL)
                                .addConverterFactory(GsonConverterFactory.create())
                                .build();
        ApiInterface client = retrofit.create(ApiInterface.class);
        return client.movieDetails(id, BuildConfig.TMDB_API_KEY, LANGUAGE);
    }
}
