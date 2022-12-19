package com.utkarshnew.android.Player.customview.ExoSpeedDemo;

import java.io.Serializable;

public class CookieData implements Serializable {
    private String cloudFrontKeyPairId;
    private String cloudFrontSignature;
    private String cloudFrontExpires;

    public CookieData() {

    }

    public String getCloudFrontKeyPairId() {
        return cloudFrontKeyPairId;
    }

    public void setCloudFrontKeyPairId(String cloudFrontKeyPairId) {
        this.cloudFrontKeyPairId = cloudFrontKeyPairId;
    }

    public String getCloudFrontSignature() {
        return cloudFrontSignature;
    }

    public void setCloudFrontSignature(String cloudFrontSignature) {
        this.cloudFrontSignature = cloudFrontSignature;
    }

    public String getCloudFrontExpires() {
        return cloudFrontExpires;
    }

    public void setCloudFrontExpires(String cloudFrontExpires) {
        this.cloudFrontExpires = cloudFrontExpires;
    }
}
