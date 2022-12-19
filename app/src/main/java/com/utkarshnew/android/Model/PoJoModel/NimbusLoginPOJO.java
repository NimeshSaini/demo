
package com.utkarshnew.android.Model.PoJoModel;

import java.io.Serializable;

public class NimbusLoginPOJO implements Serializable {

    private String user_tokken;
    private String is_social;
    private String social_type;
    private String social_tokken;
    private String device_type;
    private String device_tokken;

    public NimbusLoginPOJO(String user_tokken, String is_social, String social_type, String social_tokken, String device_type, String device_tokken) {
        this.user_tokken = user_tokken;
        this.is_social = is_social;
        this.social_type = social_type;
        this.social_tokken = social_tokken;
        this.device_type = device_type;
        this.device_tokken = device_tokken;
    }

    public String getUser_tokken() {
        return user_tokken;
    }

    public void setUser_tokken(String user_tokken) {
        this.user_tokken = user_tokken;
    }

    public String getIs_social() {
        return is_social;
    }

    public void setIs_social(String is_social) {
        this.is_social = is_social;
    }

    public String getSocial_type() {
        return social_type;
    }

    public void setSocial_type(String social_type) {
        this.social_type = social_type;
    }

    public String getSocial_tokken() {
        return social_tokken;
    }

    public void setSocial_tokken(String social_tokken) {
        this.social_tokken = social_tokken;
    }

    public String getDevice_type() {
        return device_type;
    }

    public void setDevice_type(String device_type) {
        this.device_type = device_type;
    }

    public String getDevice_tokken() {
        return device_tokken;
    }

    public void setDevice_tokken(String device_tokken) {
        this.device_tokken = device_tokken;
    }
}
