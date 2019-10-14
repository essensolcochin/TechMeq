package com.essensol.techmeq.Tab_Fragments;


import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.essensol.techmeq.Adapters.ProductsAdapter;
import com.essensol.techmeq.Adapters.PurchaseListAdapter;
import com.essensol.techmeq.Model.ProductModel;
import com.essensol.techmeq.Model.ProductRecyclerviewModel;
import com.essensol.techmeq.Model.PurchaseModel;
import com.essensol.techmeq.R;
import com.essensol.techmeq.Room.Databases.Products;
import com.essensol.techmeq.ViewModel.ProductViewModel;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProductList extends Fragment  {

    PurchaseListAdapter purchaseListAdapter;
    ArrayList<PurchaseModel> puchase = new ArrayList<>();
    ProductsAdapter adapter;
    GridLayoutManager layoutManager;
    RecyclerView products;
    List<Products> items = new ArrayList<>();
    RecyclerView purchase;
    Realm mRealm;

    public ProductList() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View Rootview =inflater.inflate(R.layout.fragment_product_list, container, false);



        products = Rootview.findViewById(R.id.products);
        layoutManager = new GridLayoutManager(getContext(), 5);
        products.setLayoutManager(layoutManager);
        products.setItemViewCacheSize(6);
        products.setDrawingCacheEnabled(true);
        products.setHasFixedSize(true);
        products.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);


        adapter = new ProductsAdapter(items,getContext());

        products.setAdapter(adapter);




        final ProductViewModel model = ViewModelProviders.of(this).get(ProductViewModel.class);

        model.GetAllProduct().observe(this, new Observer<List<Products>>() {
            @Override
            public void onChanged(@Nullable List<Products> products) {
                Log.e("OnChanged","" +products.size());
                if(products.size()>0) {
                    adapter.SetProducts(products);
                }
            }
        });









        return  Rootview;
    }







}
