package com.essensol.techmeq.UI;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.essensol.techmeq.Adapters.VoucherListAdapter;
import com.essensol.techmeq.R;
import com.essensol.techmeq.Room.Databases.Entity._dbExpenceVouchers;
import com.essensol.techmeq.Utils;
import com.essensol.techmeq.ViewModel.VoucherViewModel;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ExpenceVoucher extends Toolbar {


    private RecyclerView voucher;

    private VoucherListAdapter adapter;
    private List<_dbExpenceVouchers>vouchers=new ArrayList<>();


    private  EditText Desc,Remarks,taxable,vat,tot;

    private VoucherViewModel model;

    private LinearLayout save,reset;
    private BigDecimal SubTotal=BigDecimal.valueOf(0),Tax=BigDecimal.valueOf(0);

    private TextView subtotal,mTax,_Date;

    private long mDate,mYesterday;
    private DatePickerDialog pickerDialog;

   private final Calendar myCalendar = Calendar.getInstance();

   private Button _view;

   private Date SelectedDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FrameLayout contentFrameLayout = (FrameLayout) findViewById(R.id.content_frame);
        getLayoutInflater().inflate(R.layout.activity_expence_voucher, contentFrameLayout);


        voucher=findViewById(R.id.voucher_list);

        Desc=findViewById(R.id.desc);

        Remarks=findViewById(R.id.remark);

        taxable=findViewById(R.id.taxable);

        vat=findViewById(R.id.vat);

        tot=findViewById(R.id.tot);

        save=findViewById(R.id.save);

        subtotal=findViewById(R.id.subtotal);

        mTax=findViewById(R.id.totTax);

        _Date=findViewById(R.id.date);

        reset=findViewById(R.id.reset);

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Desc.setText("");

                Remarks.setText("");
                taxable.setText("");
                vat.setText("");
                tot.setText("");

            }
        });


        tot.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {


            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(!tot.getText().toString().equalsIgnoreCase("")&&!vat.getText().toString().equalsIgnoreCase("")) {

                    BigDecimal mTax = Utils.round(Float.parseFloat(vat.getText().toString()), 2);
                    BigDecimal _mPrice = Utils.round(Float.parseFloat(tot.getText().toString()), 2);

                    BigDecimal taxRev = GetReverse(_mPrice, mTax);

                    taxable.setText(taxRev.toString());
                }


            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });





        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        voucher.setLayoutManager(linearLayoutManager);
        voucher.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));


        adapter=new VoucherListAdapter(vouchers,this);


        model = ViewModelProviders.of(this).get(VoucherViewModel.class);


        SelectedDate = Calendar.getInstance().getTime();
        mDate =SelectedDate.getTime();

//        Date yesterDay =yesterday(c);

//        mYesterday=yesterDay.getTime();


        String myFormat = "dd/MMM/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.ENGLISH);

        _Date.setText(sdf.format(mDate));

        SetObserver(mDate);

        _Date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GetDate();
            }
        });



        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(Desc.getText().toString().equalsIgnoreCase(""))
                {
                    Desc.setError("Field Required");
                    Desc.requestFocus();
                }
                else if(Remarks.getText().toString().equalsIgnoreCase(""))
                {
                    Remarks.setError("Field Required");
                    Remarks.requestFocus();
                }
                else if(vat.getText().toString().equalsIgnoreCase(""))
                {
                    vat.setError("Field Required");
                    vat.requestFocus();
                }
                else if(taxable.getText().toString().equalsIgnoreCase(""))
                {
                    taxable.setError("Field Required");
                    taxable.requestFocus();
                }
                else if(tot.getText().toString().equalsIgnoreCase(""))
                {
                    tot.setError("Field Required");
                    tot.requestFocus();
                }
                else {
                    AddVouchers();
                }




            }
        });

//        _view.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Log.e("mDate","OnClick"+mDate);
//                SetObserver(mDate,mYesterday);
//            }
//        });







        voucher.setAdapter(adapter);

    }


    private  void AddVouchers()
    {

        Log.e("Dateeeee","AddVouchers()"+SelectedDate);

//        Date c = Calendar.getInstance().getTime();
//        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");

        BigDecimal mTax=Utils.round(Float.parseFloat(taxable.getText().toString().trim()),2);
        BigDecimal mVat=Utils.round(Float.parseFloat(vat.getText().toString().trim()),2);
        BigDecimal mTot=Utils.round(Float.parseFloat(tot.getText().toString().trim()),2);

        _dbExpenceVouchers vouchers =new _dbExpenceVouchers(Desc.getText().toString().trim(),
                Remarks.getText().toString().trim(),
                mTax,
                mVat,
                mTot,
                SelectedDate);

        model.AddVoucher(vouchers);

        Desc.setText("");
        Remarks.setText("");
        taxable.setText("");
        vat.setText("");
        tot.setText("");


    }

    public static BigDecimal GetReverse(BigDecimal price, BigDecimal taxPerc)
    {
        BigDecimal val =price.multiply(BigDecimal.valueOf(10000)).divide (BigDecimal.valueOf(10000).add(taxPerc.multiply(BigDecimal.valueOf(100))),2, RoundingMode.HALF_UP);
        return val;

    }

    private  void SetObserver(long date)
    {
        model.getAllVoucherReportByDate(date).observe(this, new Observer<List<_dbExpenceVouchers>>() {
            @Override
            public void onChanged(@Nullable List<_dbExpenceVouchers> dbExpenceVouchers) {

                Log.e("OnChanged","" +dbExpenceVouchers.size());
                if(dbExpenceVouchers.size()>0) {
                    adapter.AddVoucher(dbExpenceVouchers);

                    for(int i=0;i<dbExpenceVouchers.size();i++)
                    {
                        SubTotal =SubTotal.add(dbExpenceVouchers.get(i).getTaxable());
                        Tax =Tax.add(dbExpenceVouchers.get(i).getVat());


                    }
                    subtotal.setText(SubTotal.toString());
                    mTax.setText(Tax.toString());
                }
            }
        });

    }


    private void GetDate() {





        pickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            @SuppressLint("SetTextI18n")
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
//                Calendar newDate = Calendar.getInstance();
//                newDate.set(year, monthOfYear, dayOfMonth);
                _Date.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                pickerDialog.getDatePicker().setSpinnersShown(true);
                pickerDialog.getDatePicker().setCalendarViewShown(false);

                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);



                SelectedDate=newDate.getTime();

                mDate=SelectedDate.getTime();

//                mYesterday =yesterday(SelectedDate).getTime();

                Log.e("ToDate",""+mDate);
            }

        },myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH));



        pickerDialog.show();
    }




    private Date yesterday(Date date) {
        final Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, -1);
        return cal.getTime();
    }

}
