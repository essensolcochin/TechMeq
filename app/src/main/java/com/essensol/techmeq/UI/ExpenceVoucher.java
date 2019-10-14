package com.essensol.techmeq.UI;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.essensol.techmeq.Adapters.VoucherListAdapter;
import com.essensol.techmeq.R;
import com.essensol.techmeq.Room.Databases._dbExpenceVouchers;
import com.essensol.techmeq.ViewModel.VoucherViewModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ExpenceVoucher extends Toolbar {


    private RecyclerView voucher;

    private VoucherListAdapter adapter;
    private List<_dbExpenceVouchers>vouchers=new ArrayList<>();


    private  EditText Desc,Remarks,tax,vat,tot;

    private VoucherViewModel model;

    private LinearLayout save;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FrameLayout contentFrameLayout = (FrameLayout) findViewById(R.id.content_frame);
        getLayoutInflater().inflate(R.layout.activity_expence_voucher, contentFrameLayout);


        voucher=findViewById(R.id.voucher_list);

        Desc=findViewById(R.id.desc);

        Remarks=findViewById(R.id.remark);

        tax=findViewById(R.id.tax);

        vat=findViewById(R.id.vat);

        tot=findViewById(R.id.tot);

        save=findViewById(R.id.save);


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        voucher.setLayoutManager(linearLayoutManager);

        adapter=new VoucherListAdapter(vouchers,this);


        model = ViewModelProviders.of(this).get(VoucherViewModel.class);


        vat.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if(!tax.getText().toString().equalsIgnoreCase(""))
                {
                    String total =Integer.toString((Integer.parseInt(tax.getText().toString().trim())) * (Integer.parseInt(vat.getText().toString().trim())));

                    tot.setText(total);
                }

            }
        });



        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AddVouchers();
            }
        });




        model.GetAllVouchers().observe(this, new Observer<List<_dbExpenceVouchers>>() {
            @Override
            public void onChanged(@Nullable List<_dbExpenceVouchers> dbExpenceVouchers) {

                Log.e("OnChanged","" +dbExpenceVouchers.size());
                if(dbExpenceVouchers.size()>0) {
                    adapter.SetProducts(dbExpenceVouchers);
                }
            }
        });



        voucher.setAdapter(adapter);

    }


    private  void AddVouchers()
    {

        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        String formattedDate = df.format(c);

        _dbExpenceVouchers vouchers =new _dbExpenceVouchers(Desc.getText().toString().trim(),
                Remarks.getText().toString().trim(),
                tax.getText().toString().trim(),
                vat.getText().toString().trim(),
                tot.getText().toString().trim(),
                formattedDate);

        model.AddVoucher(vouchers);

    }


}
