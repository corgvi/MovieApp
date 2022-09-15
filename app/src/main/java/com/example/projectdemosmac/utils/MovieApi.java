package com.example.projectdemosmac.utils;

import com.example.projectdemosmac.models.Result;
import com.example.projectdemosmac.response.MovieResponse;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MovieApi {

    // Search movie by name
    //https://api.themoviedb.org/3/search/movie?api_key=9c80c58d60a64ebd960ccc7761b03455&query=Jack+Reacher&page=1
    @GET("/3/search/movie")
    Observable<MovieResponse> searchMovieByName(
            @Query("api_key") String api_key,
            @Query("query") String query,
            @Query("page") int page
    );

    // Search movie by id movie
    //https://api.themoviedb.org/3/movie/343611?api_key=9c80c58d60a64ebd960ccc7761b03455
    @GET("/3/movie/{movie_id}?")
    Call<Result> searchMovieById(
            @Query("movie_id") int movie_id,
            @Query("api_key") String api_key
    );
    // Popular movie
    //https://api.themoviedb.org/3/movie/popular?api_key=9c80c58d60a64ebd960ccc7761b03455&page=2
    @GET("3/movie/popular")
    Observable<MovieResponse> popularMovie(
            @Query("api_key") String api_key,
            @Query("page") int page
    );

    // Trending movie
    //https://api.themoviedb.org/3/trending/all/week?api_key=9c80c58d60a64ebd960ccc7761b03455&page=1
    @GET("3/trending/all/week")
    Observable<MovieResponse> trendingMovie(
            @Query("api_key") String api_key,
            @Query("page") int page
    );
}
