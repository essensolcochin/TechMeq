package com.essensol.techmeq.Tab_Fragments;


import android.app.ProgressDialog;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.essensol.techmeq.R;
import com.essensol.techmeq.ViewModel.ProductViewModel;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddProduct extends Fragment {



   private EditText mProduct_name,tax,mPrice;

    private ProductViewModel productViewModel;

   ProgressDialog progressDialog;

    public AddProduct() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View Rootview = inflater.inflate(R.layout.fragment_add_product, container, false);

//        if (!mProduct_name.getText().toString().trim().isEmpty()) {
//        }
        mProduct_name =Rootview.findViewById(R.id.productname);
        Spinner mProductCategory = Rootview.findViewById(R.id.category);
        tax =Rootview.findViewById(R.id.tax);
        mPrice =Rootview.findViewById(R.id.price);
        Button mAddProduct = Rootview.findViewById(R.id.add);



        progressDialog =new ProgressDialog(getContext());
        progressDialog.setTitle("Adding Product");
        progressDialog.setMessage("Saving...");
        progressDialog.setCancelable(false);


        productViewModel= ViewModelProviders.of(this).get(ProductViewModel.class);

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



                TabLayout tab = getActivity().findViewById(R.id.tabMode);

                tab.getTabAt(0).select();


            }
        });




        return Rootview;
    }






    private  void _AddProduct() {

//        Date c = Calendar.getInstance().getTime();
//        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
//        String formattedDate = df.format(c);
//
//        Products products =new Products(mProductCategory.getText().toString()
//                ,mPrice.getText().toString()
//                ,formattedDate);
//
//        productViewModel.AddProduct(products);




    }


}
