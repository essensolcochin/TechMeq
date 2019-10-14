package com.essensol.techmeq.UI;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputConnection;
import android.widget.LinearLayout;

import com.essensol.techmeq.R;

public class CustomKeyboard extends LinearLayout implements View.OnClickListener {


    LinearLayout l1,l2,l3,l4,l5,l6,l7,l8,l9,l0,del,next;

    private SparseArray<String> keyValues = new SparseArray<>();
    private InputConnection inputConnection;

    public CustomKeyboard(Context context) {
        super(context);
    }


    public CustomKeyboard(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }


    public CustomKeyboard(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }



    private void init(Context context, AttributeSet attrs)
    {
        LayoutInflater.from(context).inflate(R.layout.keyboard, this, true);

        l1 = (LinearLayout) findViewById(R.id.l1);
        l1.setOnClickListener(this);

        l2 = (LinearLayout) findViewById(R.id.l2);
        l2.setOnClickListener(this);

        l3 = (LinearLayout) findViewById(R.id.l3);
        l3.setOnClickListener(this);

        l4 = (LinearLayout) findViewById(R.id.l4);
        l4.setOnClickListener(this);

        l5 = (LinearLayout) findViewById(R.id.l5);
        l5.setOnClickListener(this);

        l6 = (LinearLayout) findViewById(R.id.l6);
        l6.setOnClickListener(this);

        l7 = (LinearLayout) findViewById(R.id.l7);
        l7.setOnClickListener(this);

        l8 = (LinearLayout) findViewById(R.id.l8);
        l8.setOnClickListener(this);

        l9 = (LinearLayout) findViewById(R.id.l9);
        l9.setOnClickListener(this);

        l0 = (LinearLayout) findViewById(R.id.l0);
        l0.setOnClickListener(this);

        del = (LinearLayout) findViewById(R.id.back);
        del.setOnClickListener(this);

        next = (LinearLayout) findViewById(R.id.done);
        next.setOnClickListener(this);



        keyValues.put(R.id. l1, "1");
        keyValues.put(R.id. l2, "2");
        keyValues.put(R.id. l3, "3");
        keyValues.put(R.id. l4, "4");
        keyValues.put(R.id. l5, "5");
        keyValues.put(R.id. l6, "6");
        keyValues.put(R.id. l7, "7");
        keyValues.put(R.id. l8, "8");
        keyValues.put(R.id.l9, "9");
        keyValues.put(R.id.l0, "0");
        keyValues.put(R.id.back, "del");
        keyValues.put(R.id.done, "done");


    }


    @Override
    public void onClick(View v) {

        if (inputConnection == null)
            return;

        if (v.getId() == R.id.back) {
            CharSequence selectedText = inputConnection.getSelectedText(0);

            if (TextUtils.isEmpty(selectedText)) {
                inputConnection.deleteSurroundingText(1, 0);
            } else {
                inputConnection.commitText("", 1);
            }
        } else {
            String value = keyValues.get(v.getId());
            inputConnection.commitText(value, 1);
        }

    }

    public void setInputConnection(InputConnection ic) {
        inputConnection = ic;
    }


}
