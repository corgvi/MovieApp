package com.example.projectdemosmac.models;

import android.graphics.Bitmap;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.bumptech.glide.Glide;
import com.example.projectdemosmac.R;

import java.io.Serializable;

@Entity(tableName = "favoriteMovies")
public class FavoriteMovie implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "tittle")
    private String title;
    @ColumnInfo(name = "note")
    private String note;
    @ColumnInfo(name = "image")
    private String  image;

    public FavoriteMovie(String title, String note, String image) {
        this.title = title;
        this.note = note;
        this.image = image;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public FavoriteMovie() {
    }

    @BindingAdapter("android:loadImageFavorite")
    public static void loadImage(ImageView imageView, String url){
        if(imageView == null){
            return;
        }
        Glide.with(imageView.getContext())
                .load("https://image.tmdb.org/t/p/w500"+url)
                .placeholder(R.drawable.ic_launcher_foreground)
                .into(imageView);
    }
}
