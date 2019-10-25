package com.essensol.techmeq.Adapters;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.essensol.techmeq.DialogFragments._AddProductDetailsDailog;
import com.essensol.techmeq.Model.CategoryModel;
import com.essensol.techmeq.R;
import com.essensol.techmeq.Room.Databases.Entity.Products;
import com.essensol.techmeq.Room.Databases.Entity.Sales_Category;


import java.util.List;


public  class ProductsAdapter extends  RecyclerView.Adapter<ProductsAdapter.viewHolder> {

    private List<CategoryModel>items;
    private Context mContext;





    public ProductsAdapter(List<CategoryModel> items, Context mContext) {
        this.items = items;
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

        int deviceheight = displaymetrics.heightPixels /3;

        holder.imageid.getLayoutParams().height = deviceheight;

        holder.proName.setText(items.get(position).getProductCategory());

//        holder.category.setText(items.get(position).getProduct_Category());




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
