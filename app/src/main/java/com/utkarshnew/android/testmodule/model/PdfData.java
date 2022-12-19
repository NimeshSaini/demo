package com.utkarshnew.android.testmodule.model;


import java.io.Serializable;
import java.util.ArrayList;

public class PdfData implements Serializable {
    String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public PdfListData getData() {
        return data;
    }

    public void setData(PdfListData data) {
        this.data = data;
    }

    public String getIs_android_price() {
        return is_android_price;
    }

    public void setIs_android_price(String is_android_price) {
        this.is_android_price = is_android_price;
    }

    public ArrayList<Challenge_Report.Errors> getErrors() {
        return errors;
    }

    public void setErrors(ArrayList<Challenge_Report.Errors> errors) {
        this.errors = errors;
    }

    String message;
    PdfListData data;
    String is_android_price;
    ArrayList<Challenge_Report.Errors> errors;

    public class PdfListData implements Serializable {
        public String getLayer() {
            return layer;
        }

        public void setLayer(String layer) {
            this.layer = layer;
        }

        public ArrayList<PdfList> getList() {
            return list;
        }

        public void setList(ArrayList<PdfList> list) {
            this.list = list;
        }

        String layer;
        ArrayList<PdfList> list;

    }

    public class PdfList implements Serializable {
        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getFile_url() {
            return file_url;
        }

        public void setFile_url(String file_url) {
            this.file_url = file_url;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getThumbnail_url() {
            return thumbnail_url;
        }

        public void setThumbnail_url(String thumbnail_url) {
            this.thumbnail_url = thumbnail_url;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getFile_type() {
            return file_type;
        }

        public void setFile_type(String file_type) {
            this.file_type = file_type;
        }

        public String getVideo_type() {
            return video_type;
        }

        public void setVideo_type(String video_type) {
            this.video_type = video_type;
        }

        public String getIs_locked() {
            return is_locked;
        }

        public void setIs_locked(String is_locked) {
            this.is_locked = is_locked;
        }

        public String getIs_live() {
            return is_live;
        }

        public void setIs_live(String is_live) {
            this.is_live = is_live;
        }

        public String getChat_node() {
            return chat_node;
        }

        public void setChat_node(String chat_node) {
            this.chat_node = chat_node;
        }

        public String getLive_status() {
            return live_status;
        }

        public void setLive_status(String live_status) {
            this.live_status = live_status;
        }

        String id;
        String file_url;
        String token;
        String thumbnail_url;
        String title;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        String description;
        String file_type;
        String video_type;
        String is_locked;
        String is_live;
        String chat_node;
        String live_status;

    }


}
