package com.essensol.techmeq.Room.Databases.DAO;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

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

}
