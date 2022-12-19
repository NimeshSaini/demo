
package com.utkarshnew.android.Model.PoJoModel;

import java.io.Serializable;

public class DoseMenuPOJO implements Serializable {

    private String menu_id;

    public DoseMenuPOJO(String menu_id) {
        this.menu_id = menu_id;
    }

    public String getMenu_id() {
        return menu_id;
    }

    public void setMenu_id(String menu_id) {
        this.menu_id = menu_id;
    }
}
