package com.androidsyndicate.popularmoviesontap.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidsyndicate.popularmoviesontap.adapter.ReviewAdapter;
import com.androidsyndicate.popularmoviesontap.model.MovieResults;
import com.androidsyndicate.popularmoviesontap.model.Reviews;
import com.androidsyndicate.popularmoviesontap.model.Videos;
import com.androidsyndicate.popularmoviesontap.R;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import java.util.List;

public class DetailActivity extends YouTubeBaseActivity {

    private List<MovieResults.MoviesBean> mListOfMovies;
    private MovieResults.MoviesBean mMovieOfInterest;
    private List<Reviews.ReviewBean> mReviewsResult;
    private List<Videos.VideoBean> mVideosResult;
    private int mMovieIndex;

    RecyclerView mRecyclerView;
    LinearLayoutManager mLinearLayoutManager;



    private YouTubePlayerView youTubePlayerView;
    private YouTubePlayer.OnInitializedListener onInitializedListener;


    //TODO(3):When a trailer is selected, use an intent to launch that trailer
    //TODO(4):When a user pressed a buttton, attached fab, it saves this movie as a favorite

    //TODO(5): movies are stored in a content provider when user selects favorite
    //TODO(6): update the content provider when the user selects to (un)favorite

    //TODO(7) Maybe use databinding instead of all the findview by ids...

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_activity);

        //unpack the data
        Intent intent = getIntent();
        if(intent != null) {
            mListOfMovies = intent.getParcelableArrayListExtra(MainActivity.MOVIE_DETAIL_TAG);
            mMovieIndex = intent.getIntExtra(MainActivity.MOVIE_INDEX_TAG, -1);
            mMovieOfInterest = mListOfMovies.get(mMovieIndex);
            mReviewsResult = intent.getParcelableArrayListExtra(MainActivity.MOVIE_REVIEWS_TAG);
            mVideosResult = intent.getParcelableArrayListExtra(MainActivity.MOVIE_VIDEO_TAG);

        }

        //set up the recycler view
        mRecyclerView = (RecyclerView) findViewById(R.id.review_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        ReviewAdapter reviewAdapter = new ReviewAdapter(this,mReviewsResult);

        //time for the layout manager
        mLinearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        mRecyclerView.setAdapter(reviewAdapter);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);

        buildMovieDetails();
    }

    private void buildMovieDetails(){
        //mMovieTitle.setText(mMovieOfInterest.getTitle());
        //mMovieOverView.setText(mMovieOfInterest.getOverview());
        //mMovieReleaseDate.setText(mMovieOfInterest.getRelease_date());
        //mMovieVoteAverage.setText(Double.toString(mMovieOfInterest.getVote_average()));

        //String movieBackDropUrl = ImageUrlBuilder.getBackDropUrl(mMovieOfInterest.getBackdrop_path());

        //Picasso.with(getApplicationContext())
        //.load(movieBackDropUrl).fit()
        //.into(mMoviePoster);
    }


    public void launchYoutubeTrailer(View view) {

    }
}