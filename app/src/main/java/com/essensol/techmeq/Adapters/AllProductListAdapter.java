package com.essensol.techmeq.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.essensol.techmeq.Model.ProductModel;
import com.essensol.techmeq.Callbacks.ProductItemClickListener;
import com.essensol.techmeq.R;
import com.essensol.techmeq.Room.Databases.Entity.Products;
import com.essensol.techmeq.Utils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class AllProductListAdapter  extends  RecyclerView.Adapter<AllProductListAdapter.viewHolder> implements Filterable {

    private List<ProductModel> items;

//    private List<mProductModel> newList=new ArrayList<>();

    private Context mContext;
    private List<ProductModel> searchedItems;

    private ProductItemClickListener mlistener;


    public AllProductListAdapter(List<ProductModel> items, Context mContext, ProductItemClickListener mlistener) {
        this.items = items;
        this.mContext = mContext;
        this.mlistener = mlistener;
        this.searchedItems=items;
    }

    @NonNull
    @Override
    public AllProductListAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.allproductlist, parent, false);


        return new AllProductListAdapter.viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AllProductListAdapter.viewHolder holder, final int position) {
        final ProductModel products=searchedItems.get(position);

        holder.name.setText(products.getProductName());

        holder.tax.setText(products.getTaxPercent().toString());
        holder.category.setText(products.getProductCategory());


        BigDecimal TaxAmnt =products.getSales_Price()
                .multiply(products.getTaxPercent().divide(BigDecimal.valueOf(100),3,BigDecimal.ROUND_HALF_UP)).setScale(3,BigDecimal.ROUND_DOWN);

        BigDecimal roundedTax= Utils.getRounded(TaxAmnt);

        BigDecimal priceWithTax =products.getSales_Price().add(roundedTax)
                .setScale(2,BigDecimal.ROUND_HALF_UP);



        holder.price.setText(priceWithTax.toString());

        holder.lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(mlistener!=null)
                {

                    mlistener.getProductDetailsForEdit(products.getProduct_Id(),products.getProductCatId(),products.getTaxPercent()
                    ,products.getProductName(),products.getSales_Price(),true);
                }

            }
        });



    }





    @Override
    public int getItemCount() {


        return searchedItems.size();
    }

    public  void SetProducts(List<ProductModel>products)
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

                String charString = constraint.toString();
                if (charString.isEmpty()) {
                    searchedItems = items;
                } else {
                    List<ProductModel> filteredList = new ArrayList<>();
                    for (ProductModel row : items) {

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
                searchedItems = (List<ProductModel>) results.values;
                notifyDataSetChanged();

            }
        };
    }


    public class viewHolder extends RecyclerView.ViewHolder{

        TextView name,category,price,tax;


        LinearLayout lay;

        private viewHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.proName);
            lay=itemView.findViewById(R.id.lay);
            tax=itemView.findViewById(R.id.tax);
            price=itemView.findViewById(R.id.price);
            category=itemView.findViewById(R.id.category);


        }
    }
}
