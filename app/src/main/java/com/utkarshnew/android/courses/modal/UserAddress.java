
package com.utkarshnew.android.courses.modal;

import java.io.Serializable;

public class UserAddress implements Serializable {

    private String addressOne;
    private String addressTwo;
    private String town;
    private String pincode;
    private String state;
    private String phone;

    public UserAddress(String addressOne, String addressTwo, String town, String pincode, String state, String phone) {
        this.addressOne = addressOne;
        this.addressTwo = addressTwo;
        this.town = town;
        this.pincode = pincode;
        this.state = state;
        this.phone = phone;
    }

    public String getAddressOne() {
        return addressOne;
    }

    public void setAddressOne(String addressOne) {
        this.addressOne = addressOne;
    }

    public String getAddressTwo() {
        return addressTwo;
    }

    public void setAddressTwo(String addressTwo) {
        this.addressTwo = addressTwo;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
