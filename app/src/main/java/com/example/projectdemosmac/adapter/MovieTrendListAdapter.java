package com.example.projectdemosmac.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.projectdemosmac.DetailActivity;
import com.example.projectdemosmac.R;
import com.example.projectdemosmac.models.Result;

import java.util.List;

public class MovieTrendListAdapter extends RecyclerView.Adapter<MovieTrendListAdapter.MovieViewHolder>{

    private List<Result> listMovie;
    private Context context;

    public MovieTrendListAdapter(List<Result> listMovie, Context context) {
        this.listMovie = listMovie;
        this.context = context;
    }

    public void setMovieList(List<Result> listMovie) {
        this.listMovie = listMovie;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie_trend, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {

        Result result = listMovie.get(position);
        holder.tvTitle.setText(result.getTitle());
        Glide.with(holder.itemView.getContext()).load("https://image.tmdb.org/t/p/w500" + result.
                getPosterPath()).into(holder.imgMovie);
        holder.ratingMovie.setRating(Float.parseFloat(String.valueOf(result.getVoteAverage()/2)));
        
        holder.tvTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Click title", Toast.LENGTH_SHORT).show();
                moveToDetailsMovie(context, result);
            }
        });
        
        holder.imgMovie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Click img", Toast.LENGTH_SHORT).show();
                moveToDetailsMovie(context, result);
            }
        });
    }

    @Override
    public int getItemCount() {
        if(listMovie == null){
            return 0;
        }
        return listMovie.size();
    }

    class MovieViewHolder extends RecyclerView.ViewHolder {
        private TextView tvTitle;
        private ImageView imgMovie;
        private RatingBar ratingMovie;
        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_title);
            imgMovie = itemView.findViewById(R.id.img_movie);
            ratingMovie = itemView.findViewById(R.id.rating_movie);
        }
    }

    private void moveToDetailsMovie(Context context, Result result){
        Intent intent = new Intent(context, DetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("movie", result);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }
}
