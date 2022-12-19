package com.utkarshnew.android.home.model;

import android.os.Parcel;
import android.os.Parcelable;

public class CategoryProgressData implements Parcelable {
    private String id;
    private String name;
    private String progress;
    private CategoryData categoryData;

    public CategoryProgressData(String id, String name, String progress, CategoryData categoryData) {
        this.id = id;
        this.name = name;
        this.progress = progress;
        this.categoryData = categoryData;
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

    public String getProgress() {
        return progress;
    }

    public void setProgress(String progress) {
        this.progress = progress;
    }

    public CategoryData getCategoryData() {
        return categoryData;
    }

    public void setCategoryData(CategoryData categoryData) {
        this.categoryData = categoryData;
    }

    protected CategoryProgressData(Parcel in) {
        id = in.readString();
        name = in.readString();
        progress = in.readString();
        categoryData = (CategoryData) in.readValue(CategoryData.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(progress);
        dest.writeValue(categoryData);
    }

    @SuppressWarnings("unused")
    public static final Creator<CategoryProgressData> CREATOR = new Creator<CategoryProgressData>() {
        @Override
        public CategoryProgressData createFromParcel(Parcel in) {
            return new CategoryProgressData(in);
        }

        @Override
        public CategoryProgressData[] newArray(int size) {
            return new CategoryProgressData[size];
        }
    };
}

