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
import com.essensol.techmeq.Model.ItemReportModel;
import com.essensol.techmeq.R;
import com.essensol.techmeq.Room.Databases.Products;

import java.util.List;


public  class ReportsAdapter extends  RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<ItemReportModel>items;
    private Context mContext;

    private static final int LAYOUT_HEADER = 0;
    private static final int LAYOUT_CHILD = 1;

    private  LayoutInflater inflater;

    public ReportsAdapter(List<ItemReportModel> items, Context mContext) {
        this.items = items;
        this.mContext = mContext;
        inflater = LayoutInflater.from(mContext);

    }

    @Override
    public int getItemViewType(int position) {
        if (items.get(position).isHeader()) {
            return LAYOUT_HEADER;
        } else
            return LAYOUT_CHILD;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        RecyclerView.ViewHolder holder;
        if (viewType == LAYOUT_HEADER) {
            View view = inflater.inflate(R.layout.reporthead, parent, false);
            holder = new MyViewHolderHeader(view);
        } else {
            View view = inflater.inflate(R.layout.reportbody, parent, false);
            holder = new MyViewHolderChild(view);
        }


        return holder;
    }





    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {



        if (holder.getItemViewType() == LAYOUT_HEADER) {
            MyViewHolderHeader vaultItemHolder = (MyViewHolderHeader) holder;

        } else {


            final ItemReportModel   model = items.get(position);
            final MyViewHolderChild childItemHolder = (MyViewHolderChild) holder;



        childItemHolder.itemName.setText(items.get(position).getItemName());

        childItemHolder.itemqty.setText(items.get(position).getItemQty());

        childItemHolder.price.setText(items.get(position).getItemPrice());

            childItemHolder.date.setText(items.get(position).getSaleDate());


//        childItemHolder.lay.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//
//
//
//            }
//        });

        }

    }




    @Override
    public int getItemCount() {
        return items.size();
    }





   private class MyViewHolderHeader extends RecyclerView.ViewHolder {


        private MyViewHolderHeader(View itemView) {
            super(itemView);


        }

    }

    class MyViewHolderChild extends RecyclerView.ViewHolder {

        LinearLayout lay;

        TextView itemName,itemqty,price,date;


        private MyViewHolderChild(View itemView) {
            super(itemView);



            itemName=itemView.findViewById(R.id.proName);

            itemqty=itemView.findViewById(R.id.qty);

            price=itemView.findViewById(R.id.price);

            date=itemView.findViewById(R.id.date);

        }



    }


}
