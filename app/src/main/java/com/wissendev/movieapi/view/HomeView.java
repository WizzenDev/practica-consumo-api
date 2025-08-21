package com.wissendev.movieapi.view;

import com.wissendev.movieapi.model.entities.MovieCard;
import com.wissendev.movieapi.model.utils.SECTIONS;

import java.util.List;

public interface HomeView {
    void setupAdapter(List<MovieCard> movieList, SECTIONS section);
    void displayLoading();
    void hideLoading();
    void displayNetworkError();
    /** @noinspection unused, EmptyMethod */
    void displayServerError();
    void hideNetworkError();
}
