package com.utkarshnew.android.courses.modal;

import java.io.Serializable;
import java.util.ArrayList;

public class VODTimeList implements Serializable {
    ArrayList<VODTimeDB> vodTimeDBArrayList = null;

    public VODTimeList(ArrayList<VODTimeDB> vodTimeDBArrayList) {
        this.vodTimeDBArrayList = vodTimeDBArrayList;
    }

    public ArrayList<VODTimeDB> getVodTimeDBArrayList() {
        return vodTimeDBArrayList;
    }

    public void setVodTimeDBArrayList(ArrayList<VODTimeDB> vodTimeDBArrayList) {
        this.vodTimeDBArrayList = vodTimeDBArrayList;
    }
}
