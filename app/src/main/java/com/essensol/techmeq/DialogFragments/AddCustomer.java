package com.essensol.techmeq.DialogFragments;


import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.essensol.techmeq.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddCustomer extends DialogFragment {


    public AddCustomer() {
        // Required empty public constructor
    }

    LinearLayout Save,frame;

    ImageView add;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootview= inflater.inflate(R.layout.fragment_add_customer, container, false);

        Save =rootview.findViewById(R.id.Save);

        frame=rootview.findViewById(R.id.frame);

        add=rootview.findViewById(R.id.add);

        Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                frame.setVisibility(View.GONE);

                dismiss();
            }
        });


        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                frame.setVisibility(View.VISIBLE);
            }
        });


        return  rootview;
    }



    @Override
    public void onStart()
    {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null)
        {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            dialog.getWindow().setLayout(width, height);
        }
    }

}
