package com.example.projectdemosmac.roomdatabase;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.projectdemosmac.models.FavoriteMovie;

import java.util.List;

@Dao
public interface FavoriteMoviesDAO {

    @Insert
    void insertMovie(FavoriteMovie favoriteMovies);

    @Query("SELECT * FROM favoriteMovies")
    List<FavoriteMovie> getList();

    @Update
    void update(FavoriteMovie movie);

    @Delete
    void deleteMovie(FavoriteMovie movie);

    @Query("SELECT * FROM favoriteMovies WHERE tittle= :favoriteMovie")
    List<FavoriteMovie> checkMovie(String favoriteMovie);
}
