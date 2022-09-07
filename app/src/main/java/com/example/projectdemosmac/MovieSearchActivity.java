package com.example.projectdemosmac;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Movie;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projectdemosmac.adapter.MovieSearchListAdapter;
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

public class MovieSearchActivity extends AppCompatActivity {

    private MovieApi movieApi;
    private TextView tvSearch;
    private MovieService movieService;
    public String query;
    private RecyclerView recyclerView;
    private List<Result> listMovieSearch;
    private MovieSearchListAdapter movieSearchListAdapter;
    private MovieListViewModel movieListViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_search);

        query = getIntent().getStringExtra("query");
        movieApi = movieService.getMovieApi();
//        searchMovieApi();

        tvSearch = findViewById(R.id.tv_search);
        recyclerView = (RecyclerView) findViewById(R.id.rcv_listMovieSearch);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        tvSearch.setText(query.toString());
        movieSearchListAdapter = new MovieSearchListAdapter(listMovieSearch, this);
        movieListViewModel = new ViewModelProvider(this).get(MovieListViewModel.class);
        movieListViewModel.getMovieSearch().observe(this, new Observer<List<Result>>() {
            @Override
            public void onChanged(List<Result> results) {
                if (results != null) {
                    listMovieSearch = results;
                    Log.d("TAG", "onChanged: " + results.size());
                    movieSearchListAdapter.setMovieList(results);
                } else {
                    Toast.makeText(MovieSearchActivity.this, "Failed to get list of movies", Toast.LENGTH_SHORT).show();
                }
            }
        });
        movieListViewModel.responseListMovieSearch(query, 1);
        recyclerView.setAdapter(movieSearchListAdapter);
    }
    private void setUpSearchView() {
        final SearchView searchView = findViewById(R.id.search_view);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if(query != null){
                    Intent intent = new Intent(MovieSearchActivity.this, MovieSearchActivity.class);
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