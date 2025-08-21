package com.wissendev.movieapi.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.wissendev.movieapi.R;
import com.wissendev.movieapi.model.entities.MovieCard;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {
    private final List<MovieCard> movieList;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(MovieCard movie);
    }

    public MovieAdapter(List<MovieCard> movieList) {
        this.movieList = movieList;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movie_card, parent, false);

        return new MovieViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {

        MovieCard actualMovie = movieList.get(position);

        holder.tvMovieId.setText(String.valueOf(actualMovie.getId()));
        holder.tvMovieDate.setText(actualMovie.getDate());
        Glide.with(holder.itemView.getContext())
                .load(actualMovie.getPosterSrc())
                .placeholder(R.mipmap.movie_placeholder)
                .transition(DrawableTransitionOptions.withCrossFade(400))
                .into(holder.ivMoviePoster);
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }
    public class MovieViewHolder extends RecyclerView.ViewHolder {
        public final TextView tvMovieId;
        public final TextView tvMovieDate;
        public final ImageView ivMoviePoster;

        public MovieViewHolder(View itemView) {
            super(itemView);
            tvMovieId = itemView.findViewById(R.id.card_movie_id);
            ivMoviePoster = itemView.findViewById(R.id.card_movie_image);
            tvMovieDate = itemView.findViewById(R.id.card_movie_date);

            itemView.setOnClickListener(v -> {
                int position = getBindingAdapterPosition();
                if (listener != null && position != RecyclerView.NO_POSITION) {
                    listener.onItemClick(movieList.get(position));
                }
            });
        }
    }
}
