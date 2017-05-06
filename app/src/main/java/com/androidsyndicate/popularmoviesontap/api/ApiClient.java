package com.androidsyndicate.popularmoviesontap.api;

import com.androidsyndicate.popularmoviesontap.BuildConfig;
import com.androidsyndicate.popularmoviesontap.model.MovieResults;
import com.androidsyndicate.popularmoviesontap.model.Reviews;
import com.androidsyndicate.popularmoviesontap.model.Videos;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ApiClient {

    private final static String BASE_URL = "https://api.themoviedb.org";
    private final static String LANGUAGE = "en-US";
    private final static int PAGE = 1;
    private static Retrofit retrofit;
    private static ApiInterface client;




    public ApiClient(){
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
         client = retrofit.create(ApiInterface.class);

    }

    public Call<Reviews> getReviewCall(int movieId){
        return client.movieReviews(movieId,
                BuildConfig.TMDB_API_KEY,
                LANGUAGE);
    }

    public Call<MovieResults> getMovieListCall(String movieCategory){

        return client.listOfMovies(movieCategory,
                BuildConfig.TMDB_API_KEY,
                LANGUAGE,PAGE);
    }

    public Call<Videos> getVideoCall(int movieId){
        return client.movieVideos(movieId,
                BuildConfig.TMDB_API_KEY,
                LANGUAGE);

    }

}
