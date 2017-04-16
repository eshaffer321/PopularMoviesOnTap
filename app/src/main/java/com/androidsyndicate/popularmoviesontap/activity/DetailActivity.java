package com.androidsyndicate.popularmoviesontap.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidsyndicate.popularmoviesontap.ImageUrlBuilder;
import com.androidsyndicate.popularmoviesontap.model.MovieDetailsById;
import com.androidsyndicate.popularmoviesontap.R;
import com.squareup.picasso.Picasso;

public class DetailActivity extends Activity {

    private MovieDetailsById mCurrentMovie;

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
        mCurrentMovie = intent.getParcelableExtra(MainActivity.MOVIE_DETAILS);

        buildMovieDetails();

    }

        private void buildMovieDetails(){
            mMovieTitle.setText(mCurrentMovie.getTitle());
            mMovieOverView.setText(mCurrentMovie.getOverview());
            mMovieReleaseDate.setText(mCurrentMovie.getReleaseDate());
            mMovieVoteAverage.setText(Double.toString(mCurrentMovie.getVoteAverage()));

            String movieBackDropUrl = ImageUrlBuilder.getBackDropUrl(mCurrentMovie.getBackdropPath());

            Picasso.with(getApplicationContext())
                    .load(movieBackDropUrl).fit()
                    .into(mMoviePoster);
        }


}
