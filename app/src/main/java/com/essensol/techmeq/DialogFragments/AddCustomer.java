package com.essensol.techmeq.DialogFragments;


import android.app.Dialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.essensol.techmeq.Adapters.AutoCompleteArrayAdapter;
import com.essensol.techmeq.R;
import com.essensol.techmeq.Room.Databases.DAO.Customer_DAO;
import com.essensol.techmeq.Room.Databases.Entity.Customers;
import com.essensol.techmeq.Room.Databases.OfflineDb;
import com.essensol.techmeq.UI.Login;
import com.essensol.techmeq.ViewModel.ProductViewModel;

import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddCustomer extends DialogFragment {

    public interface getCreditSaleDetails
    {
      void getCreditSaleDetails(int ID);
    }

    private getCreditSaleDetails mlistener;


    public AddCustomer() {
        // Required empty public constructor
    }

   private LinearLayout Save,frame,cancel;

    ImageView add,close;

    private EditText custName,custMob;

   private AutoCompleteTextView CustomerName;

   private ProductViewModel model;

   private  AutoCompleteArrayAdapter adapter;

   private EditText mob,address;

   private int CustId;

   private static int IS_EMPTY=0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootview= inflater.inflate(R.layout.fragment_add_customer, container, false);

        Save =rootview.findViewById(R.id.Save);

        frame=rootview.findViewById(R.id.frame);

//        custName=rootview.findViewById(R.id.custName);
//
//        custMob=rootview.findViewById(R.id.custMob);

        CustomerName=rootview.findViewById(R.id.CustomerName);

        address=rootview.findViewById(R.id.address);

        mob=rootview.findViewById(R.id.mob);

        close=rootview.findViewById(R.id.close);

        cancel=rootview.findViewById(R.id.cancel);

        model = ViewModelProviders.of(this).get(ProductViewModel.class);

        model.GetAllCustomers().observe(this, new Observer<List<Customers>>() {
            @Override
            public void onChanged(@Nullable List<Customers> customers) {

                if(customers!=null)
                {
                    adapter = new AutoCompleteArrayAdapter(getContext(),customers);
                    CustomerName.setAdapter(adapter);
                }


            }
        });


        CustomerName.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Customers object = (Customers)adapter.getItem(position);
                if(object !=null)
                {
                    CustId= object.getCustId();

                    Log.e("CustId","Credit "+CustId);
                    mob.setText(object.getMobileNo());
                }

            }
        });

        Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                mlistener =(getCreditSaleDetails)getContext();


                if(IS_EMPTY==0)
                {
                    AddCustomer();

                    try {
                       int Id= new MaxIdAsync(OfflineDb.getInstance(getContext()).customer_dao()).execute().get();
                       Log.e("ID","  "+Id);

                       if(Id!=0)
                       {

                           mlistener.getCreditSaleDetails(Id);
                           dismiss();
                       }

                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                else {

                    assert mlistener != null;
                    mlistener.getCreditSaleDetails(CustId);
                    Log.e("ID","else "+IS_EMPTY);
                    dismiss();
                }



            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                frame.setVisibility(View.GONE);


            }
        });



        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dismiss();


            }
        });


        return  rootview;
    }



    @Override
    public void onStart()
    {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null)
        {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            dialog.getWindow().setLayout(width, height);
        }
    }

    private void AddCustomer()
    {

             Customers customers =new Customers(1,0,CustomerName.getText().toString().trim(),address.getText().toString(),mob.getText().toString().trim(),
                true);

             model.AddCustomer(customers);

        frame.setVisibility(View.GONE);
    }

    public static void  SetList(int Size)
    {

        IS_EMPTY =Size;
        Log.e("IsEmpty","Setlist -"+IS_EMPTY);
    }

    private static class MaxIdAsync extends AsyncTask<Void,Void,Integer>
    {
        private Customer_DAO customer_dao;

        public MaxIdAsync(Customer_DAO customer_dao) {
            this.customer_dao = customer_dao;
        }

        @Override
        protected Integer doInBackground(Void... voids) {


            int Id =customer_dao.GetCustId();

            return Id;
        }
    }


}
