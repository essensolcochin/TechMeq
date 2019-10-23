package com.essensol.techmeq.ViewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.essensol.techmeq.Room.Databases.Entity._dbExpenceVouchers;
import com.essensol.techmeq.Room.Repository.mRepo;

import java.util.List;

public class VoucherViewModel extends AndroidViewModel {

    private mRepo m_repo;
    private LiveData<List<_dbExpenceVouchers>> allVouchers;


    public VoucherViewModel(@NonNull Application application) {
        super(application);

        this.m_repo = new mRepo(application);
        this.allVouchers= m_repo.getAllVouchers();

    }


    public void AddVoucher(_dbExpenceVouchers vouchers)
    {
        m_repo.AddVoucher(vouchers);
    }

    public void UpdateVoucher(_dbExpenceVouchers vouchers)
    {
        m_repo.UpdateVoucher(vouchers);
    }

    public void DeleteVoucher(_dbExpenceVouchers vouchers)
    {
        m_repo.DeleteVoucher(vouchers);
    }

    public LiveData<List<_dbExpenceVouchers>> GetAllVouchers()
    {
        return  allVouchers;
    }


}
