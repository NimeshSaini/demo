package com.utkarshnew.android.purchasehistory.model;

import java.io.Serializable;
import java.util.ArrayList;

public class PurchaseHistoryModel implements Serializable {

    private ArrayList<Data> data;

    public ArrayList<Data> getData() {
        return data;
    }

    public void setData(ArrayList<Data> data) {
        this.data = data;
    }

}
