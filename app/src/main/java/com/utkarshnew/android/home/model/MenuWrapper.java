package com.utkarshnew.android.home.model;

import java.io.Serializable;
import java.util.List;

public class MenuWrapper implements Serializable {
    private List<Menu> menuList;

    public MenuWrapper(List<Menu> menuList) {
        this.menuList = menuList;
    }

    public List<Menu> getMenuList() {
        return menuList;
    }
}
