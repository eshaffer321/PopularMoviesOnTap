package com.androidsyndicate.popularmoviesontap.api;

import com.androidsyndicate.popularmoviesontap.model.MovieResults;
import com.androidsyndicate.popularmoviesontap.model.Reviews;
import com.androidsyndicate.popularmoviesontap.model.Videos;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface ApiInterface {

    @GET("/3/movie/{category}")
    Call<MovieResults> listOfMovies(
            @Path("category")String category,
            @Query("api_key") String api_key,
            @Query("language") String language,
            @Query("page")int page
    );

    @GET("/3/movie/{movieId}/reviews")
    Call<Reviews> movieReviews(
            @Path("movieId") int id,
            @Query("api_key") String apiKey,
            @Query("language") String language
    );

    @GET("/3/movie/{movieId}/videos")
    Call<Videos> movieVideos(
            @Path("movieId") int id,
            @Query("api_key") String apiKey,
            @Query("language") String language
    );
}
