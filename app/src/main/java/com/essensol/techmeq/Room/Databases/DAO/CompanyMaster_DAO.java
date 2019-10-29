package com.essensol.techmeq.Room.Databases.DAO;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.essensol.techmeq.Room.Databases.Entity.CompanyMaster;

import java.util.List;

@Dao
public interface CompanyMaster_DAO {

    @Insert
    void   AddCompany(CompanyMaster company);

    @Update
    void UpdateCompany(CompanyMaster company);

    @Delete
    void DeleteCompany(CompanyMaster company);


    @Query("SELECT * FROM Company_master ORDER BY CompId DESC")
    List<CompanyMaster>GetAllCompany();

    @Query("SELECT MAX(CompId) FROM Company_master ")
    int GetCompanyId();


}
