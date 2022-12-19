package com.utkarshnew.android.Model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Cbc-03 on 06/02/17.
 */

public class Comment implements Serializable {
    private String id;

    private String profile_picture;

    public String getParent_id() {
        return parent_id;
    }

    public void setParent_id(String parent_id) {
        this.parent_id = parent_id;
    }

    private String parent_id;

    public String getSub_comment_count() {
        return sub_comment_count;
    }

    public void setSub_comment_count(String sub_comment_count) {
        this.sub_comment_count = sub_comment_count;
    }

    private String sub_comment_count;

    private String time;

    private String name;

    private String user_id;

    private String comment;

    private String post_id;

    public ArrayList<People> getTagged_people() {
        return tagged_people;
    }

    public void setTagged_people(ArrayList<People> tagged_people) {
        this.tagged_people = tagged_people;
    }

    private ArrayList<People> tagged_people;

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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getPost_id() {
        return post_id;
    }

    public void setPost_id(String post_id) {
        this.post_id = post_id;
    }

}
