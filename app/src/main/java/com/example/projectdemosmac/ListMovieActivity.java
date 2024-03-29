package com.example.projectdemosmac;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectdemosmac.adapter.MoviePopularListAdapter;
import com.example.projectdemosmac.adapter.MovieTrendListAdapter;
import com.example.projectdemosmac.models.Result;
import com.example.projectdemosmac.request.MovieService;
import com.example.projectdemosmac.response.MovieResponse;
import com.example.projectdemosmac.utils.Credentials;
import com.example.projectdemosmac.utils.MovieApi;
import com.example.projectdemosmac.viewmodels.MovieListViewModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListMovieActivity extends AppCompatActivity {

    private MovieApi movieApi;
    private MovieService movieService;
    private MovieListViewModel movieListViewModel;
    private RecyclerView rcvListMovie, rcvListMovieTrend;
    private MovieTrendListAdapter movieTrendListAdapter;
    private MoviePopularListAdapter movieListAdapter;
    private List<Result> listMovie;
    private List<Result> listMovieTrend;;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_movie);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ImageView imageFavorites = findViewById(R.id.img_favorite);
        imageFavorites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ListMovieActivity.this, FavoriteMovieActivity.class);
                startActivity(intent);
            }
        });
        rcvListMovie = findViewById(R.id.rcv_listMoviePopular);
        rcvListMovieTrend = findViewById(R.id.rcv_listMovieTrending);
        LinearLayoutManager linearLayout = new LinearLayoutManager(ListMovieActivity.this, RecyclerView.HORIZONTAL, false);
        LinearLayoutManager linearLayout1 = new LinearLayoutManager(ListMovieActivity.this, RecyclerView.HORIZONTAL, false);
        rcvListMovie.setLayoutManager(linearLayout);
        rcvListMovieTrend.setLayoutManager(linearLayout1);

        setUpSearchView();

        movieListAdapter = new MoviePopularListAdapter(listMovie, this);
        movieTrendListAdapter = new MovieTrendListAdapter(listMovieTrend, this);
        movieApi = movieService.getMovieApi();
//        searchMovieApi();
        movieListViewModel = new ViewModelProvider(this).get(MovieListViewModel.class);

        rcvListMovieTrend.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                if(!recyclerView.canScrollHorizontally(1)){
                    movieListViewModel.movieTrendNextPage();
                }
            }
        });

        rcvListMovie.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                if(!recyclerView.canScrollVertically(1)){
                    movieListViewModel.moviePopularNextPage();
                }
            }
        });

        movieListViewModel.getMoviePopularList().observe(this, new Observer<List<Result>>() {
            @Override
            public void onChanged(List<Result> results) {
                if (results != null) {
                    listMovie = results;
                    Log.d("TAG", "onChanged: " + results.size());
                    movieListAdapter.setMovieList(results);
                } else {
                    Toast.makeText(ListMovieActivity.this, "Failed to get list of movies", Toast.LENGTH_SHORT).show();
                }
            }
        });
        movieListViewModel.responseListMoviePopular(1);
        rcvListMovie.setAdapter(movieListAdapter);

        movieListViewModel.getMovieTrendList().observe(this, new Observer<List<Result>>() {
            @Override
            public void onChanged(List<Result> results) {
                if(results != null){
                    listMovieTrend = results;
                    Log.d("TAG", "onChanged: " + results.size());
                    movieTrendListAdapter.setMovieList(results);
                    for(Result result : listMovieTrend){
                        Log.d("TAG", "MovieTrend: " + result.getOriginalTitle() + " - " + result.getVoteAverage());
                    }
                }else {
                    Toast.makeText(ListMovieActivity.this, "Failed to get list of movies", Toast.LENGTH_SHORT).show();
                }
            }
        });
        movieListViewModel.responseListMovieTrend(1);
        rcvListMovieTrend.setAdapter(movieTrendListAdapter);
    }

    private void setUpSearchView() {
        final SearchView searchView = findViewById(R.id.search_view);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if(query != null){
                    Intent intent = new Intent(ListMovieActivity.this, MovieSearchActivity.class);
                    intent.putExtra("query", query);
                    startActivity(intent);
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }
}