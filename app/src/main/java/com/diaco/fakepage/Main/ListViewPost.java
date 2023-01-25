package com.diaco.fakepage.Main;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;

public class ListViewPost {
    Uri image;
    int type;
    Bitmap bit_image;


    public Bitmap getBit_image() {
        return bit_image;
    }

    public void setBit_image(Bitmap bit_image) {
        this.bit_image = bit_image;
    }

    public Uri getImage() {
        return image;
    }

    public void setImage(Uri image) {
        this.image = image;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
