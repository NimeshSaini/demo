package com.utkarshnew.android.pojo.Userinfo;

import java.util.ArrayList;

public class Data {
    static Data userprofile;
    private String creationTime;
    private String gender;
    private String deviceId;
    private String city;
    private String mobile;

    public String getHave_psw() {
        return have_psw;
    }

    public void setHave_psw(String have_psw) {
        this.have_psw = have_psw;
    }

    private String have_psw;

    public String getChat_block() {
        return chat_block;
    }

    public void setChat_block(String chat_block) {
        this.chat_block = chat_block;
    }

    private String chat_block;

    public void setCreationTime(String creationTime) {
        this.creationTime = creationTime;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public void setProfilePicture(String profile_picture) {
        this.profile_picture = profile_picture;
    }

    public void setcCode(String cCode) {
        this.cCode = cCode;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    private String profile_picture;
    private String cCode;
    private String name;
    private String id;
    private String state;
    private String lang;
    private String email;
    private String username;
    private String status;

    public ArrayList<Preferences> getPreferences() {
        return preferences;
    }

    public void setPreferences(ArrayList<Preferences> preferences) {
        this.preferences = preferences;
    }

    private ArrayList<Preferences> preferences;



    public String getCreationTime() {
        return creationTime;
    }

    public String getGender() {
        return gender;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public String getCity() {
        return city;
    }

    public String getMobile() {
        return mobile;
    }

    public String getCCode() {
        return cCode;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public String getState() {
        return state;
    }

    public String getLang() {
        return lang;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public String getStatus() {
        return status;
    }

    public String getProfilePicture() {
        return profile_picture;
    }

    public static Data getInstance() {
        if (userprofile == null) {
            userprofile = new Data();
        }
        return userprofile;
    }
    public  static  class Preferences{
        public String getMain_cat() {
            return main_cat;
        }

        public void setMain_cat(String main_cat) {
            this.main_cat = main_cat;
        }

        public String getSub_cat() {
            return sub_cat;
        }

        public void setSub_cat(String sub_cat) {
            this.sub_cat = sub_cat;
        }

        private String main_cat;

        private String sub_cat;
        private String master_type;

        public String getMaster_type() {
            return master_type;
        }

        public void setMaster_type(String master_type) {
            this.master_type = master_type;
        }
    }

}
