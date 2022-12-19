package com.utkarshnew.android.Model;

import java.io.Serializable;

public class DrishtiVerification implements Serializable {

    private String drishtiId;
    private String frontImage;
    private String backImage;
    private String isVerified;

    public String getDrishtiId() {
        return drishtiId;
    }

    public void setDrishtiId(String drishtiId) {
        this.drishtiId = drishtiId;
    }

    public String getFrontImage() {
        return frontImage;
    }

    public void setFrontImage(String frontImage) {
        this.frontImage = frontImage;
    }

    public String getBackImage() {
        return backImage;
    }

    public void setBackImage(String backImage) {
        this.backImage = backImage;
    }

    public String getIsVerified() {
        return isVerified;
    }

    public void setIsVerified(String isVerified) {
        this.isVerified = isVerified;
    }
}
