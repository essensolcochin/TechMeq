package com.essensol.techmeq.Tab_Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.essensol.techmeq.Adapters.ReportsAdapter;
import com.essensol.techmeq.Model.ItemReportModel;
import com.essensol.techmeq.R;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ReportTab extends Fragment {

    RecyclerView reports;
    ReportsAdapter adapter;
    List<ItemReportModel>report= new ArrayList<>();

    public ReportTab() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootview= inflater.inflate(R.layout.fragment_report_tab, container, false);

        reports=rootview.findViewById(R.id.report);

        reports.setLayoutManager(new LinearLayoutManager(getActivity()));
        reports.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL));


        ItemReportModel model= new ItemReportModel(true,"ProductName","100","12-22-2019","45");
        report.add(model);

        for(int i=0;i<10;i++)
        {
            ItemReportModel list= new ItemReportModel(false,"Item "+i+1,Integer.toString(100*i+1),"12-22-2019","45");

            report.add(list);
        }
        adapter=new ReportsAdapter(report,getContext());
        reports.setAdapter(adapter);

        return rootview;
    }

}
