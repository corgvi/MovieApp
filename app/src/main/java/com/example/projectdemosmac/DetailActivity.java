package com.example.projectdemosmac;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.projectdemosmac.databinding.ActivityDetailBinding;
import com.example.projectdemosmac.models.FavoriteMovie;
import com.example.projectdemosmac.models.Result;
import com.example.projectdemosmac.roomdatabase.FavoriteMoviesDatabase;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    private TextView tvTitle, tvOverView;
    private ImageView imgMovie;
    private RatingBar ratingMovie;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityDetailBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_detail);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ImageView imageFavorites = findViewById(R.id.img_favorite);
        button = findViewById(R.id.btn_add);
        imageFavorites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DetailActivity.this, FavoriteMovieActivity.class);
                startActivity(intent);
            }
        });

        if (getIntent().getExtras() != null) {
            Result result = (Result) getIntent().getExtras().get("movie");
            String strTitle = result.getOriginalTitle();
            String strNote = "This is note";
            String strImage = result.getPosterPath();
            if (result != null) {
                binding.setResult(result);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        FavoriteMovie favoriteMovie = new FavoriteMovie(strTitle, strNote, strImage);
                        if(isMovieExist(favoriteMovie)){
                            Toast.makeText(DetailActivity.this, "Movie already exists", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        FavoriteMoviesDatabase.getInstance(DetailActivity.this).favoriteMoviesDao().insertMovie(favoriteMovie);
                    }
                });

            }
        }
    }

    public boolean isMovieExist(FavoriteMovie movie){
        List<FavoriteMovie> list = FavoriteMoviesDatabase.getInstance(DetailActivity.this).favoriteMoviesDao().checkMovie(movie.getTitle());
        return list != null && !list.isEmpty();
    }
}