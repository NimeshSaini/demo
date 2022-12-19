package com.utkarshnew.android.home.model;

import android.os.Parcel;
import android.os.Parcelable;

public class CategoryData implements Parcelable {
    private String title;
    private String one;
    private String one_data;
    private String two;
    private String two_data;
    private String three;
    private String three_data;
    private String four;
    private String four_data;
    private String five;
    private String five_data;

    public CategoryData(String title, String one, String one_data, String two, String two_data, String three, String three_data, String four, String four_data, String five, String five_data) {
        this.title = title;
        this.one = one;
        this.one_data = one_data;
        this.two = two;
        this.two_data = two_data;
        this.three = three;
        this.three_data = three_data;
        this.four = four;
        this.four_data = four_data;
        this.five = five;
        this.five_data = five_data;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOne() {
        return one;
    }

    public void setOne(String one) {
        this.one = one;
    }

    public String getOne_data() {
        return one_data;
    }

    public void setOne_data(String one_data) {
        this.one_data = one_data;
    }

    public String getTwo() {
        return two;
    }

    public void setTwo(String two) {
        this.two = two;
    }

    public String getTwo_data() {
        return two_data;
    }

    public void setTwo_data(String two_data) {
        this.two_data = two_data;
    }

    public String getThree() {
        return three;
    }

    public void setThree(String three) {
        this.three = three;
    }

    public String getThree_data() {
        return three_data;
    }

    public void setThree_data(String three_data) {
        this.three_data = three_data;
    }

    public String getFour() {
        return four;
    }

    public void setFour(String four) {
        this.four = four;
    }

    public String getFour_data() {
        return four_data;
    }

    public void setFour_data(String four_data) {
        this.four_data = four_data;
    }

    public String getFive() {
        return five;
    }

    public void setFive(String five) {
        this.five = five;
    }

    public String getFive_data() {
        return five_data;
    }

    public void setFive_data(String five_data) {
        this.five_data = five_data;
    }

    protected CategoryData(Parcel in) {
        title = in.readString();
        one = in.readString();
        one_data = in.readString();
        two = in.readString();
        two_data = in.readString();
        three = in.readString();
        three_data = in.readString();
        four = in.readString();
        four_data = in.readString();
        five = in.readString();
        five_data = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(one);
        dest.writeString(one_data);
        dest.writeString(two);
        dest.writeString(two_data);
        dest.writeString(three);
        dest.writeString(three_data);
        dest.writeString(four);
        dest.writeString(four_data);
        dest.writeString(five);
        dest.writeString(five_data);
    }

    @SuppressWarnings("unused")
    public static final Creator<CategoryData> CREATOR = new Creator<CategoryData>() {
        @Override
        public CategoryData createFromParcel(Parcel in) {
            return new CategoryData(in);
        }

        @Override
        public CategoryData[] newArray(int size) {
            return new CategoryData[size];
        }
    };
}
