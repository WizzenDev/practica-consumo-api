package com.wissendev.movieapi.model;

import com.wissendev.movieapi.model.entities.Genre;
import com.wissendev.movieapi.model.entities.MovieCard;
import com.wissendev.movieapi.model.utils.SECTIONS;

import java.util.List;

public interface DataManagementPresenter {
    void obtainMoviesData(SECTIONS section);
    void processMoviesJson(String jsonResult, SECTIONS section);
    void processGenresJson(String jsonResult);
    void setupItemList(List<MovieCard> movieList, SECTIONS section);
    void isNetworkAvailable();
    void showNetworkError();
    /** @noinspection unused*/
    void hideNetworkError();
    void obtainFavoriteMovies(SECTIONS sections);
    void addFavoriteMovie(MovieCard movie);
    void removeFavoriteMovie(MovieCard movie);
    boolean isFavorite(MovieCard movie);
    void obtainGenres();
    void adjustGenres(List<MovieCard> movieList, SECTIONS section);
    void setupGenreList(List<Genre> genreList);
}
