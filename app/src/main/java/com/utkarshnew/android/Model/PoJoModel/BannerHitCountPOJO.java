
package com.utkarshnew.android.Model.PoJoModel;

import java.io.Serializable;

public class BannerHitCountPOJO implements Serializable {

    private String banner_id;

    public BannerHitCountPOJO(String banner_id) {
        this.banner_id = banner_id;
    }

    public String getBanner_id() {
        return banner_id;
    }

    public void setBanner_id(String banner_id) {
        this.banner_id = banner_id;
    }
}
