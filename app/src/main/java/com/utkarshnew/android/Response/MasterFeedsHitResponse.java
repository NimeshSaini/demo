package com.utkarshnew.android.Response;

import com.utkarshnew.android.home.model.bannerHomeData.BannerData;
import com.utkarshnew.android.Model.Course_subject_master;
import com.utkarshnew.android.Model.Courses.Course;
import com.utkarshnew.android.Model.People;
import com.utkarshnew.android.Model.Tags;
import com.utkarshnew.android.Model.Video;

import java.util.ArrayList;

/**
 * Created by Sagar on 05-01-2018.
 */

public class MasterFeedsHitResponse {

    private ArrayList<People> people_you_may_know_list;

    private ArrayList<BannerData> banner_list;

    private String duration;

    private String payment_gateways;

    private ArrayList<People> expert_list;

    private ArrayList<Tags> all_tags;

    private ArrayList<Video> suggested_videos;

    private ArrayList<Course> suggested_course;

    private ArrayList<Course_subject_master> course_subject_master;

    private ArrayList<People> course_type_master;

    public ArrayList<People> getCourse_type_master() {
        return course_type_master;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getPayment_gateways() {
        return payment_gateways;
    }

    public void setPayment_gateways(String payment_gateways) {
        this.payment_gateways = payment_gateways;
    }

    public void setCourse_type_master(ArrayList<People> course_type_master) {
        this.course_type_master = course_type_master;
    }

    public ArrayList<Course_subject_master> getCourse_subject_master() {
        return course_subject_master;
    }

    public void setCourse_subject_master(ArrayList<Course_subject_master> course_subject_master) {
        this.course_subject_master = course_subject_master;
    }

    public ArrayList<Course> getSuggested_course() {
        return suggested_course;
    }

    public void setSuggested_course(ArrayList<Course> suggested_course) {
        this.suggested_course = suggested_course;
    }

    public ArrayList<Video> getSuggested_videos() {
        return suggested_videos;
    }

    public void setSuggested_videos(ArrayList<Video> suggested_videos) {
        this.suggested_videos = suggested_videos;
    }

    public ArrayList<Tags> getAll_tags() {
        return all_tags;
    }

    public void setAll_tags(ArrayList<Tags> all_tags) {
        this.all_tags = all_tags;
    }

    public ArrayList<People> getPeople_you_may_know_list() {
        return people_you_may_know_list;
    }

    public void setPeople_you_may_know_list(ArrayList<People> people_you_may_know_list) {
        this.people_you_may_know_list = people_you_may_know_list;
    }

    public ArrayList<BannerData> getBanner_list() {
        return banner_list;
    }

    public void setBanner_list(ArrayList<BannerData> banner_list) {
        this.banner_list = banner_list;
    }

    public ArrayList<People> getExpert_list() {
        return expert_list;
    }

    public void setExpert_list(ArrayList<People> expert_list) {
        this.expert_list = expert_list;
    }

    @Override
    public String toString() {
        return "ClassPojo [people_you_may_know_list = " + people_you_may_know_list + ", banner_list = " + banner_list + ", expert_list = " + expert_list + "]";
    }
}
