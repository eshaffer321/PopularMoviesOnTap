package com.androidsyndicate.popularmoviesontap;

import android.content.Context;

import com.androidsyndicate.popularmoviesontap.model.MovieDetailsById;
import com.androidsyndicate.popularmoviesontap.model.MovieResults;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FetchMovieTask  {

    private final String MY_TAG = "FetchMovieTask";
    private Context context;

    private NetworkTaskCompleteInterface<List<MovieResults.MovieDetails>> listener;
    private Call<MovieResults> call;

    private NetworkTaskCompleteInterface<MovieDetailsById> idListener;
    private Call<MovieDetailsById> idCall;




    public FetchMovieTask(Context context,
                          NetworkTaskCompleteInterface<List<MovieResults.MovieDetails>> listener,
                          Call<MovieResults> call) {
        this.context = context;
        this.listener = listener;
        this.call = call;
    }

    public FetchMovieTask(NetworkTaskCompleteInterface<MovieDetailsById> listener,
                          Call<MovieDetailsById> call){
        idCall = call;
        idListener = listener;
    }

    public void getMovieResultsResponse() {
        call.enqueue(new Callback<MovieResults>() {
            @Override
            public void onResponse(Call<MovieResults> call, Response<MovieResults> response) {
                MovieResults movies = response.body();
                List<MovieResults.MovieDetails> movieDetails = movies.getResults();
                listener.onJobComplete(movieDetails);
            }

            @Override
            public void onFailure(Call<MovieResults> call, Throwable t) {
                t.printStackTrace();
            }

        });
    }

    public void getMovieDetailsResponse(){
        idCall.enqueue(new Callback<MovieDetailsById>() {
            @Override
            public void onResponse(Call<MovieDetailsById> call, Response<MovieDetailsById> response) {
                MovieDetailsById details = response.body();
                idListener.onJobComplete(details);
            }

            @Override
            public void onFailure(Call<MovieDetailsById> call, Throwable t) {

            }
        });

    }

}//end fetchmovietask class
