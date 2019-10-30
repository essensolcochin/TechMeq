package com.essensol.techmeq.DialogFragments;


import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.essensol.techmeq.R;
import com.essensol.techmeq.Room.Databases.Entity.Sales_Category;
import com.essensol.techmeq.ViewModel.ProductViewModel;
import com.nbsp.materialfilepicker.MaterialFilePicker;
import com.nbsp.materialfilepicker.ui.FilePickerActivity;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.regex.Pattern;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddCategoryFragment extends DialogFragment {

    EditText CategoryName;
    TextView Path;
    Button Add;
    ProgressDialog progressDialog;
    ImageView dismiss;

    private ProductViewModel viewModel;

    public AddCategoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootview = inflater.inflate(R.layout.fragment_add_category, container, false);


        Add=rootview.findViewById(R.id.add);

        CategoryName=rootview.findViewById(R.id.category);

        Path=rootview.findViewById(R.id.imagepath);

        dismiss=rootview.findViewById(R.id.dismiss);

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


                    dismiss();

//                    TabLayout tab = getActivity().findViewById(R.id.tabMode);
//
//                    tab.getTabAt(0).select();


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



    private void AddCategory()
    {



        Sales_Category category =new Sales_Category(CategoryName.getText().toString(),Path.getText().toString(),true);

        viewModel.AddProductCategory(category);

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
    @SuppressLint("NewApi")
    public void onActivityResult(int requestCode, int resultCode, Intent data) {



        Log.e("onActivityResult"," ");



            if (requestCode == 1) {

                if (resultCode == RESULT_OK) {




                String path= data.getStringExtra(FilePickerActivity.RESULT_FILE_PATH);

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



    public String getRealPathFromURI(Uri contentURI, Activity context) {
        String[] projection = { MediaStore.Images.Media.DATA };
        @SuppressWarnings("deprecation")
        Cursor cursor = context.managedQuery(contentURI, projection, null,
                null, null);
        if (cursor == null)
            return null;
        int column_index = cursor
                .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        if (cursor.moveToFirst()) {
            String s = cursor.getString(column_index);
            // cursor.close();
            return s;
        }
        // cursor.close();
        return null;
    }




}
