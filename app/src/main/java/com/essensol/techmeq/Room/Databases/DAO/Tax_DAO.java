package com.essensol.techmeq.Room.Databases.DAO;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.essensol.techmeq.Room.Databases.TaxModel;

import java.util.List;
@Dao
public interface Tax_DAO {

    @Insert
    void AddTax(TaxModel tax);

    @Update
    void UpdateTax(TaxModel tax);

    @Delete
    void DeleteTax(TaxModel tax);


    @Query("SELECT * FROM Tax_master ORDER BY Tax_Id DESC")
    List<TaxModel> GetTax();


}
