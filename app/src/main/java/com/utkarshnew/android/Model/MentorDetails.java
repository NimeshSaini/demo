/*-----------------------------------com.makemyexam.app.Model.MentorDetails.java-----------------------------------*/

package com.utkarshnew.android.Model;


import java.io.Serializable;

public class MentorDetails implements Serializable {

    private String getLike;
    private String getComment;
    private String getFollow;
    private String getQueries;
    private String getLikeTarget;
    private String getCommentTarget;
    private String getQueriesTarget;
    private String getFollowTarget;

    public String getGetLike() {
        return getLike;
    }

    public void setGetLike(String getLike) {
        this.getLike = getLike;
    }

    public String getGetComment() {
        return getComment;
    }

    public void setGetComment(String getComment) {
        this.getComment = getComment;
    }

    public String getGetFollow() {
        return getFollow;
    }

    public void setGetFollow(String getFollow) {
        this.getFollow = getFollow;
    }

    public String getGetQueries() {
        return getQueries;
    }

    public void setGetQueries(String getQueries) {
        this.getQueries = getQueries;
    }

    public String getGetLikeTarget() {
        return getLikeTarget;
    }

    public void setGetLikeTarget(String getLikeTarget) {
        this.getLikeTarget = getLikeTarget;
    }

    public String getGetCommentTarget() {
        return getCommentTarget;
    }

    public void setGetCommentTarget(String getCommentTarget) {
        this.getCommentTarget = getCommentTarget;
    }

    public String getGetQueriesTarget() {
        return getQueriesTarget;
    }

    public void setGetQueriesTarget(String getQueriesTarget) {
        this.getQueriesTarget = getQueriesTarget;
    }

    public String getGetFollowTarget() {
        return getFollowTarget;
    }

    public void setGetFollowTarget(String getFollowTarget) {
        this.getFollowTarget = getFollowTarget;
    }

}