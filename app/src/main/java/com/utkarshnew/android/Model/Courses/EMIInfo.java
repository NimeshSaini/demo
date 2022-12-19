package com.utkarshnew.android.Model.Courses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class EMIInfo implements Serializable {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("emi_no")
    @Expose
    private String emiNo;
    @SerializedName("emi_mrp")
    @Expose
    private String emiMrp;
    @SerializedName("attribute")
    @Expose
    private String attribute;
    @SerializedName("emi_validity")
    @Expose
    private String emiValidity;
    @SerializedName("txn_id")
    @Expose
    private String txnId;
    @SerializedName("valid_from")
    @Expose
    private String validFrom;
    @SerializedName("comletion_date")
    @Expose
    private String comletionDate;
    @SerializedName("valid_to")
    @Expose
    private String validTo;
    @SerializedName("txn_status")
    @Expose
    private String txnStatus;
    @SerializedName("invoice_url")
    @Expose
    private String invoiceUrl;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmiNo() {
        return emiNo;
    }

    public void setEmiNo(String emiNo) {
        this.emiNo = emiNo;
    }

    public String getEmiMrp() {
        return emiMrp;
    }

    public void setEmiMrp(String emiMrp) {
        this.emiMrp = emiMrp;
    }

    public String getAttribute() {
        return attribute;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    public String getEmiValidity() {
        return emiValidity;
    }

    public void setEmiValidity(String emiValidity) {
        this.emiValidity = emiValidity;
    }

    public String getTxnId() {
        return txnId;
    }

    public void setTxnId(String txnId) {
        this.txnId = txnId;
    }

    public String getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(String validFrom) {
        this.validFrom = validFrom;
    }

    public String getComletionDate() {
        return comletionDate;
    }

    public void setComletionDate(String comletionDate) {
        this.comletionDate = comletionDate;
    }

    public String getValidTo() {
        return validTo;
    }

    public void setValidTo(String validTo) {
        this.validTo = validTo;
    }

    public String getTxnStatus() {
        return txnStatus;
    }

    public void setTxnStatus(String txnStatus) {
        this.txnStatus = txnStatus;
    }

    public String getInvoiceUrl() {
        return invoiceUrl;
    }

    public void setInvoiceUrl(String invoiceUrl) {
        this.invoiceUrl = invoiceUrl;
    }

}