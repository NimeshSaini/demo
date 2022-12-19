
package com.utkarshnew.android.Model.PoJoModel;

import java.io.Serializable;

public class HelpSupportPOJO implements Serializable {

    private String comment_type;
    private String comment_msg;

    public HelpSupportPOJO(String comment_type, String comment_msg) {
        this.comment_type = comment_type;
        this.comment_msg = comment_msg;
    }

    public String getComment_type() {
        return comment_type;
    }

    public void setComment_type(String comment_type) {
        this.comment_type = comment_type;
    }

    public String getComment_msg() {
        return comment_msg;
    }

    public void setComment_msg(String comment_msg) {
        this.comment_msg = comment_msg;
    }
}
