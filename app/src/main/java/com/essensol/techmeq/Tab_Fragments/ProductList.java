package com.essensol.techmeq.Tab_Fragments;


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

import com.essensol.techmeq.Adapters.ProductsAdapter;
import com.essensol.techmeq.Adapters.PurchaseListAdapter;
import com.essensol.techmeq.Model.CategoryModel;
import com.essensol.techmeq.Model.PurchaseModel;
import com.essensol.techmeq.R;
import com.essensol.techmeq.Room.Databases.Entity.Products;
import com.essensol.techmeq.Room.Databases.Entity.Sales_Category;
import com.essensol.techmeq.ViewModel.ProductViewModel;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProductList extends Fragment  {

    PurchaseListAdapter purchaseListAdapter;
    ArrayList<PurchaseModel> puchase = new ArrayList<>();
    ProductsAdapter adapter;
    GridLayoutManager layoutManager;
    RecyclerView products;
    List<CategoryModel> items = new ArrayList<>();
    RecyclerView purchase;
    Realm mRealm;
    private ProductViewModel model;
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


//

        adapter = new ProductsAdapter(items,getContext());

        products.setAdapter(adapter);



        model = ViewModelProviders.of(this).get(ProductViewModel.class);

        model.GetAllProductCategory().observe(this, new Observer<List<Sales_Category>>() {
            @Override
            public void onChanged(@Nullable List<Sales_Category> sales_categories) {
                if(sales_categories !=null) {
                    items.clear();
                    for (int i=0;i<sales_categories.size();i++)
                    {
                        if(sales_categories.get(i).isStatus())
                        {

                            CategoryModel item =new CategoryModel(sales_categories.get(i).getProductCatId(),
                                    sales_categories.get(i).getProductCategory(),
                                    sales_categories.get(i).getImage(),
                                    sales_categories.get(i).isStatus());

                            items.add(item);
                        }
                    }


                    adapter.SetProducts(items);
                }

            }
        });









        return  Rootview;
    }

//
//private  void SetAdapter(){
//    adapter = new ProductsAdapter(model.GetAllProductCategory().getValue(),getContext());
////
//        products.setAdapter(adapter);
//}




}
