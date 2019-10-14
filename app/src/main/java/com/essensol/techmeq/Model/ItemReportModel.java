package com.essensol.techmeq.Model;

public class ItemReportModel {

   private boolean isHeader =false;

   private String ItemName,ItemPrice,SaleDate,ItemQty;

    public ItemReportModel(boolean isHeader, String itemName, String itemPrice, String saleDate, String itemQty) {
        this.isHeader = isHeader;
        ItemName = itemName;
        ItemPrice = itemPrice;
        SaleDate = saleDate;
        ItemQty = itemQty;
    }

    public boolean isHeader() {
        return isHeader;
    }

    public void setHeader(boolean header) {
        isHeader = header;
    }

    public String getItemName() {
        return ItemName;
    }

    public void setItemName(String itemName) {
        ItemName = itemName;
    }

    public String getItemPrice() {
        return ItemPrice;
    }

    public void setItemPrice(String itemPrice) {
        ItemPrice = itemPrice;
    }

    public String getSaleDate() {
        return SaleDate;
    }

    public void setSaleDate(String saleDate) {
        SaleDate = saleDate;
    }

    public String getItemQty() {
        return ItemQty;
    }

    public void setItemQty(String itemQty) {
        ItemQty = itemQty;
    }
}
