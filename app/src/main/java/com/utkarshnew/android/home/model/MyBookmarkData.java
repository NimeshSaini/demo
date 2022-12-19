package com.utkarshnew.android.home.model;

import android.os.Parcel;
import android.os.Parcelable;

public class MyBookmarkData implements Parcelable {
    private String id;
    private String name;
    private String image;
    private String by_name;
    private String date;
    private String detail;
    private String status;

    public MyBookmarkData(String id, String name, String image, String by_name, String date, String detail, String status) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.by_name = by_name;
        this.date = date;
        this.detail = detail;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getBy_name() {
        return by_name;
    }

    public void setBy_name(String by_name) {
        this.by_name = by_name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    protected MyBookmarkData(Parcel in) {
        id = in.readString();
        name = in.readString();
        image = in.readString();
        by_name = in.readString();
        date = in.readString();
        detail = in.readString();
        status = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(image);
        dest.writeString(by_name);
        dest.writeString(date);
        dest.writeString(detail);
        dest.writeString(status);
    }

    @SuppressWarnings("unused")
    public static final Creator<MyBookmarkData> CREATOR = new Creator<MyBookmarkData>() {
        @Override
        public MyBookmarkData createFromParcel(Parcel in) {
            return new MyBookmarkData(in);
        }

        @Override
        public MyBookmarkData[] newArray(int size) {
            return new MyBookmarkData[size];
        }
    };
}