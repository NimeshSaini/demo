package com.utkarshnew.android.home.model;

import android.os.Parcel;
import android.os.Parcelable;

public class MyOrderData implements Parcelable {
    private String id;
    private String name;
    private String image;
    private String by_name;
    private String date;
    private String detail;
    private String price;
    private String status;

    public MyOrderData(String id, String name, String image, String by_name, String date, String detail, String price, String status) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.by_name = by_name;
        this.date = date;
        this.detail = detail;
        this.price = price;
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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    protected MyOrderData(Parcel in) {
        id = in.readString();
        name = in.readString();
        image = in.readString();
        by_name = in.readString();
        date = in.readString();
        detail = in.readString();
        price = in.readString();
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
        dest.writeString(price);
        dest.writeString(status);
    }

    @SuppressWarnings("unused")
    public static final Creator<MyOrderData> CREATOR = new Creator<MyOrderData>() {
        @Override
        public MyOrderData createFromParcel(Parcel in) {
            return new MyOrderData(in);
        }

        @Override
        public MyOrderData[] newArray(int size) {
            return new MyOrderData[size];
        }
    };
}