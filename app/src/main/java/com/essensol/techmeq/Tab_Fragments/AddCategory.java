package com.essensol.techmeq.Tab_Fragments;


import android.Manifest;
import android.app.ProgressDialog;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import com.essensol.techmeq.FilePath;
import com.essensol.techmeq.R;
import com.essensol.techmeq.Room.Databases.Entity.Sales_Category;
import com.essensol.techmeq.ViewModel.ProductCategoryViewModel;
import com.essensol.techmeq.ViewModel.ProductViewModel;
import com.nbsp.materialfilepicker.MaterialFilePicker;
import com.nbsp.materialfilepicker.ui.FilePickerActivity;

import java.util.ArrayList;
import java.util.regex.Pattern;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddCategory extends Fragment {


    EditText CategoryName;
    TextView Path;
    Button Add;
    ProgressDialog progressDialog;

    private ProductViewModel viewModel;

    public AddCategory() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootview= inflater.inflate(R.layout.addcategory_tab, container, false);

        Add=rootview.findViewById(R.id.add);

        CategoryName=rootview.findViewById(R.id.category);

        Path=rootview.findViewById(R.id.imagepath);


        progressDialog =new ProgressDialog(getContext());
        progressDialog.setTitle("Adding Category");
        progressDialog.setMessage("Saving...");
        progressDialog.setCancelable(false);

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




//                Loadgallery();
            }
        });

        Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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


                    TabLayout tab = getActivity().findViewById(R.id.tabMode);

                    tab.getTabAt(0).select();





                }
            }
        });

        return rootview;
    }

    private void AddCategory()
    {
        Sales_Category category =new Sales_Category(CategoryName.getText().toString(),"Path",true);

        viewModel.AddProductCategory(category);

    }



    private void  Loadgallery()
    {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"),1);



    }




    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED)
        {
            launchImagePicker();
        }
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        Log.e("onActivityResult"," ");

        if (resultCode == RESULT_OK) {

            if (requestCode == 1) {


                String path=  data.getStringExtra(FilePickerActivity.RESULT_FILE_PATH);

                Log.e("Dataaaaaa"," "+path);
//                // currImageURI is the global variable I'm using to hold the content:// URI of the image
//               Uri currImageURI = data.getData();




                Path.setText(path);
            }
        }

    }





    public String getRealPathFromURI(Uri contentUri) {

        // can post image
        String [] proj={MediaStore.Images.Media.DATA};
        Cursor cursor = getActivity().managedQuery( contentUri,
                proj, // Which columns to return
                null,       // WHERE clause; which rows to return (all rows)
                null,       // WHERE clause selection arguments (none)
                null); // Order-by clause (ascending by name)
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();

        return cursor.getString(column_index);
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



}
