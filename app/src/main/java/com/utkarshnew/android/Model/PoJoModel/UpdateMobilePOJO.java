
package com.utkarshnew.android.Model.PoJoModel;

import java.io.Serializable;

public class UpdateMobilePOJO implements Serializable {

    private String mobile;
    private String otp;

    public UpdateMobilePOJO(String mobile, String otp) {
        this.mobile = mobile;
        this.otp = otp;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }
}
