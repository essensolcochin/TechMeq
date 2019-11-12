package com.essensol.techmeq.DialogFragments;


import android.arch.lifecycle.ViewModelProvider;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.essensol.techmeq.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class PrintDialog extends DialogFragment {


    public interface OnPrintListener
    {
        void getResponse(boolean response);
    }

   private OnPrintListener mlistener;

    private LinearLayout yes,no;

    public PrintDialog() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View Rootview = inflater.inflate(R.layout.fragment_print_dialog, container, false);
        yes=Rootview.findViewById(R.id.yes);
        no=Rootview.findViewById(R.id.no);

        getDialog().setCancelable(false);


        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mlistener =(OnPrintListener)getContext();
                assert mlistener != null;
                mlistener.getResponse(true);
                dismiss();
            }
        });

        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mlistener =(OnPrintListener)getContext();
                assert mlistener != null;
                mlistener.getResponse(false);
                dismiss();
            }
        });

        return Rootview;
    }

    @Override
    public void onStart() {
        super.onStart();

        getDialog().getWindow().setLayout(500, 300);
    }

}
