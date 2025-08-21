package com.wissendev.movieapi.model.utils;

import android.os.Handler;
import android.os.Looper;

import com.google.gson.Gson;
import com.wissendev.movieapi.model.DataManagementPresenter;
import com.wissendev.movieapi.model.entities.Genre;
import com.wissendev.movieapi.model.entities.GenresResponse;
import com.wissendev.movieapi.model.entities.MovieCard;
import com.wissendev.movieapi.model.entities.MoviesResponse;

import java.util.List;

public class JsonUtils {
    private final DataManagementPresenter dataManager;
    public JsonUtils(DataManagementPresenter dataManager) {
        this.dataManager = dataManager;
    }
    public void processMoviesJson(String jsonString, SECTIONS section) {

        Gson gson = new Gson();
        MoviesResponse movieResponse = gson.fromJson(jsonString, MoviesResponse.class);
        List<MovieCard> movieList = movieResponse.getMovies();
        dataManager.adjustGenres(movieList, section);

    }
    public void processGenresJson(String jsonString) {

        Gson gson = new Gson();
        GenresResponse genresResponse = gson.fromJson(jsonString, GenresResponse.class);
        List<Genre> genreList = genresResponse.getGenreList();
        dataManager.setupGenreList(genreList);
    }
}
