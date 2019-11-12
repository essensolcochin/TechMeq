package com.essensol.techmeq.Room.Databases.DAO;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.essensol.techmeq.Model.LoginModel;
import com.essensol.techmeq.Room.Databases.Entity.Users;

import java.util.List;
@Dao
public interface User_DAO {

    @Insert
    void   AddUser(Users users);

    @Update
    void UpdateUser(Users users);

    @Delete
    void DeleteUser(Users users);


    @Query("SELECT * FROM User_Master ORDER BY UserId DESC")
    List<Users>GetAllUsers();

    @Query("SELECT  UM.UserId , UM.UserName,UM.CompId,CM.CompName,CM.CompBuilding,CM.TRN,CM.MobileNo" +
            " FROM User_Master AS UM " +
            "INNER JOIN COMPANY_MASTER CM " +
            "ON UM.CompId=CM.CompId ")
     List<LoginModel>GetLoginDetails();

    @Query("SELECT * FROM User_Master WHERE UserName =:uname AND Password=:pword")
    List<Users>CheckLogin(String uname,String pword);

}
