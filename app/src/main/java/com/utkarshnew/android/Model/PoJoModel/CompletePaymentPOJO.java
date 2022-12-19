
package com.utkarshnew.android.Model.PoJoModel;

import java.io.Serializable;

public class CompletePaymentPOJO implements Serializable {

    private String course_id;
    private String transaction_status;
    private String pre_transaction_id;
    private String post_transaction_id;
    private String emi_no;
    private String txn_id;

    public CompletePaymentPOJO(String course_id, String transaction_status, String pre_transaction_id, String post_transaction_id, String emi_no, String txn_id) {
        this.course_id = course_id;
        this.transaction_status = transaction_status;
        this.pre_transaction_id = pre_transaction_id;
        this.post_transaction_id = post_transaction_id;
        this.emi_no = emi_no;
        this.txn_id = txn_id;
    }

    public String getCourse_id() {
        return course_id;
    }

    public void setCourse_id(String course_id) {
        this.course_id = course_id;
    }

    public String getTransaction_status() {
        return transaction_status;
    }

    public void setTransaction_status(String transaction_status) {
        this.transaction_status = transaction_status;
    }

    public String getPre_transaction_id() {
        return pre_transaction_id;
    }

    public void setPre_transaction_id(String pre_transaction_id) {
        this.pre_transaction_id = pre_transaction_id;
    }

    public String getPost_transaction_id() {
        return post_transaction_id;
    }

    public void setPost_transaction_id(String post_transaction_id) {
        this.post_transaction_id = post_transaction_id;
    }

    public String getEmi_no() {
        return emi_no;
    }

    public void setEmi_no(String emi_no) {
        this.emi_no = emi_no;
    }

    public String getTxn_id() {
        return txn_id;
    }

    public void setTxn_id(String txn_id) {
        this.txn_id = txn_id;
    }
}
