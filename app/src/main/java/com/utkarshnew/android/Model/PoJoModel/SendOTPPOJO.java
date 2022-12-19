
package com.utkarshnew.android.Model.PoJoModel;

import java.io.Serializable;

public class SendOTPPOJO implements Serializable {

    private String mobile;
    private String otp;
    private String is_registration;

    public SendOTPPOJO(String mobile, String otp, String is_registration) {
        this.mobile = mobile;
        this.otp = otp;
        this.is_registration = is_registration;
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

    public String getIs_registration() {
        return is_registration;
    }

    public void setIs_registration(String is_registration) {
        this.is_registration = is_registration;
    }
}
