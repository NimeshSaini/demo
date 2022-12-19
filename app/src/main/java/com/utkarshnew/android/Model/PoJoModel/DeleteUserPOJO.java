
package com.utkarshnew.android.Model.PoJoModel;

import java.io.Serializable;

public class DeleteUserPOJO implements Serializable {

    private String user_id;
    private String reason;

    public DeleteUserPOJO(String user_id, String reason) {
        this.user_id = user_id;
        this.reason = reason;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
