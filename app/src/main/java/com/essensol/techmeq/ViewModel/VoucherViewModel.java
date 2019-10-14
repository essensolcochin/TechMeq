package com.essensol.techmeq.ViewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.essensol.techmeq.Room.Databases._dbExpenceVouchers;
import com.essensol.techmeq.Room.Repository.Voucher_Repo;

import java.util.List;

public class VoucherViewModel extends AndroidViewModel {

    private Voucher_Repo voucher_repo;
    private LiveData<List<_dbExpenceVouchers>> allVouchers;


    public VoucherViewModel(@NonNull Application application) {
        super(application);

        this.voucher_repo= new Voucher_Repo(application);
        this.allVouchers= voucher_repo.getAllVouchers();

    }


    public void AddVoucher(_dbExpenceVouchers vouchers)
    {
        voucher_repo.AddVoucher(vouchers);
    }

    public void UpdateVoucher(_dbExpenceVouchers vouchers)
    {
        voucher_repo.UpdateVoucher(vouchers);
    }

    public void DeleteVoucher(_dbExpenceVouchers vouchers)
    {
        voucher_repo.DeleteVoucher(vouchers);
    }

    public LiveData<List<_dbExpenceVouchers>> GetAllVouchers()
    {
        return  allVouchers;
    }


}
