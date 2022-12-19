package com.utkarshnew.android.helpChat.model;

import java.io.Serializable;
import java.util.List;

public class HelpSupportChatModel implements Serializable {


    private List<DataBean> data;


    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean implements Serializable {
        /**
         * id : 28
         * query_id :
         * user_id : 238
         * category : Query
         * title : hey
         * description : no quiry
         * file :
         * close_date : 0
         * time : 1601538392
         */

        private String id;
        private String query_id;
        private String user_id;
        private String category;
        private String title;
        private String description;
        private String file;
        private String close_date;
        private String time;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getQuery_id() {
            return query_id;
        }

        public void setQuery_id(String query_id) {
            this.query_id = query_id;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
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

        public String getFile() {
            return file;
        }

        public void setFile(String file) {
            this.file = file;
        }

        public String getClose_date() {
            return close_date;
        }

        public void setClose_date(String close_date) {
            this.close_date = close_date;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }
    }
}
