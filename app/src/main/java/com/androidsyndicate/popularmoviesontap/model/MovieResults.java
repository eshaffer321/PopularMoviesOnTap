
        package com.androidsyndicate.popularmoviesontap.model;

        import java.util.List;
        import android.os.Parcel;
        import android.os.Parcelable;

        import com.google.gson.annotations.Expose;
        import com.google.gson.annotations.SerializedName;

public class MovieResults implements Parcelable
{

    @SerializedName("page")
    @Expose
    private Integer page;
    @SerializedName("detailsOfMovies")
    @Expose
    private List<DetailsOfMovie> detailsOfMovies = null;
    @SerializedName("total_results")
    @Expose
    private Integer totalResults;
    @SerializedName("total_pages")
    @Expose
    private Integer totalPages;
    public final static Parcelable.Creator<MovieResults> CREATOR = new Creator<MovieResults>() {


        @SuppressWarnings({
                "unchecked"
        })
        public MovieResults createFromParcel(Parcel in) {
            MovieResults instance = new MovieResults();
            instance.page = ((Integer) in.readValue((Integer.class.getClassLoader())));
            in.readList(instance.detailsOfMovies, (DetailsOfMovie.class.getClassLoader()));
            instance.totalResults = ((Integer) in.readValue((Integer.class.getClassLoader())));
            instance.totalPages = ((Integer) in.readValue((Integer.class.getClassLoader())));
            return instance;
        }

        public MovieResults[] newArray(int size) {
            return (new MovieResults[size]);
        }

    }
            ;

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public List<DetailsOfMovie> getDetailsOfMovies() {
        return detailsOfMovies;
    }

    public void setDetailsOfMovies(List<DetailsOfMovie> detailsOfMovies) {
        this.detailsOfMovies = detailsOfMovies;
    }

    public Integer getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(Integer totalResults) {
        this.totalResults = totalResults;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(page);
        dest.writeList(detailsOfMovies);
        dest.writeValue(totalResults);
        dest.writeValue(totalPages);
    }

    public int describeContents() {
        return 0;
    }

}

