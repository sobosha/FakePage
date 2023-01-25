package com.diaco.fakepage.Main;

import android.graphics.Color;
import android.graphics.drawable.Drawable;

public class ListviewTweet {
    int image;
    String bodyColor;
    Drawable profile;
    String Id;
    String name;
    String tweet;
    String hour;
    Drawable imageOne=null,imageTwo=null,imageThree=null,imageFour=null;
    Customview_multiImage customview_multiImage;

    public Customview_multiImage getCustomview_multiImage() {
        return customview_multiImage;
    }

    public void setCustomview_multiImage(Customview_multiImage customview_multiImage) {
        this.customview_multiImage = customview_multiImage;
    }

    public Drawable getImageOne() {
        return imageOne;
    }

    public void setImageOne(Drawable imageOne) {
        this.imageOne = imageOne;
    }

    public Drawable getImageTwo() {
        return imageTwo;
    }

    public void setImageTwo(Drawable imageTwo) {
        this.imageTwo = imageTwo;
    }

    public Drawable getImageThree() {
        return imageThree;
    }

    public void setImageThree(Drawable imageThree) {
        this.imageThree = imageThree;
    }

    public Drawable getImageFour() {
        return imageFour;
    }

    public void setImageFour(Drawable imageFour) {
        this.imageFour = imageFour;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTweet() {
        return tweet;
    }

    public void setTweet(String tweet) {
        this.tweet = tweet;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public Drawable getProfile() {
        return profile;
    }

    public void setProfile(Drawable profile) {
        this.profile = profile;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getBodyColor() {
        return bodyColor;
    }

    public void setBodyColor(String bodyColor) {
        this.bodyColor = bodyColor;
    }
}
