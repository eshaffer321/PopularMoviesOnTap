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
    private final static String API_KEY = "";


    private final static String QUERY_PARAM_LANG = "language";
    private final static String LANGUAGE = "en-US";

    private final static String QUERY_PARAM_PAGE = "page";
    private final static String PAGE = "1";

    public final static String BASE_IMAGE_URL = "http://image.tmdb.org/t/p/";
    public final static String POSTER_IMAGE_SIZE = "w185/";

    public final static String BACKDROP_IMAGE_SIZE= "w780/";

    public final static String BASE_URL = "https://api.themoviedb.org";

    public final static String MY_TAG = "NetworkUtils";


    public final static String POPULAR_PATH = "popular";
    public final static String TOP_RATED_PATH = "top_rated";


    /**********************
     * Builds a Uri for an API call to TMDB for the most popular movies.
     * Calls uriToUrl to convert the Uri to a URL then returns the URL
     *
     * @return url
     *
     */
    public static URL buildMovieUrl(String path){
        Uri uri = Uri
                .parse(BASE_URL)
                .buildUpon()
                .appendPath("3")
                .appendPath("movie")
                .appendPath(path)
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
        final String POSTER_PATH = "poster_path";
        final String OVERVIEW = "overview";
        final String RELEASE_DATE= "release_date";
        final String MOVIE_ID = "id";
        final String ORIGINAL_TITLE = "original_title";
        final String VOTE_COUNT = "vote_count";
        final String VOTE_AVERAGE = "vote_average";
        final String BACKDROP_PATH = "backdrop_path";
        final String RESULTS = "results";


        Movie currentMovieObject = new Movie();
        JSONObject mMainJSON = new JSONObject(response);
        JSONArray mJSONMovieResults = mMainJSON.getJSONArray(RESULTS);
        JSONObject movie = mJSONMovieResults.getJSONObject(Integer.parseInt(index));

        currentMovieObject.setPosterPath(movie.getString(POSTER_PATH));
        currentMovieObject.setOverview(movie.getString(OVERVIEW));
        currentMovieObject.setReleaseDate(movie.getString(RELEASE_DATE));
        currentMovieObject.setMovieId(movie.getString(MOVIE_ID));
        currentMovieObject.setTitle(movie.getString(ORIGINAL_TITLE));
        currentMovieObject.setVoteCount(movie.getInt(VOTE_COUNT));
        currentMovieObject.setVoteAverage(movie.getDouble(VOTE_AVERAGE));
        currentMovieObject.setMovieBackDropUrl(singleMoviePath(movie.getString(BACKDROP_PATH)));

        //these items are not available unless calling TMDB with movie ID
        //currentMovieObject.setBudget(movie.getInt("budget"));
        //currentMovieObject.setRunTime(movie.getInt("runtime"));
        //currentMovieObject.setTagLine(movie.getString("tagline"));

        return currentMovieObject;
    }


}//End NetworkUtils Class


