package com.example.fitnesscalendar.ui.fragment;

import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.fitnesscalendar.R;
import com.example.fitnesscalendar.model.DeEvent;
import com.example.fitnesscalendar.model.Photo;
import com.example.fitnesscalendar.viewmodel.EventViewModel;
import com.example.fitnesscalendar.viewmodel.PhotoViewModel;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class ChooseEventFragment extends Fragment {

    TextView tpTextView;
    TextView clTextView;
    ImageView phImageView;
    Button apButton;
    Button dnButton;
    Button dlButton;
    Button ppButton;
    Activity activity;
    EventViewModel em;
    PhotoViewModel pm;
    DeEvent deEvent;

    TimeLineFragment timeLineFragment;
    String base64Photo;
    String photoPath;
    String index;

    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_IMAGE_PICK = 2;
    private static final int REQUEST_TAKE_PHOTO = 3;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = getActivity();
        pm = new ViewModelProvider(
                (ViewModelStoreOwner) activity).get(
                PhotoViewModel.class);
        em = new ViewModelProvider(
                (ViewModelStoreOwner) activity).get(
                EventViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_choose_event, container, false);

        tpTextView = view.findViewById(R.id.tv_tp);
        clTextView = view.findViewById(R.id.tv_cl);
        phImageView = view.findViewById(R.id.iv_ph);
        dnButton = view.findViewById(R.id.bt_dn);
        apButton = view.findViewById(R.id.bt_ad);
        dlButton = view.findViewById(R.id.bt_dl);
        ppButton = view.findViewById(R.id.bt_pp);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //TODO: 1. get DeEvent from bundle
        Bundle bundle = getArguments();
        deEvent = (DeEvent) bundle.getSerializable("event");
        Log.d("check null", (deEvent == null) + "");

        //TODO: 2. set up text
        tpTextView.setText(deEvent.getTrain().getTrainingName());
        clTextView.setText((deEvent.getEvent().getEventRmDuration() * deEvent.getTrain().getTrainingCalories())+ "");

        if (hasPhoto(deEvent)) {
            Log.d("check photo", "has");
            // Show the photo preview and hide the "Add Photo" button
            phImageView.setVisibility(View.VISIBLE);
            apButton.setVisibility(View.GONE);
            ppButton.setVisibility(View.GONE);
            setPhotoPreview();
        } else {
            Log.d("check photo", "no");
            // Show the "Add Photo" button and hide the photo preview
            //phImageView.setVisibility(View.GONE);
            //apButton.setVisibility(View.VISIBLE);
            apButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Handle click on "Add Photo" button
                    showPhotoPicker();
                }
            });

            ppButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showPhotoTaker();
                }
            });
        }

        dnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timeLineFragment = new TimeLineFragment();
                Bundle bundle = new Bundle();
                bundle.putString("date", deEvent.getEvent().getEventDate());
                timeLineFragment.setArguments(bundle);
                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fl_container
                                ,timeLineFragment)
                        .commitAllowingStateLoss();
            }
        });

        dlButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timeLineFragment = new TimeLineFragment();
                Bundle bundle = new Bundle();
                bundle.putString("date", deEvent.getEvent().getEventDate());
                timeLineFragment.setArguments(bundle);
                em.deleteEvent(deEvent);
                pm.deletePhoto(deEvent);
                Toast.makeText(getActivity(), "Successfully delete!", Toast.LENGTH_SHORT).show();
                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fl_container
                                ,timeLineFragment)
                        .commitAllowingStateLoss();
            }
        });
    }

    private boolean hasPhoto(DeEvent deEvent) {
        // Check if the required photo exists in the eventPhoto directory
        //TODO: compose the path to appropriate one
        index = deEvent.getUserName()+deEvent.getEvent().getEventDate()+deEvent.getEvent().getEventTime();
        Log.d("photo index", index);
        File photoFile = new File(getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES), "eventPhoto/"+ index + ".jpg");

        if (photoFile.exists()) {
            photoPath = photoFile.getAbsolutePath();
            return true;
        } else {
            //TODO: get photo from firestore
            LiveData<String> base64PhotoL = pm.getPhotoOnce(deEvent);
            if(base64PhotoL != null){
                base64Photo = pm.getPhotoOnce(deEvent).getValue();
                if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                        || ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    // Request the necessary permissions
                    requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_IMAGE_CAPTURE);
                }
                byte[] decodedBytes = Base64.decode(base64Photo, Base64.DEFAULT);
                File dir = new File(getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES), "eventPhoto");
                if (!dir.exists()) {
                    dir.mkdir();
                }
                String filename = index + ".jpg";
                File file = new File(dir, filename);
                try {
                    file.createNewFile();
                    FileOutputStream fos = new FileOutputStream(file);
                    fos.write(decodedBytes);
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                File photoFileAgain = new File(getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES), "eventPhoto/"+ index + ".jpg");
                photoPath = photoFileAgain.getAbsolutePath();
                return true;
            }else{
                return false;
            }
        }
    }

    private void setPhotoPreview() {
        // Use Glide to load the photo into the photoPreview ImageView
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            // Request the necessary permissions
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_IMAGE_CAPTURE);
        }
        Glide.with(this)
                .load(photoPath)
                .into(phImageView);
    }

    private void showPhotoPicker() {
        // Check if the app has permission to access the camera and gallery
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            // Request the necessary permissions
            requestPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_IMAGE_CAPTURE);
        } else {
            // Start the photo picker intent
            Intent pickPhotoIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(pickPhotoIntent, REQUEST_IMAGE_PICK);
        }


    }

    private void showPhotoTaker(){
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            // Request the necessary permissions
            requestPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_IMAGE_CAPTURE);
        } else {
            // Start the photo taker intent
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent, REQUEST_TAKE_PHOTO);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_IMAGE_CAPTURE && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            // Start the photo picker intent if the camera permission was granted
            Intent pickPhotoIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(pickPhotoIntent, REQUEST_IMAGE_PICK);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_IMAGE_PICK && resultCode == RESULT_OK && data != null) {
            // Get the URI of the selected photo
            Uri selectedPhotoUri = data.getData();

            try {
                // Create a bitmap from the selected photo
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), selectedPhotoUri);

                // Save the picture
                //TODO: change the path
                Log.d("photo index2", index);
                File dir = new File(getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES), "eventPhoto");
                if (!dir.exists()) {
                    dir.mkdir();
                }
                String filename = index + ".jpg";
                File photoFile = new File(dir, filename);
                photoFile.createNewFile();
                FileOutputStream outputStream = new FileOutputStream(photoFile);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
                outputStream.flush();
                outputStream.close();

                // Show the photo preview and hide the "Add Photo" button
                phImageView.setVisibility(View.VISIBLE);
                apButton.setVisibility(View.GONE);
                ppButton.setVisibility(View.GONE);
                photoPath = photoFile.getAbsolutePath();
                setPhotoPreview();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {
            try {
                // Get the captured image
                Bitmap capturedImage = (Bitmap) data.getExtras().get("data");

                // Create a new file in the "temp" directory
                String fileName = index + ".jpg";
                File dir = new File(getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES), "eventPhoto");
                if (!dir.exists()) {
                    dir.mkdir();
                }
                File photoFile = new File(dir, fileName);
                photoFile.createNewFile();

                // Save the captured image to the file
                FileOutputStream outputStream = null;
                outputStream = new FileOutputStream(photoFile);
                capturedImage.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
                outputStream.flush();
                outputStream.close();
                phImageView.setVisibility(View.VISIBLE);
                apButton.setVisibility(View.GONE);
                ppButton.setVisibility(View.GONE);
                photoPath = photoFile.getAbsolutePath();
                setPhotoPreview();
            }catch (Exception e){
                Log.d("exception", e+"");
            }
        }
    }
}