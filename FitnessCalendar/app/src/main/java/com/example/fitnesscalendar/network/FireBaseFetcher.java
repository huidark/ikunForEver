package com.example.fitnesscalendar.network;


import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.fitnesscalendar.api.FireBaseEventData;
import com.example.fitnesscalendar.api.FireBaseUserData;
import com.example.fitnesscalendar.model.Event;
import com.example.fitnesscalendar.model.User;
import com.example.fitnesscalendar.viewmodel.UserViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/*
This class is used as the connector/fetcher to the firebase. We use the FireStore functionality of
firebase. Be careful that his class is automatically asynchronized.
This class should provide methods to do CURD operation to FireStore.
 */
public class FireBaseFetcher {
    FirebaseFirestore db;

    ExecutorService es = Executors.newFixedThreadPool(5);


    private final String TAG = "firebasefetcher";


    /*
    private constructor preventing external instantiation
     */
    public FireBaseFetcher() {
        //1. render firebase dependencies and app permission DONE
        //TODO: 2. connect to the firebase
        db = FirebaseFirestore.getInstance();
    }

    /*
    callback for ViewModel
     */
    public interface onResult {
        void onAddResult(String result, CountDownLatch cdadd);

        void onUpdateResult(String result,CountDownLatch cdupdate);

        void onGetUserResult(LiveData<User> user, String result, CountDownLatch cdget);

        void onGetUsersResult(LiveData<List<User>> users, String result, CountDownLatch cdgets);

        void onDeleteResult(String result, CountDownLatch cddel);//TODO:get this done
    }

    //single object
    public static FireBaseFetcher sInstance;

    //single obj instantiation
    public static FireBaseFetcher getFireBaseFetcherInstance() {
        if (sInstance == null) {
            sInstance = new FireBaseFetcher();
        }
        return sInstance;
    }

    /*
    below are CURD methods operating on the FireStore.
     */

    /*
    User CURD
     */
    public void addUser(String userName, String userGender, double userHeight, Map<String, Double> userWeight,
                        int userAge, onResult addResult, CountDownLatch cd) {


        FireBaseUserData ud = new FireBaseUserData(userName,
                userGender, userHeight, userWeight, userAge);
        db.collection("users").document(userName)
                .set(ud)
                .addOnSuccessListener(es, new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("addUser", "onSuccess");
                        addResult.onAddResult("Successfully created", cd);
                    }
                }).addOnFailureListener(es, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("addUser", "Error adding document", e);
                        addResult.onAddResult("Error writing document", cd);
                    }
                });

    }

    public void updateUser(String userName, String userGender, double userHeight, Map<String, Double> userWeight,
                           int userAge, onResult updateResult, CountDownLatch cd) {
        //TODO: render set()
        FireBaseUserData ud = new FireBaseUserData(userName,
                userGender, userHeight, userWeight, userAge);
        db.collection("users").document(userName)
                .set(ud)
                .addOnSuccessListener(es, new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        updateResult.onUpdateResult("Successfully updated", cd);
                    }
                })
                .addOnFailureListener(es, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error writing document", e);
                        updateResult.onUpdateResult("Error writing document", cd);
                    }
                });

    }

    //TODO: do not need this method for now
    /*
    public void getUsers(){
        db.collection("users")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value,
                                        @Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            Log.w(TAG, "Listen failed.", e);
                            return;
                        }

                        List<User> users = new ArrayList<>();
                        for (QueryDocumentSnapshot doc : value) {
                            if (doc.get("userName") != null) {

                                users.add(doc.getData());
                            }
                        }
                        Log.d(TAG, "Current cites in CA: " +users);
                    }
                });
    }*/
    //TODO: R should have more format
    public void getUserOnce(String userName, onResult getUserResult, CountDownLatch cd) {
                DocumentReference docRef = db.collection("users").document(userName);
                docRef.get().addOnSuccessListener(es, new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        Log.d("getUserOnce1", Thread.currentThread().getName());
                        FireBaseUserData fd = documentSnapshot.toObject(FireBaseUserData.class);
                        User u;
                        MutableLiveData<User> mu = null;
                        if(fd != null) {
                            u = new User(fd);
                            mu = new MutableLiveData<>(u);
                        }
                        getUserResult.onGetUserResult(mu, "Successfully get", cd);
                    }
                });


        /*
        DocumentReference docRef = db.collection("users").document(userName);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                Log.d("getUserOnce", "onSuccess");
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d("getUserOnce", "exist");
                        try {
                            MutableLiveData<User> um = new MutableLiveData<>();
                            um.setValue((User) document.getData());
                            getUserResult.onGetUserResult(um, "Successfully get");
                        } catch (Exception ce) {
                            Log.d(TAG, "Conversion failed");
                        }
                    } else {
                        Log.d("getUserOnce", "not exist");
                        getUserResult.onGetUserResult(null, "No such user");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });

         */
    }


    public void deleteUser(String userName, onResult deleteResult, CountDownLatch cd) {
        //TODO: render set()
        db.collection("users").document(userName)
                .delete()
                .addOnSuccessListener(es, new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        deleteResult.onDeleteResult("Successfully deleted", cd);
                    }
                })
                .addOnFailureListener(es, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error deleting document", e);
                        deleteResult.onDeleteResult("Error deleting document", cd);
                    }
                });
    }

    //---------------------------------------------------------------------------------------------------
    /*
    Event CURD TODO: implement these next ckp
     */
    public void addEvent(String userName, int eventRmDuration, String eventDate, String trainingName,
                         boolean trainingIsAn, boolean trainingIsCa, int trainingCalories) {

    }

    public void updateUserEvent(String userName, int eventRmDuration, String eventDate, String trainingName,
                                boolean trainingIsAn, boolean trainingIsCa, int trainingCalories) {

    }

    public LiveData<List<Event>> getEvents() {
        return null;
    }

    public LiveData<Event> getEvent() {
        return null;
    }

    public void deleteEvent() {

    }


}
