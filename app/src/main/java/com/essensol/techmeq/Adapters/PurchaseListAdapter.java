package com.essensol.techmeq.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.essensol.techmeq.Model.PurchaseModel;
import com.essensol.techmeq.Model.mProductModel;
import com.essensol.techmeq.R;

import java.util.ArrayList;
import java.util.List;


public  class PurchaseListAdapter extends  RecyclerView.Adapter<PurchaseListAdapter.viewHolder> {

    private List<mProductModel> items;
    private Context mContext;

    public PurchaseListAdapter(List<mProductModel> items, Context mContext) {
        this.items = items;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.purchaselist, parent, false);


        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {

        holder.name.setText(items.get(position).getProductName());
        holder.qty.setText(Integer.toString(items.get(position).getProductCatId()));
//        holder.rate.setText(items.get(position).getRate());
        holder.price.setText(Double.toString(items.get(position).getSales_Price()));

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder{

        TextView name,qty,price,rate;

        private viewHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.proName);
            qty=itemView.findViewById(R.id.qty);
            price=itemView.findViewById(R.id.price);
            rate=itemView.findViewById(R.id.rate);

        }
    }

}
