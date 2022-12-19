package com.utkarshnew.android.Model.Courses;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by appsquadz on 28/9/17.
 */

public class SingleCourseData implements Serializable {
    private String tags;

    private Reviews[] reviews;

    private String cover_image;

    private String cover_video;

    private String review_count;

    private String learner;

    private String mrp;

    private String state;

    private String id;

    private String title;

    private String non_dams;

    private String description;

    private String for_dams;

    private String rating;

    private String is_purchased;

    private String course_category_fk;

    private String publish;

    private Instructor instructor_data;

    private Curriculam curriculam;

    private Review review;

    private String desc_header_image;

    private ArrayList<Course> related_course;

    private String gst;

    private String points_conversion_rate;

    public String getPoints_conversion_rate() {
        return points_conversion_rate;
    }

    public void setPoints_conversion_rate(String points_conversion_rate) {
        this.points_conversion_rate = points_conversion_rate;
    }

    public String getGst() {
        return gst;
    }

    public void setGst(String gst) {
        this.gst = gst;
    }

    public String getDesc_header_image() {
        return desc_header_image;
    }

    public void setDesc_header_image(String desc_header_image) {
        this.desc_header_image = desc_header_image;
    }

    public String getCover_video() {
        return cover_video;
    }

    public void setCover_video(String cover_video) {
        this.cover_video = cover_video;
    }

    public ArrayList<Course> getRelated_course() {
        return related_course;
    }

    public void setRelated_course(ArrayList<Course> related_course) {
        this.related_course = related_course;
    }

    public Review getReview() {
        return review;
    }

    public void setReview(Review review) {
        this.review = review;
    }

    public String getIs_purchased() {
        return is_purchased;
    }

    public void setIs_purchased(String is_purchased) {
        this.is_purchased = is_purchased;
    }

    public Curriculam getCurriculam() {
        return curriculam;
    }

    public void setCurriculam(Curriculam curriculam) {
        this.curriculam = curriculam;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public Reviews[] getReviews() {
        return reviews;
    }

    public void setReviews(Reviews[] reviews) {
        this.reviews = reviews;
    }

    public String getCover_image() {
        return cover_image;
    }

    public void setCover_image(String cover_image) {
        this.cover_image = cover_image;
    }

    public String getReview_count() {
        return review_count;
    }

    public void setReview_count(String review_count) {
        this.review_count = review_count;
    }

    public String getLearner() {
        return learner;
    }

    public void setLearner(String learner) {
        this.learner = learner;
    }

    public String getMrp() {
        return mrp;
    }

    public void setMrp(String mrp) {
        this.mrp = mrp;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNon_dams() {
        return non_dams;
    }

    public void setNon_dams(String non_dams) {
        this.non_dams = non_dams;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFor_dams() {
        return for_dams;
    }

    public void setFor_dams(String for_dams) {
        this.for_dams = for_dams;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getCourse_category_fk() {
        return course_category_fk;
    }

    public void setCourse_category_fk(String course_category_fk) {
        this.course_category_fk = course_category_fk;
    }

    public String getPublish() {
        return publish;
    }

    public void setPublish(String publish) {
        this.publish = publish;
    }

    public Instructor getInstructor_data() {
        return instructor_data;
    }

    public void setInstructor_data(Instructor instructor_data) {
        this.instructor_data = instructor_data;
    }

}
