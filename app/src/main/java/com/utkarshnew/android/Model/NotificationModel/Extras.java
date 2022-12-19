package com.utkarshnew.android.Model.NotificationModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Extras {

    @SerializedName("users_type")
    @Expose
    private String usersType;

    @SerializedName("image")
    @Expose
    private String image;


    public String getSub_cat() {
        return sub_cat;
    }

    public void setSub_cat(String sub_cat) {
        this.sub_cat = sub_cat;
    }

    public String getMain_cat() {
        return main_cat;
    }

    public void setMain_cat(String main_cat) {
        this.main_cat = main_cat;
    }

    public String getMaster_cat() {
        return master_cat;
    }

    public void setMaster_cat(String master_cat) {
        this.master_cat = master_cat;
    }

    @SerializedName("sub_cat")
    @Expose
    private String sub_cat;

    @SerializedName("main_cat")
    @Expose
    private String main_cat;

    @SerializedName("master_cat")
    @Expose
    private String master_cat;


    public String getImage_2() {
        return image_2;
    }

    public void setImage_2(String image_2) {
        this.image_2 = image_2;
    }

    @SerializedName("image_2")
    @Expose
    private String image_2;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getUrl() {
        return url;
    }
    public String getTiletype() {
        return tiletype;
    }

    public void setTiletype(String tiletype) {
        this.tiletype = tiletype;
    }

    @SerializedName("tile_type")
    @Expose
    private String tiletype;

    public void setUrl(String url) {
        this.url = url;
    }

    @SerializedName("url")
    @Expose
    private String url;

    @SerializedName("users_message")
    @Expose
    private String usersMessage;
    @SerializedName("device_type")
    @Expose
    private String deviceType;
    @SerializedName("notification_type")
    @Expose
    private String notificationType;
    @SerializedName("notification_text")
    @Expose
    private String notificationText;
    @SerializedName("admin_id")
    @Expose
    private String adminId;
    @SerializedName("url_type")
    @Expose
    private String urlType;

    @SerializedName("course_id")
    @Expose
    private String course_id;


    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    public String getTs() {
        return ts;
    }

    public void setTs(String ts) {
        this.ts = ts;
    }

    @SerializedName("start")
    @Expose
    private String start_date;

    @SerializedName("end")
    @Expose
    private String end_date;

    public String getCoupon_for() {
        return coupon_for;
    }

    public void setCoupon_for(String coupon_for) {
        this.coupon_for = coupon_for;
    }

    @SerializedName("coupon_for")
    @Expose
    private String coupon_for;


    @SerializedName("file_id")
    @Expose
    private String file_id;


    @SerializedName("topic_id")
    @Expose
    private String topic_id;


    public String getChat_node() {
        return chat_node;
    }

    public void setChat_node(String chat_node) {
        this.chat_node = chat_node;
    }

    @SerializedName("chat_node")
    @Expose
    private String chat_node;

    @SerializedName("ts")
    @Expose
    private String ts;


    public String getFile_url() {
        return file_url;
    }

    public void setFile_url(String file_url) {
        this.file_url = file_url;
    }

    @SerializedName("file_url")
    @Expose
    private String file_url;

    public String getCourse_id() {
        return course_id;
    }

    public void setCourse_id(String course_id) {
        this.course_id = course_id;
    }

    public String getFile_id() {
        return file_id;
    }

    public void setFile_id(String file_id) {
        this.file_id = file_id;
    }

    public String getTopic_id() {
        return topic_id;
    }

    public void setTopic_id(String topic_id) {
        this.topic_id = topic_id;
    }

    public String getTile_id() {
        return tile_id;
    }

    public void setTile_id(String tile_id) {
        this.tile_id = tile_id;
    }

    @SerializedName("tile_id")
    @Expose
    private String tile_id;
    @SerializedName("users_description")
    @Expose
    private String usersDescription;

    public String getUsersDescription() {
        return usersDescription;
    }

    public void setUsersDescription(String usersDescription) {
        this.usersDescription = usersDescription;
    }


    public String getUsersType() {
        return usersType;
    }

    public void setUsersType(String usersType) {
        this.usersType = usersType;
    }

    public String getUsersMessage() {
        return usersMessage;
    }

    public void setUsersMessage(String usersMessage) {
        this.usersMessage = usersMessage;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getNotificationType() {
        return notificationType;
    }

    public void setNotificationType(String notificationType) {
        this.notificationType = notificationType;
    }

    public String getNotificationText() {
        return notificationText;
    }

    public void setNotificationText(String notificationText) {
        this.notificationText = notificationText;
    }

    public String getAdminId() {
        return adminId;
    }

    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }

    public String getUrlType() {
        return urlType;
    }

    public void setUrlType(String urlType) {
        this.urlType = urlType;
    }
}

