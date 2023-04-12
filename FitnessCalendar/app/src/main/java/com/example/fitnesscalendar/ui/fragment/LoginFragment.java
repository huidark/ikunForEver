package com.example.fitnesscalendar.ui.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.fitnesscalendar.R;
import com.example.fitnesscalendar.data.TempoData;
import com.example.fitnesscalendar.model.User;
import com.example.fitnesscalendar.viewmodel.UserViewModel;

import java.util.HashMap;
import java.util.Map;


/*
This class serve as the fragment class of login page.
This class should show the usernmae text field and button to submit.
This class should update username in datastructure
 */
public class LoginFragment extends Fragment {

    //declare of variables
    private EditText usernameText;

    private Button nextButton;

    private MenuFragment menuFragment;

    private RegisterFragment registerFragment;
    private PasswordFragment passwordFragment;


    private final String TAG = "LoginFragment";

    UserViewModel uv;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Activity activity = getActivity();
        uv = new ViewModelProvider((ViewModelStoreOwner) activity).get(UserViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        //actions after view is created
        super.onViewCreated(view, savedInstanceState);

        //find two components inside the view
        usernameText = (EditText) view.findViewById(R.id.et_username);
        nextButton = (Button) view.findViewById(R.id.bt_next);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameText.getText().toString();
                // see if this user is existed
                LiveData<User> lu = uv.getOneUser(username);
                if(lu == null){
                    //TODO: preceed to Register Fragment
                    Bundle bundle = new Bundle();
                    bundle.putString("username", username);
                    registerFragment = new RegisterFragment();
                    registerFragment.setArguments(bundle);
                    getFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fl_container,
                                    registerFragment)
                            .addToBackStack(null)
                            .commitAllowingStateLoss();
                }else {
                    Bundle bundle = new Bundle();
                    bundle.putString("username", username);
                    bundle.putString("password", lu.getValue().getPassword());
                    passwordFragment = new PasswordFragment();
                    passwordFragment.setArguments(bundle);
                    getFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fl_container,
                                    passwordFragment)
                            .addToBackStack(null)
                            .commitAllowingStateLoss();
                }
            }

        });

    }
}