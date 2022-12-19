package com.utkarshnew.android.Model;

import java.io.Serializable;

public class Learnerlist implements Serializable {
    private String profile_picture;

    private String name;

    public String getProfile_picture() {
        return profile_picture;
    }

    public void setProfile_picture(String profile_picture) {
        this.profile_picture = profile_picture;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "ClassPojo [profile_picture = " + profile_picture + ", name = " + name + "]";
    }
}
