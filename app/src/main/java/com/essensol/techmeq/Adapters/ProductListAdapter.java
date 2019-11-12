package com.essensol.techmeq.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.essensol.techmeq.Callbacks.OnSelectedListener;
import com.essensol.techmeq.R;
import com.essensol.techmeq.Room.Databases.Entity.Products;
import com.essensol.techmeq.Utils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


public  class ProductListAdapter extends  RecyclerView.Adapter<ProductListAdapter.viewHolder> implements Filterable {

    private List<Products> items;
    private List<Products> searchedItems;
//    private List<mProductModel> newList=new ArrayList<>();

    private Context mContext;




    private OnSelectedListener mListner;

    public ProductListAdapter(List<Products> items, Context mContext, OnSelectedListener mListner) {
        this.items = items;
        this.mContext = mContext;
        this.mListner = mListner;
        this.searchedItems=items;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.products, parent, false);


        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder,  int position) {
       final Products products=searchedItems.get(position);

        holder.name.setText(products.getProductName());

        BigDecimal TaxAmnt =products.getSales_Price()
                .multiply(products.getTaxPercent().divide(BigDecimal.valueOf(100),3,BigDecimal.ROUND_HALF_UP)).setScale(3,BigDecimal.ROUND_DOWN);

        BigDecimal roundedTax= Utils.getRounded(TaxAmnt);

        BigDecimal priceWithTax =products.getSales_Price().add(roundedTax)
                .setScale(2,BigDecimal.ROUND_HALF_UP);

        holder.rate.setText(priceWithTax.toString());



        holder.lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                if(mListner!=null)
                {

                    InputMethodManager imm = (InputMethodManager)mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);

                    mListner.getProductDetails(products.getProduct_Id(),products.getProductCatId()
                            ,products.getTaxPercent(),products.getProductName()
                            ,products.getSales_Price(),products.isStatus());
                }


            }
        });

        holder.name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(mListner!=null)
                {
                    InputMethodManager imm = (InputMethodManager)mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);

                    mListner.getProductDetails(products.getProduct_Id(),products.getProductCatId()
                            ,products.getTaxPercent(),products.getProductName()
                            ,products.getSales_Price(),products.isStatus());
                }


            }
        });


        holder.rate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(mListner!=null)
                {
                    InputMethodManager imm = (InputMethodManager)mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);

                    mListner.getProductDetails(products.getProduct_Id(),products.getProductCatId()
                            ,products.getTaxPercent(),products.getProductName()
                            ,products.getSales_Price(),products.isStatus());
                }


            }
        });



        if(items.size()==1)
        {
            if(mListner!=null)
            {
                InputMethodManager imm = (InputMethodManager)mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);

                mListner.getProductDetails(products.getProduct_Id(),products.getProductCatId()
                        ,products.getTaxPercent(),products.getProductName()
                        ,products.getSales_Price(),products.isStatus());
            }
        }


    }





    @Override
    public int getItemCount() {

//        Log.e("getItemCount","list Size -- "+items.size());
        return searchedItems.size();
    }

    public  void SetProducts(List<Products>products)
    {
        this.items=products;
        this.searchedItems=products;
        notifyDataSetChanged();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {

//                searchedItems.clear();
                String charString = constraint.toString();
                if (charString.isEmpty()) {
                    searchedItems = items;
                } else {
                    List<Products> filteredList = new ArrayList<>();
                    for (Products row : items) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getProductName().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }

                    searchedItems = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = searchedItems;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                searchedItems = (List<Products>) results.values;
                notifyDataSetChanged();

            }
        };
    }


    public class viewHolder extends RecyclerView.ViewHolder{

        TextView name,qty,rate;
        ImageView imageid;

        LinearLayout lay;

        private viewHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.proName);
            lay=itemView.findViewById(R.id.lay);
            rate=itemView.findViewById(R.id.rate);


        }
    }

}
