package com.utkarshnew.android.Model.Courses;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by appsquadz on 12/10/17.
 */

public class Curriculam implements Serializable {
    private String title;

    private String topic_id;

    private ArrayList<File_meta> file_meta;


    public String getTopic_id() {
        return topic_id;
    }

    public void setTopic_id(String topic_id) {
        this.topic_id = topic_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ArrayList<File_meta> getFile_meta() {
        return file_meta;
    }

    public void setFile_meta(ArrayList<File_meta> file_meta) {
        this.file_meta = file_meta;
    }


    // TODO FILE DATA
    public class File_meta implements Serializable {
        private String count;

        private String title;

        private String description;

        private String file;

        private String link;

        private String id;

        private String is_user_attemp;

        private String video_type;
        private String is_bookmarked;
        private String image;
        private String tag = "12";
        private int viewType;
        private String isPurchased;

        public String getIsPurchased() {
            return isPurchased;
        }

        public void setIsPurchased(String isPurchased) {
            this.isPurchased = isPurchased;
        }

        public String getIs_bookmarked() {
            return is_bookmarked;
        }

        public int getViewType() {
            return viewType;
        }

        public void setViewType(int viewType) {
            this.viewType = viewType;
        }

        public String isIs_bookmarked() {
            return is_bookmarked;
        }

        public void setIs_bookmarked(String is_bookmarked) {
            this.is_bookmarked = is_bookmarked;
        }

        public String getIs_user_attemp() {
            return is_user_attemp;
        }

        public void setIs_user_attemp(String is_user_attemp) {
            this.is_user_attemp = is_user_attemp;
        }

        public String getVideo_type() {
            return video_type;
        }

        public void setVideo_type(String video_type) {
            this.video_type = video_type;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String isIs_user_attemp() {
            return is_user_attemp;
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

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }

        public String getCount() {
            return count;
        }

        public void setCount(String count) {
            this.count = count;
        }

        public String getTag() {
            return tag;
        }

        public void setTag(String tag) {
            this.tag = tag;
        }

    }
}
