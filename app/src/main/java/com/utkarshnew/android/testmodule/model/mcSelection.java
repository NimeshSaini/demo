package com.utkarshnew.android.testmodule.model;

import java.io.Serializable;

public class mcSelection implements Serializable {
    private int position;
    private int bgcolor_code;


    public int getBgcolor_code() {
        return bgcolor_code;
    }

    public void setBgcolor_code(int bgcolor_code) {
        this.bgcolor_code = bgcolor_code;
    }

    public int getCirclecolor_code() {
        return circlecolor_code;
    }

    public void setCirclecolor_code(int circlecolor_code) {
        this.circlecolor_code = circlecolor_code;
    }

    public boolean select = false;
    private int circlecolor_code;

    public mcSelection(int position, int bgcolor_code, int circlecolor_code, boolean select) {
        this.position = position;
        this.bgcolor_code = bgcolor_code;
        this.circlecolor_code = circlecolor_code;
        this.select = select;
    }

    public boolean isSelect() {
        return select;
    }

    public void setSelect(boolean select) {
        this.select = select;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getColor_code() {
        return bgcolor_code;
    }

    public void setColor_code(int bgcolor_code) {
        this.bgcolor_code = bgcolor_code;
    }
}
