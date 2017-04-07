package com.androidsyndicate.popularmoviesontap;

/**
 * Created by admin on 4/5/2017.
 */

public class Movie {

    private String overview;
    private String releaseDate;
    private String title;
    private double voteAverage;
    private String tagLine;
    private int budget;
    private int voteCount;
    private int runTime;
    private String movieId;
    private String movieBackdropUrl;
    private String posterPath;

    public String getMovieId() {
        return movieId;
    }

    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(double voteAverage) {
        this.voteAverage = voteAverage;
    }

    public String getTagLine() {
        return tagLine;
    }

    public void setTagLine(String tagLine) {
        this.tagLine = tagLine;
    }

    public int getBudget() {
        return budget;
    }

    public void setBudget(int budget) {
        this.budget = budget;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }

    public int getRunTime() {
        return runTime;
    }

    public void setRunTime(int runTime) {
        this.runTime = runTime;
    }

    public void setMovieBackDropUrl(String moviePosterUrl){
        this.movieBackdropUrl = moviePosterUrl;
    }

    public String getMovieBackDropUrl(){
        return movieBackdropUrl;
    }

    public String getPosterPath(){
        return posterPath;
    }

    public void setPosterPath(String posterPath){
        this.posterPath = posterPath;
    }

    @Override
    public String toString(){
        String data = "Overview: " + overview
                    + "\nRelease Date: " + releaseDate
                    + "\nVote Average: " + voteAverage
                    + "\nVote Count: " + voteCount
                    + "\nTitle: " + title
                    + "\nPoster Path: " + posterPath
                    + "\nBackDrop Path: " + movieBackdropUrl;
        return data;
    }

}
