package com.androidsyndicate.popularmoviesontap.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidsyndicate.popularmoviesontap.model.DetailsOfMovie;
import com.androidsyndicate.popularmoviesontap.model.ReviewResult;
import com.androidsyndicate.popularmoviesontap.model.VideoResult;
import com.androidsyndicate.popularmoviesontap.utils.ImageUrlBuilder;
import com.androidsyndicate.popularmoviesontap.R;
import com.squareup.picasso.Picasso;

public class DetailActivity extends Activity {

    private DetailsOfMovie mCurrentMovie;
    private VideoResult mVideoResult;
    private ReviewResult mReviewResult;

    private TextView mMovieTitle;
    private ImageView mMoviePoster;
    private TextView mMovieOverView;
    private TextView mMovieReleaseDate;
    private TextView mMovieVoteAverage;

    private static String MY_LOG_TAG = "DetailActivity";

    //TODO(3):When a trailer is selected, use an intent to launch that trailer
    //TODO(4):When a user pressed a buttton, attached fab, it saves this movie as a favorite

    //TODO(5): movies are stored in a content provider when user selects favorite
    //TODO(6): update the content provider when the user selects to (un)favorite

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_activity);

        mMovieTitle = (TextView)findViewById(R.id.tv_movie_title);
        mMoviePoster = (ImageView)findViewById(R.id.iv_details_view);
        mMovieOverView = (TextView)findViewById(R.id.tv_overview);
        mMovieReleaseDate = (TextView)findViewById(R.id.tv_release_date);
        mMovieVoteAverage = (TextView)findViewById(R.id.tv_vote_average);

        Intent intent = getIntent();
        mCurrentMovie = intent.getParcelableExtra(MainActivity.MOVIE_DETAIL_TAG);
        mVideoResult = intent.getParcelableExtra(MainActivity.MOVIE_VIDEO_TAG);
        mReviewResult = intent.getParcelableExtra(MainActivity.MOVIE_REVIEWS_TAG);

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
