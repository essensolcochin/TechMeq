package com.essensol.techmeq.DialogFragments;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.essensol.techmeq.Adapters.ProductListAdapter;
import com.essensol.techmeq.Adapters.SelectedListAdapter;
import com.essensol.techmeq.Model.mProductModel;
import com.essensol.techmeq.OnSelectedListener;
import com.essensol.techmeq.R;
import com.essensol.techmeq.Room.Databases.Entity.Products;
import com.essensol.techmeq.Room.Databases.Entity.Sales_Category;
import com.essensol.techmeq.Utils;
import com.essensol.techmeq.ViewModel.ProductViewModel;

import org.w3c.dom.Text;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class _AddProductDetailsDailog extends DialogFragment implements View.OnClickListener, OnSelectedListener {


    ProductViewModel model;

    List<mProductModel>newlist=new ArrayList<>();




    public  interface OnCompleteListener {
        void getProductListItem(int Qty, int Product_Id, int ProductCatId, BigDecimal TaxPercent, String ProductName, BigDecimal Sales_Price, BigDecimal rate);
    }
    private OnCompleteListener mListner;

    LinearLayout[] btn = new LinearLayout[15];

    TextView qty,mRate,mPrice,title;

    String ItemName;
    double Price;
    int ItemId;

    LinearLayout rateclick;

    boolean isFocused=false;

    boolean isRate=false;

    private int CategoryId;

    RecyclerView products,selectedItems;

    List<Products>mproducts=new ArrayList<>();


    List<Sales_Category>categories=new ArrayList<>();
    ArrayAdapter<Sales_Category>categoryArrayAdapter;

    Spinner mProductCategory;


    Context mContext;

    ProductListAdapter adapter;

    SelectedListAdapter mAdapter;


    int Product_Id;
    int ProductCatId;
    BigDecimal TaxPercent;
    String ProductName;
    BigDecimal Sales_Price;
    boolean Status;



//    @Override
//    public void getProductDetails(List<mProductModel> items) {
//
//        Log.e("CallbackList","items "+items.size());
//
//      //  newlist=items;
////
////        mAdapter =new SelectedListAdapter(items,getContext());
////        selectedItems.setAdapter(mAdapter);
////        adapter.notifyDataSetChanged();
//
//    }




    @SuppressLint("ValidFragment")
    public _AddProductDetailsDailog( int CategoryId,Context context) {
        this.CategoryId = CategoryId;
        this.mContext =context;

    }

    public _AddProductDetailsDailog() {


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

        mPrice=RootView.findViewById(R.id.total);

        selectedItems=RootView.findViewById(R.id.selected);
        mProductCategory=RootView.findViewById(R.id.category);


        title=RootView.findViewById(R.id.productname);

//        input=RootView.findViewById(R.id.input);

        add=RootView.findViewById(R.id.add);

        products=RootView.findViewById(R.id.products);





        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        products.setLayoutManager(linearLayoutManager);
        products.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL));



//        LinearLayoutManager selected = new LinearLayoutManager(getContext());
//        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
//
//        selectedItems.setLayoutManager(selected);



            qty.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    final int sdk = android.os.Build.VERSION.SDK_INT;
                    if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                        qty.setBackgroundDrawable(ContextCompat.getDrawable(getContext(), R.drawable.edittextbgm) );
                        mRate.setBackgroundDrawable(ContextCompat.getDrawable(getContext(), R.drawable.selected) );

                    } else {
                        qty.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.edittextbgm));
                        mRate.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.selected));
                    }



                    isFocused=true;
                    isRate=false;
                }
            });

            mRate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    isRate=true;
                    isFocused=false;

                    final int sdk = android.os.Build.VERSION.SDK_INT;
                    if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                        mRate.setBackgroundDrawable(ContextCompat.getDrawable(getContext(), R.drawable.edittextbgm) );
                        qty.setBackgroundDrawable(ContextCompat.getDrawable(getContext(), R.drawable.selected) );

                    } else {
                        mRate.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.edittextbgm));
                        qty.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.selected));
                    }

                }
            });



        adapter=new ProductListAdapter(mproducts,mContext,_AddProductDetailsDailog.this);
        products.setAdapter(adapter);

        model= ViewModelProviders.of(this).get(ProductViewModel.class);

        setObserver(CategoryId);




        model.GetAllProductCategory().observe(this, new Observer<List<Sales_Category>>() {
            @Override
            public void onChanged(@Nullable List<Sales_Category> sales_categories) {

                categories=sales_categories;

                categoryArrayAdapter = new ArrayAdapter<Sales_Category>(getContext(), android.R.layout.simple_spinner_dropdown_item, categories);
                categoryArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                mProductCategory.setAdapter(categoryArrayAdapter);

                int pos =0;

                for(Sales_Category items : categories){
                    if (items.getProductCatId()==CategoryId){

                        pos = categories.indexOf(items);
                        break;
                    }
                }
                mProductCategory.setSelection(pos);



            }
        });



        mProductCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                CategoryId =categories.get(position).getProductCatId();
                Log.e("Cat Iddddd"," "+CategoryId);

                setObserver(CategoryId);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


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
        btn[10] = RootView.findViewById(R.id.clear);
        btn[11] = RootView.findViewById(R.id.done);
        btn[12] = RootView.findViewById(R.id.dot);
        btn[13] = RootView.findViewById(R.id.back);
        btn[14] = RootView.findViewById(R.id.Zero);


//        qty.requestFocus();

        for(int i =0;i<15;i++){
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


            qty.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {


                }

                @Override
                public void afterTextChanged(Editable s) {

                    if(isFocused) {

                        if (!qty.getText().toString().equals("") && !mRate.getText().toString().equals(""))
                        {

                           if(qty.getText()!=null&& mRate.getText()!=null)
                           {
                               Log.e("CAlC", "mRate" + mRate.getText().toString().trim());

                               Log.e("CAlC", "qty" + qty.getText().toString().trim());

                               BigDecimal rate = Utils.round(Float.parseFloat(mRate.getText().toString().trim()),2);

                               int Qty = Integer.parseInt(qty.getText().toString());

                               BigDecimal Total = rate.multiply(BigDecimal.valueOf(Qty));

                               Log.e("CAlC", "Total " + Total);

                               mPrice.setText(Total.toString());
                           }
                        }

                    }
                    else if(isRate) {
                        if (!qty.getText().toString().equals("") && !mRate.getText().toString().equals("")) {

                            if (qty.getText() != null && mRate.getText() != null) {
                                Log.e("CAlC", "mRate" + mRate.getText().toString().trim());

                                Log.e("CAlC", "qty" + qty.getText().toString().trim());

                                BigDecimal rate = Utils.round(Float.parseFloat(mRate.getText().toString().trim()), 2);

                                int Qty = Integer.parseInt(s.toString());

                                BigDecimal Total = rate.multiply(BigDecimal.valueOf(Qty));

                                Log.e("CAlC", "Total " + Total);

                                mPrice.setText(Total.toString());
                            }
                        }
                    }




                }
            });



        mRate.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


            }

            @Override
            public void afterTextChanged(Editable s) {

                if(isFocused) {

                    if (!qty.getText().toString().equals("") && !mRate.getText().toString().equals(""))
                    {

                        if(qty.getText()!=null&& mRate.getText()!=null)
                        {
                            Log.e("CAlC", "mRate" + mRate.getText().toString().trim());

                            Log.e("CAlC", "qty" + qty.getText().toString().trim());

                            BigDecimal rate = Utils.round(Float.parseFloat(mRate.getText().toString().trim()),2);

                            int Qty = Integer.parseInt(qty.getText().toString());

                            BigDecimal Total = rate.multiply(BigDecimal.valueOf(Qty));

                            Log.e("CAlC", "Total " + Total);

                            mPrice.setText(Total.toString());
                        }
                    }

                }
                else if(isRate) {
                    if (!qty.getText().toString().equals("") && !mRate.getText().toString().equals("")) {

                        if (qty.getText() != null && mRate.getText() != null) {
                            Log.e("CAlC", "mRate" + mRate.getText().toString().trim());

                            Log.e("CAlC", "qty" + qty.getText().toString().trim());

                            BigDecimal rate = Utils.round(Float.parseFloat(mRate.getText().toString().trim()), 2);

                            int Qty = Integer.parseInt(qty.getText().toString());

                            BigDecimal Total = rate.multiply(BigDecimal.valueOf(Qty));

                            Log.e("CAlC", "Total " + Total);

                            mPrice.setText(Total.toString());
                        }
                    }
                }




            }
        });







        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));


        return RootView;
    }




    private  void setObserver(int CategoryId)
    {
        model.GetProduct_By_CategoryId(CategoryId).observe(this, new Observer<List<Products>>() {
            @Override
            public void onChanged(@Nullable List<Products> products) {
                Log.e("Onchanged","list Size -- "+products.size());
                adapter.SetProducts(products);
            }
        });
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
            case R.id.dot:
                if(!isFocused) {
                    addtoarray(".");
                }
                break;

            case R.id.Zero:
                addtoarray("00");
                break;




            case R.id.clear:
                if(isFocused&&!qty.getText().toString().equalsIgnoreCase(""))
                {
                    qty.setText("");

                }
                else if(isRate&&!mRate.getText().toString().equalsIgnoreCase(""))
                {
                    mRate.setText("");

                }
                break;
            case R.id.back:
                if(isFocused&&!qty.getText().toString().equalsIgnoreCase(""))
                {
                  qty.setText(qty.getText().toString().substring(0, qty.getText().toString().length() - 1));

                }
                else if(isRate&&!mRate.getText().toString().equalsIgnoreCase(""))
                {
                    mRate.setText(mRate.getText().toString().substring(0, mRate.getText().toString().length() - 1));

                }

                break;
            case R.id.done:



                Input();

                break;

        }
    }
    private void addtoarray(String numbers){
        if(isFocused)
        {
            qty.append(numbers);
        }
        else if(isRate)
        {
            mRate.append(numbers);
        }

    }




    private void Input()
    {
        Log.e("Input()","Called"+qty.getHint());


        if(qty.getText().equals(""))
        {
//            qty.setText(input.getText().toString());
//            input.setText("");
//            input.setError(null);
//            input.getText().clear();
            if(!mRate.getText().equals("")) {
                int Total = Integer.parseInt(mRate.getText().toString().trim()) * Integer.parseInt(qty.getText().toString().trim());
                mPrice.setText(Integer.toString(Total));

            }

        }

//        else if(isFocused)
//        {
//            Log.e("RATE","Focused");
//            mRate.setText(input.getText().toString());
//            input.setText("");
//            isFocused=false;
//            input.setError(null);
//
//        }

        else if(!mRate.getText().equals("")&&!qty.getText().equals("")&&!mPrice.getText().toString().trim().equals(""))
        {
            Sales_Price =Utils.round(Float.parseFloat(mRate.getText().toString()),2);
//
            BigDecimal rate= Sales_Price.multiply(BigDecimal.valueOf(Integer.parseInt(qty.getText().toString())));

            assert mListner != null;
            mListner.getProductListItem(Integer.parseInt(qty.getText().toString()),Product_Id,ProductCatId,TaxPercent,title.getText().toString(),Sales_Price,rate);
            getDialog().dismiss();

        }

        else {
//            input.setError("Check empty fields");
        }
    }
//    private void Input()
//    {
//        Log.e("Input()","Called"+qty.getHint());
//
//
//        if(qty.getText().equals(""))
//        {
//            qty.setText(input.getText().toString());
//            input.setText("");
//            input.setError(null);
//            if(!mRate.getText().equals("")) {
//                int Total = Integer.parseInt(mRate.getText().toString().trim()) * Integer.parseInt(qty.getText().toString().trim());
//                mPrice.setText(Integer.toString(Total));
//
//            }
//
//        }
//        else if(isFocused)
//        {
//            Log.e("RATE","Focused");
//            mRate.setText(input.getText().toString());
//            input.setText("");
//            isFocused=false;
//            input.setError(null);
//
//        }
//        else if(!mRate.getText().equals("")&&!qty.getText().equals("")&&!mPrice.getText().toString().trim().equals(""))
//        {
//            input.setError(null);
//
//
//        }
//        else {
//            input.setError("Check empty fields");
//        }
//    }



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





//    @Override
//    public void getProductDetails(int Product_Id, int ProductCatId, double TaxPercent, String ProductName, double Sales_Price, boolean Status) {
//
//        mProductModel model=new mProductModel(Product_Id,ProductCatId
//                ,TaxPercent,ProductName
//                ,Sales_Price,Status);
//
//        newlist.add(model);
//
//        Log.e("CallbackList","items "+newlist.size());
//
//        mAdapter =new SelectedListAdapter(newlist,getContext());
//        selectedItems.setAdapter(mAdapter);
//        adapter.notifyDataSetChanged();
//
//    }


    @Override
    public void getProductDetails(int Product_Id, int ProductCatId, BigDecimal TaxPercent, String ProductName, BigDecimal Sales_Price, boolean Status) {

        title.setText(ProductName);

      this.Product_Id=Product_Id;
      this.ProductCatId=ProductCatId;
      this.TaxPercent=TaxPercent;
      this.ProductName=ProductName;
      this.Sales_Price=Sales_Price;
      this.Status=Status;



        qty.setText("1");

        mRate.setText(Sales_Price.toString());

        mPrice.setText(Sales_Price.multiply(new BigDecimal(Integer.parseInt(qty.getText().toString().trim()))).toString());

    }


}
