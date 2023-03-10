package com.utkarshnew.android.Model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Cbc-03 on 05/23/17.
 */

public class User implements Serializable {

    static User userprofile;
    private String id;
    private String socialId;
    private String social_tokken;
    private String email;
    private String user_tokken;
    private String is_nimbus;
    private String device_type;
    private String name;
    private String creation_time;
    private String gender;
    private String is_social;
    private String is_course_register;
    private String profile_picture;
    private String social_type;
    private String device_tokken;
    private String password;
    private int expert_following;
    private String mobile;
    private String is_mentor;
    private String category;
    private String c_code;
    private String dams_tokken;
    private String followers_count;
    private String following_count;
    private String allow_posting;
    private String dams_username;
    private String is_moderate;
    private String post_count;
    private String otp;
    private String dams_password;
    private String is_expert;
    private String user_likes;
    private Registration user_registration_info;
    private boolean is_following;
    private String speciality;
    private String state;
    private String age;
    private String update_status;
    private ArrayList<AddressUser> address = null;
    private Leadsquared leadsquared;
    private String dristi_user_id = "";
    private String id_card_front = "";
    private String id_card_back = "";
    private String is_verified = "";

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    public static User newInstance() {
        userprofile = new User();
        return userprofile;
    }

    public static User copyInstance(User user) {
        userprofile = user;
        return userprofile;
    }

    public static User getInstance() {
        if (userprofile == null) {
            userprofile = new User();
        }
        return userprofile;
    }


    public String getUser_tokken() {
        return user_tokken;
    }

    public void setUser_tokken(String user_tokken) {
        this.user_tokken = user_tokken;
    }

    public String getIs_nimbus() {
        return is_nimbus;
    }

    public void setIs_nimbus(String is_nimbus) {
        this.is_nimbus = is_nimbus;
    }

    public String getUser_likes() {
        return user_likes;
    }

    public void setUser_likes(String user_likes) {
        this.user_likes = user_likes;
    }

    public boolean isIs_following() {
        return is_following;
    }

    public void setIs_following(boolean is_following) {
        this.is_following = is_following;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getIs_expert() {
        return is_expert;
    }

    public void setIs_expert(String is_expert) {
        this.is_expert = is_expert;
    }

    public int getExpert_following() {
        return expert_following;
    }

    public void setExpert_following(int expert_following) {
        this.expert_following = expert_following;
    }

    public String getC_code() {
        return c_code;
    }

    public void setC_code(String c_code) {
        this.c_code = c_code;
    }

    public String getIs_moderate() {
        return is_moderate;
    }

    public void setIs_moderate(String is_moderate) {
        this.is_moderate = is_moderate;
    }

    public String getDams_password() {
        return dams_password;
    }

    public void setDams_password(String dams_password) {
        this.dams_password = dams_password;
    }

    public String getDams_username() {
        return dams_username;
    }

    public void setDams_username(String dams_username) {
        this.dams_username = dams_username;
    }

    public String getPost_count() {
        return post_count;
    }

    public void setPost_count(String post_count) {
        this.post_count = post_count;
    }

    public Registration getUser_registration_info() {
        return user_registration_info;
    }

    public void setUser_registration_info(Registration user_registration_info) {
        this.user_registration_info = user_registration_info;
    }

    public boolean is_following() {
        return is_following;
    }

    public String getFollowers_count() {
        return followers_count;
    }

    public void setFollowers_count(String followers_count) {
        this.followers_count = followers_count;
    }

    public String getFollowing_count() {
        return following_count;
    }

    public void setFollowing_count(String following_count) {
        this.following_count = following_count;
    }

    public String getAllow_posting() {
        return allow_posting;
    }

    public void setAllow_posting(String allow_posting) {
        this.allow_posting = allow_posting;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public String getProfile_picture() {
        return profile_picture;
    }

    public void setProfile_picture(String profile_picture) {
        this.profile_picture = profile_picture;
    }

    public String getIs_course_register() {
        return is_course_register;
    }

    public void setIs_course_register(String is_course_register) {
        this.is_course_register = is_course_register;
    }

    public String getSocialId() {
        return socialId;
    }

    public void setSocialId(String socialId) {
        this.socialId = socialId;
    }

    public String getDams_tokken() {
        return dams_tokken;
    }

    public void setDams_tokken(String dams_tokken) {
        this.dams_tokken = dams_tokken;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSocial_tokken() {
        return social_tokken;
    }

    public void setSocial_tokken(String social_tokken) {
        this.social_tokken = social_tokken;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDevice_type() {
        return device_type;
    }

    public void setDevice_type(String device_type) {
        this.device_type = device_type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreation_time() {
        return creation_time;
    }

    public void setCreation_time(String creation_time) {
        this.creation_time = creation_time;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
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

    public String getDevice_tokken() {
        return device_tokken;
    }

    public void setDevice_tokken(String device_tokken) {
        this.device_tokken = device_tokken;
    }

    public String getIs_mentor() {
        return is_mentor;
    }

    public void setIs_mentor(String is_mentor) {
        this.is_mentor = is_mentor;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getUpdate_status() {
        return update_status;
    }

    public void setUpdate_status(String update_status) {
        this.update_status = update_status;
    }

    public ArrayList<AddressUser> getAddress() {
        return address;
    }

    public void setAddress(ArrayList<AddressUser> address) {
        this.address = address;
    }

    public Leadsquared getLeadsquared() {
        return leadsquared;
    }

    public void setLeadsquared(Leadsquared leadsquared) {
        this.leadsquared = leadsquared;
    }

    public String getDristi_user_id() {
        return dristi_user_id;
    }

    public void setDristi_user_id(String dristi_user_id) {
        this.dristi_user_id = dristi_user_id;
    }

    public String getId_card_front() {
        return id_card_front;
    }

    public void setId_card_front(String id_card_front) {
        this.id_card_front = id_card_front;
    }

    public String getId_card_back() {
        return id_card_back;
    }

    public void setId_card_back(String id_card_back) {
        this.id_card_back = id_card_back;
    }

    public String getIs_verified() {
        return is_verified;
    }

    public void setIs_verified(String is_verified) {
        this.is_verified = is_verified;
    }
}
