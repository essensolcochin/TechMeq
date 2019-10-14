package com.essensol.techmeq.Room.Repository;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.essensol.techmeq.Room.Databases.DAO.Voucher_DAO;
import com.essensol.techmeq.Room.Databases.ExpenceVoucher_DB;
import com.essensol.techmeq.Room.Databases._dbExpenceVouchers;

import java.util.List;

public class Voucher_Repo {

    private Voucher_DAO voucher_dao;
    private LiveData<List<_dbExpenceVouchers>> AllVouchers;

    public Voucher_Repo(Application application) {

        ExpenceVoucher_DB db =ExpenceVoucher_DB.getInstance(application);
        voucher_dao=db.voucher_dao();
        AllVouchers=voucher_dao.GetAllVouchers();

    }

    public  void  AddVoucher(_dbExpenceVouchers vouchers)
    {

        new AddVoucherAsync(voucher_dao).execute(vouchers);
    }

    public  void  UpdateVoucher(_dbExpenceVouchers vouchers)
    {
        new UpdateVoucherAsync(voucher_dao).execute(vouchers);
    }

    public  void  DeleteVoucher(_dbExpenceVouchers vouchers)
    {
        new DeleteVoucherAsync(voucher_dao).execute(vouchers);
    }




    public  LiveData<List<_dbExpenceVouchers>>getAllVouchers()
    {
        return AllVouchers;
    }


    private  static class AddVoucherAsync extends AsyncTask<_dbExpenceVouchers,Void,Void> {

        private Voucher_DAO voucher_dao;

        public AddVoucherAsync(Voucher_DAO voucher_dao) {
            this.voucher_dao = voucher_dao;
        }

        @Override
        protected Void doInBackground(_dbExpenceVouchers... vouchers) {

            voucher_dao.AddVoucher(vouchers[0]);

            return null;
        }
    }


    private  static class UpdateVoucherAsync extends AsyncTask<_dbExpenceVouchers,Void,Void> {

        private Voucher_DAO voucher_dao;

        public UpdateVoucherAsync(Voucher_DAO voucher_dao) {
            this.voucher_dao = voucher_dao;
        }

        @Override
        protected Void doInBackground(_dbExpenceVouchers... vouchers) {

            voucher_dao.UpdateVoucher(vouchers[0]);

            return null;
        }
    }


    private  static class DeleteVoucherAsync extends AsyncTask<_dbExpenceVouchers,Void,Void> {

        private Voucher_DAO voucher_dao;

        public DeleteVoucherAsync(Voucher_DAO voucher_dao) {
            this.voucher_dao = voucher_dao;
        }

        @Override
        protected Void doInBackground(_dbExpenceVouchers... vouchers) {

            voucher_dao.DeleteVoucher(vouchers[0]);

            return null;
        }
    }



}
