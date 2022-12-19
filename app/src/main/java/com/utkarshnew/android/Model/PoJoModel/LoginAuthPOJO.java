
package com.utkarshnew.android.Model.PoJoModel;

import java.io.Serializable;

public class LoginAuthPOJO implements Serializable {

    private String email;
    private String password;
    private String is_social;
    private String social_type;
    private String device_type;
    private String location;
    private String device_id;
    private String device_tokken;
    private String profile_picture;

    public LoginAuthPOJO(String mobile, String email, String password, String is_social, String social_type, String device_type, String location, String device_id, String profile_picture) {
        this.email = email;
        this.password = password;
        this.is_social = is_social;
        this.social_type = social_type;
        this.device_type = device_type;
        this.location = location;
        this.device_id = device_id;
        this.device_tokken = device_tokken;
        this.profile_picture = profile_picture;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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


    public String getDevice_type() {
        return device_type;
    }

    public void setDevice_type(String device_type) {
        this.device_type = device_type;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDevice_id() {
        return device_id;
    }

    public void setDevice_id(String device_id) {
        this.device_id = device_id;
    }

    public String getDevice_tokken() {
        return device_tokken;
    }

    public void setDevice_tokken(String device_tokken) {
        this.device_tokken = device_tokken;
    }

    public String getProfile_picture() {
        return profile_picture;
    }

    public void setProfile_picture(String profile_picture) {
        this.profile_picture = profile_picture;
    }
}
