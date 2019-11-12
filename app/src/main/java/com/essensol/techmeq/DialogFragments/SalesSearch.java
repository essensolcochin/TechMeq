package com.essensol.techmeq.DialogFragments;


import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import com.essensol.techmeq.Adapters.ReportsAdapter;
import com.essensol.techmeq.Adapters.SaleReportsAdapter;
import com.essensol.techmeq.Callbacks.SaleReportItemClickListener;
import com.essensol.techmeq.Model.ItemReportModel;
import com.essensol.techmeq.R;
import com.essensol.techmeq.ViewModel.ReportViewModel;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 */
public class SalesSearch extends DialogFragment implements SaleReportItemClickListener {


    private SaleReportsAdapter adapter;

    private ReportViewModel model;

    private RecyclerView reports;

    TextView from,to,totalSale,mTax;

    Button view;

    private List<ItemReportModel>mList=new ArrayList<>();

    final Calendar myCalendar = Calendar.getInstance();

    DatePickerDialog pickerDialog;

    long  mFromDate,mToDate;

    private BigDecimal totalDailySale=BigDecimal.valueOf(0),totalDailyVat=BigDecimal.valueOf(0);

    public  interface GetSaleId
    {
        void getSaleIdForEdit(int Id);
    }

    GetSaleId getSaleId;

    public SalesSearch() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootview = inflater.inflate(R.layout.fragment_sales_search, container, false);

        reports=rootview.findViewById(R.id.reports);
        from=rootview.findViewById(R.id.fromdate);
        to=rootview.findViewById(R.id.todate);
        view=rootview.findViewById(R.id.view);
        totalSale=rootview.findViewById(R.id.totalSale);
        mTax=rootview.findViewById(R.id.totTax);



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


        reports.setLayoutManager(new LinearLayoutManager(getContext()));
        reports.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL));

        model= ViewModelProviders.of(this).get(ReportViewModel.class);


        Date yesterday =yesterday(Calendar.getInstance().getTime());

        long mYesterday =yesterday.getTime();

        Date today =Calendar.getInstance().getTime();
        long mToday =today.getTime();

        String myFormat = "dd/MM/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.ENGLISH);

        from.setText(sdf.format(yesterday));
        to.setText(sdf.format(today));

        setObserver(mYesterday,mToday);


        adapter=new SaleReportsAdapter(mList,getContext(),SalesSearch.this);
        reports.setAdapter(adapter);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.e("Dates"," "+mFromDate+" todate"+mToDate);

                setObserver(mFromDate,mToDate);


            }
        });


        return  rootview;
    }


    private  void setObserver(long d1,long d2)
    {
        model.GetSalesReport(d1,d2).observe(this, new Observer<List<ItemReportModel>>() {
            @Override
            public void onChanged(@Nullable List<ItemReportModel> itemReportModels) {


                if(itemReportModels!=null)
                {
                    adapter.AddReports(itemReportModels);

                    for (int i=0;i<itemReportModels.size();i++)
                    {
                        totalDailySale=totalDailySale.add(itemReportModels.get(i).getGrandTotal());
                        totalDailyVat=totalDailyVat.add(itemReportModels.get(i).getTaxAmt());
                    }

                    totalSale.setText(totalDailySale.toString());
                    mTax.setText(totalDailyVat.toString());
                }
            }
        });
    }


    private void setfromDate() {


        pickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {

            @SuppressLint("SetTextI18n")
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
//                Calendar newDate = Calendar.getInstance();
//                newDate.set(year, monthOfYear, dayOfMonth);
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





        pickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {

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


    @Override
    public void onStart() {
        super.onStart();

        Dialog dialog = getDialog();
        if (dialog != null)
        {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            dialog.getWindow().setLayout(width, height);
        }

    }

    @Override
    public void getSaleId(int SaleId) {

        getSaleId=(GetSaleId)getContext();
        if(getSaleId!=null)
        {
            getSaleId.getSaleIdForEdit(SaleId);
        }
        this.getDialog().dismiss();
    }


    private Date yesterday(Date date) {
        final Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, -1);
        return cal.getTime();
    }

}
