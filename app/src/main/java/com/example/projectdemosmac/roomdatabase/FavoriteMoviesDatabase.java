package com.example.projectdemosmac.roomdatabase;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

import com.example.projectdemosmac.models.FavoriteMovie;

@Database(entities = {FavoriteMovie.class}, version = 1)
public abstract class FavoriteMoviesDatabase extends RoomDatabase {
    private static final String databaseName = "favoriteMovies.db";
    private static FavoriteMoviesDatabase instance;

    public static synchronized FavoriteMoviesDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    FavoriteMoviesDatabase.class,
                    databaseName)
                    .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }

    public abstract FavoriteMoviesDAO favoriteMoviesDao();
}
