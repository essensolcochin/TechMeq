package com.essensol.techmeq.ViewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;

import com.essensol.techmeq.Room.Databases.TaxModel;
import com.essensol.techmeq.Room.Repository.Tax_Repo;

import java.util.List;

public class TaxViewModel extends AndroidViewModel {

    private Tax_Repo tax_repo;
    private List<TaxModel>GetTax;

    public TaxViewModel(@NonNull Application application) {
        super(application);

        this.tax_repo = new Tax_Repo(application);
        GetTax = tax_repo.getTax();
    }


    public void AddTax(TaxModel tax)
    {
        tax_repo.AddTax(tax);
    }

    public void UpdateTax(TaxModel tax)
    {
        tax_repo.UpdateTax(tax);
    }

    public void DeleteTax(TaxModel tax)
    {
        tax_repo.DeleteTax(tax);
    }

    public List<TaxModel> GetTax()
    {
        return  GetTax;
    }
}
