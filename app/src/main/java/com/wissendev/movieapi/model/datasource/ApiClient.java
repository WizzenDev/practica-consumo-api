package com.wissendev.movieapi.model.datasource;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.wissendev.movieapi.model.DataManagementPresenter;
import com.wissendev.movieapi.model.utils.SECTIONS;
import com.wissendev.movieapi.model.utils.TYPE;

import java.util.HashMap;
import java.util.Map;

public class ApiClient {
    private final DataManagementPresenter dataManager;
    private final Context context;
    private SECTIONS requestSection;
    private TYPE dataType;
    private String jsonResult;
    public ApiClient(DataManagementPresenter dataManager, Context context) {
        this.dataManager = dataManager;
        this.context = context;

    }
    public void requestMoviesData(String url, SECTIONS section) {
        dataType = TYPE.MOVIE;
        requestSection = section;
        requestData(url);
    }
    public void requestGenresData(String url) {
        dataType = TYPE.GENRE;
        requestData(url);
    }
    public void requestData(String url) {

        RequestQueue queue = Volley.newRequestQueue(context);
        int method = Request.Method.GET;
        String token = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJhMWZiNGFhZWUxOGJmMTcxNGRhNzI3N2QwMTE3MWNkNyIsIm5iZiI6MTc1NTM2MTk4OC4wOTQsInN1YiI6IjY4YTBiMmM0MTNhYzA4YzBhZDE4YzU4ZSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.2KuKhrXXyTVcN_tVnswNPIHWKAohvXKodd69ADarnGc";

        JsonObjectRequest request = new JsonObjectRequest(
                method,
                url,
                null,
                response -> {
                    jsonResult = response.toString();
                    Log.d("API_RESULT", jsonResult);
                    returnResponse(jsonResult);
                },
                error -> {
                    Log.e("API_ERROR", "Error: " + error.getMessage());
                    jsonResult = "";
                    returnResponse(jsonResult);
                    dataManager.showNetworkError();
                } )
        {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                headers.put("accept", "application/json");
                headers.put("Authorization", token);
                return headers;
            }
        };

        queue.add(request);

    }

    private void returnResponse(String jsonResult) {
        switch (dataType) {
            case MOVIE: {
                if (jsonResult.isBlank()) {
                    jsonResult = "{\"page\":1,\"results\":[{\"adult\":false,\"backdrop_path\":\"/images/no_signal_backdrop.jpg\",\"genre_ids\":[0],\"id\":0,\"original_language\":\"xx\",\"original_title\":\"Connection Lost\",\"overview\":\"It seems you've drifted off the grid. Please check your network settings and try again.\",\"popularity\":0.0,\"poster_path\":\"/images/no_signal_poster.jpg\",\"release_date\":\"1970-01-01\",\"title\":\"Connection Lost\",\"video\":false,\"vote_average\":0.0,\"vote_count\":0},{\"adult\":false,\"backdrop_path\":\"/images/offline_mode_backdrop.jpg\",\"genre_ids\":[0],\"id\":-1,\"original_language\":\"xx\",\"original_title\":\"Offline Mode\",\"overview\":\"You're now exploring in offline mode. Some features may be limited until you reconnect.\",\"popularity\":0.0,\"poster_path\":\"/images/offline_mode_poster.jpg\",\"release_date\":\"1970-01-01\",\"title\":\"Offline Mode\",\"video\":false,\"vote_average\":0.0,\"vote_count\":0},{\"adult\":false,\"backdrop_path\":\"/images/signal_outage_backdrop.jpg\",\"genre_ids\":[0],\"id\":-2,\"original_language\":\"xx\",\"original_title\":\"Signal Outage\",\"overview\":\"Alert! No signal detected. The network went on a mysterious hiatus.\",\"popularity\":0.0,\"poster_path\":\"/images/signal_outage_poster.jpg\",\"release_date\":\"1970-01-01\",\"title\":\"Signal Outage\",\"video\":false,\"vote_average\":0.0,\"vote_count\":0},{\"adult\":false,\"backdrop_path\":\"/images/reconnect_backdrop.jpg\",\"genre_ids\":[0],\"id\":-3,\"original_language\":\"xx\",\"original_title\":\"Please Reconnect\",\"overview\":\"Your device is taking a short break from the internet. Tap retry to rejoin the adventure.\",\"popularity\":0.0,\"poster_path\":\"/images/reconnect_poster.jpg\",\"release_date\":\"1970-01-01\",\"title\":\"Please Reconnect\",\"video\":false,\"vote_average\":0.0,\"vote_count\":0}],\"total_pages\":1,\"total_results\":4}";
                }
                dataManager.processMoviesJson(jsonResult, requestSection);
                break;
            }
            case GENRE: {
                if (jsonResult.isBlank()) {
                    System.out.println("GENRES BLANK, CHARGING PH");
                    jsonResult = "\"{\\\"genres\\\":[{\\\"id\\\":0,\\\"name\\\":\\\"Unknown\\\"}]}\";";
                    System.out.println(jsonResult);
                }
                dataManager.processGenresJson(jsonResult);
                break;
            }
        }
    }
}
