package com.example.projectdemosmac.viewmodels;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.projectdemosmac.models.Result;
import com.example.projectdemosmac.request.MovieService;
import com.example.projectdemosmac.response.MovieResponse;
import com.example.projectdemosmac.utils.Credentials;
import com.example.projectdemosmac.utils.MovieApi;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieListViewModel extends ViewModel {
    private MovieApi movieApi = MovieService.getMovieApi();
    private MutableLiveData<List<Result>> listMovie;
    private MutableLiveData<List<Result>> listMovieTrend;
    private MutableLiveData<List<Result>> listMovieSearch;

    public MovieListViewModel(){
        listMovie = new MutableLiveData<>();
        listMovieTrend = new MutableLiveData<>();
        listMovieSearch = new MutableLiveData<>();
    }

    public MutableLiveData<List<Result>> getMoviePopularList(){
        return listMovie;
    }

    public MutableLiveData<List<Result>> getMovieTrendList(){
        return listMovieTrend;
    }

    public MutableLiveData<List<Result>> getMovieSearch(){
        return listMovieSearch;
    }

    public void responseListMoviePopular(){
        Call<MovieResponse> call = movieApi.popularMovie(Credentials.API_KEY, 1);
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                if(response.isSuccessful()){
                    List<Result> results = response.body().getResults();
                    listMovie.postValue(results);
                }else {
                    Log.v("MoviePopular", response.code() + " " + response.message());
                }
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                listMovie.postValue(null);
                Log.e("ERROR MoviePopular", t.getMessage());
            }
        });
    }

    public void responseListMovieTrend(){
        Call<MovieResponse> call = movieApi.trendingMovie(Credentials.API_KEY, 1);
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                if(response.isSuccessful()){
                    List<Result> results = response.body().getResults();
                    listMovieTrend.postValue(results);
                }else {
                    Log.v("MoviePopular", response.code() + " " + response.message());
                }
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                listMovieTrend.postValue(null);
                Log.e("ERROR MovieTrend", t.getMessage());
            }
        });
    }

    public void responseListMovieSearch(String query, int page){
        Call<MovieResponse> call = movieApi.searchMovieByName(Credentials.API_KEY, query, page);
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                if(response.isSuccessful()){
                    List<Result> results = response.body().getResults();
                    listMovieSearch.postValue(results);
                }else {
                    Log.v("MovieSearch", response.code() + " " + response.message());
                }
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                listMovieSearch.postValue(null);
                Log.e("ERROR MovieSearch", t.getMessage());
            }
        });
    }

}
