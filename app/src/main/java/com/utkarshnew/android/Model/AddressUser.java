package com.utkarshnew.android.Model;

import java.io.Serializable;

public class AddressUser implements Serializable {
    private String id;
    private AddressData address;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public AddressData getAddress() {
        return address;
    }

    public void setAddress(AddressData address) {
        this.address = address;
    }
}
