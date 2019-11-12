package com.essensol.techmeq.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.essensol.techmeq.Model.mProductModel;
import com.essensol.techmeq.R;

import java.util.List;


public  class SelectedListAdapter extends  RecyclerView.Adapter<SelectedListAdapter.viewHolder> {

    private List<mProductModel> items;



    private Context mContext;






    public SelectedListAdapter(List<mProductModel> items, Context mContext) {
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
    public void onBindViewHolder(@NonNull viewHolder holder, final int position) {

        holder.name.setText(items.get(position).getProductName());

        holder.rate.setText(Double.toString(items.get(position).getSales_Price()));




    }





    @Override
    public int getItemCount() {

        Log.e("getItemCount","Selected list Size -- "+items.size());
        return items.size();
    }

    public  void SetProducts(List<mProductModel>products)
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
