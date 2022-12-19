package com.utkarshnew.android.Utils;

import android.os.Parcel;
import android.os.Parcelable;

public class CookieHelper implements Parcelable {

    private String expires;
    private String signature;
    private String keyPairId;

    public CookieHelper() {

    }

    public CookieHelper(Parcel in) {
        expires = in.readString();
        signature = in.readString();
        keyPairId = in.readString();
        isV2 = in.readByte() != 0;
    }

    public static final Creator<CookieHelper> CREATOR = new Creator<CookieHelper>() {
        @Override
        public CookieHelper createFromParcel(Parcel in) {
            return new CookieHelper(in);
        }

        @Override
        public CookieHelper[] newArray(int size) {
            return new CookieHelper[size];
        }
    };

    public boolean isV2() {
        return isV2;
    }

    public void setV2(boolean v2) {
        isV2 = v2;
    }

    private boolean isV2 = false;

    public String getExpires() {
        return expires;
    }

    public void setExpires(String expires) {
        this.expires = expires;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getKeyPairId() {
        return keyPairId;
    }

    public void setKeyPairId(String keyPairId) {
        this.keyPairId = keyPairId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(expires);
        dest.writeString(signature);
        dest.writeString(keyPairId);
        dest.writeByte((byte) (isV2 ? 1 : 0));
    }
}
