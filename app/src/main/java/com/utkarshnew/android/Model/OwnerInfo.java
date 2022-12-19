package com.utkarshnew.android.Model;

import java.io.Serializable;

/**
 * Created by Cbc-03 on 08/17/17.
 */


public class OwnerInfo implements Serializable {
    private String id;
    private String profile_picture;
    private String name;
    private String speciality;
    private String is_mentor;
    private String is_expert;

    public String getIs_mentor() {
        return is_mentor;
    }

    public void setIs_mentor(String is_mentor) {
        this.is_mentor = is_mentor;
    }

    public String getIs_expert() {
        return is_expert;
    }

    public void setIs_expert(String is_expert) {
        this.is_expert = is_expert;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProfile_picture() {
        return profile_picture;
    }

    public void setProfile_picture(String profile_picture) {
        this.profile_picture = profile_picture;
    }

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

