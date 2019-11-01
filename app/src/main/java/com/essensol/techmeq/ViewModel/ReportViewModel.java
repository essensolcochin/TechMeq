package com.essensol.techmeq.ViewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.essensol.techmeq.Model.ItemReportModel;

import com.essensol.techmeq.Room.Repository.mRepo;

import java.util.Date;
import java.util.List;

public class ReportViewModel extends AndroidViewModel {

    private mRepo product_repo;
    private LiveData<List<ItemReportModel>> SaleReport;

    public ReportViewModel(@NonNull Application application) {
        super(application);

        product_repo =new mRepo(application);

//        SaleReport =product_repo.getAllSalesReport();

    }

    public LiveData<List<ItemReportModel>>GetSalesReport(long d,long d2)
    {
        return product_repo.getAllSalesReport(d,d2);
    }
}
