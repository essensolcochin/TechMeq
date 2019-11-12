package com.essensol.techmeq.DialogFragments;


import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.essensol.techmeq.Adapters.AllCategoryListAdapter;
import com.essensol.techmeq.Callbacks.CategoryItemClickListener;
import com.essensol.techmeq.R;
import com.essensol.techmeq.Room.Databases.Entity.Sales_Category;
import com.essensol.techmeq.ViewModel.ProductViewModel;
import com.nbsp.materialfilepicker.MaterialFilePicker;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddCategoryFragment extends DialogFragment implements CategoryItemClickListener {

    private EditText CategoryName;
    private TextView Path;
    private Button Add,delete;
    private ProgressDialog progressDialog;
    private ImageView dismiss;

    private AllCategoryListAdapter adapter;

    private ArrayList<Sales_Category>mlist=new ArrayList<>();

    private ProductViewModel viewModel;

    private RecyclerView categories;

    private int mCategoryId;

    public AddCategoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootview = inflater.inflate(R.layout.fragment_add_category, container, false);


        Add=rootview.findViewById(R.id.add);

        delete=rootview.findViewById(R.id.delete);

        CategoryName=rootview.findViewById(R.id.category);

        Path=rootview.findViewById(R.id.imagepath);

        dismiss=rootview.findViewById(R.id.dismiss);

        categories=rootview.findViewById(R.id.categories);

        progressDialog =new ProgressDialog(getContext());
        progressDialog.setTitle("Adding Category");
        progressDialog.setMessage("Saving...");
        progressDialog.setCancelable(false);


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        categories.setLayoutManager(linearLayoutManager);
        categories.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL));


        adapter =new AllCategoryListAdapter(mlist,this.getActivity(), this);

        categories.setAdapter(adapter);

        viewModel= ViewModelProviders.of(this).get(ProductViewModel.class);


        Path.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(android.os.Build.VERSION.SDK_INT >= 23){
                    if (getActivity().checkSelfPermission(android.Manifest.permission.CAMERA)
                            != PackageManager.PERMISSION_GRANTED) {
                        requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 0);
                    }
                }




            }
        });


        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DeleteCategory();


            }
        });



        viewModel.GetAllProductCategory().observe(this, new Observer<List<Sales_Category>>() {
            @Override
            public void onChanged(@Nullable List<Sales_Category> sales_categories) {

                adapter.SetCategory(sales_categories);

            }
        });


        Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!Add.getText().toString().equalsIgnoreCase("Update"))
                {
                    //&&!Path.getText().toString().equalsIgnoreCase("")

                    if(!CategoryName.getText().toString().equalsIgnoreCase(""))
                    {


                        Runnable progressRunnable = new Runnable() {

                            @Override
                            public void run() {

                                AddCategory();

                                progressDialog.cancel();

                                CategoryName.setText("");
                                Path.setText("");



                            }};



                        Handler pdCanceller = new Handler();
                        pdCanceller.postDelayed(progressRunnable, 500);


                        dismiss();




                    }
                }
                else {
                    UpdateCategory();
                }


            }
        });

        dismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dismiss();
            }
        });


        return  rootview;
    }

    private void DeleteCategory() {

        Sales_Category category =new Sales_Category(mCategoryId,CategoryName.getText().toString(),Path.getText().toString(),true);

        viewModel.DeleteProductCategory(category);

        delete.setVisibility(View.GONE);
        Add.setText("Add Category");
        CategoryName.setText("");
        Path.setText("");


    }


    private void AddCategory() {



        Sales_Category category =new Sales_Category(0,CategoryName.getText().toString(),Path.getText().toString(),true);

        viewModel.AddProductCategory(category);

    }


    private void UpdateCategory() {



        Sales_Category category =new Sales_Category(mCategoryId,CategoryName.getText().toString(),Path.getText().toString(),true);

        viewModel.UpdateProductCategory(category);

        Add.setText("Add Category");
        CategoryName.setText("");
        Path.setText("");


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED)
        {
            Loadgallery();
        }
    }




    @Override
    @SuppressLint("NewApi")
    public void onActivityResult(int requestCode, int resultCode, Intent data) {



        Log.e("onActivityResult"," ");



            if (requestCode == 1) {

                if (resultCode == RESULT_OK) {




                String path= getRealPathFromURI(getContext(),data.getData());

                Log.e("Dataaaaaa"," "+path);



                Path.setText(path);
            }
        }

    }


    private void launchImagePicker() {
        new MaterialFilePicker()
                .withActivity(getActivity())
                .withRequestCode(1)
                .withHiddenFiles(true)
                .withFilter(Pattern.compile(".*\\.jpg$"))
                .withFilter(Pattern.compile(".*\\.jpeg$"))
                .withTitle("Select  file")
                .start();



    }




    private void  Loadgallery()
    {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"),1);



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



    private String getRealPathFromURI(Context context, Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = { MediaStore.Images.Media.DATA };
            cursor = context.getContentResolver().query(contentUri,  proj, null, null, null);
            assert cursor != null;
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } catch (Exception e) {
            Log.e("Pathhhh", "getRealPathFromURI Exception : " + e.toString());
            return "";
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }


    @Override
    public void getCategoryDetailsForEdit(int ProductCatId, String ProductCategory, String Image, boolean Status) {

        mCategoryId=ProductCatId;
        CategoryName.setText(ProductCategory);
        Path.setText(Image);
        Add.setText("Update");
        delete.setVisibility(View.VISIBLE);


    }
}
