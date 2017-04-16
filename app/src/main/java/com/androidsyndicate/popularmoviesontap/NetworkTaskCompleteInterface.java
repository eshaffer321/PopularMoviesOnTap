package com.androidsyndicate.popularmoviesontap;

/**
 * Created by admin on 4/10/2017.
 */

public interface NetworkTaskCompleteInterface<T> {

    public void onJobComplete(T results);

}
