package com.diaco.fakepage.Main;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ImageDao {
    @Query("SELECT * FROM Instagram")
    List<ImageData> getAllInsta();

    @Insert
    void insertAll(ImageData... image);

}
