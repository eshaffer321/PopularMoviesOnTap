package com.androidsyndicate.popularmoviesontap.network;

import android.content.Context;

import com.androidsyndicate.popularmoviesontap.model.MovieDetailsById;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FetchMovieByIdTask {

    private Context context;
    private NetworkTaskCompleteInterface<MovieDetailsById> listener;
    private Call<MovieDetailsById> call;

    public FetchMovieByIdTask(NetworkTaskCompleteInterface<MovieDetailsById> listener,
                          Call<MovieDetailsById> call){
        this.listener = listener;
        this.call = call;
    }

    public void getMovieDetailsResponse(){
        call.enqueue(new Callback<MovieDetailsById>() {
            @Override
            public void onResponse(Call<MovieDetailsById> call, Response<MovieDetailsById> response) {
                MovieDetailsById details = response.body();
                listener.onJobComplete(details);
            }

            @Override
            public void onFailure(Call<MovieDetailsById> call, Throwable t) {
                t.printStackTrace();
            }
        });

    }

}
