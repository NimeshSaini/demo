package com.utkarshnew.android.Model.Courses;

import java.io.Serializable;

/**
 * Created by Cbc-03 on 09/04/17.
 */
public class Course implements Serializable {
    private String tags;

    private String cover_image;

    private String learner;

    private String mrp;

    private String state;

    private String currency;

    private String id;

    private String non_dams;

    private String title;

    private String description;

    private String for_dams;

    private String course_category_fk;

    private String publish;

    private String desc_header_image;

    private String rating;

    private Review review;

    private String segment_information;

    private String course_attribute;

    private String free_segment_count;

    private Element_meta[] element_meta;

    private String paid_segment_count;

    private String c_code;

    private String practice_id;

    public Course() {
    }

    public String getCourse_attribute() {
        return course_attribute;
    }

    public void setCourse_attribute(String course_attribute) {
        this.course_attribute = course_attribute;
    }

    public String getPractice_id() {
        return practice_id;
    }

    public void setPractice_id(String practice_id) {
        this.practice_id = practice_id;
    }

    public String getC_code() {
        return c_code;
    }

    public void setC_code(String c_code) {
        this.c_code = c_code;
    }

    public String getSegment_information() {
        return segment_information;
    }

    public void setSegment_information(String segment_information) {
        this.segment_information = segment_information;
    }

    public Element_meta[] getElement_meta() {
        return element_meta;
    }

    public void setElement_meta(Element_meta[] element_meta) {
        this.element_meta = element_meta;
    }

    public String getFree_segment_count() {
        return free_segment_count;
    }

    public void setFree_segment_count(String free_segment_count) {
        this.free_segment_count = free_segment_count;
    }

    public String getPaid_segment_count() {
        return paid_segment_count;
    }

    public void setPaid_segment_count(String paid_segment_count) {
        this.paid_segment_count = paid_segment_count;
    }

    public String getDesc_header_image() {
        return desc_header_image;
    }

    public void setDesc_header_image(String desc_header_image) {
        this.desc_header_image = desc_header_image;
    }

    public Review getReview() {
        return review;
    }

    public void setReview(Review review) {
        this.review = review;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getCover_image() {
        return cover_image;
    }

    public void setCover_image(String cover_image) {
        this.cover_image = cover_image;
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

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNon_dams() {
        return non_dams;
    }

    public void setNon_dams(String non_dams) {
        this.non_dams = non_dams;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }


    public class Element_meta implements Serializable {
        private String position;

        private String id;

        private String topic_name;

        private String own;
        private String image;

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getPosition() {
            return position;
        }

        public void setPosition(String position) {
            this.position = position;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTopic_name() {
            return topic_name;
        }

        public void setTopic_name(String topic_name) {
            this.topic_name = topic_name;
        }

        public String getOwn() {
            return own;
        }

        public void setOwn(String own) {
            this.own = own;
        }

        @Override
        public String toString() {
            return "ClassPojo [position = " + position + ", id = " + id + ", topic_name = " + ", image = " + image + ", own = " + own + "]";
        }
    }
}

