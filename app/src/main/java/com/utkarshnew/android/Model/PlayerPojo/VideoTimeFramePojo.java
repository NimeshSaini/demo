package com.utkarshnew.android.Model.PlayerPojo;

public class VideoTimeFramePojo {
    private String v_fk;

    private String id;

    private String time;

    private String info;

    public String getV_fk() {
        return v_fk;
    }

    public void setV_fk(String v_fk) {
        this.v_fk = v_fk;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    @Override
    public String toString() {
        return "ClassPojo [v_fk = " + v_fk + ", id = " + id + ", time = " + time + ", info = " + info + "]";
    }
}