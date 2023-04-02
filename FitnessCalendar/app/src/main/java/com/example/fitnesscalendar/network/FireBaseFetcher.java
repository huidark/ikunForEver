package com.example.fitnesscalendar.network;


import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.compose.ui.node.MyersDiffKt;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.fitnesscalendar.api.FireBaseEventData;
import com.example.fitnesscalendar.api.FireBasePhotoData;
import com.example.fitnesscalendar.api.FireBaseUserData;
import com.example.fitnesscalendar.model.DeEvent;
import com.example.fitnesscalendar.model.Event;
import com.example.fitnesscalendar.model.Photo;
import com.example.fitnesscalendar.model.Train;
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
import java.util.Objects;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/*
This class is used as the connector/fetcher to the firebase. We use the FireStore functionality of
firebase. Be careful that his class is automatically asynchronized, but now implemented as synchronized
//TODO: ?whether to run all methods with ThreadPool to improve performance?
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
    public interface onUserResult {
        void onAddResult(String result, CountDownLatch cdadd);

        void onUpdateResult(String result,CountDownLatch cdupdate);

        void onGetUserResult(LiveData<User> user, String result, CountDownLatch cdget);

        void onGetUsersResult(LiveData<List<User>> users, String result, CountDownLatch cdgets);

        void onDeleteResult(String result, CountDownLatch cddel);
    }

    public interface onEventResult {
        void onAddResult(String result, CountDownLatch cdadd);

        void onUpdateResult(String result,CountDownLatch cdupdate);

        void onGetEventsResult(LiveData<List<DeEvent>> users, String result, CountDownLatch cdgets);

        void onDeleteResult(String result, CountDownLatch cddel);
    }

    public interface onPhotoResult{
        void onAddResult(String result, CountDownLatch cdadd);

        void onGetPhotoResult(LiveData<String> photo64, String result, CountDownLatch cdgets);

        void onDeleteResult(String result, CountDownLatch cddel);
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
    !!!THE NAME OF THE DOCUMENT SHOULD BE NAMED IN SUCH FORMAT:
    USERNAME
     */
    public void addUser(User u, onUserResult addResult, CountDownLatch cd) {

        FireBaseUserData ud = new FireBaseUserData(u);
        db.collection("users").document(u.getUserName())
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

    public void updateUser(User u, onUserResult updateResult, CountDownLatch cd) {
        //TODO: render set()
        FireBaseUserData ud = new FireBaseUserData(u);
        db.collection("users").document(u.getUserName())
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

    public void getUserOnce(String userName, onUserResult getUserResult, CountDownLatch cd) {
                DocumentReference docRef = db
                        .collection("users")
                        .document(userName);
                docRef.get().addOnSuccessListener(es, new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        FireBaseUserData fd = documentSnapshot.toObject(FireBaseUserData.class);
                        User u;
                        MutableLiveData<User> mu = null;
                        if(fd != null) {
                            u = new User(fd);
                            if(u.getUserWeight() == null){
                                Log.d("getUserOnce", "u is Null");
                            }else{
                                //Log.d("getUserOnce", "u is"+u.getUserWeight().get("00000000"));
                            }
                            mu = new MutableLiveData<>(u);
                            if(mu.getValue().getUserWeight() == null){
                                Log.d("getUserOnce", "mu is null ");
                            }else{
                                //Log.d("getUserOnce", "mu is"+mu.getValue().getUserWeight().get("00000000"));
                            }
                        }
                        getUserResult.onGetUserResult(mu, "Successfully get", cd);
                    }
                });
    }


    public void deleteUser(String userName, onUserResult deleteResult, CountDownLatch cd) {
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
    Event CURD
    !!! THE NAME OF THE DOCUMENT SHOULD BE NAMED IN SUCH FORMAT:
    USERNAME|DATE|TIME where | stands for concat
     */
    public void addEvent(DeEvent deEvent, onEventResult addResult,
                         CountDownLatch cd) {
        String index;
        index = deEvent.getUserName()+deEvent.getEvent().getEventDate()+deEvent.getEvent().getEventTime();
        FireBaseEventData ed = new FireBaseEventData(deEvent);
        db.collection("events").document(index)
                .set(ed)
                .addOnSuccessListener(es, new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("addEvent", "onSuccess");
                        addResult.onAddResult("Successfully created", cd);
                    }
                }).addOnFailureListener(es, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("addEvent", "Error adding document", e);
                        addResult.onAddResult("Error writing document", cd);
                    }
                });
    }



    public void updateEvent(DeEvent deEvent, onEventResult updateResult,
                                CountDownLatch cd) {
        String index;
        index = deEvent.getUserName()+deEvent.getEvent().getEventDate()+deEvent.getEvent().getEventTime();
        FireBaseEventData ed = new FireBaseEventData(deEvent);
        db.collection("events").document(index)
                .set(ed)
                .addOnSuccessListener(es, new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("updateEvents", "onSuccess");
                        updateResult.onAddResult("Successfully created", cd);
                    }
                }).addOnFailureListener(es, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("updateEvents", "Error adding document", e);
                        updateResult.onAddResult("Error writing document", cd);
                    }
                });
    }

    public void getEvents(String userName, String date, onEventResult getResult,
                                           CountDownLatch cd) {
        db.collection("events")
                .whereEqualTo("userName", userName)
                .whereEqualTo("eventDate", date)
                .get()
                .addOnCompleteListener(es, new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            LiveData<List<DeEvent>> lle = null;
                            List<DeEvent> deEvents = new ArrayList<>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                FireBaseEventData fd = document.toObject(FireBaseEventData.class);
                                DeEvent deEvent = new DeEvent(fd);
                                deEvents.add(deEvent);
                            }
                            lle = new MutableLiveData<>(deEvents);
                            getResult.onGetEventsResult(lle, "successfully get all result", cd);//TODO: add implementation
                        } else {
                            getResult.onGetEventsResult(null, "fail", cd);
                        }
                    }
                });
    }

    public void deleteEvent(DeEvent deEvent, onEventResult deleteResult, CountDownLatch cd) {
        String index;
        index = deEvent.getUserName()+deEvent.getEvent().getEventDate()+deEvent.getEvent().getEventTime();
        db.collection("events").document(index)
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

    //---------------------------------------------------------------------------------------------
    /*
    Photo CRD: the reason that this is separated from Event is because fetching photo is slow.
    should fetching only when the photo is not stored locally. Photo is stored in base64.

     */
    public void CreatePhoto(DeEvent deEvent, Photo photo, onPhotoResult updateResult,
                            CountDownLatch cd) {
        String index;
        index = deEvent.getUserName()+deEvent.getEvent().getEventDate()+deEvent.getEvent().getEventTime();
        FireBasePhotoData fd = new FireBasePhotoData(photo);
        db.collection("photos").document(index)
                .set(photo)
                .addOnSuccessListener(es, new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("addPhoto", "onSuccess");
                        updateResult.onAddResult("Successfully created", cd);
                    }
                }).addOnFailureListener(es, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("addPhoto", "Error adding document", e);
                        updateResult.onAddResult("Error writing document", cd);
                    }
                });
    }

    public void getPhoto(DeEvent deEvent, onPhotoResult getPhotoResult, CountDownLatch cd) {
        String index;
        index = deEvent.getUserName()+deEvent.getEvent().getEventDate()+deEvent.getEvent().getEventTime();
        DocumentReference docRef = db
                .collection("photos")
                .document(index);
        docRef.get().addOnSuccessListener(es, new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists()) {
                    FireBasePhotoData fd = documentSnapshot.toObject(FireBasePhotoData.class);
                    LiveData<String> mPhoto = new MutableLiveData<>(fd.getPhoto());
                    getPhotoResult.onGetPhotoResult(mPhoto, "Successfully get", cd);
                }else{
                    getPhotoResult.onGetPhotoResult(null, "no such photo", cd);
                }
            }
        });
    }


    public void deletePhoto(DeEvent deEvent, onPhotoResult deleteResult, CountDownLatch cd) {
        String index;
        index = deEvent.getUserName()+deEvent.getEvent().getEventDate()+deEvent.getEvent().getEventTime();
        db.collection("photos").document(index)
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
}
