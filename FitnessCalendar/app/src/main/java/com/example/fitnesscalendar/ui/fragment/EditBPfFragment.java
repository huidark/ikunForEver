package com.example.fitnesscalendar.ui.fragment;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.fitnesscalendar.R;
import com.example.fitnesscalendar.viewmodel.UserViewModel;

public class EditBPfFragment extends Fragment implements View.OnClickListener {
    //instantiation of Button
    Button buttonCp;

    //instantiation of EditPfFragment
    EditPfFragment editPfFragment;

    //instantiation of bundle
    Bundle tempBundle;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_b_pf, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        buttonCp = getActivity().findViewById(R.id.bt_cp);
        buttonCp.setOnClickListener(this);
        tempBundle = new Bundle();
    }

    @Override
    public void onClick(View v) {
        editPfFragment = new EditPfFragment();
        editPfFragment.setArguments(tempBundle);
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.fl_container
                        ,editPfFragment)
                .commitAllowingStateLoss();
    }
}