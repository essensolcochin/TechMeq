package com.essensol.techmeq.Room.Databases.DAO;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.essensol.techmeq.Model.ItemReportModel;
import com.essensol.techmeq.Model.SaleReportModel;
import com.essensol.techmeq.Room.Databases.Entity.SalesHeader;
import com.essensol.techmeq.Room.Databases.Entity.SalesItem;

import java.util.Date;
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


    @Query("SELECT * FROM sales_header ORDER BY SaleId DESC LIMIT 1")
    LiveData<SalesHeader> getLastProductLive();


    @Query("SELECT MAX(SaleId) FROM sales_header ")
    LiveData<Integer> getId();

//    @Query("SELECT  SH.SaleId , SH.SaleNo,SH.SaleDate,SI.Price,SH.TaxAmt,SH.GrandTotal" +
//            " FROM sales_header AS SH " +
//            "INNER JOIN sales_item SI " +
//            "ON SH.SaleId=SI.SaleId ")
//    LiveData<List<ItemReportModel>> getSalesitemReport();


    @Query("SELECT  SaleId , SaleNo,SaleDate,SubTotal,TaxAmt,GrandTotal,PaidAmt,GrandTotal-PaidAmt AS Credit,Rounded FROM sales_header " +
            "WHERE SaleDate >= :FromDate AND SaleDate <= :toDate")
     LiveData<List<ItemReportModel>> getSalesitemReport(long FromDate,long toDate);

//    @Query("SELECT  SaleId , SaleNo,SaleDate,SubTotal,TaxAmt,GrandTotal,PaidAmt,GrandTotal-PaidAmt AS Credit FROM sales_header ")
//    LiveData<List<ItemReportModel>> getSalesitemReport();


    @Query("SELECT  SaleDate FROM sales_header")
    List<Date> GetDates();



}
