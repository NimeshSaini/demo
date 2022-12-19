package com.utkarshnew.android.table;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.utkarshnew.android.Model.ExtendValidity;
import com.utkarshnew.android.TypeConverter.Converters;

import java.util.List;

@Entity(tableName = "mycoursetable")
public class MycourseTable {

    public int getAutoid() {
        return autoid;
    }

    public void setAutoid(int autoid) {
        this.autoid = autoid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBatch_id() {
        return batch_id;
    }

    public void setBatch_id(String batch_id) {
        this.batch_id = batch_id;
    }

    public String getCover_image() {
        return cover_image;
    }

    public void setCover_image(String cover_image) {
        this.cover_image = cover_image;
    }

    public String getExpiry_date() {
        return expiry_date;
    }

    public void setExpiry_date(String expiry_date) {
        this.expiry_date = expiry_date;
    }

    public String getPurchase_date() {
        return purchase_date;
    }

    public void setPurchase_date(String purchase_date) {
        this.purchase_date = purchase_date;
    }

    public String getMrp() {
        return mrp;
    }

    public void setMrp(String mrp) {
        this.mrp = mrp;
    }

    public String getTxn_id() {
        return txn_id;
    }

    public void setTxn_id(String txn_id) {
        this.txn_id = txn_id;
    }

    public String getLastread() {
        return lastread;
    }

    public void setLastread(String lastread) {
        this.lastread = lastread;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getBatchtype() {
        return batchtype;
    }

    public void setBatchtype(String batchtype) {
        this.batchtype = batchtype;
    }

    @PrimaryKey(autoGenerate = true)
    private int autoid;


    @ColumnInfo(name = "id")
    private String id;


    @ColumnInfo(name = "title")
    private String title;


    public String getIsSelect() {
        return isSelect;
    }

    public void setIsSelect(String isSelect) {
        this.isSelect = isSelect;
    }

    @ColumnInfo(name = "isSelect")
    private String isSelect;

    public String getIsExpand() {
        return isExpand;
    }

    public void setIsExpand(String isExpand) {
        this.isExpand = isExpand;
    }

    @ColumnInfo(name = "isExpand")
    private String isExpand;


    @ColumnInfo(name = "batch_id")
    private String batch_id;

    @ColumnInfo(name = "cover_image")
    private String cover_image;


    @ColumnInfo(name = "expiry_date")
    private String expiry_date;

    @ColumnInfo(name = "purchase_date")
    private String purchase_date;


    @ColumnInfo(name = "mrp")
    private String mrp;

    @ColumnInfo(name = "txn_id")
    private String txn_id;

    @ColumnInfo(name = "lastread")
    private String lastread;


    @ColumnInfo(name = "userid")
    private String userid;


    @ColumnInfo(name = "batchtype")
    private String batchtype;


    public int getDelete() {
        return delete;
    }

    public void setDelete(int delete) {
        this.delete = delete;
    }

    @ColumnInfo(name = "delete")
    private int delete;




    public List<ExtendValidity> getPrices() {
        return prices;
    }

    public void setPrices(List<ExtendValidity> prices) {
        this.prices = prices;
    }

    @TypeConverters(Converters.class) // add here
    @ColumnInfo(name = "prices")
    private List<ExtendValidity> prices;


}
