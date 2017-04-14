package com.androidsyndicate.popularmoviesontap;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class FetchMovieTask extends AsyncTask<URL, Void, ArrayList<String>> {
    private final String MY_TAG = "FetchMovieTask";
    private Context context;
    private AsyncTaskCompleteListener<ArrayList<String>> listener;

    public FetchMovieTask(Context context, AsyncTaskCompleteListener<ArrayList<String>> listener){
        this.context = context;
        this.listener = listener;
    }

    @Override
    protected ArrayList<String> doInBackground(URL... urls) {

        URL url = urls[0];
        try {
            String response = NetworkUtils.getResponseFromHTTP(url);
            if(response != null) {
                Log.v(MY_TAG, response);
                return NetworkUtils.getImagePath(response);
            }
            return null;
        }
        catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(ArrayList<String> results){
        super.onPostExecute(results);
        listener.onJobComplete(results);
    }
}
