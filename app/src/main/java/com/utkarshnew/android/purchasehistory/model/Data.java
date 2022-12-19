package com.utkarshnew.android.purchasehistory.model;

import android.content.Intent;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.utkarshnew.android.Model.ExtendValidity;
import com.utkarshnew.android.Webview.WebViewActivty;

import java.io.Serializable;
import java.util.List;

public  class Data implements Serializable {

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    public String getInvoice_url() {
        return invoice_url;
    }

    public void setInvoice_url(String invoice_url) {
        this.invoice_url = invoice_url;
    }

    private String invoice_url;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getCover_image() {
            return cover_image;
        }

        public void setCover_image(String cover_image) {
            this.cover_image = cover_image;
        }

        public String getBatch_id() {
            return batch_id;
        }

        public void setBatch_id(String batch_id) {
            this.batch_id = batch_id;
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

        public String getInvoice_no() {
            return invoice_no;
        }

        public void setInvoice_no(String invoice_no) {
            this.invoice_no = invoice_no;
        }

        public String getPayment_id() {
            return payment_id;
        }

        public void setPayment_id(String payment_id) {
            this.payment_id = payment_id;
        }

        private String id;
        private String title;
        private String cover_image;
        private String pay_via;

    public String getPay_via() {
        return pay_via;
    }

    public void setPay_via(String pay_via) {
        this.pay_via = pay_via;
    }

    private String batch_id;
        private String expiry_date;
        private String purchase_date;
        private String mrp;
        private String txn_id;
        private String invoice_no;
        private String payment_id;


    public String getTrack_url_1() {
        return track_url_1;
    }

    public void setTrack_url_1(String track_url_1) {
        this.track_url_1 = track_url_1;
    }

    private String track_url_1;

        public String getTransaction_status() {
            return transaction_status;
        }

        public void setTransaction_status(String transaction_status) {
            this.transaction_status = transaction_status;
        }

        private String transaction_status;

        @SerializedName("prices")
        @Expose
        private List<ExtendValidity> prices;

        public List<ExtendValidity> getPrices() {
            return prices;
        }

        public void setPrices(List<ExtendValidity> prices) {
            this.prices = prices;
        }


        public void trackOrder(String track_url)
        {
         /*   Intent intent4 = new Intent(this, WebViewActivty.class);
            intent4.putExtra("type", "OFFLINE BATCH");
            intent4.putExtra("url", track_url);
            startActivity(intent4);*/
        }
    }
