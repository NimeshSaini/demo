
package com.utkarshnew.android.testmodule.model;

import java.io.Serializable;

public class FIBSelectedTag implements Serializable {

    private int pos;
    private String answers;

    public FIBSelectedTag(int pos, String answers) {
        this.pos = pos;
        this.answers = answers;
    }

    public int getPos() {
        return pos;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }

    public String getAnswers() {
        return answers;
    }

    public void setAnswers(String answers) {
        this.answers = answers;
    }
}
