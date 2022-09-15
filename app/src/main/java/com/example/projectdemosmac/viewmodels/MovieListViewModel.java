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

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
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

    public MovieListViewModel() {
        listMovie = new MutableLiveData<>();
        listMovieTrend = new MutableLiveData<>();
        listMovieSearch = new MutableLiveData<>();
    }

    public MutableLiveData<List<Result>> getMoviePopularList() {
        return listMovie;
    }

    public MutableLiveData<List<Result>> getMovieTrendList() {
        return listMovieTrend;
    }

    public MutableLiveData<List<Result>> getMovieSearch() {
        return listMovieSearch;
    }

    public void responseListMoviePopular(int pageNumber) {
        Observable<MovieResponse> listMoviePopular = movieApi.popularMovie(Credentials.API_KEY, pageNumber);
        listMoviePopular.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<MovieResponse>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull MovieResponse movieResponse) {
                        List<Result> results = movieResponse.getResults();
                        Log.d("TAG", "onResponse: " + results.size());
                        if (pageNumber == 1) {
                            listMovie.postValue(results);
                        } else {
                            List<Result> resultsCurrent = listMovie.getValue();
                            resultsCurrent.addAll(results);
                            listMovie.postValue(resultsCurrent);
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.e("TAG", "onError: " + e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void responseListMovieTrend(int pageNumber) {
        Observable<MovieResponse> listMovieTrending = movieApi.trendingMovie(Credentials.API_KEY, pageNumber);
        listMovieTrending.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<MovieResponse>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull MovieResponse movieResponse) {
                        List<Result> results = movieResponse.getResults();
                        if (pageNumber == 1) {
                            listMovieTrend.postValue(results);
                        } else {
                            List<Result> resultsCurrent = listMovieTrend.getValue();
                            resultsCurrent.addAll(results);
                            listMovieTrend.postValue(resultsCurrent);
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }


    public void responseListMovieSearch(String query, int pageNumber) {
        Observable<MovieResponse> listMovieSearching = movieApi.searchMovieByName(Credentials.API_KEY, query, pageNumber);
        listMovieSearching.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<MovieResponse>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull MovieResponse movieResponse) {
                        List<Result> results = movieResponse.getResults();
                        if (pageNumber == 1) {
                            listMovieSearch.postValue(results);
                        } else {
                            List<Result> resultsCurrent = listMovieSearch.getValue();
                            resultsCurrent.addAll(results);
                            listMovieSearch.postValue(resultsCurrent);
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void searchNextPage() {
        responseListMovieSearch(queryMovie, page++);
    }

    public void movieTrendNextPage() {
        responseListMovieTrend(page++);
    }

    public void moviePopularNextPage() {
        responseListMoviePopular(page++);
    }
}
