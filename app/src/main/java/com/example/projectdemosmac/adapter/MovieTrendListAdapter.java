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
import com.example.projectdemosmac.databinding.ItemMovieTrendBinding;
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
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemMovieTrendBinding binding = ItemMovieTrendBinding.inflate(inflater, parent, false);
        return new MovieViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {

        Result result = listMovie.get(position);
        holder.binding.setResult(result);
    }

    @Override
    public int getItemCount() {
        if(listMovie == null){
            return 0;
        }
        return listMovie.size();
    }

    class MovieViewHolder extends RecyclerView.ViewHolder {
        ItemMovieTrendBinding binding;
        public MovieViewHolder(@NonNull ItemMovieTrendBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            binding.imgMovie.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Result result = listMovie.get(getAdapterPosition());
                    moveToDetailsMovie(context, result);
                }
            });
            binding.tvTitle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Result result = listMovie.get(getAdapterPosition());
                    moveToDetailsMovie(context, result);
                }
            });
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
