package com.androidsyndicate.popularmoviesontap.utils;

import android.net.Uri;

import com.androidsyndicate.popularmoviesontap.model.MovieResults;

import java.util.ArrayList;
import java.util.List;

public class ImageUrlBuilder {

    public final static String BASE_IMAGE_URL = "http://image.tmdb.org/t/p/";
    public final static String POSTER_IMAGE_SIZE = "w185/";

    public final static String BACKDROP_IMAGE_SIZE= "w780/";

    private static final String YOU_TUBE_BASE_URL = "https://www.youtube.com";
    private static final String PATH_WATCH = "watch";

    private static final String KEY_V = "v";
    public static List<String> createImagePath(List<MovieResults.MoviesBean> results){
        List<String> imagePaths = new ArrayList<>();
        for (int i = 0; i < results.size(); i++) {
            String relativePath = results.get(i).getPoster_path();
            String fullPosterPath = BASE_IMAGE_URL + POSTER_IMAGE_SIZE + relativePath;
            imagePaths.add(fullPosterPath);
        }
        return imagePaths;
    }

    public static String getBackDropUrl(String relativePath) {
        return BASE_IMAGE_URL + BACKDROP_IMAGE_SIZE + relativePath;
    }

    public static Uri buildYouTubeLink(String key){
        Uri youtubeUri = Uri.parse(YOU_TUBE_BASE_URL).buildUpon()
                                                     .appendPath(PATH_WATCH)
                                                     .appendQueryParameter(KEY_V,key)
                                                     .build();

        return youtubeUri;

    }

}//End ImageUrlBuilder Class


