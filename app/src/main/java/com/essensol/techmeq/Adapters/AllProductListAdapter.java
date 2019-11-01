package com.essensol.techmeq.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.essensol.techmeq.Model.ProductModel;
import com.essensol.techmeq.Model.mProductModel;
import com.essensol.techmeq.OnSelectedListener;
import com.essensol.techmeq.R;
import com.essensol.techmeq.Room.Databases.Entity.Products;

import java.util.ArrayList;
import java.util.List;

public class AllProductListAdapter  extends  RecyclerView.Adapter<AllProductListAdapter.viewHolder> {

    private List<ProductModel> items;

//    private List<mProductModel> newList=new ArrayList<>();

    private Context mContext;






    public AllProductListAdapter(List<ProductModel> items, Context mContext) {
        this.items = items;
        this.mContext = mContext;


    }

    @NonNull
    @Override
    public AllProductListAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.allproductlist, parent, false);


        return new AllProductListAdapter.viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AllProductListAdapter.viewHolder holder, int position) {
        final ProductModel products=items.get(position);

        holder.name.setText(items.get(position).getProductName());

        holder.tax.setText(products.getTaxPercent().toString());
        holder.category.setText(products.getProductCategory());
        holder.price.setText(products.getSales_Price().toString());

        holder.lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });



    }





    @Override
    public int getItemCount() {

        Log.e("getItemCount","list Size -- "+items.size());
        return items.size();
    }

    public  void SetProducts(List<ProductModel>products)
    {
        this.items=products;
        notifyDataSetChanged();
    }


    public class viewHolder extends RecyclerView.ViewHolder{

        TextView name,category,price,tax;


        LinearLayout lay;

        private viewHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.proName);
            lay=itemView.findViewById(R.id.lay);
            tax=itemView.findViewById(R.id.tax);
            price=itemView.findViewById(R.id.price);
            category=itemView.findViewById(R.id.category);


        }
    }
}
