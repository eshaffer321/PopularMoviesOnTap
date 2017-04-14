package com.androidsyndicate.popularmoviesontap;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by admin on 4/5/2017.
 */

public class DetailActivity extends Activity {

    private String movieIndex;
    private boolean isMostPopular;

    private Movie mCurrentMovie;

    private TextView mMovieTitle;
    private ImageView mMoviePoster;
    private TextView mMovieOverView;
    private TextView mMovieReleaseDate;
    private TextView mMovieVoteAverage;

    private static String MY_LOG_TAG = "DetailActivity";
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_activity);

        mMovieTitle = (TextView)findViewById(R.id.tv_movie_title);
        mMoviePoster = (ImageView)findViewById(R.id.iv_details_view);
        mMovieOverView = (TextView)findViewById(R.id.tv_overview);
        mMovieReleaseDate = (TextView)findViewById(R.id.tv_release_date);
        mMovieVoteAverage = (TextView)findViewById(R.id.tv_vote_average);


        Intent intent = getIntent();
        isMostPopular = intent.getBooleanExtra(MainActivity.MOST_POPULAR, false);
        movieIndex = intent.getStringExtra(MainActivity.MOVIE_INDEX);

        if(isMostPopular){
           URL mostPopularUrl = NetworkUtils.buildMovieUrl(NetworkUtils.POPULAR_PATH);
           new GetMovieDetails().execute(mostPopularUrl);
        }
        else{
            URL topRatedUrl = NetworkUtils.buildMovieUrl(NetworkUtils.TOP_RATED_PATH);
            new GetMovieDetails().execute(topRatedUrl);
        }


    }

    public class GetMovieDetails extends AsyncTask<URL,Void,Movie> {

        @Override
        protected Movie doInBackground(URL... urls) {
            URL url = urls[0];
            try {
                //Get the response from HTTP request and pass it to postExecute
                String response = NetworkUtils.getResponseFromHTTP(url);
                return  NetworkUtils.getMovieDetails(movieIndex, response);
            }
            catch (IOException |JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        private void buildMovieDetails(){
            mMovieTitle.setText(mCurrentMovie.getTitle());
            mMovieOverView.setText(mCurrentMovie.getOverview());
            mMovieReleaseDate.setText(mCurrentMovie.getReleaseDate());
            mMovieVoteAverage.setText(Double.toString(mCurrentMovie.getVoteAverage()));

            String movieBackDropUrl = mCurrentMovie.getMovieBackDropUrl();

            Picasso.with(getApplicationContext())
                    .load(movieBackDropUrl).fit()
                    .into(mMoviePoster);
        }

        @Override
        protected void onPostExecute(Movie movie){
            mCurrentMovie = movie;
            buildMovieDetails();
        }
    }//end FetchMovieTaskListener class

}
