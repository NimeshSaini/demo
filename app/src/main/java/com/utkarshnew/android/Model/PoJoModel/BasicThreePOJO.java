
package com.utkarshnew.android.Model.PoJoModel;

import java.io.Serializable;

public class BasicThreePOJO implements Serializable {

    private String id;
    private String data_required;

    public BasicThreePOJO(String id, String data_required) {
        this.id = id;
        this.data_required = data_required;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getData_required() {
        return data_required;
    }

    public void setData_required(String data_required) {
        this.data_required = data_required;
    }
}
