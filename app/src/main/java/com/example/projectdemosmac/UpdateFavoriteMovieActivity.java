package com.example.projectdemosmac;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.projectdemosmac.databinding.ActivityUpdateFavoriteMovieBinding;
import com.example.projectdemosmac.models.FavoriteMovie;
import com.example.projectdemosmac.roomdatabase.FavoriteMoviesDatabase;

public class UpdateFavoriteMovieActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityUpdateFavoriteMovieBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_update_favorite_movie);
        FavoriteMovie favoriteMovie = (FavoriteMovie) getIntent().getExtras().get("movieFavorites");
        Log.d("TAG", "onCreate: " + favoriteMovie.getTitle());
        binding.setUpdateMovie(favoriteMovie);
        binding.btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                favoriteMovie.setNote(binding.edNote.getText().toString());
                FavoriteMoviesDatabase.getInstance(UpdateFavoriteMovieActivity.this).favoriteMoviesDao().update(favoriteMovie);
                Toast.makeText(UpdateFavoriteMovieActivity.this, "Update success", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent();
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }
}