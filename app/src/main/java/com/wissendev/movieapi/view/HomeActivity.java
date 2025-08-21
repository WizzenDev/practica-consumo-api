package com.wissendev.movieapi.view;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.wissendev.movieapi.R;
import com.wissendev.movieapi.model.entities.MovieCard;
import com.wissendev.movieapi.model.utils.NetworkUtils;
import com.wissendev.movieapi.presenter.Director;
import com.wissendev.movieapi.presenter.DirectorPresenter;

import java.util.List;
import com.wissendev.movieapi.model.utils.SECTIONS;


public class HomeActivity extends AppCompatActivity implements HomeView{
    private RecyclerView rvNowPlayingMovies,rvPopularMovies,rvTopRatedMovies,rvUpcomingMovies, rvFavoriteMovies;
    private ScrollView lyHome, lyDetails;
    private LinearLayout lyLoading, lyFavorites, lyErrorNetwork;
    private TextView tvDetailsName, tvDetailsGenres, tvDetailsVoteAverage, tvDetailsVoteCount, tvDetailsOverview;
    private ImageView ivDetailsBackdrop;
    private ImageButton ibFavorite;
    private boolean online = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        lyLoading = findViewById(R.id.ly_loading);
        lyErrorNetwork = findViewById(R.id.ly_error_network);

        displayLoading();

        lyHome = findViewById(R.id.ly_home_section);
        lyHome.setVisibility(VISIBLE);

        rvNowPlayingMovies = findViewById(R.id.rv_movies_now_playing);
        rvPopularMovies = findViewById(R.id.rv_movies_popular);
        rvTopRatedMovies = findViewById(R.id.rv_movies_top_rated);
        rvUpcomingMovies = findViewById(R.id.rv_movies_upcoming);

        lyFavorites = findViewById(R.id.ly_favorites_section);
        lyFavorites.setVisibility(GONE);

        rvFavoriteMovies = findViewById(R.id.rv_movies_favorite);

        lyDetails = findViewById(R.id.ly_details_section);
        lyDetails.setVisibility(GONE);

        ivDetailsBackdrop = findViewById(R.id.iv_details_backdrop);
        tvDetailsName = findViewById(R.id.tv_details_name);
        tvDetailsGenres = findViewById(R.id.tv_details_genres);
        tvDetailsVoteAverage = findViewById(R.id.tv_details_vote_average);
        tvDetailsVoteCount = findViewById(R.id.tv_details_vote_count);
        ibFavorite = findViewById(R.id.iv_details_favorite);
        tvDetailsOverview = findViewById(R.id.tv_details_overview);

        DirectorPresenter directorPresenter = new Director(HomeActivity.this,this);
        directorPresenter.loadMoviesData();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!NetworkUtils.isNetworkAvailable(this) || !online) {
            Toast.makeText(this,"Valido conexion",Toast.LENGTH_SHORT).show();
            DirectorPresenter directorPresenter = new Director(HomeActivity.this,this);
            directorPresenter.valideNetwork();
        }
    }

    @Override
    public void setupAdapter(List<MovieCard> movieList, SECTIONS section) {

        MovieAdapter adapter = new MovieAdapter(movieList);
        RecyclerView rvActual = null;

        switch (section) {
            case NOW_PLAYING: {
                rvActual = rvNowPlayingMovies;
                break;
            }
            case POPULAR: {
                rvActual = rvPopularMovies;
                break;
            }
            case TOP_RATED: {
                rvActual = rvTopRatedMovies;
                break;
            }
            case UPCOMING: {
                rvActual = rvUpcomingMovies;
                break;
            }
            case FAVORITE: {
                rvActual = rvFavoriteMovies;
                break;
            }
        }

        if (rvActual != null) {

            LinearLayoutManager layout;

            if (section == SECTIONS.FAVORITE) {
                layout = new GridLayoutManager(
                        this,
                        2,
                        GridLayoutManager.VERTICAL,
                        false);
            } else {
                layout = new LinearLayoutManager(
                        this,
                        LinearLayoutManager.HORIZONTAL,
                        false);
            }

            rvActual.setLayoutManager(layout);
            rvActual.setAdapter(adapter);
            rvActual.setItemViewCacheSize(20);
            rvActual.setHasFixedSize(true);
            adapter.setOnItemClickListener(this::displayDetails);
        }
    }

    @Override
    public void displayLoading() {
        lyLoading.setVisibility(VISIBLE);
    }

    @Override
    public void hideLoading() {
        lyLoading.setVisibility(GONE);
    }

    @Override
    public void displayNetworkError() {
        lyErrorNetwork.setVisibility(VISIBLE);
        online = false;
    }

    @Override
    public void hideNetworkError() {
        lyErrorNetwork.setVisibility(GONE);
        online = true;
    }

    @Override
    public void displayServerError() {

    }

    public void displayHome(View v) {
        lyHome.setVisibility(VISIBLE);
        lyDetails.setVisibility(GONE);
        lyFavorites.setVisibility(GONE);
    }

    public void displayFavorites(View v) {

        DirectorPresenter directorPresenter = new Director(HomeActivity.this,this);
        directorPresenter.obtainFavoriteMovies();

        lyFavorites.setVisibility(VISIBLE);
        lyHome.setVisibility(GONE);
        lyDetails.setVisibility(GONE);
    }

    public void displayDetails(MovieCard movie) {

        DirectorPresenter directorPresenter = new Director(HomeActivity.this,this);
        boolean isFavorite = directorPresenter.isFavorite(movie);

        Glide.with(this)
                .load(movie.getBackdropSrc())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.mipmap.backdrop_placeholder)
                .transition(DrawableTransitionOptions.withCrossFade(300))
                .into(ivDetailsBackdrop);
        tvDetailsName.setText(movie.getName());
        tvDetailsGenres.setText(movie.getGenres().toString());
        tvDetailsVoteAverage.setText(movie.getVoteAverage());
        tvDetailsVoteCount.setText(movie.getVoteCount());
        ibFavorite.setImageResource((isFavorite ? R.drawable.drw_details_icon_fav_checked : R.drawable.drw_details_icon_fav_add));
        tvDetailsOverview.setText(movie.getOverview());

        ibFavorite.setOnClickListener(v -> {
            movie.setFavorite(!movie.isFavorite());
            if (movie.isFavorite()) {
                directorPresenter.addFavoriteMovie(movie);
            } else {
                directorPresenter.removeFavoriteMovie(movie);
            }
            ibFavorite.setImageResource((movie.isFavorite() ? R.drawable.drw_details_icon_fav_checked : R.drawable.drw_details_icon_fav_add));
        });

        lyDetails.setVisibility(VISIBLE);
        lyHome.setVisibility(GONE);
        lyFavorites.setVisibility(GONE);

    }
}