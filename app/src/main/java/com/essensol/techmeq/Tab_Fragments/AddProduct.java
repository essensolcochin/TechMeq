package com.essensol.techmeq.Tab_Fragments;


import android.app.ProgressDialog;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.essensol.techmeq.Model.ProductModel;
import com.essensol.techmeq.R;
import com.essensol.techmeq.Room.Databases.Products;
import com.essensol.techmeq.UI.MainActivity;
import com.essensol.techmeq.ViewModel.ProductViewModel;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;
import io.realm.exceptions.RealmPrimaryKeyConstraintException;

import static com.essensol.techmeq.Model.ProductModel.ProductId;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddProduct extends Fragment {



   private EditText mProduct_name,mProductCategory,mProductCode,mPrice;
   private Button mAddProduct;

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
//        mProduct_name =Rootview.findViewById(R.id.productname);
        mProductCategory =Rootview.findViewById(R.id.category);
        mProductCode =Rootview.findViewById(R.id.productcode);
        mPrice =Rootview.findViewById(R.id.price);
        mAddProduct =Rootview.findViewById(R.id.add);



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

//                    mProduct_name.setText("");
                    mProductCategory.setText("");
                    mProductCode.setText("");
                    mPrice.setText("");
                    mAddProduct.setText("");


                }};



                Handler pdCanceller = new Handler();
            pdCanceller.postDelayed(progressRunnable, 500);

//                Fragment fragment = new ProductList();
//                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                fragmentTransaction.replace(R.id.tabMode, fragment);
//                fragmentTransaction.addToBackStack(null);
//                fragmentTransaction.commit();

                TabLayout tab = getActivity().findViewById(R.id.tabMode);

                tab.getTabAt(0).select();


            }
        });




        return Rootview;
    }

//    private void  AddProduct() {
//        Realm realm=null;
//
//        try {
//            realm = Realm.getDefaultInstance();
//            realm.executeTransaction(new Realm.Transaction() {
//                @Override
//                public void execute(Realm realm) {
//
//
//                    try {
//                        Number currentIdNum = realm.where(ProductModel.class).max(ProductModel.ProductId);
//                        int nextId;
//                        if(currentIdNum == null) {
//                            nextId = 1;
//                        } else {
//                            nextId = currentIdNum.intValue() + 1;
//                        }
//
//
//                            ProductModel product = new ProductModel(nextId,mProduct_name.getText().toString()
//                                                                     ,mProductCategory.getText().toString(),mPrice.getText().toString());
//
//
//
//
////                            if (!languageKnown.isEmpty()) {
////                                Skill skill = realm.where(Skill.class).equalTo(Skill.PROPERTY_SKILL, languageKnown).findFirst();
////
////                                if (skill == null) {
////                                    skill = realm.createObject(Skill.class, languageKnown);
////                                    realm.copyToRealm(skill);
////                                }
////
////                                employee.skills = new RealmList<>();
////                                employee.skills.add(skill);
////                            }
//
//                            realm.copyToRealm(product);
//
//
//                    } catch (RealmPrimaryKeyConstraintException e) {
//                        Toast.makeText(getActivity(), "Primary Key exists, Press Update instead", Toast.LENGTH_SHORT).show();
//                    }
//                }
//            });
//        } finally {
//            if (realm != null) {
//                realm.close();
//            }
//        }
//
//
//
//    }




    private  void _AddProduct() {

        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        String formattedDate = df.format(c);

        Products products =new Products(mProductCategory.getText().toString()
                ,mPrice.getText().toString()
                ,formattedDate);

        productViewModel.AddProduct(products);




    }


}
