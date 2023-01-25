package com.diaco.fakepage.Main;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Instagram")
public class ImageData{

    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name="_image")
    private byte[] image;

    @ColumnInfo(name="type")
    private String type;

    public void setImage(byte[] image) {
        this.image = image;
    }

    public byte[] getImage() {
        return image;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
