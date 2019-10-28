package com.essensol.techmeq.Room.Databases.DAO;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.essensol.techmeq.Room.Databases.Entity.FinancialYear;

import java.util.List;
@Dao
public interface FinancialYear_DAO {

    @Insert
    void AddFinancialYear(FinancialYear products);

    @Update
    void UpdateFinancialYear(FinancialYear products);

    @Delete
    void DeleteFinancialYear(FinancialYear products);


    @Query("SELECT * FROM Financial_Year ORDER BY FinyearId DESC")
    List<FinancialYear> GetAllProduct();

    @Query("SELECT MAX(FinyearId) FROM Financial_Year ")
    int GetMaxFinId();



}
