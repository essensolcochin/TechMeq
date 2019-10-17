package com.essensol.techmeq.DialogFragments;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.essensol.techmeq.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class _AddProductDetailsDailog extends DialogFragment implements View.OnClickListener {

    public  interface OnCompleteListener {
        void getProductDetails(String name,String Qty,String rate,String amnt);
    }
    private OnCompleteListener mListner;

    LinearLayout[] btn = new LinearLayout[12];

    TextView qty,mRate,mPrice,title;

    String ItemName,Price;
    int ItemId;

    LinearLayout rateclick;

    boolean isFocused=false;



    @SuppressLint("ValidFragment")
    public _AddProductDetailsDailog(String itemName, int itemId, String price) {
        ItemName = itemName;
        ItemId = itemId;
        Price = price;
    }

    public _AddProductDetailsDailog() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mListner=(OnCompleteListener) context;
        } catch (ClassCastException e) {
            e.printStackTrace();
        }

    }





    EditText input;
    Button add;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View RootView = inflater.inflate(R.layout.add_product_details_dailog, container, false);

        qty=RootView.findViewById(R.id.qty);
        mRate=RootView.findViewById(R.id.rate);

        mPrice=RootView.findViewById(R.id.price);
        input=RootView.findViewById(R.id.inputfield);
        title=RootView.findViewById(R.id.title);

        title.setText(ItemName);

        add=RootView.findViewById(R.id.add);

        rateclick=RootView.findViewById(R.id.rateclick);


        btn[0] = RootView.findViewById(R.id.l1);
        btn[1] = RootView.findViewById(R.id.l2);
        btn[2] = RootView.findViewById(R.id.l3);
        btn[3] = RootView.findViewById(R.id.l4);
        btn[4] = RootView.findViewById(R.id.l5);
        btn[5] = RootView.findViewById(R.id.l6);
        btn[6] = RootView.findViewById(R.id.l7);
        btn[7] = RootView.findViewById(R.id.l8);
        btn[8] = RootView.findViewById(R.id.l9);
        btn[9] = RootView.findViewById(R.id.l0);
        btn[10] = RootView.findViewById(R.id.back);
        btn[11] = RootView.findViewById(R.id.done);


        qty.requestFocus();

        for(int i =0;i<12;i++){
            btn[i].setOnClickListener(this);
        }



//        add.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Log.e("add","Clicked");
//
////                ProductList list=new ProductList();
////                list.setTargetFragment(list,1);
////                sendResult(Activity.RESULT_OK, "Test");
//
//
//            }
//        });


        mRate.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


            }

            @Override
            public void afterTextChanged(Editable s) {

                if(!mRate.getText().equals("")&&!qty.getText().equals(""))
                {
                    Log.e("CAlC","Focused");
                    int Total =Integer.parseInt(mRate.getText().toString().trim())*Integer.parseInt(qty.getText().toString().trim());
                    mPrice.setText(Integer.toString(Total));
                    input.setText("");
                }


            }
        });

        mRate.setText(Price);


        input.setOnTouchListener(new View.OnTouchListener(){

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int inType = input.getInputType(); // backup the input type
                input.setInputType(InputType.TYPE_NULL); // disable soft input
                input.onTouchEvent(event); // call native handler
                input.setInputType(inType); // restore input type
                return true; // consume touch even
            }
        });

        rateclick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                isFocused=true;
            }
        });

        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


        return RootView;
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.l1:
                addtoarray("1");
                break;
            case R.id.l2:
                addtoarray("2");
                break;
            case R.id.l3:
                addtoarray("3");
                break;
            case R.id.l4:
                addtoarray("4");
                break;
            case R.id.l5:
                addtoarray("5");
                break;
            case R.id.l6:
                addtoarray("6");
                break;
            case R.id.l7:
                addtoarray("7");
                break;
            case R.id.l8:
                addtoarray("8");
                break;
            case R.id.l9:
                addtoarray("9");
                break;
            case R.id.l0:
                addtoarray("0");
                break;
            case R.id.back:

                break;
            case R.id.done:
                Input();

                break;

        }
    }
    private void addtoarray(String numbers){
        //register TextBox

        input.append(numbers);
    }

    private void Input()
    {
        Log.e("Input()","Called"+qty.getHint());


        if(qty.getText().equals(""))
        {
            qty.setText(input.getText().toString());
            input.setText("");
            input.setError(null);
            if(!mRate.getText().equals("")) {
                int Total = Integer.parseInt(mRate.getText().toString().trim()) * Integer.parseInt(qty.getText().toString().trim());
                mPrice.setText(Integer.toString(Total));

            }

        }
        else if(isFocused)
        {
            Log.e("RATE","Focused");
            mRate.setText(input.getText().toString());
            input.setText("");
            isFocused=false;
            input.setError(null);

        }
        else if(!mRate.getText().equals("")&&!qty.getText().equals("")&&!mPrice.getText().toString().trim().equals(""))
        {
            input.setError(null);

            assert mListner != null;
            mListner.getProductDetails(ItemName,qty.getText().toString(),mRate.getText().toString(),mPrice.getText().toString());
            getDialog().dismiss();
        }
        else {
            input.setError("Check empty fields");
        }
    }


    @Override
    public void onDetach() {
        super.onDetach();
        mListner=null;

    }

    @Override
    public void onResume() {
        super.onResume();


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        isFocused=false;
    }
}
