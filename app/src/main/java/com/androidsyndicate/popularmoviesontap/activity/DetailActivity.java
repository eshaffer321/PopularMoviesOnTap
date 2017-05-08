package com.androidsyndicate.popularmoviesontap.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

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
    private Reviews.ReviewBean mReviewsResult;
    private Videos.VideoBean mVideosResult;
    private int mMovieIndex;

    private TextView mMovieTitle;
    private ImageView mMoviePoster;
    private TextView mMovieOverView;
    private TextView mMovieReleaseDate;
    private TextView mMovieVoteAverage;

    private YouTubePlayerView youTubePlayerView;
    private YouTubePlayer.OnInitializedListener onInitializedListener;

    private static String MY_LOG_TAG = "DetailActivity";

    //TODO(3):When a trailer is selected, use an intent to launch that trailer
    //TODO(4):When a user pressed a buttton, attached fab, it saves this movie as a favorite

    //TODO(5): movies are stored in a content provider when user selects favorite
    //TODO(6): update the content provider when the user selects to (un)favorite

    //TODO(7) Maybe use databinding instead of all the findview by ids...

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_activity);



        //mMovieTitle = (TextView)findViewById(R.id.tv_movie_title);
        ///mMoviePoster = (ImageView)findViewById(R.id.iv_details_view);
       // mMovieOverView = (TextView)findViewById(R.id.tv_overview);
        //mMovieReleaseDate = (TextView)findViewById(R.id.tv_release_date);
       // mMovieVoteAverage = (TextView)findViewById(R.id.tv_vote_average);



        Intent intent = getIntent();
        mListOfMovies =  intent.getParcelableArrayListExtra(MainActivity.MOVIE_DETAIL_TAG);
        mMovieIndex = intent.getIntExtra(MainActivity.MOVIE_INDEX_TAG, -1);
        mMovieOfInterest = mListOfMovies.get(mMovieIndex);

        mReviewsResult = intent.getParcelableExtra(MainActivity.MOVIE_VIDEO_TAG);
        mVideosResult = intent.getParcelableExtra(MainActivity.MOVIE_REVIEWS_TAG);




        youTubePlayerView = (YouTubePlayerView) findViewById(R.id.youtube_player_view);
        onInitializedListener = new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                //TODO:GET THE VIDEO PATH NEEDED
                youTubePlayer.loadVideo(mVideosResult.getKey());
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

            }
        };
        buildMovieDetails();

    }

        private void buildMovieDetails(){
            //mMovieTitle.setText(mMovieOfInterest.getTitle());
           // mMovieOverView.setText(mMovieOfInterest.getOverview());
            //mMovieReleaseDate.setText(mMovieOfInterest.getRelease_date());
           // mMovieVoteAverage.setText(Double.toString(mMovieOfInterest.getVote_average()));

            //String movieBackDropUrl = ImageUrlBuilder.getBackDropUrl(mMovieOfInterest.getBackdrop_path());

            //Picasso.with(getApplicationContext())
                    //.load(movieBackDropUrl).fit()
                    //.into(mMoviePoster);
        }



}
