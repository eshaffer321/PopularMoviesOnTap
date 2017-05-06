package com.androidsyndicate.popularmoviesontap.activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.androidsyndicate.popularmoviesontap.model.DetailsOfMovie;
import com.androidsyndicate.popularmoviesontap.model.ReviewResult;
import com.androidsyndicate.popularmoviesontap.model.Reviews;
import com.androidsyndicate.popularmoviesontap.model.Videos;
import com.androidsyndicate.popularmoviesontap.model.VideoResult;
import com.androidsyndicate.popularmoviesontap.utils.ImageUrlBuilder;
import com.androidsyndicate.popularmoviesontap.adapter.ImageAdapter;
import com.androidsyndicate.popularmoviesontap.R;
import com.androidsyndicate.popularmoviesontap.api.ApiClient;
import com.androidsyndicate.popularmoviesontap.model.MovieResults;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements ImageAdapter.ListItemClickListener {
    private final String MAIN_TAG = "MainActivity";

    private RecyclerView mRecyclerView;
    private GridLayoutManager mLayoutManager;
    

    private TextView mErrorMessage;
    private ProgressBar mProgressBar;

    public static String MOVIE_INDEX = "index";
    public static String POPULAR = "popular";
    public static String TOP_RATED= "top_rated";
    
    
    public static final int FLAG_POPULAR = 0;
    public static final int FLAG_TOP_RATED = 1;
    public static final int FLAG_FAVORITE = 2;

    public static String MOVIE_REVIEWS_TAG = "movie-review-tag";
    public static String MOVIE_VIDEO_TAG = "movie-video-tag";
    public static String MOVIE_DETAIL_TAG = "movie-detail-tag";
    
    private int currentFlag;
    
    
    
    List<DetailsOfMovie> detailOfMovies;
    List<ReviewResult> reviewResult;
    List<VideoResult> videoResult;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mErrorMessage= (TextView)findViewById(R.id.tv_error_message);
        mProgressBar = (ProgressBar)findViewById(R.id.pb_grid_view);

        mRecyclerView = (RecyclerView)findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);


        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            mLayoutManager = new GridLayoutManager(this, 2);
        }
        else{
            mLayoutManager = new GridLayoutManager(this, 3);
        }

        createImagePosters(POPULAR);

    }

    public void showErrorMessage(){
        mErrorMessage.setVisibility(View.VISIBLE);
        mRecyclerView.setVisibility(View.INVISIBLE);
    }

    public void cancelErrorMessage(){
        mErrorMessage.setVisibility(View.INVISIBLE);
        mRecyclerView.setVisibility(View.VISIBLE);
    }

    /****************
     * These methods create an options menu where the user can select
     * to show most popular or top rated movies, it then resorts it accordingly
     * @param menu
     * @return
     */
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.my_options_menu, menu);
        return true;
    }

    //TODO(?): Make it so when the user selects a favorite, it sorts accordingly
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()) {
            case R.id.top_rated:
                if(currentFlag != FLAG_TOP_RATED){
                    currentFlag = FLAG_TOP_RATED;
                    createImagePosters(TOP_RATED);
                }
                return true;

            case R.id.most_popular:
                if(currentFlag != FLAG_POPULAR) {
                    currentFlag = FLAG_POPULAR;
                    createImagePosters(POPULAR);
                }
                return true;
            //TODO: Create a menu item in the spinner for if favorite is selected
           // case null:
                //return;
        }
        return true;
    }

    public void createImagePosters(String category){
        Call<MovieResults> call = ApiClient.getMovieListCall(TOP_RATED);
        call.enqueue(new Callback<MovieResults>() {
            @Override
            public void onResponse(Call<MovieResults> call, Response<MovieResults> response) {
                MovieResults movies = response.body();
                List<DetailsOfMovie> movieDetails = movies.getDetailsOfMovies();
                if(movieDetails != null) {
                    cancelErrorMessage();
                    detailOfMovies = movieDetails;

                    ImageAdapter myAdapter = new ImageAdapter(ImageUrlBuilder.createImagePath(movieDetails),
                            getApplicationContext(),
                            MainActivity.this);

                    mRecyclerView.setLayoutManager(mLayoutManager);
                    mRecyclerView.setAdapter(myAdapter);
                }
                else{
                    showErrorMessage();
                }
            }

            @Override
            public void onFailure(Call<MovieResults> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }


    /*****
     * Gets the index of the movie poster clicked, then starts the
     * detail activity
     * @param moviePosterIndex
     */
    @Override
    public void onMoviePosterItemClick(int moviePosterIndex) {
        //TODO: Instead of the nested call, make it so we get the videos, as well as the reviews
        int id = detailOfMovies.get(moviePosterIndex).getId();


        Call<Videos> callVideos = ApiClient.getVideosCall(id);
        Call<Reviews> callReviews = ApiClient.getReviewsCall(id);

        callVideos.enqueue(new Callback<Videos>() {
            @Override
            public void onResponse(Call<Videos> call, Response<Videos> response) {
                Videos videos = response.body();
                videoResult = videos.getVideoResults();
            }

            @Override
            public void onFailure(Call<Videos> call, Throwable t) {

            }
        });

        callReviews.enqueue(new Callback<Reviews>() {
            @Override
            public void onResponse(Call<Reviews> call, Response<Reviews> response) {
                Reviews review = response.body();
                reviewResult = review.getReviewResults();

            }

            @Override
            public void onFailure(Call<Reviews> call, Throwable t) {

            }
        });


        Intent intent = new Intent(MainActivity.this,DetailActivity.class);
        DetailsOfMovie movieOfInterest = detailOfMovies.get(moviePosterIndex);
        intent.putExtra(MOVIE_VIDEO_TAG, (Parcelable) videoResult);
        intent.putExtra(MOVIE_REVIEWS_TAG, (Parcelable) reviewResult);
        intent.putExtra(MOVIE_DETAIL_TAG, movieOfInterest);
        startActivity(intent);
    }


}//end MainActivity class
