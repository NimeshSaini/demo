
package com.utkarshnew.android.Model.PoJoModel;

import java.io.Serializable;

public class UpdatePassPOJO implements Serializable {

    private String c_code;
    private String otp;
    private String mobile;
    private String password;

    public UpdatePassPOJO(String c_code, String otp, String mobile, String password) {
        this.c_code = c_code;
        this.otp = otp;
        this.mobile = mobile;
        this.password = password;
    }

    public String getC_code() {
        return c_code;
    }

    public void setC_code(String c_code) {
        this.c_code = c_code;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
