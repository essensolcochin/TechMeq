package com.essensol.techmeq.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import com.essensol.techmeq.DialogFragments.AddCustomer;
import com.essensol.techmeq.R;
import com.essensol.techmeq.Room.Databases.Entity.Customers;

import java.util.ArrayList;
import java.util.List;

public class AutoCompleteArrayAdapter extends ArrayAdapter<Customers> {
    private List<Customers> countryListFull;
    private Context context;

    public AutoCompleteArrayAdapter(@NonNull Context context, @NonNull List<Customers> customers) {
        super(context, 0, customers);
        countryListFull = new ArrayList<>(customers);
        this.context =context;
    }

    @NonNull
    @Override
    public Filter getFilter() {
        return countryFilter;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.custlist, parent, false
            );
        }

        TextView textViewName = convertView.findViewById(R.id.custName);


        Customers customers = getItem(position);

        if (customers != null) {
            textViewName.setText(customers.getCustName());

        }

        return convertView;
    }

    private Filter countryFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            List<Customers> suggestions = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                suggestions.addAll(countryListFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (Customers item : countryListFull) {
                    if (item.getCustName().toLowerCase().contains(filterPattern)) {
                        suggestions.add(item);
                    }
                }
            }

            results.values = suggestions;
            results.count = suggestions.size();




            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            clear();
            addAll((List) results.values);
            AddCustomer.SetList(results.count);
            notifyDataSetChanged();

        }

        @Override
        public CharSequence convertResultToString(Object resultValue) {
            return ((Customers) resultValue).getCustName();
        }
    };

    @Nullable
    @Override
    public Customers getItem(int position) {
        return super.getItem(position);
    }
}