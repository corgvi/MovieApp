package com.example.projectdemosmac;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.projectdemosmac.models.Result;

public class DetailActivity extends AppCompatActivity {

    private TextView tvTitle,tvOverView;
    private ImageView imgMovie;
    private RatingBar ratingMovie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        tvTitle = findViewById(R.id.tv_title);
        tvOverView = findViewById(R.id.tv_overview);
        imgMovie = findViewById(R.id.img_movie);
        ratingMovie = findViewById(R.id.rating_movie);

        if(getIntent().getExtras() != null ){
            Result result = (Result) getIntent().getExtras().get("movie");
            tvTitle.setText(result.getOriginalTitle());
            Glide.with(this).load("https://image.tmdb.org/t/p/w500" + result.
                    getPosterPath()).into(imgMovie);
            ratingMovie.setRating(Float.parseFloat(String.valueOf(result.getVoteAverage()/2)));
            tvOverView.setText(result.getOverview());
        }
    }
}