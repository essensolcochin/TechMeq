package com.essensol.techmeq.ViewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;

import com.essensol.techmeq.Model.LoginModel;
import com.essensol.techmeq.Room.Databases.Entity.Users;
import com.essensol.techmeq.Room.Repository.mRepo;

import java.util.List;

public class LoginViewModel extends AndroidViewModel {

    private mRepo loginRepo;
    private List<LoginModel>Checklogin;
    public LoginViewModel(@NonNull Application application) {
        super(application);

        loginRepo=new mRepo(application);
//        Checklogin=loginRepo.VerifyLogin();
    }

    public List<LoginModel>CheckLogin()
    {
        return Checklogin;
    }

    public List<Users>CheckUserCredentials(String uname,String pword)
    {

       return loginRepo.CheckLoginValidation(uname,pword);
    }
}
