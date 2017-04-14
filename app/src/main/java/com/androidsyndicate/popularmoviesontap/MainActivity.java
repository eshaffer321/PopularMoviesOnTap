package com.androidsyndicate.popularmoviesontap;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements ImageAdapter.ListItemClickListener{
    private final String MAIN_TAG = "MainActivity";

    private RecyclerView mRecyclerView;
    private GridLayoutManager mLayoutManager;
    private boolean isMostPopular;

    private TextView mErrorMessage;
    private ProgressBar mProgressBar;

    public static String MOVIE_INDEX = "index";
    public static String MOST_POPULAR = "popular";

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


        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(NetworkUtils.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();
        MovieApi client = retrofit.create(MovieApi.class);
        Call<List<Movie>> call = client.listOfMovies(NetworkUtils.POPULAR_PATH,
                                BuildConfig.OPEN_WEATHER_MAP_API_KEY,
                                NetworkUtils.LANGUAGE,
                                NetworkUtils.PAGE);
        call.enqueue(new Callback<List<Movie>>() {
            @Override
            public void onResponse(Call<List<Movie>> call, Response<List<Movie>> response) {
                List<Movie> movies = response.body();
            }

            @Override
            public void onFailure(Call<List<Movie>> call, Throwable t) {

            }
        });

        new FetchMovieTask(this, new FetchMovieTaskListener()).execute(url);


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
            case R.id.most_rated:
                isMostPopular = false;
                URL tmdbTopRated = NetworkUtils.buildMovieUrl(NetworkUtils.TOP_RATED_PATH);
                new FetchMovieTask(this, new FetchMovieTaskListener()).execute(tmdbTopRated);
                return true;

            case R.id.most_popular:
                isMostPopular=true;
                URL tmdbMostPopular = NetworkUtils.buildMovieUrl(NetworkUtils.POPULAR_PATH);
                new FetchMovieTask(this, new FetchMovieTaskListener()).execute(tmdbMostPopular);
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
        Intent intent = new Intent(this,DetailActivity.class);
        intent.putExtra(MOVIE_INDEX, Integer.toString(moviePosterIndex));
        intent.putExtra(MOST_POPULAR, isMostPopular);
        startActivity(intent);

    }
    /*****
     * Takes the popular movie formed URL and makes a connection in the background
     * Gets the HTTP in String format.
     */
    public class FetchMovieTaskListener implements AsyncTaskCompleteListener<ArrayList<String>> {
        private final String TAGARINO = "FetchMovieTaskListener";
        @Override
        public void onJobComplete(ArrayList<String> moviePosters){
            //cancelProgressBar();
            if(moviePosters != null) {
                cancelErrorMessage();
                Context context = getApplicationContext();
                ImageAdapter myAdapter = new ImageAdapter(moviePosters, context, MainActivity.this);
                mRecyclerView.setLayoutManager(mLayoutManager);
                mRecyclerView.setAdapter(myAdapter);
            }
            else{
                showErrorMessage();
            }
        }

    }//end FetchMovieTaskListener class

}//end MainActivity class
