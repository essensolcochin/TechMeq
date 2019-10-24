package com.essensol.techmeq.Adapters;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.essensol.techmeq.DialogFragments._AddProductDetailsDailog;
import com.essensol.techmeq.Model.PurchaseModel;
import com.essensol.techmeq.Model.mProductModel;
import com.essensol.techmeq.OnSelectedListener;
import com.essensol.techmeq.R;
import com.essensol.techmeq.Room.Databases.Entity.Products;

import java.util.ArrayList;
import java.util.List;


public  class ProductListAdapter extends  RecyclerView.Adapter<ProductListAdapter.viewHolder> {

    private List<Products> items;

    private List<mProductModel> newList=new ArrayList<>();

    private Context mContext;




    private OnSelectedListener mListner;

    public ProductListAdapter(List<Products> items, Context mContext) {
        this.items = items;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.products, parent, false);




        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder,  int position) {
       final Products products=items.get(position);

        holder.name.setText(items.get(position).getProductName());

        holder.rate.setText(Double.toString(items.get(position).getSales_Price()));

        holder.lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mProductModel model=new mProductModel(products.getProduct_Id(),products.getProductCatId()
                                                        ,products.getTaxPercent(),products.getProductName()
                                                        ,products.getSales_Price(),products.isStatus());

                newList.add(model);

                Log.e("newList","size "+newList.size());

                if(mListner==null)
                {
                    try {

                        mListner = ((OnSelectedListener) mContext);

                    } catch (ClassCastException e) {
                        e.printStackTrace();
                    }
                }
                else {
                    mListner.getProductDetails(newList);

                }

            }
        });


    }

    public void setListener(OnSelectedListener listener) {
        this.mListner = listener;
    }




    @Override
    public int getItemCount() {

        Log.e("getItemCount","list Size -- "+items.size());
        return items.size();
    }

    public  void SetProducts(List<Products>products)
    {
        this.items=products;
        notifyDataSetChanged();
    }


    public class viewHolder extends RecyclerView.ViewHolder{

        TextView name,qty,rate;

        LinearLayout lay;

        private viewHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.proName);
            lay=itemView.findViewById(R.id.lay);
            rate=itemView.findViewById(R.id.rate);

        }
    }

}
