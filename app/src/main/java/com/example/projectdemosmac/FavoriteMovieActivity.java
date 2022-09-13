package com.example.projectdemosmac;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.projectdemosmac.adapter.FavoriteMovieAdapter;
import com.example.projectdemosmac.models.FavoriteMovie;
import com.example.projectdemosmac.roomdatabase.FavoriteMoviesDatabase;

import java.util.ArrayList;
import java.util.List;

public class FavoriteMovieActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<FavoriteMovie> list = new ArrayList<>();
    private FavoriteMovieAdapter favoriteMovieAdapter;
    private ActivityResultLauncher<Intent> resultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == RESULT_OK) {
                        loadData();
                    }
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_movie);

        favoriteMovieAdapter = new FavoriteMovieAdapter(new FavoriteMovieAdapter.IClickItemMovie() {
            @Override
            public void updateMovie(FavoriteMovie movie) {
                mUpdateMovie(movie);
            }

            @Override
            public void deleteMovie(FavoriteMovie movie) {
                mDeleteMovie(movie);
            }
        });

        recyclerView = (RecyclerView) findViewById(R.id.rcv_favoriteMovie);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        loadData();
        recyclerView.setAdapter(favoriteMovieAdapter);


    }

    private void mDeleteMovie(FavoriteMovie movie) {
        new AlertDialog.Builder(this)
                .setTitle("Confirm delete of favorite movie")
                .setMessage("Are you sure you want to delete this movie?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        FavoriteMoviesDatabase.getInstance(FavoriteMovieActivity.this).favoriteMoviesDao().deleteMovie(movie);
                        Toast.makeText(FavoriteMovieActivity.this, "Delete success!", Toast.LENGTH_SHORT).show();
                        loadData();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }

    private void mUpdateMovie(FavoriteMovie movie) {
        Intent intent = new Intent(this, UpdateFavoriteMovieActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("movieFavorites", movie);
        intent.putExtras(bundle);
        resultLauncher.launch(intent);
    }

    private void loadData() {
        list = FavoriteMoviesDatabase.getInstance(this).favoriteMoviesDao().getList();
        favoriteMovieAdapter.setData(list);
    }
}