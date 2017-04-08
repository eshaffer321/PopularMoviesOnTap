package com.androidsyndicate.popularmoviesontap;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
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

import org.json.JSONException;
import org.w3c.dom.Text;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

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

        mLayoutManager = new GridLayoutManager(this, 2);

        URL url = NetworkUtils.buildMovieUrl(NetworkUtils.POPULAR_PATH);
        isMostPopular = true;

        new MoviePosterTask().execute(url);

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
                new MoviePosterTask().execute(tmdbTopRated);
                return true;

            case R.id.most_popular:
                isMostPopular=true;
                URL tmdbMostPopular = NetworkUtils.buildMovieUrl(NetworkUtils.POPULAR_PATH);
                new MoviePosterTask().execute(tmdbMostPopular);
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
    public class MoviePosterTask extends AsyncTask<URL,Void,ArrayList<String>>{

        @Override
        protected ArrayList<String> doInBackground(URL... urls) {

            URL url = urls[0];
            try {
                String response = NetworkUtils.getResponseFromHTTP(url);
                if(response != null) {
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
        protected void onPostExecute(ArrayList<String> moviePosters){
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
    }//end MoviePosterTask class
}//end MainActivity class
