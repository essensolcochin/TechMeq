package com.essensol.techmeq.UI;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.essensol.techmeq.R;
import com.essensol.techmeq.Room.Databases.TaxModel;
import com.essensol.techmeq.ViewModel.TaxViewModel;

public class TaxManagement extends AppCompatActivity {


    private TaxViewModel viewModel;

    private EditText taxName,taxPer;

    private LinearLayout add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tax_management);

        taxName =findViewById(R.id.taxname);

        taxPer=findViewById(R.id.taxper);

        add=findViewById(R.id.add);

        viewModel= ViewModelProviders.of(this).get(TaxViewModel.class);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addTax();
            }
        });

    }

    private  void  addTax()
    {
        TaxModel tax=new TaxModel(taxName.getText().toString().trim(),Integer.parseInt(taxPer.getText().toString().trim()));

        viewModel.AddTax(tax);

    }
}
