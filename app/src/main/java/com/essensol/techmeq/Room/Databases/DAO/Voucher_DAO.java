package com.essensol.techmeq.Room.Databases.DAO;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.essensol.techmeq.Room.Databases.Entity._dbExpenceVouchers;

import java.util.List;

@Dao
public interface Voucher_DAO {

    @Insert
    void AddVoucher(_dbExpenceVouchers vouchers);

    @Update
    void UpdateVoucher(_dbExpenceVouchers vouchers);

    @Delete
    void DeleteVoucher(_dbExpenceVouchers vouchers);


    @Query("SELECT * FROM Voucher_Master ORDER BY Created_On DESC")
    LiveData<List<_dbExpenceVouchers>> GetAllVouchers();

}
