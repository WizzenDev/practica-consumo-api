package com.wissendev.movieapi.model;

import android.content.Context;

import com.wissendev.movieapi.model.datasource.ApiClient;
import com.wissendev.movieapi.model.datasource.FavoritesManager;
import com.wissendev.movieapi.model.entities.Genre;
import com.wissendev.movieapi.model.entities.MovieCard;
import com.wissendev.movieapi.model.utils.JsonUtils;
import com.wissendev.movieapi.model.utils.NetworkUtils;
import com.wissendev.movieapi.model.utils.SECTIONS;
import com.wissendev.movieapi.presenter.DirectorPresenter;

import java.util.HashMap;
import java.util.List;

public class DataManagement implements DataManagementPresenter{
    private final Context context;
    private final DirectorPresenter directorPresenter;
    private HashMap<String,String> genreHash;
    public DataManagement(DirectorPresenter directorPresenter, Context context) {
        this.context = context;
        this.directorPresenter = directorPresenter;
    }

    @Override
    public void obtainGenres() {
        String url = "https://api.themoviedb.org/3/genre/movie/list?language=en";
        getGenresFromApi(url);
    }

    @Override
    public void obtainMoviesData(SECTIONS section) {
        String baseUrl = "https://api.themoviedb.org/3/movie/";
        String url = "";

        switch (section){
            case NOW_PLAYING: {
                url = baseUrl + "now_playing?language=en-US&page=1";
                break;
            }
            case POPULAR: {
                url = baseUrl + "popular?language=en-US&page=1";
                break;
            }
            case TOP_RATED: {
                url = baseUrl + "top_rated?language=en-US&page=1";
                break;
            }
            case UPCOMING: {
                url = baseUrl + "upcoming?language=en-US&page=1";
                break;
            }
        }

        getMoviesFromApi(url, section);
    }
    @Override
    public void adjustGenres(List<MovieCard> movieList, SECTIONS section) {
        System.out.println("DUCK DEBUG");
        System.out.println(movieList);
        for (MovieCard movie : movieList) {
            List<String> genres = movie.getGenres();
            System.out.println(movie);
            System.out.println(genres);
            System.out.println(genreHash);
            genres.replaceAll(key -> genreHash.get(key));
            movie.setGenres(genres);
        }
        setupItemList(movieList, section);
    }

    @Override
    public void setupGenreList(List<Genre> genreList) {
        HashMap<String,String> genreHash = new HashMap<>();
        for (Genre genre : genreList) {
            System.out.println(genre.getName());
            genreHash.put(String.valueOf(genre.getId()),genre.getName());
        }
        this.genreHash = genreHash;
    }

    @Override
    public void obtainFavoriteMovies(SECTIONS section) {
        FavoritesManager favManager = new FavoritesManager(context);
        List<MovieCard> favoriteMovies = favManager.getFavorites();
        setupItemList(favoriteMovies, section);
    }

    @Override
    public void addFavoriteMovie(MovieCard movie) {
        FavoritesManager favManager = new FavoritesManager(context);
        favManager.addFavorite(movie);
    }

    @Override
    public void removeFavoriteMovie(MovieCard movie) {
        FavoritesManager favManager = new FavoritesManager(context);
        favManager.removeFavorite(movie);
    }

    @Override
    public boolean isFavorite(MovieCard movie) {
        FavoritesManager favManager = new FavoritesManager(context);
        return favManager.isFavorite(movie);
    }

    @Override
    public void setupItemList(List<MovieCard> movieList, SECTIONS section) {
        directorPresenter.setupItemsList(movieList, section);
    }

    @Override
    public void isNetworkAvailable() {
        if (NetworkUtils.isNetworkAvailable(context)) {
            directorPresenter.loadMoviesData();
            hideNetworkError();
        } else {
            showNetworkError();
        }
    }

    @Override
    public void showNetworkError() {
        directorPresenter.showNetworkError();
    }

    @Override
    public void hideNetworkError() {
        directorPresenter.hideNetworkError();
    }

    private void getMoviesFromApi(String url, SECTIONS section) {
        ApiClient apiClient = new ApiClient(this, context);
        apiClient.requestMoviesData(url, section);
    }
    private void getGenresFromApi(String url) {
        ApiClient apiClient = new ApiClient(this, context);
        apiClient.requestGenresData(url);
    }
    @Override
    public void processMoviesJson(String jsonResult, SECTIONS section) {
        JsonUtils jsonUtils = new JsonUtils(this);
        jsonUtils.processMoviesJson(jsonResult, section);
    }
    @Override
    public void processGenresJson(String jsonResult) {
        JsonUtils jsonUtils = new JsonUtils(this);
        jsonUtils.processGenresJson(jsonResult);
    }

}
