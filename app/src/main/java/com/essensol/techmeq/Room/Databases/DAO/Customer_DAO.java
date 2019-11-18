package com.essensol.techmeq.Room.Databases.DAO;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.essensol.techmeq.Model.CustomerSpinnerModel;
import com.essensol.techmeq.Room.Databases.Entity.Customers;

import java.util.List;
@Dao
public interface Customer_DAO {

    @Insert
    void   AddCustomers(Customers customers);

    @Update
    void UpdateCustomers(Customers customers);

    @Delete
    void DeleteCustomers(Customers customers);


     @Query("SELECT * FROM Customer_master ORDER BY CustId DESC")
     LiveData<List<Customers> >GetAllCustomers();

    @Query("SELECT MAX(CustId) FROM Customer_master")
    int GetCustId();

    @Query("SELECT CustId,CustName FROM Customer_master ")
    LiveData<List<CustomerSpinnerModel>> GetCustNameAndId();

}
