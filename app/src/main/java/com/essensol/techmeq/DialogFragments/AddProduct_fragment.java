package com.essensol.techmeq.DialogFragments;


import android.app.Dialog;
import android.app.ProgressDialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.essensol.techmeq.R;
import com.essensol.techmeq.Room.Databases.Entity.Products;
import com.essensol.techmeq.Room.Databases.Entity.Sales_Category;
import com.essensol.techmeq.ViewModel.ProductViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddProduct_fragment extends DialogFragment {



    List<Sales_Category> categories=new ArrayList<>();
    ArrayAdapter<Sales_Category> categoryArrayAdapter;

    private EditText mProduct_name,tax,mPrice;

    private ProductViewModel productViewModel;
    int  catId;
    Spinner mProductCategory;

    ProgressDialog progressDialog;



    public AddProduct_fragment() {
        // Required empty public constructor
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View Rootview= inflater.inflate(R.layout.add_product, container, false);




        mProduct_name =Rootview.findViewById(R.id.productname);
        mProductCategory = Rootview.findViewById(R.id.category);
        tax =Rootview.findViewById(R.id.tax);
        mPrice =Rootview.findViewById(R.id.price);
        Button mAddProduct = Rootview.findViewById(R.id.add);



        progressDialog =new ProgressDialog(getContext());
        progressDialog.setTitle("Adding Product");
        progressDialog.setMessage("Saving...");
        progressDialog.setCancelable(false);







        productViewModel= ViewModelProviders.of(this).get(ProductViewModel.class);

        productViewModel.GetAllProductCategory().observe(this, new Observer<List<Sales_Category>>() {
            @Override
            public void onChanged(@Nullable List<Sales_Category> sales_categories) {

                categories=sales_categories;

                categoryArrayAdapter = new ArrayAdapter<Sales_Category>(getContext(), android.R.layout.simple_spinner_dropdown_item, categories);
                categoryArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                mProductCategory.setAdapter(categoryArrayAdapter);

            }
        });



        mProductCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                catId =categories.get(position).getProductCatId();
                Log.e("Cat Iddddd"," "+catId);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });




        mAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {




                progressDialog.show();

                Runnable progressRunnable = new Runnable() {

                    @Override
                    public void run() {

                        _AddProduct();


                        progressDialog.cancel();

                        mProduct_name.setText("");
                        tax.setText("");
                        mPrice.setText("");



                    }};



                Handler pdCanceller = new Handler();
                pdCanceller.postDelayed(progressRunnable, 500);

                getDialog().dismiss();




            }
        });


        return Rootview;
    }

    private  void _AddProduct() {



        Products products =new Products(catId,Double.parseDouble(tax.getText().toString()),mProduct_name.getText().toString()
                ,Double.parseDouble(mPrice.getText().toString())
                ,true);

        productViewModel.AddProduct(products);




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
