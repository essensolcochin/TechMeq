package com.essensol.techmeq.Adapters;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.essensol.techmeq.DialogFragments._AddProductDetailsDailog;
import com.essensol.techmeq.Model.CategoryModel;
import com.essensol.techmeq.R;
import com.essensol.techmeq.Room.Databases.Entity.Products;
import com.essensol.techmeq.Room.Databases.Entity.Sales_Category;


import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public  class ProductsAdapter extends  RecyclerView.Adapter<ProductsAdapter.viewHolder>  {

    private List<CategoryModel>items;
    private Context mContext;
    private List<CategoryModel> searchList;





    public ProductsAdapter(List<CategoryModel> items, Context mContext) {
        this.items = items;
        this.searchList = items;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.productlist, parent, false);


        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, final int position) {


        DisplayMetrics displaymetrics = new DisplayMetrics();
        ((Activity) mContext).getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);

        int devicewidth = displaymetrics.widthPixels/2;

        int deviceheight = displaymetrics.heightPixels /6;

        holder.imageid.getLayoutParams().height = deviceheight;

        holder.proName.setText(items.get(position).getProductCategory());

        Log.e("Products Adapter"," "+items.get(position).getImage());

        if(!items.get(position).getImage().equalsIgnoreCase("Image Path"))
        {
            Glide.with(mContext).load(Uri.fromFile(new File(items.get(position).getImage()))).into(holder.imageid);
        }
        else {
            Glide.with(mContext).load(R.drawable.placeholder).into(holder.imageid);

        }



        holder.lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                FragmentManager fm =((AppCompatActivity) mContext).getSupportFragmentManager();


                final _AddProductDetailsDailog dialog= new _AddProductDetailsDailog(items.get(position).getProductCatId(),mContext);

                dialog.show(fm,"TAG");



            }
        });

    }

    public  void SetProducts(List<CategoryModel>products)
    {
        this.items=products;
        notifyDataSetChanged();
    }

    public void filter(String charText){

        charText = charText.toLowerCase(Locale.getDefault());
//        items.clear();
        if (charText.length() == 0) {
            searchList.addAll(items);
        } else {
            for (CategoryModel model : searchList) {
                if (model.getProductCategory().toLowerCase(Locale.getDefault())
                        .contains(charText)) {
                    Log.e("Contains",""+model.getProductCategory());

                    items.add(model);
                }
//                else if (model.getProductCategory().toLowerCase(Locale.getDefault())
//                        .contains(charText)) {
//                    friendList.add(model);
//                }
            }
        }
        notifyDataSetChanged();

    }




    @Override
    public int getItemCount() {


            return items.size();


    }




    public class viewHolder extends RecyclerView.ViewHolder{
        LinearLayout lay;

        TextView proName,category,price,code;
        ImageView imageid;

        private viewHolder(@NonNull View itemView) {
            super(itemView);

            lay=itemView.findViewById(R.id.lay);

            proName=itemView.findViewById(R.id.name);

            imageid=itemView.findViewById(R.id.imageid);

//            price=itemView.findViewById(R.id.Price);

//            code=itemView.findViewById(R.id.code);

        }
    }





}
