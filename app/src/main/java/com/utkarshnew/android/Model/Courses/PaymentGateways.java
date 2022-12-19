package com.utkarshnew.android.Model.Courses;

import java.io.Serializable;

public class PaymentGateways implements Serializable {

    private String id;
    private String payment_name;

    public PaymentGateways(String id, String payment_name) {
        this.id = id;
        this.payment_name = payment_name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPayment_name() {
        return payment_name;
    }

    public void setPayment_name(String payment_name) {
        this.payment_name = payment_name;
    }
}
