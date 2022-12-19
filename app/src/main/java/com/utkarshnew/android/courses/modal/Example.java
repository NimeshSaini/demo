package com.utkarshnew.android.courses.modal;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Example {

    @SerializedName("quote")
    private String quote;
    @SerializedName("ranges")

    private List<Range> ranges = null;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @SerializedName("text")

    private String text;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @SerializedName("id")

    private String id;
    @SerializedName("uri")

    private String uri;
    @SerializedName("tags")

    private Object tags;
    @SerializedName("user")

    private String user;
    @SerializedName("permissions")

    private Permissions permissions;

    public String getQuote() {
        return quote;
    }

    public void setQuote(String quote) {
        this.quote = quote;
    }

    public List<Range> getRanges() {
        return ranges;
    }

    public void setRanges(List<Range> ranges) {
        this.ranges = ranges;
    }


    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public Object getTags() {
        return tags;
    }

    public void setTags(Object tags) {
        this.tags = tags;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public Permissions getPermissions() {
        return permissions;
    }

    public void setPermissions(Permissions permissions) {
        this.permissions = permissions;
    }

    public class Permissions {

        @SerializedName("read")

        private List<String> read = null;
        @SerializedName("admin")

        private List<String> admin = null;
        @SerializedName("update")

        private List<String> update = null;
        @SerializedName("delete")

        private List<String> delete = null;

        public List<String> getRead() {
            return read;
        }

        public void setRead(List<String> read) {
            this.read = read;
        }

        public List<String> getAdmin() {
            return admin;
        }

        public void setAdmin(List<String> admin) {
            this.admin = admin;
        }

        public List<String> getUpdate() {
            return update;
        }

        public void setUpdate(List<String> update) {
            this.update = update;
        }

        public List<String> getDelete() {
            return delete;
        }

        public void setDelete(List<String> delete) {
            this.delete = delete;
        }

    }

    public class Range {

        @SerializedName("endOffset")

        private Integer endOffset;
        @SerializedName("startOffset")

        private Integer startOffset;
        @SerializedName("start")

        private String start;
        @SerializedName("end")

        private String end;

        public Integer getEndOffset() {
            return endOffset;
        }

        public void setEndOffset(Integer endOffset) {
            this.endOffset = endOffset;
        }

        public Integer getStartOffset() {
            return startOffset;
        }

        public void setStartOffset(Integer startOffset) {
            this.startOffset = startOffset;
        }

        public String getStart() {
            return start;
        }

        public void setStart(String start) {
            this.start = start;
        }

        public String getEnd() {
            return end;
        }

        public void setEnd(String end) {
            this.end = end;
        }

    }


}




