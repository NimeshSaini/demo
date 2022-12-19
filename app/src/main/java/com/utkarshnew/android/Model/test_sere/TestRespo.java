package com.utkarshnew.android.Model.test_sere;

import com.google.gson.annotations.SerializedName;


public class TestRespo {

    @SerializedName("data")
    private Data mData;
    @SerializedName("is_ios_price")
    private Long mIsIosPrice;

    public Data getData() {
        return mData;
    }

    public void setData(Data data) {
        mData = data;
    }

    public Long getIsIosPrice() {
        return mIsIosPrice;
    }

    public void setIsIosPrice(Long isIosPrice) {
        mIsIosPrice = isIosPrice;
    }

}
