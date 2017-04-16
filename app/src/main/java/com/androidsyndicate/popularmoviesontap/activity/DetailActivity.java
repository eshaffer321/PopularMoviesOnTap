package com.androidsyndicate.popularmoviesontap.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidsyndicate.popularmoviesontap.model.MovieResults;
import com.androidsyndicate.popularmoviesontap.NetworkUtils;
import com.androidsyndicate.popularmoviesontap.R;
import com.squareup.picasso.Picasso;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;

/**
 * Created by admin on 4/5/2017.
 */

public class DetailActivity extends Activity {

    private String movieIndex;
    private boolean isMostPopular;

    private MovieResults mCurrentMovie;

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
        //isMostPopular = intent.getBooleanExtra(MainActivity.MOST_POPULAR, false);
        movieIndex = intent.getStringExtra(MainActivity.MOVIE_INDEX);




    }

        //TODO:This is totally broken and needs to be fixed

       // private void buildMovieDetails(){
           // mMovieTitle.setText(mCurrentMovie.getTitle());
           // mMovieOverView.setText(mCurrentMovie.getOverview());
           // mMovieReleaseDate.setText(mCurrentMovie.getReleaseDate());
           // mMovieVoteAverage.setText(Double.toString(mCurrentMovie.getVoteAverage()));

          //  String movieBackDropUrl = mCurrentMovie.getMovieBackDropUrl();

           // Picasso.with(getApplicationContext())
               //     .load(movieBackDropUrl).fit()
               //     .into(mMoviePoster);
       // }


    //}//end FetchMovieTaskListener class

}
