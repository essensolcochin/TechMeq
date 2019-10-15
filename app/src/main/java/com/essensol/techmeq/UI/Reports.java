package com.essensol.techmeq.UI;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.essensol.techmeq.Adapters.ReportsAdapter;
import com.essensol.techmeq.Model.ItemReportModel;
import com.essensol.techmeq.R;

import java.util.ArrayList;
import java.util.List;

public class Reports extends AppCompatActivity {

    RecyclerView reports;
    ReportsAdapter adapter;
    List<ItemReportModel> report= new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reports);


        reports=findViewById(R.id.report);

        reports.setLayoutManager(new LinearLayoutManager(this));
        reports.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));


        ItemReportModel model= new ItemReportModel(true,"ProductName","100","12-22-2019","45");
        report.add(model);

        for(int i=0;i<10;i++)
        {
            ItemReportModel list= new ItemReportModel(false,"Item "+i+1,Integer.toString(100*i+1),"12-22-2019","45");

            report.add(list);
        }
        adapter=new ReportsAdapter(report,this);
        reports.setAdapter(adapter);


    }
}
