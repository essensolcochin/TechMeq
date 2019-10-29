package com.essensol.techmeq.Model;

public class SaleReportModel {

    private  int SaleId;
    private  double paidamnt,grandtotal,tax;
    private String Sale_no,Date;

    public SaleReportModel() {
    }

    public SaleReportModel(int saleId, double paidamnt, double grandtotal, double tax, String sale_no, String date) {
        SaleId = saleId;
        this.paidamnt = paidamnt;
        this.grandtotal = grandtotal;
        this.tax = tax;
        Sale_no = sale_no;
        Date = date;
    }

    public int getSaleId() {
        return SaleId;
    }

    public void setSaleId(int saleId) {
        SaleId = saleId;
    }

    public double getPaidamnt() {
        return paidamnt;
    }

    public void setPaidamnt(double paidamnt) {
        this.paidamnt = paidamnt;
    }

    public double getGrandtotal() {
        return grandtotal;
    }

    public void setGrandtotal(double grandtotal) {
        this.grandtotal = grandtotal;
    }

    public double getTax() {
        return tax;
    }

    public void setTax(double tax) {
        this.tax = tax;
    }

    public String getSale_no() {
        return Sale_no;
    }

    public void setSale_no(String sale_no) {
        Sale_no = sale_no;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }
}
