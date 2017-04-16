package com.androidsyndicate.popularmoviesontap;

import com.androidsyndicate.popularmoviesontap.api.ApiInterface;
import com.androidsyndicate.popularmoviesontap.model.MovieResults;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkUtils {

    public final static String BASE_IMAGE_URL = "http://image.tmdb.org/t/p/";
    public final static String POSTER_IMAGE_SIZE = "w185/";

    public final static String BACKDROP_IMAGE_SIZE= "w780/";

    public static List<String> createImagePath(List<MovieResults.MovieDetails> results){
        List<String> imagePaths = new ArrayList<>();
        for (int i = 0; i < results.size(); i++) {
            String relativePath = results.get(i).getPoster_path();
            String fullPosterPath = BASE_IMAGE_URL + POSTER_IMAGE_SIZE + relativePath;
            imagePaths.add(fullPosterPath);
        }
        return imagePaths;
    }

}//End NetworkUtils Class


