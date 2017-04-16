package com.androidsyndicate.popularmoviesontap.activity;

import android.content.Intent;
import android.content.res.Configuration;
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

import com.androidsyndicate.popularmoviesontap.ImageUrlBuilder;
import com.androidsyndicate.popularmoviesontap.model.MovieDetailsById;
import com.androidsyndicate.popularmoviesontap.network.FetchMovieByIdTask;
import com.androidsyndicate.popularmoviesontap.network.NetworkTaskCompleteInterface;
import com.androidsyndicate.popularmoviesontap.network.FetchMovieTask;
import com.androidsyndicate.popularmoviesontap.adapter.ImageAdapter;
import com.androidsyndicate.popularmoviesontap.R;
import com.androidsyndicate.popularmoviesontap.api.ApiClient;
import com.androidsyndicate.popularmoviesontap.model.MovieResults;

import java.util.List;

import retrofit2.Call;

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

    public static String MOVIE_DETAILS = "details";
    
    private int currentFlag;
    
    
    
    List<MovieResults.MovieDetails> list;
    MovieDetailsById movieDetailsById;

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

        new FetchMovieTask(this, new FetchMovieTaskListener(),
                        ApiClient.getResultsClientCall(POPULAR)).getMovieResultsResponse();

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
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()) {
            case R.id.top_rated:
                if(currentFlag != FLAG_TOP_RATED){
                    currentFlag = FLAG_TOP_RATED;
                new FetchMovieTask(this, new FetchMovieTaskListener(),
                        ApiClient.getResultsClientCall(TOP_RATED)).getMovieResultsResponse();
                }
                return true;

            case R.id.most_popular:
                if(currentFlag != FLAG_POPULAR) {
                    currentFlag = FLAG_POPULAR;
                    new FetchMovieTask(this, new FetchMovieTaskListener(),
                            ApiClient.getResultsClientCall(POPULAR)).getMovieResultsResponse();
                }
                return true;
        }
        return true;
    }
    /*****
     * Gets the index of the movie poster clicked, then starts the
     * detail activity
     * @param moviePosterIndex
     */
    @Override
    public void onMoviePosterItemClick(int moviePosterIndex) {
        int id = list.get(moviePosterIndex).getId();
        Call<MovieDetailsById> call = ApiClient.getMovieIdClientCall(id);
        new FetchMovieByIdTask(new FetchMovieByIdTaskListener(), call).getMovieDetailsResponse();
    }

    public class FetchMovieTaskListener implements NetworkTaskCompleteInterface<List<MovieResults.MovieDetails>> {
       
        @Override
        public void onJobComplete(List<MovieResults.MovieDetails> movieDetails){
            if(movieDetails != null) {
                cancelErrorMessage();
                list = movieDetails;

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
    }//end FetchMovieTaskListener class

    public class FetchMovieByIdTaskListener implements NetworkTaskCompleteInterface<MovieDetailsById>{

        @Override
        public void onJobComplete(MovieDetailsById movie){
            movieDetailsById = movie;

            Intent intent = new Intent(MainActivity.this,DetailActivity.class);
            intent.putExtra(MOVIE_DETAILS, movieDetailsById);
            startActivity(intent);
        }
    }

}//end MainActivity class
