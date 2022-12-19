
package com.utkarshnew.android.Model.PoJoModel;

import java.io.Serializable;

public class MasterHitOnePOJO implements Serializable {

    private String device_tokken;

    public MasterHitOnePOJO(String device_tokken) {
        this.device_tokken = device_tokken;
    }

    public String getDevice_tokken() {
        return device_tokken;
    }

    public void setDevice_tokken(String device_tokken) {
        this.device_tokken = device_tokken;
    }
}
