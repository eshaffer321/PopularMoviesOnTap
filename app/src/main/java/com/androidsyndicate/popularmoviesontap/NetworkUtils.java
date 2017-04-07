package com.androidsyndicate.popularmoviesontap;


import android.net.Uri;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Scanner;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class NetworkUtils {

    private final static String QUERY_PARAM_API = "api_key";
    private final static String API_KEY = "65b0f0c1dca6b0957d34d1fceaf3107a";

    //consider a different method to use different languages
    private final static String QUERY_PARAM_LANG = "language";
    private final static String LANGUAGE = "en-US";

    private final static String QUERY_PARAM_PAGE = "page";
    private final static String PAGE = "1";

    public final static String BASE_IMAGE_URL = "http://image.tmdb.org/t/p/";
    public final static String POSTER_IMAGE_SIZE = "w185/";

    public final static String BACKDROP_IMAGE_SIZE= "w780/";

    public final static String BASE_URL = "https://api.themoviedb.org";

    public final static String MY_TAG = "NetworkUtils";



    /**********************
     * Builds a Uri for an API call to TMDB for the most popular movies.
     * Calls uriToUrl to convert the Uri to a URL then returns the URL
     *
     * @return url
     *
     */
    public static URL buildPopularMoviesUrl() {
        Uri uri = Uri
                .parse(BASE_URL)
                .buildUpon()
                .appendPath("3")
                .appendPath("movie")
                .appendPath("popular")
                .appendQueryParameter(QUERY_PARAM_API, API_KEY)
                .appendQueryParameter(QUERY_PARAM_LANG, LANGUAGE)
                .appendQueryParameter(QUERY_PARAM_PAGE, PAGE)
                .build();
        URL url = convertToUrl(uri);
        return url;
    }

    public static URL buildTopRatedMovieUrl(){
        Uri uri = Uri
                .parse(BASE_URL)
                .buildUpon()
                .appendPath("3")
                .appendPath("movie")
                .appendPath("top_rated")
                .appendQueryParameter(QUERY_PARAM_API,API_KEY)
                .appendQueryParameter(QUERY_PARAM_LANG,LANGUAGE)
                .appendQueryParameter(QUERY_PARAM_PAGE,PAGE)
                .build();
                URL url = convertToUrl(uri);
        return url;
    }

    /************
     * Overloaded methods that will convert the parameter into a URL and return
     * @param uri
     * @return url
     */
    public static URL convertToUrl(Uri uri) {
        try {
            return new URL(uri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static URL convertToUrl(String path){
        try{
            return new URL(path);
        }
        catch(MalformedURLException e){
            e.printStackTrace();
        }
        return null;
    }
    /*******************************
     * This method is used to form a connection to a url and form a String.
     * In our case this is going to be a String with a JSONObject inside.
     *
     * @param url
     * @return
     * @throws IOException
     */
    public static String getResponseFromHTTP(URL url) throws IOException {
        HttpURLConnection httpConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = httpConnection.getInputStream();

            Scanner scan = new Scanner(in);
            scan.useDelimiter("\\A");

            boolean hasInput = scan.hasNext();
            if (hasInput) {
                return scan.next();
            }
            else {
                return null;
            }
        }
        finally {
            httpConnection.disconnect();
        }
    }

    public static String singleMoviePath(String relativePath){
        String fullPosterUrl = BASE_IMAGE_URL + BACKDROP_IMAGE_SIZE + relativePath;
        return fullPosterUrl;
    }



    public static ArrayList<String> getImagePath(String responseFromHTTP) throws JSONException {
        ArrayList<String> imagePaths = new ArrayList<>();
        JSONObject mMainJSON = new JSONObject(responseFromHTTP);
        JSONArray mJSONMovieResults = mMainJSON.getJSONArray("results");

        for (int i = 0; i<mJSONMovieResults.length(); i++) {
            JSONObject moviePath = mJSONMovieResults.getJSONObject(i);
            String relativePath = moviePath.getString("poster_path");
            String fullPosterUrl = BASE_IMAGE_URL + POSTER_IMAGE_SIZE + relativePath;
            imagePaths.add(fullPosterUrl);
        }
        return imagePaths;
    }



    public static Movie getMovieDetails(String index, String response) throws JSONException {
        Movie currentMovieObject = new Movie();
        JSONObject mMainJSON = new JSONObject(response);
        JSONArray mJSONMovieResults = mMainJSON.getJSONArray("results");

        JSONObject movie = mJSONMovieResults.getJSONObject(Integer.parseInt(index));

        currentMovieObject.setPosterPath(movie.getString("poster_path"));
        currentMovieObject.setOverview(movie.getString("overview"));
        currentMovieObject.setReleaseDate(movie.getString("release_date"));
        currentMovieObject.setMovieId(movie.getString("id"));
        currentMovieObject.setTitle(movie.getString("original_title"));

        currentMovieObject.setVoteCount(movie.getInt("vote_count"));
        currentMovieObject.setVoteAverage(movie.getDouble("vote_average"));
        currentMovieObject.setMovieBackDropUrl(singleMoviePath(movie.getString("backdrop_path")));

        Log.v(MY_TAG, currentMovieObject.toString());

        //currentMovieObject.setBudget(movie.getInt("budget"));
        //currentMovieObject.setRunTime(movie.getInt("runtime"));
        //currentMovieObject.setTagLine(movie.getString("tagline"));





        return currentMovieObject;

    }


}//End NetworkUtils Class


