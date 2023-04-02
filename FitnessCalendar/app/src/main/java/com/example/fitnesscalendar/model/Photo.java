package com.example.fitnesscalendar.model;

import com.example.fitnesscalendar.api.FireBasePhotoData;

/*
This is the model class of photo. This class take care of information related to photo, which would
simply contain a photo data(file). One should use this class everywhere else than calling FireBaseFetcher.
The EventViewModel class should render this class.
 */
public class Photo {
    private String photo;

    public Photo(String photo) {
        this.photo = photo;
    }

    public Photo(FireBasePhotoData fd){
        this.photo = fd.getPhoto();
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}
