package com.essensol.techmeq.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.essensol.techmeq.DialogFragments._AddProductDetailsDailog;
import com.essensol.techmeq.R;
import com.essensol.techmeq.Room.Databases.Entity.Products;


import java.util.List;


public  class ProductsAdapter extends  RecyclerView.Adapter<ProductsAdapter.viewHolder> {

    private List<Products>items;
    private Context mContext;

    public ProductsAdapter(List<Products> items, Context mContext) {
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



        holder.proName.setText(items.get(position).getProduct_Category());

        holder.category.setText(items.get(position).getProduct_Category());

        holder.price.setText(items.get(position).getProduct_Price()+""+"AED");


        holder.lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                FragmentManager fm =((AppCompatActivity) mContext).getSupportFragmentManager();


                final _AddProductDetailsDailog dialog= new _AddProductDetailsDailog(items.get(position).getProduct_Category()
                        ,items.get(position).getProduct_Id(),items.get(position).getProduct_Price());

                dialog.show(fm,"TAG");



            }
        });

    }

    public  void SetProducts(List<Products>products)
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

        private viewHolder(@NonNull View itemView) {
            super(itemView);

            lay=itemView.findViewById(R.id.lay);

            proName=itemView.findViewById(R.id.name);

            category=itemView.findViewById(R.id.category);

            price=itemView.findViewById(R.id.Price);

//            code=itemView.findViewById(R.id.code);

        }
    }





}
