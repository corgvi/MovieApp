package com.example.projectdemosmac.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.projectdemosmac.DetailActivity;
import com.example.projectdemosmac.R;
import com.example.projectdemosmac.UpdateFavoriteMovieActivity;
import com.example.projectdemosmac.databinding.ItemFavoriteMovieBinding;
import com.example.projectdemosmac.models.FavoriteMovie;
import com.example.projectdemosmac.models.Result;

import java.util.ArrayList;
import java.util.List;

public class FavoriteMovieAdapter extends RecyclerView.Adapter<FavoriteMovieAdapter.FavoriteMovieViewHolder>{
    private List<FavoriteMovie> list = new ArrayList<>();
    private Context context;
    private IClickItemMovie iClickItemMovie;

    public interface IClickItemMovie{
        void updateMovie(FavoriteMovie movie);
        void deleteMovie(FavoriteMovie movie);
    }

    public FavoriteMovieAdapter(IClickItemMovie iClickItemMovie) {
        this.iClickItemMovie = iClickItemMovie;
    }


    public void setData(List<FavoriteMovie> favorites){
        this.list = favorites;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FavoriteMovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemFavoriteMovieBinding binding = ItemFavoriteMovieBinding.inflate(inflater, parent, false);
        return new FavoriteMovieViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteMovieViewHolder holder, int position) {
        FavoriteMovie movie = list.get(position);
        holder.binding.setFavoriteMovie(movie);
        holder.binding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        if (list == null) {
            return 0;
        }
        return list.size();
    }

    class FavoriteMovieViewHolder extends RecyclerView.ViewHolder {
        ItemFavoriteMovieBinding binding;
        public FavoriteMovieViewHolder(@NonNull ItemFavoriteMovieBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            binding.imgMovie.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    FavoriteMovie movie = list.get(getAdapterPosition());
                    Toast.makeText(context, movie.getTitle(), Toast.LENGTH_SHORT).show();
                }
            });
            binding.btnSua.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    FavoriteMovie movie = list.get(getAdapterPosition());
                    iClickItemMovie.updateMovie(movie);
                }
            });
            binding.btnXoa.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    FavoriteMovie movie = list.get(getAdapterPosition());
                    iClickItemMovie.deleteMovie(movie);
                }
            });
        }
    }
}
