package com.wissendev.movieapi.model.datasource;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wissendev.movieapi.model.entities.MovieCard;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class FavoritesManager {
    private final SharedPreferences prefs;
    private final Gson gson;
    private Set<MovieCard> favorites;
    private static final String FAVORITES_KEY = "movie_favorites";

    public FavoritesManager(Context context) {
        prefs = context.getSharedPreferences("favorites_prefs", Context.MODE_PRIVATE);
        gson = new Gson();
        loadFavorites();
    }

    private void loadFavorites() {
        String jsonFavorites = prefs.getString(FAVORITES_KEY, "");
        if (jsonFavorites.isEmpty()) {
            favorites = new LinkedHashSet<>();
        } else {
            Type type = new TypeToken<LinkedHashSet<MovieCard>>() {}.getType();
            favorites = gson.fromJson(jsonFavorites, type);
        }
    }

    public void addFavorite(MovieCard movie) {
        boolean wasAdded = favorites.add(movie);
        if (wasAdded) {
            saveFavorites();
        }
    }

    public void removeFavorite(MovieCard movie) {
        if (favorites.remove(movie)) {
            saveFavorites();
        }
    }

    public List<MovieCard> getFavorites() {
        return new ArrayList<>(favorites);
    }

    private void saveFavorites() {
        String jsonFavorites = gson.toJson(favorites);
        prefs.edit().putString(FAVORITES_KEY, jsonFavorites).apply();
    }

    public boolean isFavorite(MovieCard movie) {
        return favorites.contains(movie);
    }
}
