package com.wissendev.movieapi.model.entities;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MoviesResponse {
    @SerializedName("results")
    private List<MovieCard> movies;

    public List<MovieCard> getMovies() {
        return movies;
    }
}
