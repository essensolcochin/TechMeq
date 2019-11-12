package com.essensol.techmeq.DialogFragments;


import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.essensol.techmeq.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ExitDialog extends DialogFragment {


    public ExitDialog() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View Rootview= inflater.inflate(R.layout.fragment_exit_dialog, container, false);

        return  Rootview;


    }


    @Override
    public void onStart() {
        super.onStart();

        getDialog().getWindow().setLayout(500, 300);
    }
}
