package com.example.projectdemosmac.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.projectdemosmac.DetailActivity;
import com.example.projectdemosmac.databinding.ItemMovieBinding;
import com.example.projectdemosmac.models.Result;

import java.util.List;

public class MoviePopularListAdapter extends RecyclerView.Adapter<MoviePopularListAdapter.MovieViewHolder>{

    private List<Result> listMovie;
    private Context context;

    public MoviePopularListAdapter(List<Result> listMovie, Context context) {
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
        ItemMovieBinding binding = ItemMovieBinding.inflate(inflater,parent,false);
        return new MovieViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {

        Result result = listMovie.get(position);
        holder.binding.setResult(result);
        holder.binding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        if(listMovie == null){
            return 0;
        }
        return listMovie.size();
    }

    class MovieViewHolder extends RecyclerView.ViewHolder {
        ItemMovieBinding binding;
        public MovieViewHolder(@NonNull ItemMovieBinding binding) {
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
