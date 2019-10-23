package com.essensol.techmeq.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.essensol.techmeq.R;
import com.essensol.techmeq.Room.Databases.Entity._dbExpenceVouchers;

import java.util.List;


public  class VoucherListAdapter extends  RecyclerView.Adapter<VoucherListAdapter.viewHolder> {

    private List<_dbExpenceVouchers>items;
    private Context mContext;

    public VoucherListAdapter(List<_dbExpenceVouchers> items, Context mContext) {
        this.items = items;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.voucherlist, parent, false);


        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {

        holder.desc.setText(items.get(position).getDescription());
        holder.remark.setText(items.get(position).getRemarks());
        holder.tax.setText(items.get(position).getTaxable());
        holder.vat.setText(items.get(position).getVat());

    }


    public  void SetProducts(List<_dbExpenceVouchers> vouchers)
    {
        this.items=vouchers;
        notifyDataSetChanged();
    }



    @Override
    public int getItemCount() {
        return items.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder{

        TextView desc,remark,tax,vat;

        private viewHolder(@NonNull View itemView) {
            super(itemView);
            desc=itemView.findViewById(R.id.desc);
            remark=itemView.findViewById(R.id.remark);
            tax=itemView.findViewById(R.id.tax);
            vat=itemView.findViewById(R.id.vat);

        }
    }

}
