package com.essensol.techmeq.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.essensol.techmeq.Model.PurchaseModel;
import com.essensol.techmeq.Model.mProductModel;
import com.essensol.techmeq.R;
import com.essensol.techmeq.UI.MainActivity;

import java.util.ArrayList;
import java.util.List;


public  class PurchaseListAdapter extends  RecyclerView.Adapter<PurchaseListAdapter.viewHolder> {

    private List<PurchaseModel> items;
    private Context mContext;

    public PurchaseListAdapter(List<PurchaseModel> items, Context mContext) {
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
    public void onBindViewHolder(@NonNull final viewHolder holder, int position) {

        holder.name.setText(items.get(position).getName());
        holder.qty.setText(items.get(position).getQty().toString());
        holder.rate.setText(items.get(position).getRate().toString());
        holder.vat.setText(items.get(position).getTaxAmnt().toString());
        holder.price.setText(items.get(position).getLinetot().toString());

        holder.remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                removeItem(holder.getAdapterPosition());
            }
        });

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder{

        TextView name,qty,price,rate,vat;
        ImageView remove;

        private viewHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.proName);
            qty=itemView.findViewById(R.id.qty);
            price=itemView.findViewById(R.id.price);
            rate=itemView.findViewById(R.id.rate);
            remove=itemView.findViewById(R.id.remove);
            vat=itemView.findViewById(R.id.vat);
        }
    }


    private void removeItem(int position) {
        items.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, items.size());
        ((MainActivity)mContext).Calc();
//        mContext
    }

}
