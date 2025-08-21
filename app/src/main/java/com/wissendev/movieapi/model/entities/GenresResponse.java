package com.wissendev.movieapi.model.entities;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GenresResponse {
    @SerializedName("genres")
    public List<Genre> genreList;

    public List<Genre> getGenreList() {
        return genreList;
    }
}
