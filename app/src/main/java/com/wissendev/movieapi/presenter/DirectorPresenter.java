package com.wissendev.movieapi.presenter;

import com.wissendev.movieapi.model.entities.MovieCard;
import com.wissendev.movieapi.model.utils.SECTIONS;

import java.util.List;

public interface DirectorPresenter {
    void loadMoviesData();
    void setupItemsList(List<MovieCard> movieList, SECTIONS section);
    void valideNetwork();
    void showNetworkError();
    /** @noinspection unused, EmptyMethod */
    void showServerError();
    void obtainFavoriteMovies();
    void addFavoriteMovie(MovieCard movie);
    void removeFavoriteMovie(MovieCard movie);
    boolean isFavorite(MovieCard movie);
    void hideNetworkError();
}
