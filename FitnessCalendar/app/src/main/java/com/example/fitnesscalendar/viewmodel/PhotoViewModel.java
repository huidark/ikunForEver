package com.example.fitnesscalendar.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.fitnesscalendar.model.DeEvent;
import com.example.fitnesscalendar.model.Photo;
import com.example.fitnesscalendar.network.FireBaseFetcher;

import java.util.concurrent.CountDownLatch;

public class PhotoViewModel extends ViewModel implements FireBaseFetcher.onPhotoResult {

    public LiveData<String> photo;

    private final FireBaseFetcher fr = new FireBaseFetcher();

    public LiveData<String> getPhotoOnce(DeEvent deEvent) {
        getPhoto(deEvent);
        return photo;
    }

    public void getPhoto(DeEvent deEvent){
        CountDownLatch cdget = new CountDownLatch(1);
        synchronized (cdget) {
            cdget = new CountDownLatch(1);
            fr.getPhoto(deEvent, PhotoViewModel.this, cdget);
            try {
                cdget.await();
            } catch (Exception e) {
                //none
            }
        }
    }

    public void createPhoto(DeEvent deEvent, Photo photo){
        CountDownLatch cdadd = new CountDownLatch(1);
        synchronized (cdadd) {
            cdadd = new CountDownLatch(1);
            fr.CreatePhoto(deEvent, photo, PhotoViewModel.this, cdadd);
            try {
                cdadd.await();
            } catch (Exception e) {
                //none
            }
        }
    }

    public void deletePhoto(DeEvent deEvent){
        CountDownLatch cddel = new CountDownLatch(1);
        synchronized (cddel) {
            cddel = new CountDownLatch(1);
            fr.deletePhoto(deEvent, PhotoViewModel.this, cddel);
            try {
                cddel.await();
            } catch (Exception e) {
                //none
            }
        }
    }

    @Override
    public void onAddResult(String result, CountDownLatch cdadd) {
        cdadd.countDown();
    }

    @Override
    public void onGetPhotoResult(LiveData<String> photo64, String result, CountDownLatch cdgets) {
        this.photo = photo64;
        cdgets.countDown();
    }

    @Override
    public void onDeleteResult(String result, CountDownLatch cddel) {
        cddel.countDown();
    }
}
