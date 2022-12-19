
package com.utkarshnew.android.Model.PoJoModel;

import java.io.Serializable;

public class MasterRegHitPOJO implements Serializable {

    private String id;

    public MasterRegHitPOJO(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
