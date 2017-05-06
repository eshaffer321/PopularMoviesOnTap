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
    private static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    private static ApiInterface client = retrofit.create(ApiInterface.class);

    public static Call<MovieResults> getMovieListCall(String category){
        return client.listOfMovies( category,
                                    BuildConfig.TMDB_API_KEY,
                                    LANGUAGE,PAGE);

    }

    public static Call<Videos> getVideosCall(int id){
        return client.movieVideos(id,
                                  BuildConfig.TMDB_API_KEY,
                                  LANGUAGE);
    }

    public static Call<Reviews> getReviewsCall(int id){
        return client.movieReviews(id,
                                  BuildConfig.TMDB_API_KEY,
                                  LANGUAGE);
    }
}
