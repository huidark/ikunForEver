package com.example.fitnesscalendar.api;

import com.example.fitnesscalendar.model.Photo;
import com.example.fitnesscalendar.network.FireBaseFetcher;

/*
this class, just like two others, serves fetching photo data. PHOTO is STORED IN BASE64!
 */
public class FireBasePhotoData {

    private String photo;

    public FireBasePhotoData(){}

    public FireBasePhotoData(String photo){
        this.photo = photo;
    }

    public FireBasePhotoData(Photo photo){
        this.photo = photo.getPhoto();
    }

    public String getPhoto() {
        return photo;
    }
}
