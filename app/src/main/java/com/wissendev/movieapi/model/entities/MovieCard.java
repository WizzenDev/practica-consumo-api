package com.wissendev.movieapi.model.entities;

import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Objects;

public class MovieCard {
    private final String baseUrl = "https://image.tmdb.org/t/p/original";
    private boolean favorite = false;
    @SerializedName("id")
    private int id;
    @SerializedName("title")
    private String name;
    @SerializedName("overview")
    private String overview;
    @SerializedName("genre_ids")
    private List<String> genres;
    @SerializedName("release_date")
    private String date;
    @SerializedName("vote_average")
    private String voteAverage;
    @SerializedName("vote_count")
    private String voteCount;
    @SerializedName("poster_path")
    private String posterSrc;
    @SerializedName("backdrop_path")
    private String backdropSrc;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MovieCard movie = (MovieCard) o;
        return id == movie.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getOverview() {
        return overview;
    }

    public List<String> getGenres() {
        return genres;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

    public String getDate() {
        return date;
    }

    public String getVoteAverage() {
        return voteAverage;
    }

    public String getVoteCount() {
        return voteCount;
    }

    public String getPosterSrc() {
        return baseUrl + posterSrc;
    }

    public String getBackdropSrc() {
        return baseUrl + backdropSrc;
    }
}
