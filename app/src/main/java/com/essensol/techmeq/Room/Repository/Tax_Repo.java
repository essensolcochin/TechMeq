package com.essensol.techmeq.Room.Repository;

public class Tax_Repo {

//    private Tax_DAO tax_dao;
//    private List<TaxModel>Tax;
//
//
//    public Tax_Repo(Application application) {
//
//        TaxDb db =TaxDb.getInstance(application);
//        tax_dao=db.tax_dao();
//        Tax=tax_dao.GetTax();
//
//    }
//
//
//    public  List<TaxModel>getTax()
//    {
//        return Tax;
//    }
//
//    public  void  AddTax(TaxModel tax)
//    {
//
//        new AddTaxAsync(tax_dao).execute(tax);
//    }
//
//    public  void  UpdateTax(TaxModel tax)
//    {
//        new UpdateTaxAsync(tax_dao).execute(tax);
//    }
//
//    public  void  DeleteTax(TaxModel tax)
//    {
//        new DeleteTaxAsync(tax_dao).execute(tax);
//    }
//
//
//
//
//
//    private  static class AddTaxAsync extends AsyncTask<TaxModel,Void,Void> {
//
//        private Tax_DAO tax_dao;
//
//        public AddTaxAsync(Tax_DAO tax_dao) {
//            this.tax_dao = tax_dao;
//        }
//
//        @Override
//        protected Void doInBackground(TaxModel... taxModels) {
//
//            tax_dao.AddTax(taxModels[0]);
//
//            return null;
//        }
//    }
//
//
//    private  static class UpdateTaxAsync extends AsyncTask<TaxModel,Void,Void> {
//
//        private Tax_DAO tax_dao;
//
//        public UpdateTaxAsync(Tax_DAO tax_dao) {
//            this.tax_dao = tax_dao;
//        }
//
//        @Override
//        protected Void doInBackground(TaxModel... taxModels) {
//
//            tax_dao.UpdateTax(taxModels[0]);
//
//            return null;
//        }
//    }
//
//
//
//    private  static class DeleteTaxAsync extends AsyncTask<TaxModel,Void,Void> {
//
//        private Tax_DAO tax_dao;
//
//        public DeleteTaxAsync(Tax_DAO tax_dao) {
//            this.tax_dao = tax_dao;
//        }
//
//        @Override
//        protected Void doInBackground(TaxModel... taxModels) {
//
//            tax_dao.DeleteTax(taxModels[0]);
//
//            return null;
//        }
//    }
//
//

}
