
package com.utkarshnew.android.Model.PoJoModel;

import java.io.Serializable;

public class RequestOnePOJO implements Serializable {

    private String name;

    public RequestOnePOJO(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
