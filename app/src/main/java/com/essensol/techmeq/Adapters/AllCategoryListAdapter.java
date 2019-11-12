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

import com.essensol.techmeq.Callbacks.CategoryItemClickListener;
import com.essensol.techmeq.R;
import com.essensol.techmeq.Room.Databases.Entity.Sales_Category;

import java.util.List;

public class AllCategoryListAdapter extends  RecyclerView.Adapter<AllCategoryListAdapter.viewHolder> {

    private List<Sales_Category> items;


    private Context mContext;


    private CategoryItemClickListener mlistener;


    public AllCategoryListAdapter(List<Sales_Category> items, Context mContext, CategoryItemClickListener mlistener) {
        this.items = items;
        this.mContext = mContext;
        this.mlistener = mlistener;
    }

    @NonNull
    @Override
    public AllCategoryListAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.allcategorylist, parent, false);


        return new AllCategoryListAdapter.viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AllCategoryListAdapter.viewHolder holder,  int position) {
        final Sales_Category category=items.get(position);

        if(!category.getProductCategory().equalsIgnoreCase("Select"))
        {
            holder.name.setText(items.get(position).getProductCategory());


            holder.lay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(mlistener!=null)
                    {
                        mlistener.getCategoryDetailsForEdit(category.getProductCatId(),category.getProductCategory(),category.getImage(),true);
                    }

                }
            });
        }

//        else {
//            items.remove(position);
//            notifyItemRemoved(position);
//        }





    }





    @Override
    public int getItemCount() {

        Log.e("getItemCount","list Size -- "+items.size());
        return items.size();
    }

    public  void SetCategory(List<Sales_Category>categories)
    {
        this.items=categories;
        notifyDataSetChanged();
    }


    public class viewHolder extends RecyclerView.ViewHolder{

        TextView name;


        LinearLayout lay;

        private viewHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.categoryName);
            lay=itemView.findViewById(R.id.lay);



        }
    }
}
