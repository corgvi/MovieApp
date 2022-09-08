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
    public String queryMovie;
    public int page = 1;

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

    public void responseListMoviePopular(int pageNumber){
        Call<MovieResponse> call = movieApi.popularMovie(Credentials.API_KEY, pageNumber);
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                if(response.isSuccessful()){
                    List<Result> results = response.body().getResults();
                    if(pageNumber == 1){
                        listMovie.postValue(results);
                    }else {
                        List<Result> resultsCurrent = listMovie.getValue();
                        resultsCurrent.addAll(results);
                        listMovie.postValue(resultsCurrent);
                    }
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

    public void responseListMovieTrend(int pageNumber){
        Call<MovieResponse> call = movieApi.trendingMovie(Credentials.API_KEY, pageNumber);
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                List<Result> results = response.body().getResults();
                if(response.isSuccessful()){
                    if(pageNumber == 1){
                        listMovieTrend.postValue(results);
                    } else {
                        List<Result> resultsCurrent = listMovieTrend.getValue();
                        resultsCurrent.addAll(results);
                        listMovieTrend.postValue(resultsCurrent);
                    }
                }else {
                    Log.v("MovieTrend", response.code() + " " + response.message());
                }
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                listMovieTrend.postValue(null);
                Log.e("ERROR MovieTrend", t.getMessage());
            }
        });
    }

    public void responseListMovieSearch(String query, int pageNumber){
        queryMovie = query;
        Call<MovieResponse> call = movieApi.searchMovieByName(Credentials.API_KEY, query, pageNumber);
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                if(response.isSuccessful()){
                    List<Result> results = response.body().getResults();
                    if(pageNumber == 1){
                        listMovieSearch.postValue(results);
                    } else {
                        List<Result> resultsCurrent = listMovieSearch.getValue();
                        resultsCurrent.addAll(results);
                        listMovieSearch.postValue(resultsCurrent);
                    }
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

    public void searchNextPage(){
        responseListMovieSearch(queryMovie, page++);
    }

    public void movieTrendNextPage(){
        responseListMovieTrend(page++);
    }

    public void moviePopularNextPage(){
        responseListMoviePopular(page++);
    }
}
