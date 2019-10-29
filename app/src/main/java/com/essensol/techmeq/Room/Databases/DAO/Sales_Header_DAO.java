package com.essensol.techmeq.Room.Databases.DAO;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.essensol.techmeq.Model.SaleReportModel;
import com.essensol.techmeq.Room.Databases.Entity.SalesHeader;

import java.util.List;
@Dao
public interface Sales_Header_DAO {

    @Insert
    long  AddSalesHeader(SalesHeader header);

    @Update
    void UpdateSalesHeader(SalesHeader header);

    @Delete
    void DeleteSalesHeader(SalesHeader header);


    @Query("SELECT * FROM sales_header ORDER BY SaleId DESC")
    List<SalesHeader> GetSalesHeader();


    @Query("SELECT MAX(SaleId) FROM sales_header ")
    int getId();

//        @Query("SELECT  sales_header.SaleNo , sales_header.SaleDate," +
//                "Customer_master.CustName ,sales_header.TaxAmt," +
//                "sales_header.PaidAmt,sales_header.GrandTotal " +
//                "FROM sales_header " +
//                "INNER JOIN Customer_master " +
//                "ON Customer_master.CustId=sales_header.CustId ")
//
//          LiveData<List<SaleReportModel>> getSalesReport();



}
