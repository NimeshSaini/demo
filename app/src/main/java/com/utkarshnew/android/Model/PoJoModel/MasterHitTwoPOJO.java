
package com.utkarshnew.android.Model.PoJoModel;

import java.io.Serializable;

public class MasterHitTwoPOJO implements Serializable {

    private String sub_cat;

    public MasterHitTwoPOJO(String sub_cat) {
        this.sub_cat = sub_cat;
    }

    public String getSub_cat() {
        return sub_cat;
    }

    public void setSub_cat(String sub_cat) {
        this.sub_cat = sub_cat;
    }
}
