package com.utkarshnew.android.testmodule.model;

import java.io.Serializable;

public class Social implements Serializable {

    public String name;
    public String option;
    public int tag;

    public boolean isSelect() {
        return select;
    }

    public void setSelect(boolean select) {
        this.select = select;
    }

    public boolean select = false;
    public mcSelection selcted;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }

    public int getTag() {
        return tag;
    }

    public void setTag(int tag) {
        this.tag = tag;
    }

    public mcSelection getSelcted() {
        return selcted;
    }

    public void setSelcted(mcSelection selcted, boolean select) {
        this.selcted = selcted;
        this.select = select;
    }

    public boolean isParent() {
        return parent;
    }

    public void setParent(boolean parent) {
        this.parent = parent;
    }

    public boolean isSwiped() {
        return swiped;
    }

    public void setSwiped(boolean swiped) {
        this.swiped = swiped;
    }

    public boolean parent = false;

    // flag when item swiped
    public boolean swiped = false;

    public Social() {
    }

    public Social(String option, String name, int tag) {
        this.option = option;
        this.name = name;
        this.tag = tag;
    }
}
