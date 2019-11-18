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

import com.essensol.techmeq.Callbacks.SaleReportItemClickListener;
import com.essensol.techmeq.Model.ItemReportModel;
import com.essensol.techmeq.R;
import com.essensol.techmeq.UI.Login;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;


public  class SaleReportsAdapter extends  RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<ItemReportModel>items;
    private Context mContext;

    private static final int LAYOUT_HEADER = 0;
    private static final int LAYOUT_CHILD = 1;

    private  LayoutInflater inflater;

    private SaleReportItemClickListener mlistener;

    public SaleReportsAdapter(List<ItemReportModel> items, Context mContext, SaleReportItemClickListener mlistener) {
        this.items = items;
        this.mContext = mContext;
        inflater = LayoutInflater.from(mContext);
        this.mlistener =mlistener;

    }

//    @Override
//    public int getItemViewType(int position) {
//        if (items.get(position).isHeader()) {
//            return LAYOUT_HEADER;
//        } else
//            return LAYOUT_CHILD;
//    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.reportbody, parent, false);


        return new MyViewHolderChild(view);

//        RecyclerView.ViewHolder holder;
//        if (viewType == LAYOUT_HEADER) {
//            View view = inflater.inflate(R.layout.reporthead, parent, false);
//            holder = new MyViewHolderHeader(view);
//        } else {
//            View view = inflater.inflate(R.layout.reportbody, parent, false);
//            holder = new MyViewHolderChild(view);
//        }



    }





    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {



//        if (holder.getItemViewType() == LAYOUT_HEADER) {
//            MyViewHolderHeader vaultItemHolder = (MyViewHolderHeader) holder;
//
//        } else {


            final ItemReportModel   model = items.get(position);
            final MyViewHolderChild childItemHolder = (MyViewHolderChild) holder;



        childItemHolder.Saleno.setText(items.get(position).getSaleNo());

        childItemHolder.grandtotal.setText(items.get(position).getGrandTotal().toString());

        childItemHolder.price.setText(items.get(position).getSubTotal().toString());

        String myFormat = "dd/MMM/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.ENGLISH);

        childItemHolder.date.setText(sdf.format(items.get(position).getSaleDate()));

        childItemHolder.vat.setText(items.get(position).getTaxAmt().toString());

        childItemHolder.paid.setText(items.get(position).getPaidAmt().toString());

        if(model.getPaidAmt().toString().equalsIgnoreCase("0.00"))
        {
            childItemHolder.credit.setText(model.getGrandTotal().toString());
        }
        else {
            childItemHolder.credit.setText("0.00");
        }



        childItemHolder.lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.e("SaleId Adapter",""+model.getSaleId());

            if(mlistener!=null)
            {
                mlistener.getSaleId(model.getSaleId(),model.getSaleNo(),model.getSaleDate());
            }


            }
        });

        childItemHolder.round_off.setText(model.getRounded().toString());

    }




    @Override
    public int getItemCount() {
        return items.size();
    }



    public  void AddReports(List<ItemReportModel>reports)
    {
        this.items=reports;
        notifyDataSetChanged();
    }



   private class MyViewHolderHeader extends RecyclerView.ViewHolder {


        private MyViewHolderHeader(View itemView) {
            super(itemView);


        }

    }

    class MyViewHolderChild extends RecyclerView.ViewHolder {

        LinearLayout lay;

        TextView Saleno,grandtotal,price,date,vat,paid,credit,round_off;


        private MyViewHolderChild(View itemView) {
            super(itemView);



            Saleno=itemView.findViewById(R.id.Saleno);

            grandtotal=itemView.findViewById(R.id.grandtotal);

            price=itemView.findViewById(R.id.price);

            date=itemView.findViewById(R.id.date);
            vat=itemView.findViewById(R.id.vat);

            paid=itemView.findViewById(R.id.paid);

            lay=itemView.findViewById(R.id.lay);

            credit=itemView.findViewById(R.id.credit);

            round_off=itemView.findViewById(R.id.round_off);

        }



    }


}
