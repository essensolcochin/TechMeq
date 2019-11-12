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

import com.essensol.techmeq.Model.ItemReportModel;
import com.essensol.techmeq.R;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;


public  class TaxAdapter extends  RecyclerView.Adapter<TaxAdapter.viewHolder> {

    private List<ItemReportModel> items;


    private Context mContext;

    public TaxAdapter(List<ItemReportModel> items, Context mContext) {
        this.items = items;
        this.mContext = mContext;

    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.taxlist, parent, false);


        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder,  int position) {

        holder.Saleno.setText(items.get(position).getSaleNo());
        holder.grandtotal.setText(items.get(position).getGrandTotal().toString());
        holder.price.setText(items.get(position).getSubTotal().toString());

        String myFormat = "dd/MMM/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.ENGLISH);

        holder.date.setText(sdf.format(items.get(position).getSaleDate()));

        holder.vat.setText(items.get(position).getTaxAmt().toString());

    }





    @Override
    public int getItemCount() {

        Log.e("getItemCount","list Size -- "+items.size());
        return items.size();
    }

    public  void AddTax(List<ItemReportModel>taxlist)
    {
        this.items=taxlist;
        notifyDataSetChanged();
    }


    public class viewHolder extends RecyclerView.ViewHolder{

        TextView Saleno,grandtotal,price,date,vat;
        ImageView imageid;

        LinearLayout lay;

        private viewHolder(@NonNull View itemView) {
            super(itemView);

            Saleno=itemView.findViewById(R.id.Saleno);

            grandtotal=itemView.findViewById(R.id.grandtotal);

            price=itemView.findViewById(R.id.price);

            date=itemView.findViewById(R.id.date);

            vat=itemView.findViewById(R.id.vat);

        }
    }

}
