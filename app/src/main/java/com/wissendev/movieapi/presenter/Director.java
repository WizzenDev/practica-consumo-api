package com.wissendev.movieapi.presenter;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import com.wissendev.movieapi.model.DataManagement;
import com.wissendev.movieapi.model.DataManagementPresenter;
import com.wissendev.movieapi.model.entities.MovieCard;
import com.wissendev.movieapi.model.utils.SECTIONS;
import com.wissendev.movieapi.view.HomeView;

import java.util.List;

public class Director implements DirectorPresenter{
    private final HomeView homeView;
    private final DataManagementPresenter dataManagement;
    public Director(HomeView homeView, Context context) {
        this.homeView = homeView;
        dataManagement = new DataManagement(this,context);
    }
    @Override
    public void loadMoviesData() {
        dataManagement.obtainGenres();
        dataManagement.obtainMoviesData(SECTIONS.NOW_PLAYING);
        dataManagement.obtainMoviesData(SECTIONS.POPULAR);
        dataManagement.obtainMoviesData(SECTIONS.TOP_RATED);
        dataManagement.obtainMoviesData(SECTIONS.UPCOMING);
        dataManagement.obtainFavoriteMovies(SECTIONS.FAVORITE);
        new Handler(Looper.getMainLooper()).postDelayed(homeView::hideLoading,2000);
    }

    @Override
    public void setupItemsList(List<MovieCard> movieList, SECTIONS section) {
        homeView.setupAdapter(movieList,section);
    }

    @Override
    public void valideNetwork() {
        dataManagement.isNetworkAvailable();
    }

    @Override
    public void showNetworkError() {
        homeView.displayNetworkError();
    }

    @Override
    public void showServerError() {

    }
    @Override
    public void obtainFavoriteMovies() {
        dataManagement.obtainFavoriteMovies(SECTIONS.FAVORITE);
    }

    @Override
    public void addFavoriteMovie(MovieCard movie) {
        dataManagement.addFavoriteMovie(movie);
    }

    @Override
    public void removeFavoriteMovie(MovieCard movie) {
        dataManagement.removeFavoriteMovie(movie);
    }

    @Override
    public boolean isFavorite(MovieCard movie) {
        return dataManagement.isFavorite(movie);
    }

    @Override
    public void hideNetworkError() {
        homeView.hideNetworkError();
    }

}
