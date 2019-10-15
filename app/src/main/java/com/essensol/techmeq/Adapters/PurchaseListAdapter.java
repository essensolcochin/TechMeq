package com.essensol.techmeq.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.essensol.techmeq.Model.PurchaseModel;
import com.essensol.techmeq.R;

import java.util.ArrayList;


public  class PurchaseListAdapter extends  RecyclerView.Adapter<PurchaseListAdapter.viewHolder> {

    private ArrayList<PurchaseModel>items;
    private Context mContext;

    public PurchaseListAdapter(ArrayList<PurchaseModel> items, Context mContext) {
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

        holder.name.setText(items.get(position).getName());
        holder.qty.setText(items.get(position).getQty());
        holder.rate.setText(items.get(position).getRate());
        holder.price.setText(items.get(position).getNetAmount());

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
