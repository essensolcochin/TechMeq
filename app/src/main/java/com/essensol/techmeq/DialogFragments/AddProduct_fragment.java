package com.essensol.techmeq.DialogFragments;


import android.app.Dialog;
import android.app.ProgressDialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.essensol.techmeq.Adapters.AllProductListAdapter;
import com.essensol.techmeq.Model.ProductModel;
import com.essensol.techmeq.Callbacks.ProductItemClickListener;
import com.essensol.techmeq.R;
import com.essensol.techmeq.Room.Databases.DAO.Product_DAO;
import com.essensol.techmeq.Room.Databases.Entity.Products;
import com.essensol.techmeq.Room.Databases.Entity.Sales_Category;
import com.essensol.techmeq.Room.Databases.OfflineDb;
import com.essensol.techmeq.Utils;
import com.essensol.techmeq.ViewModel.ProductViewModel;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddProduct_fragment extends DialogFragment implements ProductItemClickListener {



    List<Sales_Category> categories=new ArrayList<>();
    List<ProductModel> mList=new ArrayList<>();

    ArrayAdapter<Sales_Category> categoryArrayAdapter;

    private TextInputEditText mProduct_name,tax,mPrice,pricewithtax;

    private ProductViewModel productViewModel;
    int  catId;
    Spinner mProductCategory;
    ImageView dismiss;
    ProgressDialog progressDialog;

    RecyclerView products;
    AllProductListAdapter adapter;

    private boolean withTax =false;

    private boolean withoutTax =false;

    private Button mAddProduct,delete,reset;

    private int ProductId;
    private EditText search;

    private List<ProductModel>mlist=new ArrayList<>();

    public AddProduct_fragment() {
        // Required empty public constructor
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View Rootview= inflater.inflate(R.layout.add_product, container, false);




        mProduct_name =Rootview.findViewById(R.id.productname);
        mProductCategory = Rootview.findViewById(R.id.category);
        tax =Rootview.findViewById(R.id.tax);
        mPrice =Rootview.findViewById(R.id.price);
        mAddProduct = Rootview.findViewById(R.id.add);
        dismiss= Rootview.findViewById(R.id.dismiss);
        products = Rootview.findViewById(R.id.products);
        pricewithtax =Rootview.findViewById(R.id.pricewithtax);
        search=Rootview.findViewById(R.id.search);
        delete=Rootview.findViewById(R.id.delete);
        reset=Rootview.findViewById(R.id.reset);


        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mProduct_name.setText("");
                mProductCategory.setSelection(0);
                tax.setText("");
                mPrice.setText("");
                pricewithtax.setText("");
            }
        });

        progressDialog =new ProgressDialog(getContext());
        progressDialog.setTitle("Adding Product");
        progressDialog.setMessage("Saving...");
        progressDialog.setCancelable(false);

        mProduct_name.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_NEXT) {

                    tax.requestFocus();

                    return true;
                }
                return false;
            }
        });



        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        products.setLayoutManager(linearLayoutManager);
        products.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL));


        adapter = new AllProductListAdapter(mList,getContext(), (ProductItemClickListener) AddProduct_fragment.this);

        products.setAdapter(adapter);




        productViewModel= ViewModelProviders.of(this).get(ProductViewModel.class);

        //SetSpinner Observer
        setSpinnerObserver(-1);

        productViewModel.GetAllProduct().observe(this, new Observer<List<ProductModel>>() {
            @Override
            public void onChanged(@Nullable List<ProductModel> products) {
                if(products!=null)
                {
                    mlist =products;

                    if(products.size()!=0)
                    {
                        for(int i=0;i<products.size();i++)
                        {
                            Log.e("Name"," "+products.get(i).getProductName());
                            Log.e("Category"," "+products.get(i).getProductCategory());
                            Log.e("Tax"," "+products.get(i).getTaxPercent());
                            Log.e("Sales Price"," "+products.get(i).getSales_Price());
                        }


                        adapter.SetProducts(products);
                    }
                }

                SearchFilter();


            }
        });








        mProductCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                catId =categories.get(position).getProductCatId();
                Log.e("Cat Iddddd"," "+catId);
                if(catId!=1)
                {
                    tax.setError(null);
                    tax.setText("5");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DeleteProduct();
            }
        });









        pricewithtax.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                withTax =false;
                withoutTax=true;
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

               if(!pricewithtax.getText().toString().equalsIgnoreCase("")&&!tax.getText().toString().equalsIgnoreCase("")) {

                    BigDecimal mTax = Utils.round(Float.parseFloat(tax.getText().toString()), 2);
                    BigDecimal _mPrice = Utils.round(Float.parseFloat(pricewithtax.getText().toString()), 2);

                    BigDecimal taxRev = GetReverse(_mPrice, mTax);

                    mPrice.setText(taxRev.toString());
                }


            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });




        mAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (mProduct_name.getText().toString().equalsIgnoreCase("")){

                    tax.setError(null);

                    mPrice.setError(null);

                    mProduct_name.setError("Field Empty");
                }
                else if(catId==1)
                {
                    mProduct_name.setError(null);

                    mPrice.setError(null);


                    tax.setError("Select A Category");
                }

                else if(tax.getText().toString().equalsIgnoreCase("")){

                    mProduct_name.setError(null);

                    mPrice.setError(null);

                    tax.setError("Field Empty");
                }
                else if(mPrice.getText().toString().equalsIgnoreCase("")){

                    mProduct_name.setError(null);

                    tax.setError(null);


                    mPrice.setError("Field Empty");
                }

                else if(mAddProduct.getText().toString().equalsIgnoreCase("Update"))
                {

                    Runnable progressRunnable = new Runnable() {

                        @Override
                        public void run() {

                            _UpdateProduct(ProductId);


                            progressDialog.cancel();

                            mProduct_name.setText("");
                            tax.setText("");
                            mPrice.setText("");
                            pricewithtax.setText("");
                            mAddProduct.setText("Save");
                            delete.setVisibility(View.GONE);
                            mProduct_name.requestFocus();
                            mProductCategory.setSelection(0);
                            reset.setVisibility(View.VISIBLE);

                        }};

                    Handler pdCanceller = new Handler();
                    pdCanceller.postDelayed(progressRunnable, 500);



                }

                else {

                    CheckExist ();

                }





            }
        });

        dismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dismiss();
            }
        });
        return Rootview;
    }

    private void DeleteProduct() {



            float mTax = Float.parseFloat(tax.getText().toString().trim());

            float d = Float.parseFloat(mPrice.getText().toString().trim());

            BigDecimal _salesPrice  = Utils.round(d,2);

            BigDecimal _tax  =Utils.round(mTax,2);


            Log.e("_tax",""+_tax);
            Log.e("_salesPrice",""+_salesPrice);

            Products products =new Products(ProductId,catId,_tax,mProduct_name.getText().toString().trim()
                    ,_salesPrice
                    ,true);

            productViewModel.DeleteProduct(products);


            delete.setVisibility(View.GONE);
            mAddProduct.setText("Save");
            mPrice.setText("");
            mProduct_name.setText("");
            tax.setText("");
            mProductCategory.setSelection(0);
            mProduct_name.requestFocus();
            reset.setVisibility(View.VISIBLE);


    }






    private void CheckExist ()
    {


        try {
            String isExist = new CheckExistAsync(OfflineDb.getInstance(getContext()).product_dao()).execute(mProduct_name.getText().toString()).get();

            if(isExist.equalsIgnoreCase("Already exist"))
            {
                Toast.makeText(getContext(),"Product Already Exists",Toast.LENGTH_LONG).show();
            }
            else {

                   progressDialog.show();

                    Runnable progressRunnable = new Runnable() {

                        @Override
                        public void run() {

                            _AddProduct();


                            progressDialog.cancel();
                            mProduct_name.requestFocus();
                            mProduct_name.setText("");
                            tax.setText("");
                            mPrice.setText("");
                            pricewithtax.setText("");
                            mProductCategory.setSelection(0);



                        }};

                    Handler pdCanceller = new Handler();
                    pdCanceller.postDelayed(progressRunnable, 500);




                }











        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }


    private void setSpinnerObserver(final int Id)
    {

        Log.e("ProductId"," "+Id);

        productViewModel.GetAllProductCategory().observe(this, new Observer<List<Sales_Category>>() {
            @Override
            public void onChanged(@Nullable List<Sales_Category> sales_categories) {

                categories=sales_categories;

                categoryArrayAdapter = new ArrayAdapter<Sales_Category>(getContext(), android.R.layout.simple_spinner_dropdown_item, categories);
                categoryArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                mProductCategory.setAdapter(categoryArrayAdapter);

                int pos =0;

                for(Sales_Category items : categories){
                    if (items.getProductCatId()==Id){

                        pos = categories.indexOf(items);
                        break;
                    }
                }

                mProductCategory.setSelection(pos);

            }
        });

    }


    private  void _AddProduct() {


        float mTax = Float.parseFloat(tax.getText().toString().trim());

        float d = Float.parseFloat(mPrice.getText().toString().trim());

        BigDecimal _salesPrice  = Utils.round(d,2);

        BigDecimal _tax  =Utils.round(mTax,2);


        Log.e("_tax",""+_tax);
        Log.e("_salesPrice",""+_salesPrice);

        Products products =new Products(0,catId,_tax,mProduct_name.getText().toString().trim()
                ,_salesPrice
                ,true);

        productViewModel.AddProduct(products);




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




    public static BigDecimal GetReverse(BigDecimal price, BigDecimal taxPerc)
    {                                                                                                                                                     //RoundingMode.DOWN
        BigDecimal val =price.multiply(BigDecimal.valueOf(10000)).divide (BigDecimal.valueOf(10000).add(taxPerc.multiply(BigDecimal.valueOf(100))),2,RoundingMode.HALF_UP);
        return val;

    }

    @Override
    public void getProductDetailsForEdit(int Product_Id, int ProductCatId, BigDecimal TaxPercent, String ProductName, BigDecimal Sales_Price, boolean Status) {


        mProduct_name.requestFocus();
        mProduct_name.setText(ProductName);
        tax.setText(TaxPercent.toString());
        mPrice.setText(Sales_Price.toString());



        BigDecimal TaxAmnt =Sales_Price
                .multiply(TaxPercent.divide(BigDecimal.valueOf(100),3,BigDecimal.ROUND_HALF_UP)).setScale(3,BigDecimal.ROUND_DOWN);

        BigDecimal roundedTax= Utils.getRounded(TaxAmnt);

        pricewithtax.setText(Sales_Price.add(roundedTax).setScale(2,BigDecimal.ROUND_HALF_UP).toString());

        this.ProductId =Product_Id;


        setSpinnerObserver(ProductCatId);

        mAddProduct.setText("Update");

        delete.setVisibility(View.VISIBLE);

        reset.setVisibility(View.GONE);

    }



    private  void _UpdateProduct(int ProductId) {


        progressDialog.show();

        float mTax = Float.parseFloat(tax.getText().toString().trim());

        float d = Float.parseFloat(mPrice.getText().toString().trim());

        BigDecimal _salesPrice  = Utils.round(d,2);

        BigDecimal _tax  =Utils.round(mTax,2);


        Log.e("_tax",""+_tax);
        Log.e("_salesPrice",""+_salesPrice);

        Products products =new Products(ProductId,catId,_tax,mProduct_name.getText().toString().trim()
                ,_salesPrice
                ,true);

        productViewModel.UpdateProduct(products);




    }






    private  static class CheckExistAsync extends AsyncTask<String,Void,String> {

        private Product_DAO product_dao;


        public CheckExistAsync(Product_DAO product_dao) {
            this.product_dao = product_dao;
        }

        @Override
        protected String doInBackground(String... voids) {


//            InvoiceNo = header_dao.getId()+1;

            String result;

            List<Products> name =   product_dao.getDuplicateIfExist(voids[0]);


            if(name.size()>0)
            {
                result= "Already exist";
            }
            else {
                result="New Record";
            }


            return result;
        }


    }


    private  void SearchFilter() {
        if (mlist.isEmpty()) {

            Log.e("Arraylist", "" + mlist.size());

        } else {

            Log.e("Search", "else " + mlist.size());


            search.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                    adapter.getFilter().filter(s);

                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });


            search.setOnEditorActionListener(new EditText.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    if (actionId == EditorInfo.IME_ACTION_SEARCH) {


                        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
                        return true;
                    }
                    return false;
                }
            });


        }
    }





}
