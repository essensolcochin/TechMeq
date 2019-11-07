package com.essensol.techmeq.UI;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.essensol.techmeq.Adapters.ReportsAdapter;
import com.essensol.techmeq.Adapters.TaxAdapter;
import com.essensol.techmeq.Model.ItemReportModel;
import com.essensol.techmeq.R;
import com.essensol.techmeq.ViewModel.ReportViewModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class TaxReport extends Toolbar {


    RecyclerView reports;
    TaxAdapter adapter;
    List<ItemReportModel> report= new ArrayList<>();

    private ReportViewModel model;

    List<ItemReportModel>mList=new ArrayList<>();

    TextView from,to;

    long  mFromDate,mToDate;

    Button view;

    final Calendar myCalendar = Calendar.getInstance();

    private DatePickerDialog pickerDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FrameLayout contentFrameLayout = (FrameLayout) findViewById(R.id.content_frame);
        getLayoutInflater().inflate(R.layout.activity_tax_report, contentFrameLayout);





        reports=findViewById(R.id.report);
        from=findViewById(R.id.fromdate);
        to=findViewById(R.id.todate);
        view=findViewById(R.id.view);


        final DatePickerDialog.OnDateSetListener fromdate = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            }

        };


        final DatePickerDialog.OnDateSetListener todate = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            }

        };


        from.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                setfromDate();
            }
        });

        to.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                setToDate();
            }
        });

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.e("Dates"," "+mFromDate+" todate"+mToDate);

                setObserver(mFromDate,mToDate);

//                new DateAsync(OfflineDb.getInstance(Reports.this).sales_header_dao()).execute();

            }
        });



        reports.setLayoutManager(new LinearLayoutManager(this));
        reports.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));

        model= ViewModelProviders.of(this).get(ReportViewModel.class);



        adapter=new TaxAdapter(mList,this);
        reports.setAdapter(adapter);



    }


    private  void setObserver(long d1,long d2)
    {
        model.GetSalesReport(d1,d2).observe(this, new Observer<List<ItemReportModel>>() {
            @Override
            public void onChanged(@Nullable List<ItemReportModel> itemReportModels) {


                if(itemReportModels!=null)
                {
                    adapter.AddTax(itemReportModels);
                }
            }
        });
    }


    private void setfromDate() {





        pickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            @SuppressLint("SetTextI18n")
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                from.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);

                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);

                Date d =newDate.getTime();

                mFromDate =d.getTime();
                Log.e("GetDateeeee",""+mFromDate);

                pickerDialog.getDatePicker().setSpinnersShown(true);
                pickerDialog.getDatePicker().setCalendarViewShown(false);
            }

        },myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH));



        pickerDialog.show();
    }

    private void setToDate() {





        pickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            @SuppressLint("SetTextI18n")
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
//                Calendar newDate = Calendar.getInstance();
//                newDate.set(year, monthOfYear, dayOfMonth);
                to.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                pickerDialog.getDatePicker().setSpinnersShown(true);
                pickerDialog.getDatePicker().setCalendarViewShown(false);

                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);



                Date to=newDate.getTime();

                mToDate=to.getTime();

                Log.e("ToDate",""+mToDate);
            }

        },myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH));



        pickerDialog.show();
    }



}
