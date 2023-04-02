package com.example.fitnesscalendar.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.fitnesscalendar.model.User;
import com.example.fitnesscalendar.network.FireBaseFetcher;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class UserViewModel extends ViewModel implements FireBaseFetcher.onUserResult {

    //LiveData
    public LiveData<List<User>> users = null;
    public LiveData<User> user = null;

    //instantiation of FireBaseFetcher
    FireBaseFetcher fr = new FireBaseFetcher();

    //TODO: decide whether use an thread pool when executing query
    //ExecutorService es = Executors.newFixedThreadPool(5);

    // This class uses implicit constructor

    /*
    get all users
     */
    /* TODO: do not need this function right now
    public LiveData<List<User>> getAllUsers(){
        //users = fr.getUsers();
        return users;
    }*/


    //TODO: some of the query should be using listener instead on on-time retrieving, since it will
    //TODO: for multi users. Optimize this in FireBaseFetcher
    /*
    this is the function that used in case of observer which returns one user recorded in field user
     */
    public LiveData<User> observerDataOneUser(){
        return this.user;
    }

    /*
    this is the function that manually get and set one user. One should not use this in case of observer.
     */
    public LiveData<User> getOneUser(String userName) {
        this.getOneUserOnce(userName);
        return this.user;
    }

    /*
    this functions serves to get one user by username. One should it after register observers. This function
    is manually set to be synchronized.
     */
    public void getOneUserOnce(String userName) {
        Log.d("getOneUserOnce", "getting" + userName);
        CountDownLatch cdget = new CountDownLatch(1);
        synchronized (cdget) {
            cdget = new CountDownLatch(1);

            fr.getUserOnce(userName, UserViewModel.this, cdget);
            try {
                cdget.await();
            } catch (Exception e) {
                //none
            }
        }

    }

    /*
    this function serves to add a user to database by user specified field values. This function is
    manually set to be synchronized
     */
    public void createUser(User u) {
        CountDownLatch cdadd = new CountDownLatch(1);
        synchronized (cdadd) {
            Map<String, Double> weights = new HashMap<>();
            weights.put("00000000", 0.0);
            fr.addUser(u, UserViewModel.this, cdadd);
            try {
                cdadd.await();
            } catch (Exception e) {
                //none
            }
        }

    }

    /*
    this function serves to delete a user from database by username. This function is manually set to
    be synchronized
     */
    public void deleteUser(String userName) {
        CountDownLatch cddel = new CountDownLatch(1);
        synchronized (cddel) {
            cddel = new CountDownLatch(1);
            fr.deleteUser(userName, UserViewModel.this, cddel);
            try {
                cddel.await();
            } catch (Exception e) {
                //none
            }
        }

    }
    /*
    this function serves to update a user to database by user specified field values. This function is
    manually set to be synchronized.
    */
    public void updateUser(User user) {
        CountDownLatch cdupdate = new CountDownLatch(1);
        synchronized (cdupdate) {
            fr.updateUser(user,UserViewModel.this, cdupdate);
            try {
                cdupdate.await();
            } catch (Exception e) {
                //none
            }
        }
    }

    /*
    this function is the callback function for FireStore to call when done the executing of create.
     */
    @Override
    public void onAddResult(String result, CountDownLatch cdadd) {
        //TODO: decide whether to keep the String result as the operation result of FireStore
        cdadd.countDown();
    }

    /*
    this function is the callback function for FireStore to call when done the executing of update.
     */
    @Override
    public void onUpdateResult(String result, CountDownLatch cdupdate) {
        cdupdate.countDown();
    }

    /*
    this function is the callback function for FireStore to call when done the executing of get.
     */
    @Override
    public void onGetUserResult(LiveData<User> user, String result, CountDownLatch cdget) {
        this.user = user;
        cdget.countDown();
    }


    /*
    this function is the callback function for FireStore to call when done the executing of get all.
     */
    @Override
    public void onGetUsersResult(LiveData<List<User>> users, String result, CountDownLatch cdgets) {
        this.users = users;
        cdgets.countDown();
    }

    /*
    this function is the callback function for FireStore to call when done the executing of delete.
     */
    public void onDeleteResult(String result, CountDownLatch cddel) {
        cddel.countDown();
    }

}
